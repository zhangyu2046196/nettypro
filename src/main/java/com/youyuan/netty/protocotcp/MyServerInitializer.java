package com.youyuan.netty.protocotcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义事件管理类
 * @date 2019/12/20 12:06
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //获取Pipeline
        ChannelPipeline pipeline = ch.pipeline();
        //添加自定义解码器
        pipeline.addLast(new MessageDecoder());
        //添加自定义编码器
        pipeline.addLast(new MessageEncoder());
        //添加自定义事件处理器
        pipeline.addLast(new MyServerHandler());
    }
}
