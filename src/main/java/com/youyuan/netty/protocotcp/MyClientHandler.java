package com.youyuan.netty.protocotcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义事件处理器
 * @date 2019/12/20 12:24
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtoco> {
    private int count;

    private static List<String> list = new ArrayList<String>();

    static {
        list.add("自定义协议");
        list.add("服务器端每次读取数据长度的问题");
        list.add("客户端发送消息");
        list.add("不会出现服务器多读或少读数据的问题");
        list.add("服务器端每次接收");
        list.add("hello,client message");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtoco msg) throws Exception {
        System.out.println("第" + (++this.count) + "次获取服务端数据" + new String(msg.getBytes(), CharsetUtil.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 1; i <= 10; i++) {
            String str = getRandomMessage();
            System.out.println("str:"+str);
            byte[] bytes = str.getBytes(CharsetUtil.UTF_8);
            int len = bytes.length;
            ctx.writeAndFlush(new MessageProtoco(len, bytes));
        }
    }

    /**
     * 客户端异常处理接口
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

    private String getRandomMessage() {
        int len = list.size();
        return list.get(new Random().nextInt(len));
    }
}
