package com.lc.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 6666);
        if (!socketChannel.connect(socketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("未连接");
            }
        }
        String mes = "hello,world";
        ByteBuffer byteBuffer = ByteBuffer.wrap(mes.getBytes());
        socketChannel.write(byteBuffer);
        System.in.read();
    }
}
