package com.youyuan.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试ReplayingDecoder，在解码的时候不用判断数据的长度
 * @date 2019/12/20 8:29
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder2 被调用，使用的是ReplayingDecoder");
        //ReplayingDecoder在解码的时候不用判断数据的长度
        out.add(in.readLong());
    }
}
