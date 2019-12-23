package com.youyuan.nio.groupchat;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author zhangy
 * @version 1.0
 * @description NIO网络编程案例  群聊系统
 * @date 2019/12/11 20:50
 */
public class GroupChatServer {
    /**
     * 定义选择器
     */
    private Selector selector;
    /**
     * 定义ServerSocketChannel
     */
    private ServerSocketChannel serverSocketChannel;
    /**
     * 定义端口号
     */
    private final static int PORT = 6667;

    public GroupChatServer() {
        try {
            //定义选择器
            selector = Selector.open();
            //定义ServerSocketChannel
            serverSocketChannel = ServerSocketChannel.open();
            //绑定端口号
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            //设置ServerSocketChannel为非阻塞
            serverSocketChannel.configureBlocking(false);
            //把ServerSocketChannel注册到Selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器启动");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听
     */
    private void monitor() {
        try {
            //监听
            while (true) {
                //获取Channel注册到Selector的此时触发事件的Channel个数
                int select = selector.select(1000);
                if (select == 0) {
                    continue;
                }
                //获取SelectionKeys
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                //遍历SelectionKeys集合
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    //客户端请求连接请求
                    if (selectionKey.isAcceptable()) {
                        //创建客户端SocketChannel
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        //设置SocketChannel为非阻塞
                        socketChannel.configureBlocking(false);
                        //把SocketChannel注册到Selector
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        //打印客户端上线 消息
                        System.out.println(socketChannel.getRemoteAddress() + " 上线 ");
                    }
                    //读事件
                    if (selectionKey.isReadable()) {
                        //通过SelectionKey反向获取SocketChannel
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        try {
                            //创建Buffer
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                            //读取客户端消息
                            int read = channel.read(byteBuffer);
                            if (read > 0) {
                                String msg = new String(byteBuffer.array());

                                System.out.println(msg);

                                //转发消息到其它Channel
                                sendInfoToOtherChannel(msg, channel);

                            }

                        } catch (Exception e) {
                            System.out.println(channel.getRemoteAddress() + " 下线 ");
                            selectionKey.channel();
                            channel.close();
                        }

                    }
                    //清除当前SelectionKey，避免重复操作
                    iterator.remove();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转发消息到其它Channel
     *
     * @param self
     * @param msg
     */
    private void sendInfoToOtherChannel(String msg, SocketChannel self) {
        try {
            //遍历 所有注册到selector 上的 SocketChannel,并排除 self
            for (SelectionKey key : selector.keys()) {

                //通过 key  取出对应的 SocketChannel
                Channel targetChannel = key.channel();

                //排除自己
                if (targetChannel instanceof SocketChannel && targetChannel != self) {

                    //转型
                    SocketChannel dest = (SocketChannel) targetChannel;
                    //将msg 存储到buffer
                    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                    //将buffer 的数据写入 通道
                    dest.write(buffer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.monitor();
    }


}
