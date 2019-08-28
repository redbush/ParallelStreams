package brian.stream.core;

import java.io.IOException;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;

public abstract class AbstractAsyncChannel implements AsyncChannel<UniqueByteBuffer> {

    private boolean error = false;
    private final SinkChannel sink;
    private final SourceChannel source;
    private final Object statusLock = new Object();

    public AbstractAsyncChannel() {

        try {
            Pipe pipe = Pipe.open();
            sink = pipe.sink();
            source = pipe.source();
        } catch (IOException e) {
            throw new IllegalStateException("An error occurred constructing async channel");
        }
    }

    @Override
    public final void write(final UniqueByteBuffer byteBuffer) {

        try {
            sink.write(byteBuffer.get());
        } catch (Exception doNothing) {}
    }



    @Override
    public final boolean isFailure() {

        synchronized (statusLock) {
            return error;
        }
    }

    @Override
    public final void close() {

        try {
            sink.close();
        } catch (Exception doNothing) {}
    }

    @Override
    public final Void call() throws Exception {

        try {
            doCall(source);
        } catch (Exception e) {

            synchronized (statusLock) {
                error = true;
            }
            throw e;
        } finally {

            try {
                sink.close();
                source.close();
            } catch (Exception doNothing) {}
        }

        return null;
    }

    protected abstract void doCall(final SourceChannel channel) throws Exception;

}
