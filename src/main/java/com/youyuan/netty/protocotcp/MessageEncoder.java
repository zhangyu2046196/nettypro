package com.youyuan.netty.protocotcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义编码器
 * @date 2019/12/20 12:22
 */
public class MessageEncoder extends MessageToByteEncoder<MessageProtoco> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtoco msg, ByteBuf out) throws Exception {
        //获取传输数据长度
        int len = msg.getLen();
        //获取传输数据
        byte[] buffer = msg.getBytes();
        //写入ByteBuf
        out.writeInt(len);
        out.writeBytes(buffer);
    }
}
