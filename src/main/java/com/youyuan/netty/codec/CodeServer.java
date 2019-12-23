package com.youyuan.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试PtotoBuf编码解码器单对象传输
 * @date 2019/12/17 19:55
 */
public class CodeServer {
    //定义端口号
    private final int port;

    public CodeServer(int port) {
        this.port = port;
    }

    /**
     * 服务器端启动
     */
    public void run() {
        //定义两个事件循环组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //定义启动对象  配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)//配置事件循环组
                    .channel(NioServerSocketChannel.class)//配置服务端Channel实现类
                    .option(ChannelOption.SO_BACKLOG, 128)//配置任务队列连接数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//配置任务队列连接数活跃状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            //获取Pipeline
                            ChannelPipeline pipeline = ch.pipeline();

                            //添加ProtoBuf编码解码器
                            pipeline.addLast(new ProtobufDecoder(StuduentPOJO.Student.getDefaultInstance()));

                            //添加自定义事件处理器用于执行业务逻辑
                            pipeline.addLast(new CodeServerHandler());

                        }
                    });

            //启动服务器
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
        new CodeServer(7004).run();
    }
}
