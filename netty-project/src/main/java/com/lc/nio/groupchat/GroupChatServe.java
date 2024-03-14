package com.lc.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServe {

    public Selector selector;

    public ServerSocketChannel serverSocketChannel;

    public GroupChatServe() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(6666));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        GroupChatServe chatServe = new GroupChatServe();
        chatServe.listen();
    }

    public void listen() {
        try {
            while (true) {
                int num = selector.select();
                if (num > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()) {
                            SocketChannel accept = serverSocketChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector, SelectionKey.OP_READ);
                            System.out.println("有" + accept.getRemoteAddress() + "用户上线了");
                        }
                        // 读取内容
                        if (selectionKey.isReadable()) {
                            readData(selectionKey);
                        }

                        // 删除，防止重复操作
                        iterator.remove();
                    }
                } else {
//                    System.out.println("等待……");
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务器给其他客户端发送消息
     *
     * @param meg
     * @param socketChannel
     */
    public void sendMessage(String meg, SocketChannel socketChannel) {
        try {
            System.out.println("服务器转发消息中");
            ByteBuffer wrap = ByteBuffer.wrap(meg.getBytes());
            Set<SelectionKey> selectionKeys = selector.keys();
            selectionKeys.forEach(item -> {
                Channel channel = item.channel();
                if (channel instanceof SocketChannel && channel != socketChannel) {
                    SocketChannel target = (SocketChannel) channel;
                    try {
                        target.write(wrap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取数据
     *
     * @param selectionKey
     */
    private void readData(SelectionKey selectionKey) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
            int count = channel.read(byteBuffer);
            if (count > 0) {
                String message = new String(byteBuffer.array());
                System.out.println("from 客户端:" + message);
                sendMessage(message, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                // 取消注册
                selectionKey.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
