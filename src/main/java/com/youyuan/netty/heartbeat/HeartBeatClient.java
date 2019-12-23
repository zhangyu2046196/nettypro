package com.youyuan.netty.heartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description netty心跳检测案例
 * <p>
 * 心跳检测客户端
 * @date 2019/12/17 8:44
 */
public class HeartBeatClient {
    //定义服务器地址
    private final String host;
    //定义服务器端口号
    private final int port;

    public HeartBeatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 客户端启动
     */
    public void run() {
        //定义事件循环组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //定义客户端启动类对象 配置参数信息
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)//
                    .channel(NioSocketChannel.class)//配置客户端Channel实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取Pipeline
                            ChannelPipeline pipeline = ch.pipeline();

                            //添加自定义事件处理器
                            pipeline.addLast(new HeartBeatClientHandler());
                        }
                    });

            //启动客户端
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            System.out.println("客户端启动");


            //对关闭通道监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭事件循环组
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new HeartBeatClient("127.0.0.1", 7002).run();
    }
}
