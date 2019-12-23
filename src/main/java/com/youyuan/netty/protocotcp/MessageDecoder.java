package com.youyuan.netty.protocotcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义解码器
 * @date 2019/12/20 12:09
 */
public class MessageDecoder extends ReplayingDecoder<MessageProtoco> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //获取发送数据长度
        int len = in.readInt();
        //获取发送数据
        byte[] buffer = new byte[len];
        in.readBytes(buffer);
        //写入到list集合
        out.add(new MessageProtoco(len, buffer));
    }
}
