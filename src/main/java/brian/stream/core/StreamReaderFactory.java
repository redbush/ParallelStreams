package brian.stream.core;

import java.io.InputStream;

public interface StreamReaderFactory<T> {

    StreamReader<T> getInstance(final InputStream inputStream);

}
