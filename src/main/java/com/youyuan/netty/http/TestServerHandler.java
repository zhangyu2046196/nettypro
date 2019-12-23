package com.youyuan.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author zhangy
 * @version 1.0
 * @description netty应用案例之http服务
 * <p>
 * 自定义ChannelHandler
 * <p>
 * HttpObject是客户端请求服务端封装的参数
 * @date 2019/12/16 8:53
 */
public class TestServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println("对应的Channel：" + ctx.channel() + " 对应的PipeLine:" + ctx.pipeline() + " 通过PipeLine获取的Channel:" + ctx
                .pipeline().channel());

        //判断是否是客户端请求
        if (msg instanceof HttpRequest) {
            //强转请求消息
            HttpRequest httpRequest = (HttpRequest) msg;

            //获取URI过滤指定资源
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico, 不做响应");
                return;
            }

            //回复消息给浏览器  http协议
            //定义回复消息
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,客户端", CharsetUtil.UTF_8);
            //定义回复的Http协议  传入参数Http版本、Http响应状态码、回写的数据
            HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            //设置http响应的头信息
            response.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            response.headers().add(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());


            //回写数据到客户端
            ctx.writeAndFlush(response);
        }
    }
}
