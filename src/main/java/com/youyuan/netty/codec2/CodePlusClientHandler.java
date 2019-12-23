package com.youyuan.netty.codec2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试ProtoBuf编码解码器   传递不同类型对象
 * <p>
 * 客户端自定义事件处理器
 * @date 2019/12/17 21:10
 */
public class CodePlusClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;
        if (random == 1) {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.studentType).setStudent(MyDataInfo.Student.newBuilder().setId(10).setName("鼓上骚 石阡")).build();
        } else {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.workerType).setWorker(MyDataInfo.Worker.newBuilder().setAge(32).setName("武松")).build();
        }
        ctx.writeAndFlush(myMessage);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }
}
