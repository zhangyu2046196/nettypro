package com.youyuan.netty.inboundhandlerandoutboundhandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description 通过自定义编码解码器讲解Handler链表的出站入站的顺序
 * <p>
 * 服务端类
 * @date 2019/12/18 10:10
 */
public class MyServer {
    //定义服务器端口
    private final int port;

    public MyServer(int port) {
        this.port = port;
    }

    /**
     * 服务端启动
     */
    public void run() {
        //定义两个事件循环组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //定义启动类对象  配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)//配置事件循环组
                    .channel(NioServerSocketChannel.class)//配置服务端Channel实现类
                    .option(ChannelOption.SO_BACKLOG, 128)//配置任务队列连接数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//配置任务队列连接的状态
                    .childHandler(new MyServerInitializer());

            //启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("服务器端启动");
            //对关闭通道监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭事件循环组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new MyServer(7006).run();
    }

}
