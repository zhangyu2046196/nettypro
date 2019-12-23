package com.youyuan.netty.groupchat;

import com.youyuan.nio.groupchat.GroupChatClient;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author zhangy
 * @version 1.0
 * @description netty群聊系统
 * <p>
 * 服务端
 * @date 2019/12/16 20:23
 */
public class GroupChatServer {
    //端口号
    private final int port;

    public GroupChatServer(int port) {
        this.port = port;
    }

    /**
     * 服务端启动方法
     */
    public void run() {
        //定义两个事件循环组BossNioEventLoopGroup 和 WorkerNioEventLoopGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //定义启动类对象并配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)//配置事件循环组
                    .channel(NioServerSocketChannel.class)//配置服务端Channel实现类
                    .option(ChannelOption.SO_BACKLOG, 128)//配置任务队列连接数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//配置任务队列连接状态是活跃状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取Pipeline
                            ChannelPipeline pipeline = ch.pipeline();

                            //添加解码器
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            //添加编码器
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));

                            //添加自定义事件处理器
                            pipeline.addLast(new GroupChatServerHandler());
                        }
                    });

            //启动服务端
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("服务端启动了");


            //监听关闭通道
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
        new GroupChatServer(7001).run();
    }
}
