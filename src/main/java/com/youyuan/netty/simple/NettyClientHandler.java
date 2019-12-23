package com.youyuan.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义通道处理器, 需要继承HandlerAdapter适配器
 * @date 2019/12/13 8:46
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 建立连接请求并发送消息给服务端
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端上下文ctx:" + ctx);
        //发送消息给服务端  写入缓存并刷新
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("hello,服务端,喵", CharsetUtil.UTF_8));
    }

    /**
     * 客户端接收服务端返回消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //把服务端返回消息msg转成ByteBuf
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("接收到服务端返回消息:" + byteBuf.toString(CharsetUtil.UTF_8) + " 服务端地址：" + ctx.channel().remoteAddress().toString().substring(1));
    }
}
