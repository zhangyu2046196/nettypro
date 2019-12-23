package com.youyuan.nio.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description NIO零拷贝  Client端  使用transferTo(底层使用的零拷贝)
 * <p>
 * linux:transferTo一次可以把文件拷贝
 * windows:transferTo一次最多拷贝8m文件，所以大于8m文件需要分段拷
 * @date 2019/12/12 9:24
 */
public class NIOClient {

    public static void main(String[] args) throws IOException {
        //定义SocketChannel 并启动客户端
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 7001));
        System.out.println("客户端启动");
        long startTime = System.currentTimeMillis();
        //定义FileChannel与FileInputStream关联
        File file = new File("protoc-3.6.1-win32.zip");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();

        //拷贝  transferTo
        long fileCount = fileChannel.transferTo(0, file.length(), socketChannel);
        System.out.println("拷贝字节数:" + fileCount + " 耗时:" + (System.currentTimeMillis() - startTime));

        //关闭流
        fileInputStream.close();
        fileChannel.close();
        socketChannel.close();
    }

}
