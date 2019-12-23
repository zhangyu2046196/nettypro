package com.youyuan.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description netty入门案例:Netty 服务器在 6668 端口监听，客户端能发送消息给服务器 "hello, 服务器~"服务器可以回复消息给客户端 "hello, 客户端~"
 * <p>
 * netty客户端类
 * @date 2019/12/13 8:39
 */
public class NettyClient {

    public static void main(String[] args) {
        //定义事件轮询组，客户端只需要一个事件轮询组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建客户端启动类对象且配置启动参数
            Bootstrap bootstrap = new Bootstrap();

            //配置启动类参数
            bootstrap.group(group)//设置事件轮询组
                    .channel(NioSocketChannel.class)//设置客户端Channel实现类
                    .handler(new ChannelInitializer<SocketChannel>() {//设置处理器
                        //给PipeLine管道设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());//设置定义通道处理器
                        }
                    });
            //绑定端口启动
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
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

}
