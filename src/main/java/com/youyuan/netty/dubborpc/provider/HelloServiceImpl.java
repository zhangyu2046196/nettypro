package com.youyuan.netty.dubborpc.provider;

import com.youyuan.netty.dubborpc.api.HelloService;
import org.springframework.util.StringUtils;

/**
 * @author zhangy
 * @version 1.0
 * @description 接口实现类
 * @date 2019/12/23 9:53
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String param) {
        System.out.println("服务端接收到客户端的消息[" + param + "]");
        if (!StringUtils.isEmpty(param)) {
            return "服务端接收到客户端的消息[" + param + "]";
        } else {
            return "服务端接收到客户端的消息";
        }
    }
}
