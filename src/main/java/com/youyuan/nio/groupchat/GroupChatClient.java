package com.youyuan.nio.groupchat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangy
 * @version 1.0
 * @description NIO网络编程案例：群聊系统
 * @date 2019/12/11 21:27
 */
public class GroupChatClient {
    //定义Selector
    private Selector selector;
    //定义SocketChannel
    private SocketChannel socketChannel;
    //定义ServerHost
    private final static String SERVER_HOST = "127.0.0.1";
    //定义 服务端口号
    private final static int PORT = 6667;

    public GroupChatClient() {
        try {
            //定义Selector
            selector = Selector.open();
            //定义SocketChannel
            socketChannel = SocketChannel.open(new InetSocketAddress(SERVER_HOST, PORT));
            //SocketChannel设置非阻塞
            socketChannel.configureBlocking(false);
            //SocketChannel注册到Selector
            socketChannel.register(selector, SelectionKey.OP_READ);
            System.out.println(socketChannel.getLocalAddress() + " 客户端启动 ");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 发送消息到服务器
     */
    private void sendInfo() {
        try {
            //定义扫描器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                //获取控制台输入内容
                String str = scanner.next();
                str = socketChannel.getLocalAddress().toString().substring(1) + " 说 " + str;
                //创建Buffer
                ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
                byteBuffer.put(str.getBytes());
                //Buffer切换读模式
                byteBuffer.flip();

                //写入Channel
                socketChannel.write(byteBuffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 从服务器接收消息
     */
    private void receiveInfo() {
        try {
            //监听
            while (true) {
                //获取Channel注册到Selector此时触发事件的Channel个数
                int select = selector.select(1000);
                if (select == 0) {
                    continue;
                }
                //获取SelectionKey
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    //获取SelectionKey
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        //通过SelectionKey反向获取SocketChannel
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        //创建Buffer
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        //读取客户端消息
                        channel.read(byteBuffer);
                        System.out.println(new String(byteBuffer.array()));
                    }
                    //清除SelectionKey
                    iterator.remove();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatClient groupChatClient = new GroupChatClient();

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                groupChatClient.receiveInfo();
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                groupChatClient.sendInfo();
            }
        });
    }

}
