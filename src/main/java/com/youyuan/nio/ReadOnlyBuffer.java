package com.youyuan.nio;

import java.nio.ByteBuffer;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试只读Buffer，只读Buffer不能写入，写入时会报错
 * @date 2019/12/11 9:36
 */
public class ReadOnlyBuffer {

    public static void main(String[] args) {
        //创建Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        //写入数据到Buffer
        for (int i = 1; i <= byteBuffer.limit(); i++) {
            byteBuffer.put((byte) i);
        }

        //切换成读模式
        byteBuffer.flip();
        //打印
        while (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }

        System.out.println("=======切换成只读模式后=========");

        //切换成只读模式
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

        readOnlyBuffer.flip();
        //打印
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }

        //如果往只读模式的ByteBuffer存放数据会报异常
        readOnlyBuffer.put((byte) 100);
    }

}
