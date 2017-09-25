package com.example;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class InetAddressDemo {

    public static void main(String[] args) throws UnknownHostException {

        //获取本机的InetAddress实例
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("计算机名：" + address.getHostName());
        System.out.println("IP地址：" + address.getHostAddress());
        System.out.println("获取字节数组形式的IP地址：" +
                Arrays.toString(address.getAddress()));
        System.out.println("计算机名和IP地址：" + address);

        //根据主机名获取InetAddress实例
        InetAddress address1 = InetAddress.getByName("JackdeMacBook-Pro.local");
        System.out.println("计算机名和IP地址：" + address1);

        InetAddress address2 = InetAddress.getByName("192.168.1.104");
        System.out.println("计算机名和IP地址：" + address2);
    }


}
