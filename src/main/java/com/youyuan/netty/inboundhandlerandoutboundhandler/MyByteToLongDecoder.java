package com.youyuan.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author zhangy
 * @version 1.0
 * @description 通过自定义编码解码器，讲解Handler链表中Handler的调用顺序
 * <p>
 * 自定义解码器
 * @date 2019/12/18 10:18
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     * 解码
     *
     * @param ctx 上下文
     * @param in  入栈的in
     * @param out 读取内容放到List交给下个Handler处理
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder decode被调用");
        //因为传递的long,long是占用8字节，所以需要下面判断
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
