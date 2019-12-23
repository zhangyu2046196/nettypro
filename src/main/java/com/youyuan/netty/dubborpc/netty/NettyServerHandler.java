package com.youyuan.netty.dubborpc.netty;

import com.youyuan.netty.dubborpc.api.HelloService;
import com.youyuan.netty.dubborpc.provider.HelloServiceImpl;
import com.youyuan.netty.dubborpc.util.NettyProtoco;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义服务端事件处理器
 * @date 2019/12/23 9:47
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
        System.out.println("NettyServerHandler channelRead被调用");
        String paramName = msg.toString();//消费者发送的消息内容
        if (paramName.startsWith(NettyProtoco.PROTOCO_PRIFIX)) {
            String param = paramName.substring(paramName.lastIndexOf("#") + 1);//访问参数
            HelloService helloService = new HelloServiceImpl();
            String res = helloService.hello(param);
            System.out.println("服务端返回结果:" + res);
            //回写到客户端
            ctx.writeAndFlush(res);
        }
    }

    /**
     * 异常处理接口
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }
}
