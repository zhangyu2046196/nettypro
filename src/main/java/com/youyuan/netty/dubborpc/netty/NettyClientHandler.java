package com.youyuan.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义事件处理器
 * @date 2019/12/23 10:04
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    //上下文
    private ChannelHandlerContext ctx;
    //请求参数
    private String param;
    //返回参数
    private String res;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    /**
     * 客户端与服务端建立连接执行方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("NettyClientHandler  channelActive被调用");
        this.ctx = ctx;
    }

    /**
     * 读取服务端返回消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("NettyClientHandler channelRead被调用");
        res = msg.toString();//获取服务端返回的消息
        notifyAll();//唤醒等待结果线程
    }

    /**
     * 异常触发方法
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();//关闭通道
    }

    /**
     * 被代理对象调用发送数据给服务器 -> wait->等待channelRead唤醒->返回结果信息
     *
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("NettyClientHandler call被调用" + param);
        ctx.writeAndFlush(param);//发送数据给服务端
        wait();//等待唤醒(channelRead)
        return res;//返回结果给消费者
    }
}
