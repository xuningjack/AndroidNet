package udp_socket_server_client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



/**
 * UDP客户端
 */
public class UDPClient {

    public static void main(String[] args) throws IOException {

        //一、给服务器发送数据
        //step1 定义服务器的地址、端口号、数据
        InetAddress address = InetAddress.getByName("localhost");
        int port = 8888;
        byte[] data = "用户名：Jack  密码：123".getBytes();

        //step2 创建数据报，包括发送的相关信息
        DatagramPacket packet = new DatagramPacket(data, data.length,
                address, port);

        //step3 创建DatagramSocket
        DatagramSocket socket = new DatagramSocket();

        //step4 向服务器发送数据
        socket.send(packet);




        //二、接受服务器的返回数据
        //step1 创建数据报，接受响应的信息
        byte[] dataBack = new byte[1024];
        DatagramPacket packetBack = new DatagramPacket(dataBack, dataBack.length);
        //step2 接受服务器的响应信息
        socket.receive(packetBack);
        //step3 打印服务器的响应信息。   注意：要加入length，否则有空的乱码
        String infoBack = new String(dataBack, 0 , packetBack.getLength());
        System.out.println("服务器响应：" + infoBack);
        //step4 关闭资源
        socket.close();
    }


}
