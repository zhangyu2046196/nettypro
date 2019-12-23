package com.youyuan.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangy
 * @version 1.0
 * @description netty群聊  自定义事件处理器
 * @date 2019/12/16 20:37
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义通道组  通道组存储所有的channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //定义时间格式化
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 客户端连接服务端口第一个执行的方法   标记加入群聊系统
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //加入到通道管理组
        Channel channel = ctx.channel();
        System.out.println("[客户端] " + channel.remoteAddress().toString().substring(1) + " 加入群聊系统," + sdf.format(new Date()));

        //通知其它通道此通道加入群聊系统
        channelGroup.writeAndFlush("[客户端] " + channel.remoteAddress().toString().substring(1) + " 加入群聊系统," + sdf.format(new Date()));

        //当前通道加入通道组
        channelGroup.add(channel);
    }

    /**
     * 客户端断开服务端时调用方法   标记客户端离开
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //因为handlerRemoved方法会自动把通道组ChannelGroup中的当前通道删除，所以此处不用手动删除
        Channel channel = ctx.channel();
        System.out.println("[客户端] " + channel.remoteAddress().toString().substring(1) + " 离开," + sdf.format(new Date()));

        //通知其它通道此通道离开群聊系统
        channelGroup.writeAndFlush("[客户端] " + channel.remoteAddress().toString().substring(1) + " 离开群聊系统," + sdf.format(new Date()));

        System.out.println("ChannelGroup size:" + channelGroup.size());
    }

    /**
     * 通道活跃状态   标记客户端上线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //通知其它通道此通道上线
        channelGroup.writeAndFlush("[客户端] " + channel.remoteAddress().toString().substring(1) + " 上线," + sdf.format(new Date()));
    }

    /**
     * 通道非活跃状态  标记客户端下线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //通知其它通道此通道下线
        channelGroup.writeAndFlush("[客户端] " + channel.remoteAddress().toString().substring(1) + " 下线," + sdf.format(new Date()));
    }

    /**
     * 异常时关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

    /**
     * 处理客户端发送的内容
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取当前通道 发送消息的通道
        Channel channel = ctx.channel();
        //循环遍历通道组
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush("[客户端]" + channel.remoteAddress().toString().substring(1) + " 说 " + msg + "," + sdf.format(new Date()));
            } else {
                channel.writeAndFlush("自己说" + msg + "," + sdf.format(new Date()));
            }
        });
    }
}
