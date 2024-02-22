package com.lc.nio.netty.nettygroupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GroupChatServer {

    private Integer port;

    public GroupChatServer(Integer port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //使用链式编程来进行设置
            serverBootstrap.group(bossGroup, workerGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class)  //使用NioSocketChannel 作为服务器的通道实现(bossGroup)
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        // (workerGroup)
                        protected void initChannel(SocketChannel socketChannel) throws Exception {// //创建一个通道初始化对象(匿名对象)
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 解码
                            pipeline.addLast("decoder", new StringDecoder());
                            // 编码
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast("myHandler", new GroupChatServerHandler());
                        }
                    });
            System.out.println("服务器已准备");
            ////绑定一个端口并且同步生成了一个 ChannelFuture 对象（也就是立马返回这样一个对象）
            //            //启动服务器(并绑定端口)
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            // //对关闭通道事件  进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new GroupChatServer(8000).start();
    }
}
