package com.youyuan.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试ProtoBuf编码解码器
 * <p>
 * 自定义事件处理器
 * <p>
 * StuduentPOJO.Student  是ProtoBuf生成的外部对象和内部对象，传递用内部对象
 * @date 2019/12/17 20:06
 */
public class CodeServerHandler extends SimpleChannelInboundHandler<StuduentPOJO.Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StuduentPOJO.Student msg) throws Exception {
        System.out.println("客户端发送的消息,id:" + msg.getId() + " 名字:" + msg.getName());
    }
}
