package http_socket_server_client.multi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * 基于TCP协议的Socket通信，实现用户登陆的Client
 */
public class MultiClient {


    public static void main(String[] args) {

        try {
            //step1 创建客户端的Socket，指定服务器地址和端口
            Socket socket = new Socket("localhost", 8888);
            System.out.println("---客户端连接上服务器---");

            //step2 获取输出流，想服务器发送信息
            OutputStream os = socket.getOutputStream();   //字节流
            PrintWriter pw = new PrintWriter(os);   //将输出流包装为打印流
            pw.write("用户名：Jack 密码：Jack");   //todo update 向Server发送数据
            pw.flush();
            socket.shutdownOutput();

            //step3 获取输入流，读取服务器的响应
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is); //字符输入流
            BufferedReader br = new BufferedReader(isr);  //缓冲输入流
            String info;
            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端，服务器说：" + info);
            }

            //step4 关闭资源
            br.close();
            isr.close();
            is.close();
            pw.close();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
