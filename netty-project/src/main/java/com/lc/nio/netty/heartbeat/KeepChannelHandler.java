package com.lc.nio.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class KeepChannelHandler extends ChannelInboundHandlerAdapter {
    /**
     * 心跳
     *
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 将evt向下转型
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String evenType = null;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    evenType = "读空闲";
                    break;
                case WRITER_IDLE:
                    evenType = "写空闲";
                    break;
                case ALL_IDLE:
                    evenType = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "---超时时间---" + evenType);
        }
    }
}
