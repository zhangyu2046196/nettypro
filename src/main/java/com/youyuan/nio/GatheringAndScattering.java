package com.youyuan.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author zhangy
 * @version 1.0
 * @description 分散(Gathering)读取和聚集(Scattering)写入, 原理是写入或从一个Buffer数组中读取写入
 * @date 2019/12/11 11:19
 */
public class GatheringAndScattering {

    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //定义端口号
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //ServerSockeChannel绑定端口号
        SocketChannel socketChannel = serverSocketChannel.bind(inetSocketAddress).accept();
        System.out.println("服务端启动");

        //定义Buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        int messageLength = 8;//总共读取字节数
        //循环读取
        while (true) {
            //读取到Buffer数组
            int readByteLength = 0;//当前读取的长度
            while (readByteLength < messageLength) {
                long read = socketChannel.read(byteBuffers);
                readByteLength += read;
                System.out.println("readByteLength: "+readByteLength);
                //使用流的方式打印Buffer数组中的Buffer的position和limit
                Arrays.asList(byteBuffers).stream().map(byteBuffer -> "position: " + byteBuffer.position() + " limit: " + byteBuffer.limit()).forEach(System.out::println);
            }

            //遍历Buffer数组
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());//切换为读模式
            //遍历
            Arrays.asList(byteBuffers).forEach(byteBuffer -> System.out.println(new String(byteBuffer.array())));
        }
    }

}
