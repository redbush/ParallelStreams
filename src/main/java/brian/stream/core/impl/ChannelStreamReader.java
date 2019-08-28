package brian.stream.core.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import brian.stream.core.StreamReader;
import brian.stream.core.UniqueByteBuffer;

public class ChannelStreamReader implements StreamReader<UniqueByteBuffer> {

	private final ByteBuffer buffer = ByteBuffer.allocate(8192);
	private final ReadableByteChannel channel;

	public ChannelStreamReader(final InputStream inputStream) {
		channel = Channels.newChannel(inputStream);
	}

	public static ChannelStreamReader newReader(InputStream inputStream) {
		return new ChannelStreamReader(inputStream);
	}

	@Override
	public void close() throws IOException {
		channel.close();
	}

	@Override
	public UniqueByteBuffer read() throws IOException {

		buffer.clear();
		int count = channel.read(buffer);
		if (count != -1) {
			buffer.flip();
			return UniqueByteBuffer.newBuffer(buffer);
		}

		return null;
	}

}
