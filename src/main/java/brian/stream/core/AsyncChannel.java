package brian.stream.core;

import java.util.concurrent.Callable;

public interface AsyncChannel<T> extends Callable<Void> {

    String getId();

    void write(final T data);

    boolean isFailure();

    boolean isCritical();

    void close();

}
