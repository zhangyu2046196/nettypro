package com.youyuan.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description 通过自定义编码解码器，讲解Handler链表中Handler的调用顺序
 * <p>
 * 自定义Initializer
 * @date 2019/12/18 10:15
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //写入数据是出站(从Pipeline链表尾部读取执行),读取数据是入栈(从Pipeline头部读取)
        //获取Pipeline
        ChannelPipeline pipeline = ch.pipeline();
        //添加入栈的自定义解码器Handler用于接收客户端发送数据的解码
        //pipeline.addLast(new MyByteToLongDecoder());
        //使用ReplayingDecoder
        pipeline.addLast(new MyByteToLongDecoder2());
        //添加出站的自定义编码器Handler用于回复客户端数据的编码器
        pipeline.addLast(new MyLongToByteEncoder());
        //添加自定义事件处理器  用于业务处理
        pipeline.addLast(new MyServerHandler());
        System.out.println("....");
    }
}
