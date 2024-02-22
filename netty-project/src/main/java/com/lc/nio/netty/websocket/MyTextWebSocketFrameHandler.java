package com.lc.nio.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;


/**
 * TextWebSocketFrame表示文本帧
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    /**
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        // id表示唯一的值，LongText表示唯一的，short表示不唯一
        System.out.println("handlerAdded被调用了" + ctx.channel().id().asLongText());
        System.out.println("handlerAdded被调用了" + ctx.channel().id().asShortText());

        ctx.channel();
    }

    /**
     * 接收消息
     *
     * @param channelHandlerContext
     * @param textWebSocketFrame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        System.out.println("接收到文本新消息" + textWebSocketFrame.text());

        channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(textWebSocketFrame.text() + "服务器时间" + LocalDateTime.now()));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // id表示唯一的值，LongText表示唯一的，short表示不唯一
        System.out.println("handlerRemoved被调用了" + ctx.channel().id().asLongText());
        System.out.println("handlerRemoved被调用了" + ctx.channel().id().asShortText());
    }
}
