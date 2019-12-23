package com.youyuan.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author zhangy
 * @version 1.0
 * @description netty心跳检测
 * <p>
 * 自定义处理器用于执行IdleStateHandler处理器被触发后的业务
 * @date 2019/12/17 8:37
 */
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 处理IdleStateHandler空闲状态处理器被触发后的业务
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = "";
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
            }

            System.out.println("服务器触发:" + eventType);

            //处理自己的业务逻辑，可以关闭通道
            //ctx.channel().close();
            System.out.println("处理业务逻辑");
        }
    }
}
