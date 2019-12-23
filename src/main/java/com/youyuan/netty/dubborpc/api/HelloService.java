package com.youyuan.netty.dubborpc.api;

/**
 * @author zhangy
 * @version 1.0
 * @description 服务提供者和服务消费者共用接口
 * @date 2019/12/23 9:36
 */
public interface HelloService {
    /**
     * 接口方法名
     *
     * @param param 参数
     * @return 返回内容信息
     */
    public String hello(String param);
}
