package com.youyuan.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试通过FileChannel和ByteBuffer读取磁盘文件d:\\fc01.txt文件内容，打印到控制台
 * @date 2019/12/11 8:15
 */
public class NIOFileChannel02 {

    public static void main(String[] args) throws IOException {
        //创建File
        File file = new File("d:\\fc01.txt");
        //创建输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        //创建FileChannel实际上创建的是FileChannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();
        //创建ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //读取文件内容写入ByteBuffer
        fileChannel.read(byteBuffer);
        //打印内容到控制台
        System.out.println(new String(byteBuffer.array()));
        //关闭流
        fileInputStream.close();
        fileChannel.close();
    }

}
