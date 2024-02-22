package com.lc.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServe {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            if (selector.select(2000) == 0) {
                System.out.println("服务器等待连接");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    SocketChannel accept = serverSocketChannel.accept();
                    accept.configureBlocking(false);
                    accept.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (next.isReadable()) {
                    SocketChannel channel = (SocketChannel) next.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) next.attachment();
                    channel.read(byteBuffer);
                    System.out.println("收到信息" + new String(byteBuffer.array()));
                }
                iterator.remove();
            }

        }
    }
}
