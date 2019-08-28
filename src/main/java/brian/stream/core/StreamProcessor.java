package brian.stream.core;

import java.io.InputStream;

public interface StreamProcessor {

	void process(final InputStream inputStream) throws Exception;
	
}
