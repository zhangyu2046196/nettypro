package com.youyuan.netty.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义事件处理器配置类
 * @date 2019/12/20 10:58
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {


        //获取Pipeline
        ChannelPipeline pipeline = ch.pipeline();

        //添加自定义事件处理器
        pipeline.addLast(new MyClientHandler());
    }
}
