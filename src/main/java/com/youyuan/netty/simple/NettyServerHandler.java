package com.youyuan.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义netty服务端处理器, 需要继承HandlerAdapter适配器
 * @date 2019/12/13 8:24
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("当前服务器中的WorkerGroup中的NioEventLoop的线程名:" + Thread.currentThread().getName());
        System.out.println("服务端上线文ctx:" + ctx);

        //当读取任务时如果处理的业务逻辑比较耗时，会造成服务端与客户端阻塞，比如处理的时候休眠10秒，解决方案如下
        //1、自定义任务放到taskQueue中,把NioSocketChannel放入到NioEventLoop中的TaskQueue中

        //获取NioSocketChannel放入NioEventLoop的TaskQueue中
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //模拟处理业务耗时
                    Thread.sleep(10 * 1000);
                    //向客户端回写数据
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端2", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //2、自定义定时任务放到TaskQueue中，把NioSocketChannel放到对应的NioEventLoop线程中的ScheduleTaskQueue中

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端3", CharsetUtil.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5, TimeUnit.SECONDS);

        System.out.println("服务端读取客户端数据");




        /*Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();
        //将客户端消息强转ByteBuf
        ByteBuf byteBuf = (ByteBuf) msg;
        //打印客户端消息
        System.out.println("接收到客户端消息:" + byteBuf.toString(CharsetUtil.UTF_8) + " 客户端地址:" + ctx.channel().remoteAddress().toString().substring(1));*/
    }

    /**
     * 向客户端返回消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //消息写入缓存并刷新
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("hello，客户端1", CharsetUtil.UTF_8));
    }

    /**
     * 处理Channel异常信息
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //通道出现异常一般是关闭通道
        ctx.channel().close();
    }
}
