package com.lc.nio.netty.nettygroupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class GroupChatClient {

    private static String ip;

    private static Integer port;

    public GroupChatClient(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public static void start() {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    // 解码
                    pipeline.addLast("decoder", new StringDecoder());
                    // 编码
                    pipeline.addLast("encoder", new StringEncoder());
                    pipeline.addLast("myGroup", new GroupChatClientHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            // 得到channel
            Channel channel = channelFuture.channel();
            System.out.println("----------" + channel.localAddress() + "----------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                channel.writeAndFlush(message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new GroupChatClient("127.0.0.1", 7000).start();
    }
}
