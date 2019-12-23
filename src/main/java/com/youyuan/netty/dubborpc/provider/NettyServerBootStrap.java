package com.youyuan.netty.dubborpc.provider;

import com.youyuan.netty.dubborpc.netty.NettyServer;
import com.youyuan.netty.dubborpc.util.NettyProtoco;

/**
 * @author zhangy
 * @version 1.0
 * @description rpc服务端启动类
 * @date 2019/12/23 9:38
 */
public class NettyServerBootStrap {

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer(NettyProtoco.HOST_NAME, NettyProtoco.PORT);
        nettyServer.start();
    }

}
