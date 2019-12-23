package com.youyuan.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义编码解码器，讲解Handler链表中的
 * <p>
 * 自定义事件处理器
 * @date 2019/12/18 10:22
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("MyServerHandler channelRead0 被调用");
        System.out.println("客户端" + ctx.channel().remoteAddress().toString().substring(1) + "发送消息" + msg);

        //向客户端写回消息
        ctx.writeAndFlush(98765L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }
}
