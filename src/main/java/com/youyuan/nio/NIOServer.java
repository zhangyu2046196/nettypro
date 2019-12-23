package com.youyuan.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhangy
 * @version 1.0
 * @description NIO非阻塞网络编程服务端
 * @date 2019/12/11 16:40
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建Selector
        Selector selector = Selector.open();
        //设置端口号
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        //绑定端口号
        serverSocketChannel.socket().bind(inetSocketAddress);
        //设置ServerSocketChannel非阻塞
        serverSocketChannel.configureBlocking(false);
        //把ServerSocketChannel注册到Selector  监听连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端启动~~~~~~");
        //循环监听
        while (true) {
            //调用select方法监听通道的事件，设置为阻塞1秒,select方法返回当前有监听事件的channel数量
            if (selector.select(1000) == 0) {
                System.out.println("等待1秒,继续等待~~~~~~");
                continue;
            }

            //有监听事件  是一个集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            //循环监听事件
            while (iterator.hasNext()) {
                //具体的监听事件
                SelectionKey selectionKey = iterator.next();
                //请求连接事件
                if (selectionKey.isAcceptable()) {
                    //创建客户端SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置socketChannel为非阻塞
                    socketChannel.configureBlocking(false);
                    System.out.println("接收到客户端的连接请求,hashCode:" + socketChannel.hashCode());
                    //把SocketChannel注册到Selector
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                //客户端读事件
                if (selectionKey.isReadable()) {
                    //通过selectKey反向获取SocketChannel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    System.out.println("读取客户端发送的数据,hashCode:" + channel.hashCode());
                    //创建Buffer
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    //将数据从Channel读取到Buffer
                    channel.read(byteBuffer);
                    //打印到控制台
                    System.out.println("from Client:" + new String(byteBuffer.array()));
                }

                //清除事件key
                iterator.remove();
            }
        }
    }

}
