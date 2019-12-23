package com.youyuan.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description netty应用案例之Http服务
 * <p>
 * 服务端
 * @date 2019/12/16 8:44
 */
public class TestServer {

    public static void main(String[] args) {
        //定义两个事件循环组  boosGrup和workerGroup
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //定义启动类对象配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(boosGroup, workerGroup)//指定两个事件组
                    .channel(NioServerSocketChannel.class)//指定服务端通道实现类
                    .childHandler(new TestChannelInitializer());//指定自定义ChannelHandler

            //绑定端口并启动
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();

            //监听关闭通道
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭事件组
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
