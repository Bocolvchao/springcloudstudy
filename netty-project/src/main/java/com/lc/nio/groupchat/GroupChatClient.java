package com.lc.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChatClient {

    private String ip = "127.0.0.1";
    private Integer port = 6666;
    private SocketChannel socketChannel;
    private Selector selector;
    private String userIp;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(ip, port));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            userIp = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(userIp + "准备就绪");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatClient chatClient = new GroupChatClient();
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    chatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            chatClient.sendInfo(message);
        }


    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void sendInfo(String message) {
        message = userIp + "说:" + message;
        try {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户端接收消息
     */
    public void readInfo() {
        try {
            int count = selector.select();
            if (count > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel readChannel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        readChannel.read(byteBuffer);
                        System.out.println("接收消息:" + new String(byteBuffer.array()));

                    }
                    iterator.remove();
                }

            } else {
//                System.out.println("没有可用通道");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
