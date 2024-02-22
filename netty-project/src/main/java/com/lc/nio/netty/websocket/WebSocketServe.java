package com.lc.nio.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketServe {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 基于http协议，使用http的编码和解码
                            pipeline.addLast("httpServerCodec", new HttpServerCodec());
                            // 以块形式写
                            // ，添加chunkedwrited
                            pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
                            /**
                             * 1. http传输过程是分段的，HttpObjectAggregator是可以将多段集合
                             * 2.浏览器发送大量数据的时候，就会发送多次http请求
                             */
                            pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(8192));
                            /**
                             * 1.对应websocket，数据是以帧（frame）形式传递
                             * 2.WebSocketFrame下面有6个对应子类
                             * 3.浏览器请求时：ws/loaclhost:7000/hello
                             * 4.WebSocketServerProtocolHandler核心功能是将http协议升级为ws协议，长链接
                             */
                            pipeline.addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler("/ws"));

                            /**
                             * 自定义handle，处理业务逻辑
                             */
                            pipeline.addLast("myTextWebS ocketFrameHandler", new MyTextWebSocketFrameHandler());

                        }
                    });

            System.out.println("服务器已准备");
            ////绑定一个端口并且同步生成了一个 ChannelFuture 对象（也就是立马返回这样一个对象）
            //            //启动服务器(并绑定端口)
            ChannelFuture channelFuture = serverBootstrap.bind(8333).sync();
            // //对关闭通道事件  进行监听
            channelFuture.channel().closeFuture().sync();


        } catch (Exception e) {
            log.error(e.getMessage());

        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
