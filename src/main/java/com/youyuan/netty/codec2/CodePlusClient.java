package com.youyuan.netty.codec2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试 ProtoBuf 编码解码器
 * <p>
 * 客户端
 * @date 2019/12/17 21:02
 */
public class CodePlusClient {
    //定义服务端地址
    private final String host;
    //定义服务端端口号
    private final int port;

    public CodePlusClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 启动客户端
     */
    public void run() {
        //定义事件循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //定义启动类对象 配置参数

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)//配置事件循环组
                    .channel(NioSocketChannel.class)//配置客户端Channel实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取Pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //添加ProtoBuf编码解码器
                            pipeline.addLast(new ProtobufEncoder());
                            //添加自定义事件处理器
                            pipeline.addLast(new CodePlusClientHandler());
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
        new CodePlusClient("127.0.0.1", 7005).run();
    }
}
