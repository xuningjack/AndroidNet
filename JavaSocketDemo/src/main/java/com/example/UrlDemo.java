package com.example;

import java.net.MalformedURLException;
import java.net.URL;



/**
 * URL常用方法
 * Created by xuning on 17/6/6.
 */
public class UrlDemo {

    public static void main(String[] args){

        try {
            //创建URL实例
            URL url = new URL("http://www.baidu.com");
            //?后面表示参数，#后面表示锚点
            URL baiUrl = new URL(url, "/index.html?username=jack#test");
            System.out.println("协议：" + baiUrl.getProtocol());
            System.out.println("主机：" + baiUrl.getHost());
            //如果未指定端口号，根据协议使用默认的端口，未设置返回-1
            System.out.println("端口：" + baiUrl.getPort());
            System.out.println("文件路径：" + baiUrl.getPath());
            System.out.println("文件的名称：" + baiUrl.getFile());
            System.out.println("文件相对路径：" + baiUrl.getRef());
            System.out.println("查询字符串：" + baiUrl.getQuery());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }



}
