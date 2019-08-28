package brian.stream.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import brian.stream.core.impl.ChannelStreamProcessorFactory;
import brian.stream.core.impl.StreamProcessorConfig;

public class DemoTest {

	private static final String OUTPUT_FILE_ONE = "target/outputOne.txt";
	private static final String OUTPUT_FILE_TWO = "target/outputTwo.txt";

	@Before
	public void before() throws IOException {
		
		try {
			Files.createDirectory(Paths.get("target"));
		} catch(Exception e) {}
		
		Files.deleteIfExists(Paths.get(OUTPUT_FILE_ONE));
		Files.deleteIfExists(Paths.get(OUTPUT_FILE_TWO));
	}
	
	@Test
	public void test() throws FileNotFoundException, Exception {
		
		StreamProcessorConfig config = StreamProcessorConfig
			.builder()
			.withOutputFileOne(OUTPUT_FILE_ONE)
			.withOutputFileTwo(OUTPUT_FILE_TWO)
			.build();
		StreamProcessorFactory processorFactory = new ChannelStreamProcessorFactory(config);
		processorFactory.getInstance().process(ClassLoader.getSystemResourceAsStream("testData.txt"));
	}

}
