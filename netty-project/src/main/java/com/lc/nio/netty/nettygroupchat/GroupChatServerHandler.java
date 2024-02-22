package com.lc.nio.netty.nettygroupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("【客户端】" + channel.remoteAddress() + "离开了");
        System.out.println("在线客户数" + channelGroup.size());
    }

    /**
     * 活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线了");
    }

    /**
     * 非活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "下线了");
    }

    /**
     * 连接建立，第一个执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 遍历所有的channel 并且发送信息
        channelGroup.writeAndFlush("【客户端】" + channel.remoteAddress() + "已经加入");
        channelGroup.add(channel);
    }

    /**
     * @param channelHandlerContext
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channelGroup.forEach(e -> {
            if (channel != e) {
                e.writeAndFlush("【客户端】" + channel.remoteAddress() + "发送了消息：" + msg);
            } else {
                channel.writeAndFlush("【自己发送了消息】" + msg);
            }
        });
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
