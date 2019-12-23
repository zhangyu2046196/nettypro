package com.youyuan.netty.codec2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试ProtoBuf编码解码器  传递多个对象
 * <p>
 * 服务器端类
 * @date 2019/12/17 20:50
 */
public class CodePlusServer {
    //定义端口号
    private final int port;

    public CodePlusServer(int port) {
        this.port = port;
    }

    /**
     * 启动服务端
     */
    public void run() {
        //定义两个事件循环组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //定义启动类对象 配置参数
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
                            //添加Protobuf解码器
                            pipeline.addLast(new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
                            //添加自定义事件处理器
                            pipeline.addLast(new CodePlusServerHandler());
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
        new CodePlusServer(7005).run();
    }
}
