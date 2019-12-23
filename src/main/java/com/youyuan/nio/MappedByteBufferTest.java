package com.youyuan.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description 验证MappedByteBuffer可以在内存中修改Buffer中的内容
 * @date 2019/12/11 10:05
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws IOException {
        //创建RandomAccessFile 是IO包下面的类，作用是可以在读取文件并且在内存中修改文件
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        //创建对应的FileChannel
        FileChannel fileChannel = randomAccessFile.getChannel();

        //设置FileChannel的修改方式及修改范围
        //参数1：设置类型为读写模式
        //参数2：在内存修改FileChannel关联的1.txt文件的起始位置
        //参数3：在内存修改FileChannel关联的1.txt文件的长度    此处代表从0位置可修改，最多修改5个字节
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        //修改
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) 'W');

        //关闭流
        fileChannel.close();
        randomAccessFile.close();

        System.out.println("修改完成");
    }

}
