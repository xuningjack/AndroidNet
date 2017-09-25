package udp_socket_server_client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



/**
 * 基于UDP的用户登陆
 */
public class UDPServer {

    public static void main(String[] args) throws IOException {

        //服务器接受客户端发送数据

        //Step1 创建服务器端的DatagramSocket，指定端口
        DatagramSocket socket = new DatagramSocket(8888);

        //Step2 创建数据报，用于接收客户端发送的数据
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);

        //Step3 接受客户端发送的数据
        socket.receive(packet);  //此方法在接受数据报之前一直阻塞

        //Step4 读取数据
        String info = new String(data, 0, packet.getLength());

        System.out.println("我是服务器，客户端说：" + info);



        //服务器响应客户端
        //step1 客户端的IP、端口、数据
        InetAddress clientAddress = packet.getAddress();
        int port = packet.getPort();
        byte[] bytesBack = "欢迎您！！".getBytes();
        //step2 创建数据报，包括响应的信息
        DatagramPacket packetBack = new DatagramPacket(bytesBack,
                bytesBack.length, clientAddress, port);
        //step3 响应客户端
        socket.send(packetBack);
        //step4 关闭相应的信息
        socket.close();

    }



}
