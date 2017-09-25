package http_socket_server_client.multi;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于TCP协议的Socket通信，实现用户登陆的Server
 */
public class MultiServer {


    public static void main(String[] args){

        ServerSocket serverSocket;
        InputStream is;
        Socket socket;
        /**记录客户端的连接数量*/
        int count = 0;

        try {
            //step1 创建一个服务器端的Socket，指定端口号并监听
            serverSocket = new ServerSocket(8888);   //指定1023以后的端口
            //step2 调用accept()方法开始监听，等待客户端的连接
            System.out.println("---服务器即将启动，等待客户端的连接---");

            //循环监听客户端的连接
            while(true){
                socket = serverSocket.accept();
                //创建线程与client通信
                ServerThread serverThread = new ServerThread(socket);
                //启动线程
                serverThread.start();

                count++;  //统计客户端的数量
                System.out.println("客户端的连接数量为：" + count);
                InetAddress address = socket.getInetAddress();
                System.out.println("当前客户端的ip：" + address.getHostAddress());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
