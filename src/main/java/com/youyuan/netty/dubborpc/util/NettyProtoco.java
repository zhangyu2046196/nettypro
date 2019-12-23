package com.youyuan.netty.dubborpc.util;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义协议(消费者访问服务提供者的前缀)
 * @date 2019/12/23 9:49
 */
public class NettyProtoco {
    //服务器地址
    public static final String HOST_NAME = "127.0.0.1";
    //服务器端口号
    public static final int PORT = 7009;
    //访问前缀信息
    public static final String PROTOCO_PRIFIX = "HelloService#hello#";
}
