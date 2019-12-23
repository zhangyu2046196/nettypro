package com.youyuan.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description NIO零拷贝  Server端
 * @date 2019/12/12 9:20
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {
        //定义ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //定义端口号
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7001);
        //绑定端口
        serverSocketChannel.socket().bind(inetSocketAddress);

        System.out.println("服务端启动");

        //监听
        while (true) {
            //获取客户端的SocketChannel
            SocketChannel socketChannel = serverSocketChannel.accept();
            //创建Buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //循环读取
            int readCount = 0;
            while (readCount != -1) {
                //读取Channel内容到Buffer,返回读取的字节数
                readCount = socketChannel.read(byteBuffer);
                //Buffer倒带  重置position、limit
//                byteBuffer.rewind();
                byteBuffer.clear();
            }
        }
    }

}
