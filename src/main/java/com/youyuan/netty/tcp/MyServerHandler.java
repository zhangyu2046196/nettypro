package com.youyuan.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义事件处理器  处理业务逻辑
 * @date 2019/12/20 10:48
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        System.out.println("这是第" + (++this.count) + "次接收客户端数据" + new String(buffer, CharsetUtil.UTF_8));

        String res = UUID.randomUUID().toString()+"\n";
        ctx.writeAndFlush(Unpooled.copiedBuffer(res.toString(), CharsetUtil.UTF_8));  //向客户端回写数据
    }

    /**
     * 处理异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();  //关闭通道
    }
}
