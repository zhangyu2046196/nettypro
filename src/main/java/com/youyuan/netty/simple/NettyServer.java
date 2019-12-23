package com.youyuan.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description netty入门案例:Netty 服务器在 6668 端口监听，客户端能发送消息给服务器 "hello, 服务器~"服务器可以回复消息给客户端 "hello, 客户端~"
 * <p>
 * netty服务端类
 * @date 2019/12/13 8:10
 */
public class NettyServer {

    public static void main(String[] args) {
        //创建BossGroup和WorkerGroup两个事件轮询组
        //EventLoopGroup bossGroup = new NioEventLoopGroup();
        //可以指定NioEventLoopGroup中的NioEventLoop的线程个数，如果参数不添加默认是CPU*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建启动类对象且配置启动参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //采用流方式配置启动参数
            serverBootstrap.group(bossGroup, workerGroup)//设置两个事件轮询组
                    .channel(NioServerSocketChannel.class)//设置服务器端Channel实现类
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//设置ChannelHandler
                        //给PipeLine管道设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());//传入自定义处理器
                        }
                    });

            //启动类对象绑定端口启动
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
            System.out.println("服务器端启动");

            //因为bind是异步操作，返回的ChannelFuture用于监听，增加监听器测试Future-Listener机制
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("服务端绑定端口  6668  成功");
                    } else {
                        System.out.println("服务端绑定端口  6668  失败");
                    }
                }
            });

            //对关闭通道进行监听
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
