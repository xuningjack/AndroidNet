package http_socket_server_client.multi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;



/**
 * 服务器线程处理类
 * Created by xuning on 17/6/8.
 */
public class ServerThread extends Thread{

    /**和本线程相关的Socket*/
    Socket socket;

    public ServerThread(Socket socket){
        this.socket = socket;
    }

    //线程执行的操作，响应客户端的请求
    @Override
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        try {
            //step3 获取输入流，读取客户端所发送的信息
            is = socket.getInputStream();
            //将字节流包装为字符流
            isr = new InputStreamReader(is);
            //为输入流添加缓冲
            br = new BufferedReader(isr);

            String info;
            while((info = br.readLine()) != null){  //循环读取客户端的信息
                System.out.println("我是服务器，客户端说：" + info);
            }
            socket.shutdownInput();

            //step4 获取输出流，用来响应客户端
            os = socket.getOutputStream();
            //包装为打印流
            pw = new PrintWriter(os);
            pw.write("欢迎您");
            pw.flush();  //刷新缓冲区
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //step5 关闭资源
            try {
                if(pw != null){
                    pw.close();
                }
                if(os != null){
                    os.close();
                }
                if(br != null){
                    br.close();
                }
                if(isr != null){
                    isr.close();
                }
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
