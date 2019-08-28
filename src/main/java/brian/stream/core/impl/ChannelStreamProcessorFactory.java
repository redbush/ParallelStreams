package brian.stream.core.impl;

import java.util.LinkedList;
import java.util.List;

import brian.stream.core.AsyncChannel;
import brian.stream.core.StreamProcessor;
import brian.stream.core.StreamProcessorFactory;
import brian.stream.core.UniqueByteBuffer;

public class ChannelStreamProcessorFactory implements StreamProcessorFactory {

	private final StreamProcessorConfig config;
	
	public ChannelStreamProcessorFactory(final StreamProcessorConfig configIn) {
		config = configIn;
	}
	
    @Override
    public StreamProcessor getInstance() {

    	List<AsyncChannel<UniqueByteBuffer>> asyncChannels = new LinkedList<>();
        asyncChannels.add(new OutputChannelOne(config.getOutputFileOne()));
        asyncChannels.add(new OutputChannelTwo(config.getOutputFileTwo()));
        return new ChannelStreamProcessor<>(new ChannelStreamReaderFactory(), asyncChannels);

    }

}