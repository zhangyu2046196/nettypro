package com.youyuan.nio;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试将字符串 "hello,北京"通过FileChannel和ByteBuffer写入到d:\\fc01.txt文件中
 * @date 2019/12/10 10:55
 */
public class NIOFileChannel01 {

    public static void main(String[] args) throws IOException {
        //要写入文件的字符串
        String str = "hello,北京";
        //创建Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将字符串写入到Buffer
        byteBuffer.put(str.getBytes());
        //将ByteBuffer切换为读模式
        byteBuffer.flip();
        //创建输出流
        FileOutputStream outputStream = new FileOutputStream("d:\\fc01.txt");
        //创建FileChannel
        FileChannel fileChannel = outputStream.getChannel();
        //将Buffer数据写入Channel
        fileChannel.write(byteBuffer);
        //关闭流
        outputStream.close();
        fileChannel.close();
    }

}
