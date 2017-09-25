package http_socket_server_client.single;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于TCP协议的Socket通信，实现用户登陆的Server
 */
public class Server {


    public static void main(String[] args){

        ServerSocket serverSocket;
        InputStream is;
        Socket socket;

        try {
            //step1 创建一个服务器端的Socket，指定端口号并监听
            serverSocket = new ServerSocket(8888);   //指定1023以后的端口
            //step2 调用accept()方法开始监听，等待客户端的连接
            System.out.println("---服务器即将启动，等待客户端的连接---");
            socket = serverSocket.accept();
            //step3 获取输入流，读取客户端所发送的信息
            is = socket.getInputStream();
            //将字节流包装为字符流
            InputStreamReader isr = new InputStreamReader(is);
            //为输入流添加缓冲
            BufferedReader br = new BufferedReader(isr);

            String info;
            while((info = br.readLine()) != null){  //循环读取客户端的信息
                System.out.println("我是服务器，客户端说：" + info);
            }
            socket.shutdownInput();

            //step4 获取输出流，用来响应客户端
            OutputStream os = socket.getOutputStream();
            //包装为打印流
            PrintWriter pw = new PrintWriter(os);
            pw.write("欢迎您");
            pw.flush();  //刷新缓冲区

            //step5 关闭资源
            pw.close();
            os.close();
            br.close();
            isr.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
