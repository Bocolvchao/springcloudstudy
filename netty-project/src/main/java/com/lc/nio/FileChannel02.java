package com.lc.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannel02 {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\data\\a.txt");
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel channel = inputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        channel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
    }
}
