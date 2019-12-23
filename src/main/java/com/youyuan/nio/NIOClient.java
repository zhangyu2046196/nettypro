package com.youyuan.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试NIO非阻塞网络编程客户端
 * @date 2019/12/11 16:58
 */
public class NIOClient {

    public static void main(String[] args) throws IOException {
        //创建服务器地址和端口号
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //创建SocketChannel
        SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);
        //设置SocketChannel非阻塞
        socketChannel.configureBlocking(false);

        //写入数据到SocketChannel
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.next();
            //创建接收到内容大小的Buffer
            ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
            //数据写入Buffer
            byteBuffer.put(str.getBytes());
            //设置Buffer读模式
            byteBuffer.flip();
            //数据写入SocketChannel
            socketChannel.write(byteBuffer);
        }
    }

}
