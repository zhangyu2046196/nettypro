package com.youyuan.netty.protocotcp;

import io.netty.buffer.ByteBuf;

import java.io.Serializable;

/**
 * @author zhangy
 * @version 1.0
 * @description  自定义协议类
 * @date 2019/12/20 12:04
 */
public class MessageProtoco implements Serializable {

    private static final long serialVersionUID = 1721163281331731261L;
    //发送数据的长度
    private int len;
    //发送的数据
    private byte[] bytes;

    public MessageProtoco(int len, byte[] bytes) {
        this.len = len;
        this.bytes = bytes;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
