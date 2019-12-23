package com.youyuan.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试BIO  同步阻塞IO
 * <p>
 * 测试项目
 * 1、使用BIO模型编写一个服务器端，监听6666端口，当有客户端连接时，就启动一个线程与之通讯。
 * 2、要求使用线程池机制改善，可以连接多个客户端.
 * 3、服务器端可以接收客户端发送的数据(telnet 方式即可)。
 * @date 2019/12/9 21:43
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        System.out.println("服务端启动");

        while (true) {
            System.out.println("监听客户端连接等待中...");
            //监听客户端socket连接,并获取客户端socket
            Socket socket = serverSocket.accept();

            //启动线程
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("当前客户端线程id:" + Thread.currentThread().getId() + " 线程名:" + Thread.currentThread().getName());
                    try {
                        handler(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 处理客户端请求并打印客户端数据
     *
     * @param socket
     */
    private static void handler(Socket socket) throws IOException {
        //存放客户端数据byte数组
        byte[] bytes = new byte[1024];

        InputStream inputStream = socket.getInputStream();

        try {
            while (true) {
                System.out.println("当前客户端线程id:" + Thread.currentThread().getId() + " 线程名:" + Thread.currentThread().getName() + " 阻塞等待读取客户端数据...");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println("客户端数据是 :" + new String(bytes));
                    bytes = new byte[1024];
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            socket.close();
        }
    }

}
