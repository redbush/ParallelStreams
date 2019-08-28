package brian.stream.core;

import java.nio.ByteBuffer;

public class UniqueByteBuffer {

    private final ByteBuffer clonedByteBuffer;

    public UniqueByteBuffer(final ByteBuffer byteBuffer) {
        clonedByteBuffer = cloneBuffer(byteBuffer);
    }

    public static UniqueByteBuffer newBuffer(final ByteBuffer byteBuffer) {
        return new UniqueByteBuffer(byteBuffer);
    }

    public ByteBuffer get() {
        return clonedByteBuffer.duplicate();
    }

    private ByteBuffer cloneBuffer(final ByteBuffer buffer) {

        ByteBuffer clone = ByteBuffer.allocate(buffer.capacity());
        clone.put(buffer);
        clone.flip();
        buffer.rewind();
        return clone;
    }

}
