package brian.stream.core.impl;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import brian.stream.core.AbstractAsyncChannel;

public class OutputChannelOne extends AbstractAsyncChannel {

	private final String outputFile;
	
	public OutputChannelOne(final String outputFileIn) {
		outputFile = outputFileIn;
	}
	
	@Override
	protected void doCall(final SourceChannel channel) throws Exception {

		ByteBuffer buffer = ByteBuffer.allocate(8192);
		try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(Paths.get(outputFile),
				StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {

			while (channel.read(buffer) != -1) {
				buffer.flip();
				fileChannel.write(buffer);
				buffer.clear();
			}

		} catch (Exception e) {}
	}

	@Override
	public boolean isCritical() {
		return false;
	}

	@Override
	public String getId() {
		return "OUTPUT_CHANNEL_ONE";
	}

}
