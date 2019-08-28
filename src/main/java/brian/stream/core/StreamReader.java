package brian.stream.core;

import java.io.Closeable;
import java.io.IOException;

public interface StreamReader<T> extends Closeable {
	
    T read() throws IOException;

}