package brian.stream.core.impl;

import java.io.InputStream;

import brian.stream.core.StreamReader;
import brian.stream.core.StreamReaderFactory;
import brian.stream.core.UniqueByteBuffer;

public class ChannelStreamReaderFactory implements StreamReaderFactory<UniqueByteBuffer> {

	@Override
	public StreamReader<UniqueByteBuffer> getInstance(final InputStream inputStream) {
		return new ChannelStreamReader(inputStream);
	}

}
