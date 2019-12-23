package com.youyuan.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义事件处理器
 * <p>
 * 处理业务逻辑
 * @date 2019/12/17 10:36
 */
public class MyTextWebServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 读取客户端消
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("[客户端]" + msg.text() + "," + sdf.format(new Date()));
        //向客户端回显数据
        ctx.writeAndFlush(new TextWebSocketFrame(msg.text()));
    }

    /**
     * 客户端连接服务端成功后第一个执行的方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接成功");
    }

    /**
     * 客户端断开服务端连接后执行的方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开连接");
    }

    /**
     * 异常执行的方法
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("通道:" + ctx.channel() + " 异常信息:" + cause.getMessage());

        ctx.channel().close();  //关闭通道
    }
}
