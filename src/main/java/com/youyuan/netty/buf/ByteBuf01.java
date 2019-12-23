package com.youyuan.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试Unpooled和ByteBuf
 * <p>
 * Unpooled是操作netty内存ByteBuf的工具类
 * @date 2019/12/16 16:38
 */
public class ByteBuf01 {

    public static void main(String[] args) {
        //通过Unpooled创建ByteBuf对象 创建了一个长度为10的数据
        //netty中的buffer在读取的时候不需要切换读模式flip
        //readerIndex:读取buffer数组中的下一个下标
        //writerIndex:写入buffer数组中的下一个下标
        //capacity:buffer的容量，就是数组的长度
        //0--->readerIndex   已读取的下标区域
        //readerIndex--->writerIndex   可读取的下标区域
        //writerIndex--->capacity   可写入下标区域
        ByteBuf buffer = Unpooled.buffer(10);

        //循环放入数据
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.writeByte(i);
        }

        for (int i=0;i<buffer.capacity();i++){
            //buffer.getByte(i);//readerIndex不会移动
            //buffer.readByte();//readerIndex会移动
            System.out.println(buffer.getByte(i));
        }

        System.out.println("readerIndex:" + buffer.readerIndex());
        System.out.println("writerIndex:" + buffer.writerIndex());
        System.out.println("capacity:" + buffer.capacity());
    }

}
