package brian.stream.core.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import brian.stream.core.AsyncChannel;

public class ChannelContext<T> {

	private final AsyncChannel<T> asyncChannel;
	private final Future<Void> future;

	public ChannelContext(final AsyncChannel<T> asyncChannelIn, final Future<Void> futureIn) {

		asyncChannel = asyncChannelIn;
		future = futureIn;
	}

	public String getId() {
		return asyncChannel.getId();
	}

	public Void get() throws InterruptedException, ExecutionException {
		return future.get();
	}

	public void cancel() {
		future.cancel(true);
	}

	public void close() {
		asyncChannel.close();
	}

	public boolean isFailure() {
		return asyncChannel.isFailure();
	}

	public boolean isCritical() {
		return asyncChannel.isCritical();
	}

	public void write(T data) {
		asyncChannel.write(data);
	}

}