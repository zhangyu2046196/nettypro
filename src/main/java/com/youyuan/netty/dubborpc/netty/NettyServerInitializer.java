package com.youyuan.netty.dubborpc.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义服务端事件管理器
 * @date 2019/12/23 9:45
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //获取Pipeline
        ChannelPipeline pipeline = ch.pipeline();
        //添加解码器
        pipeline.addLast(new StringDecoder());
        //添加编码器
        pipeline.addLast(new StringEncoder());
        //添加自定义事件处理器
        pipeline.addLast(new NettyServerHandler());
    }
}
