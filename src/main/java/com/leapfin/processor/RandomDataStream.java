package com.leapfin.processor;

import java.util.Random;

public class RandomDataStream implements DataStream {

    private final Random random;
    private long bytesRead;

    public RandomDataStream() {
        this.random = new Random();
    }

    @Override
    public byte[] read(final int numberOfBytes) {
        byte[] data = new byte[numberOfBytes];
        random.nextBytes(data);

        bytesRead += numberOfBytes;

        return data;
    }

    @Override
    public long getBytesRead() {
        return bytesRead;
    }
}
