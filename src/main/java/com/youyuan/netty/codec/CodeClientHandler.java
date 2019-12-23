package com.youyuan.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试ProtoBuf编码解码器
 * <p>
 * 客户端自定义处理器
 * @date 2019/12/17 20:19
 */
public class CodeClientHandler extends SimpleChannelInboundHandler<StuduentPOJO.Student> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StuduentPOJO.Student student = StuduentPOJO.Student.newBuilder().setId(20).setName("智多星 吴用").build();
        ctx.writeAndFlush(student);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StuduentPOJO.Student msg) throws Exception {

    }
}
