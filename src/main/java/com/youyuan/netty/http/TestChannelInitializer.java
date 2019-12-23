package com.youyuan.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;


/**
 * @author zhangy
 * @version 1.0
 * @description netty案例之http服务
 * <p>
 * Channel类
 * @date 2019/12/16 8:49
 */
public class TestChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //获取PipeLine
        ChannelPipeline pipeline = socketChannel.pipeline();
        //1、向管道加入编码解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //2、向管道加入自定义ChannelHandler
        pipeline.addLast("MyChannelHandler", new TestServerHandler());
    }
}
