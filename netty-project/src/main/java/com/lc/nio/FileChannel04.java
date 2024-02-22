package com.lc.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class FileChannel04 {

    public static void main(String[] args) throws Exception {
        //创建相关流
        FileInputStream fileInputStream = new FileInputStream("netty-project/11.png");
        FileOutputStream fileOutputStream = new FileOutputStream("netty-project/112.png");

        //获取各个流对应的 FileChannel
        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();

        //使用 transferForm 完成拷贝
        destCh.transferFrom(sourceCh, 0, sourceCh.size());

        //关闭相关通道和流
        sourceCh.close();
        destCh.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
