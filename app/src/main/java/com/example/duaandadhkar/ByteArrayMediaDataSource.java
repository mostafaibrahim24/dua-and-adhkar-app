package com.example.duaandadhkar;

import android.media.MediaDataSource;

import java.io.IOException;

public class ByteArrayMediaDataSource extends MediaDataSource {
    private final byte[] data;

    public ByteArrayMediaDataSource(byte[] data) {
        this.data = data;
    }

    @Override
    public int readAt(long position, byte[] buffer, int offset, int size) throws IOException {
        if (position >= data.length) {
            return -1;
        }
        int length = (int) Math.min(size, data.length - position);
        System.arraycopy(data, (int) position, buffer, offset, length);
        return length;
    }

    @Override
    public long getSize() throws IOException {
        return data.length;
    }

    @Override
    public void close() throws IOException {
        // Nothing to do here
    }
}

