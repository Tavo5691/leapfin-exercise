package com.leapfin.processor;

public interface DataStream {

    byte[] read(final int numberOfBytes);

    long getBytesRead();
}
