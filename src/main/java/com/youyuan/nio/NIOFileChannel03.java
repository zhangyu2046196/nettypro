package com.youyuan.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试用一个ByteBuffer读取1.txt文件内容然后写入到2.txt文件，实现拷贝功能
 * @date 2019/12/11 8:44
 */
public class NIOFileChannel03 {

    public static void main(String[] args) throws IOException {
        //创建输入流指定1.txt文件
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        //创建输入流对应的FileChannel
        FileChannel inFileChannel = fileInputStream.getChannel();
        //创建输入流指定2.txt文件
        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        //创建输出流对应的FileChannel
        FileChannel outFileChannel = fileOutputStream.getChannel();
        //创建ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //循环读取和写入ByteBuffer
        while (true) {
            //清除ByteBuffer内容，实际就是重置ByteBuffer中的属性值
            //public final Buffer clear() {
            // position = 0;
            // limit = capacity;
            // mark = -1;
            // return this;
            //}
            byteBuffer.clear();
            //读取1.txt文件内容到ByteBuffer
            int read = inFileChannel.read(byteBuffer);
            if (read == -1) {//读取不到内容
                break;
            } else {
                //切换ByteBuffer为读模式
                byteBuffer.flip();
                //读取的内容写到2.txt
                outFileChannel.write(byteBuffer);
            }
        }

        //关闭流
        fileInputStream.close();
        fileOutputStream.close();

    }

}
