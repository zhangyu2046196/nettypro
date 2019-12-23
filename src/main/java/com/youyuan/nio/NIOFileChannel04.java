package com.youyuan.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description 通过FileChannel的transferFrom或transferTo方法实现文件拷贝
 * @date 2019/12/11 9:07
 */
public class NIOFileChannel04 {

    public static void main(String[] args) throws IOException {
        //创建输入流
        FileInputStream fileInputStream = new FileInputStream("e:\\1.jfif");
        //创建输入流的FileChannel
        FileChannel sourceFileChannel = fileInputStream.getChannel();
        //创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("e:\\2.jfif");
        //创建输出流的FileChannel
        FileChannel targetFileChannel = fileOutputStream.getChannel();

        //拷贝
        targetFileChannel.transferFrom(sourceFileChannel, 0, sourceFileChannel.size());

        //关闭流
        sourceFileChannel.close();
        targetFileChannel.close();
        fileInputStream.close();
        fileOutputStream.close();

    }

}
