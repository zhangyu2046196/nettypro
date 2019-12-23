package com.youyuan.netty.dubborpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description netty服务端
 * @date 2019/12/23 9:38
 */
public class NettyServer {
    //定义服务器地址
    private final String hostName;
    //定义服务器端口
    private final int port;

    public NettyServer(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    /**
     * 服务器启动接口
     */
    public void start() {
        serverStart();
    }

    /**
     * 服务器启动接口
     */
    private void serverStart() {
        //定义两个事件循环组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建启动类对象  配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)//配置事件循环组
                    .channel(NioServerSocketChannel.class)//配置服务器端Channel实现类
                    .option(ChannelOption.SO_BACKLOG, 128)//配置任务队列线程数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//配置任务队列线程状态
                    .childHandler(new NettyServerInitializer());//配置自定义事件管理器

            //启动服务端
            ChannelFuture channelFuture = serverBootstrap.bind(hostName, port).sync();
            System.out.println("服务端启动");
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
}
