package com.youyuan.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangy
 * @version 1.0
 * @description netty应用案例心跳检测
 * <p>
 * 服务端类
 * @date 2019/12/17 8:25
 */
public class HeartBeatServer {
    //定义端口
    private final int port;

    public HeartBeatServer(int port) {
        this.port = port;
    }

    /**
     * 服务端启动方法
     */
    public void run() {
        //定义两个事件循环组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //定义服务端启动对象 配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)//配置事件循环组
                    .channel(NioServerSocketChannel.class)//配置服务端Channel实现类
                    .option(ChannelOption.SO_BACKLOG, 128)//配置任务队列连接数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//配置任务队列连接活跃状态
                    .handler(new LoggingHandler(LogLevel.INFO))//往bossGroup事件循环组配置日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取Pipeline
                            ChannelPipeline pipeline = ch.pipeline();

                            //往Pipeline管道中增加IdleStateHandler空闲状态检测处理器
                            /**
                             * 1、IdleStateHandler  空闲状态检测处理器
                             * 2、readerIdleTime   多长时间没有读操作，触发心跳检测
                             * 3、writerIdleTime   多长时间没有写操作，触发心跳检测
                             * 4、allIdleTime      多长时间没有读写操作，触发心跳检测
                             * 5、TimeUtil         时间单位信息
                             * 6、空闲状态心跳检测处理器被触发后会执行pipeline管道中的下一个Handler的useEventTrigger方法，在这个方法中执行相应的业务处理
                             */
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            //增加一个自定义的处理器，用于执行IdleStateHandler触发后的业务处理，也就执行自定义处理器中的useEventTrigger方法
                            pipeline.addLast(new HeartBeatServerHandler());
                        }
                    });

            //启动服务端
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
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

    public static void main(String[] args) {
        new HeartBeatServer(7002).run();
    }
}
