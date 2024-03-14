package com.lc.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannel03 {

    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream("netty-project/1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("netty-project/2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true) { //循环读取
            //这里有一个重要的操作，一定不要忘了
            /*
            public final Buffer clear() {
                position = 0;
                limit = capacity;
                mark = -1;
                return this;
            }
            public final Buffer flip() {
                limit = position;
                position = 0;
                mark = -1;
                return this;
            }

            */
            byteBuffer.clear(); //清空 buffer
            int read = fileChannel01.read(byteBuffer);
            System.out.println("read = " + read);
            if (read == -1) { //表示读完
                break;
            }
            //将 buffer 中的数据写入到 fileChannel02--2.txt
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }

        //关闭相关的流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
