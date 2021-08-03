package com.leapfin.search;

import com.leapfin.processor.DataStream;

import java.nio.charset.StandardCharsets;

public class TestDataStream implements DataStream {
    private final String data;
    private long bytesRead;

    public TestDataStream(final String baseString) {
        this.data = baseString;
        this.bytesRead = 0;
    }

    @Override
    public byte[] read(final int numberOfBytes) {
        bytesRead += numberOfBytes;

        return data.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public long getBytesRead() {
        return bytesRead;
    }

}
