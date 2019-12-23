package com.youyuan.netty.dubborpc.consumer;

import com.youyuan.netty.dubborpc.api.HelloService;
import com.youyuan.netty.dubborpc.netty.NettyClient;
import com.youyuan.netty.dubborpc.util.NettyProtoco;

/**
 * @author zhangy
 * @version 1.0
 * @description 客户端启动
 * @date 2019/12/23 10:42
 */
public class NettyClientBootStrap {

    public static void main(String[] args) {
        //启动客户端
        NettyClient nettyClient = new NettyClient();
        //获取代理对象
        HelloService helloService = (HelloService) nettyClient.getInstanceProxy(HelloService.class);
        //调用服务端
        String result = helloService.hello(NettyProtoco.PROTOCO_PRIFIX + "你好，北京");
        System.out.println("服务端返回结果:" + result);
    }

}
