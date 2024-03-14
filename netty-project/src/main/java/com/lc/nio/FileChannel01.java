package com.lc.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannel01 {

    public static void main(String[] args) throws Exception {
        String message = "hellworld";

        FileOutputStream outputStream = new FileOutputStream("D:\\data\\a.txt");
        FileChannel channel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
        byteBuffer.put(message.getBytes());

        byteBuffer.flip();

        channel.write(byteBuffer);
        outputStream.close();

    }
}
