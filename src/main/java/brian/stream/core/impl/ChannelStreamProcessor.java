package brian.stream.core.impl;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import brian.stream.core.AsyncChannel;
import brian.stream.core.StreamProcessor;
import brian.stream.core.StreamReader;
import brian.stream.core.StreamReaderFactory;

public class ChannelStreamProcessor<T> implements StreamProcessor {

	private final StreamReaderFactory<T> streamReader;
	private final List<AsyncChannel<T>> asyncChannels;
	private final ExecutorService threadPool;

	public ChannelStreamProcessor(final StreamReaderFactory<T> streamReaderIn,
			final List<AsyncChannel<T>> asyncChannelsIn) {

		streamReader = streamReaderIn;
		asyncChannels = asyncChannelsIn;
		threadPool = Executors.newFixedThreadPool(asyncChannels.size());
	}

	@Override
	public void process(final InputStream inputStream) throws Exception {

		try {

			List<ChannelContext<T>> channelContexts = new LinkedList<>();
			for (AsyncChannel<T> asyncChannel : asyncChannels) {
				channelContexts.add(new ChannelContext<>(asyncChannel, threadPool.submit(asyncChannel)));
			}

			try (StreamReader<T> reader = streamReader.getInstance(inputStream)) {

				T data = null;
				while ((data = reader.read()) != null) {

					for (ChannelContext<T> channelContext : channelContexts) {
						// what if main channel interrupted
						if (channelContext.isFailure()) {
							if (channelContext.isCritical()) {
								cancelOtherChannels(channelContexts, channelContext);
								throw new Exception("critical");
							}
						} else {
							channelContext.write(data);
						}
					}
				}

			} catch (Exception e) {
				System.out.println("Error: " + e.getClass());
			} finally {
				closeChannels(channelContexts);
			}

			waitAndLogResults(channelContexts);
		} finally {
			threadPool.shutdownNow();
		}
	}

	private void cancelOtherChannels(final List<ChannelContext<T>> channelContexts,
			final ChannelContext<T> contextToExclude) {

		channelContexts.stream().filter(channelContext -> {
			return !channelContext.getId().equals(contextToExclude.getId());
		}).forEach(channelContext -> {
			channelContext.cancel();
		});
	}

	private void closeChannels(final List<ChannelContext<T>> channelContexts) {

		for (ChannelContext<T> channelContext : channelContexts) {
			channelContext.close();
		}
	}

	private void waitAndLogResults(final List<ChannelContext<T>> channelContexts) {

		for (ChannelContext<T> channelContext : channelContexts) {

			try {
				channelContext.get();
				System.out.println("Channel " + channelContext.getId() + " stopped.");
			} catch (Exception e) {
				System.out.println("Channel " + channelContext.getId() + " stopped with error: " + e.getClass());
			}
		}
	}

}
