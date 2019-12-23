package com.youyuan.netty.dubborpc.netty;

import com.youyuan.netty.dubborpc.util.NettyProtoco;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangy
 * @version 1.0
 * @description 客户端
 * @date 2019/12/23 10:00
 */
public class NettyClient {

    //定义线程池
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);//创建线程数量是cpu数量*2

    //自定义事件处理器
    private static NettyClientHandler nettyClientHandler;

    /**
     * 利用jdk的反射构建代理对象
     *
     * @return
     */
    public Object getInstanceProxy(final Class<?> serviceClass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass}, (proxy, method, args) -> {

                    //{}  部分的代码，客户端每调用一次 hello, 就会进入到该代码
                    if (nettyClientHandler == null) {
                        initClient();
                    }

                    //设置要发给服务器端的信息
                    //providerName 协议头 args[0] 就是客户端调用api hello(???), 参数
                    nettyClientHandler.setParam(args[0].toString());

                    //
                    return executor.submit(nettyClientHandler).get();

                });
    }

    /**
     * 初始化客户端
     */
    private static void initClient() {
        nettyClientHandler = new NettyClientHandler();
        //定义事件循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建启动类对象 配置
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)//配置事件处理器
                    .channel(NioSocketChannel.class)//配置客户端Channel实现类
                    .option(ChannelOption.TCP_NODELAY, true)//配置创建无延迟的tcp连接
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取Pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //添加编码器
                            pipeline.addLast(new StringEncoder());
                            //添加解码器
                            pipeline.addLast(new StringDecoder());
                            //添加自定义事件处理器
                            pipeline.addLast(nettyClientHandler);
                        }
                    });

            //启动客户端
            ChannelFuture channelFuture = bootstrap.connect(NettyProtoco.HOST_NAME, NettyProtoco.PORT).sync();
            System.out.println("客户端启动");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
