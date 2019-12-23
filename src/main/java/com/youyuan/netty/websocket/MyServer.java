package com.youyuan.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author zhangy
 * @version 1.0
 * @description netty应用案例  基于WebSocket的服务端和客户端长连接
 * <p>
 * 服务端类
 * @date 2019/12/17 10:08
 */
public class MyServer {
    //定义端口号
    private final int port;

    public MyServer(int port) {
        this.port = port;
    }

    /**
     * 服务端启动类
     */
    public void run() {
        //定义事件循环组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //定义启动类对象  配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)//配置事件循环组
                    .channel(NioServerSocketChannel.class)//配置服务器端Channel实现类
                    .option(ChannelOption.SO_BACKLOG, 128)//配置任务队列连接数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//配置任务队列连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取Pipeline
                            ChannelPipeline pipeline = ch.pipeline();

                            //因为是基于http协议所以添加http的编码解码器
                            pipeline.addLast(new HttpServerCodec());

                            //因为是以块的方式写入数据加入 ChunkedWriteHandler
                            pipeline.addLast(new ChunkedWriteHandler());

                        /*
                            说明
                            1. http数据在传输过程中是分段, HttpObjectAggregator ，就是可以将多个段聚合
                            2. 这就就是为什么，当浏览器发送大量数据时，就会发出多次http请求
                        */
                            pipeline.addLast(new HttpObjectAggregator(8192));

                        /*
                            说明
                            1. 对应websocket ，它的数据是以 帧(frame) 形式传递
                            2. 可以看到WebSocketFrame 下面有六个子类
                            3. 浏览器请求时 ws://localhost:7003/hello 表示请求的uri
                            4. WebSocketServerProtocolHandler 核心功能是将 http协议升级为 ws协议 , 保持长连接
                            5. 是通过一个 状态码 101
                     */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            //增加自定义事件处理器，处理业务逻辑
                            pipeline.addLast(new MyTextWebServerHandler());
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
        new MyServer(7003).run();
    }
}
