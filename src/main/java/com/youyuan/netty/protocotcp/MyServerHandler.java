package com.youyuan.netty.protocotcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义事件处理器
 * @date 2019/12/20 12:14
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtoco> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtoco msg) throws Exception {
        System.out.println("第" + (++this.count) + "次接收客户端数据长度:" + msg.getLen() + " 接收客户端数据:" + new String(msg.getBytes(), CharsetUtil.UTF_8));

        //往客户端回送服务端消息
        String str = UUID.randomUUID().toString();
        byte[] bytes = str.getBytes();
        int len = bytes.length;
        ctx.writeAndFlush(new MessageProtoco(len, bytes));
    }

    /**
     * 服务端异常处理接口
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();  //关闭通道
    }
}
