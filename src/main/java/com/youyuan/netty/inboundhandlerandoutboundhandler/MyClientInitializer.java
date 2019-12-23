package com.youyuan.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * @author zhangy
 * @version 1.0
 * @description 自定义编码解码器，讲解Handler链表中Handler执行顺序
 * @date 2019/12/18 10:40
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //写入数据是出站(Pipeline中的Handler从尾部执行),读取数据是入栈(Pipeline中的Handler从头部执行)
        //获取Pipeline
        ChannelPipeline pipeline = ch.pipeline();
        //添加出站自定义编码器 Handler 用于发送数据到服务端
        pipeline.addLast(new MyLongToByteEncoder());
        //添加入栈自定义解码器 Handler 用于接收服务端回送消息
        //pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());
        //添加自定义事件处理器，用业务处理
        pipeline.addLast(new MyClientHandler());
    }
}
