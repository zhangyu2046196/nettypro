package com.youyuan.netty.codec2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试ProtoBuf编码解码器
 * <p>
 * 服务端自定义事件处理器
 * @date 2019/12/17 20:57
 */
public class CodePlusServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        if (msg.getDataType() == MyDataInfo.MyMessage.DataType.studentType) {
            System.out.println("客户端发送消息,id:" + msg.getStudent().getId() + " 名字:" + msg.getStudent().getName());
        } else {
            System.out.println("客户端发送消息,age:" + msg.getWorker().getAge() + " 名字:" + msg.getWorker().getName());
        }
    }
}
