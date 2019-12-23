package com.youyuan.nio;

import java.nio.IntBuffer;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试NIO的buffer
 * @date 2019/12/10 8:31
 */
public class BasicBuffer {

    public static void main(String[] args) {
        //Buffer分类  IntBuffer、ShortBuffer、LongBuffer、ByteBuffer、CharBuffer、FloatBuffer、DoubleBuffer
        //创建大小为5的IntBuffer
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //保存数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        //切换Buffer状态为读模式
        intBuffer.flip();

        //读取Buffer
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }

}
