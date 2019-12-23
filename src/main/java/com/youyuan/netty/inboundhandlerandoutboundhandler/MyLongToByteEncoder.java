package com.youyuan.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义编码解码器，讲解Handler链表中Handler执行顺序
 * <p>
 * 自定义编码器
 * @date 2019/12/18 10:44
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder encode被调用");
        out.writeLong(msg);
    }
}
