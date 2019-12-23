package com.youyuan.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author zhangy
 * @version 1.0
 * @description  测试Unpooled和ByteBuf
 *
 * Unpooled是操作内存buffer的工具类
 *
 * @date 2019/12/16 16:54
 */
public class ByteBuf02 {

    public static void main(String[] args) {
        //创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world", CharsetUtil.UTF_8);

        System.out.println("readerIndex:" + byteBuf.readerIndex());
        System.out.println("writerIndex:" + byteBuf.writerIndex());
        System.out.println("capacity:" + byteBuf.capacity());
    }

}
