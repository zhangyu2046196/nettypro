package com.youyuan.netty.protocotcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description 通过自定义协议和编码器解决tcp粘包和拆包问题
 * @date 2019/12/20 12:16
 */
public class MyClient {
    //服务端地址
    private final String host;
    //服务端端口
    private final int port;

    public MyClient(String host, int port) {
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
            //定义启动类对象  配置参数
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)//配置事件循环组
                    .channel(NioSocketChannel.class)//配置客户端Channel实现类
                    .handler(new MyClientInitializer());

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
        new MyClient("127.0.0.1", 7009).run();
    }

}
