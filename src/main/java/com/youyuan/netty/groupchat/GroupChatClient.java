package com.youyuan.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * @author zhangy
 * @version 1.0
 * @description netty群聊系统  客户端
 * @date 2019/12/16 21:02
 */
public class GroupChatClient {
    //定义服务端地址
    private final String host;
    //定义服务端端口
    private final int port;

    public GroupChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    //定义启动方法
    public void run() {
        //定义事件循环组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //定义启动类对象 配置参数信息
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)//配置事件循环组
                    .channel(NioSocketChannel.class)//配置服务端Channel实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //后去Pipeline
                            ChannelPipeline pipeline = ch.pipeline();

                            //添加解码器
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));

                            //添加编码器
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));

                            //添加自定义事件处理器
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });

            //绑定地址端口启动
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            //获取扫描器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                //内容
                String str = scanner.next();
                channelFuture.channel().writeAndFlush(str);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new GroupChatClient("127.0.0.1", 7001).run();
    }


}
