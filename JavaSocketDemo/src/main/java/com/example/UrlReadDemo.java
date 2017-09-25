package com.example;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;




/**
 * 使用URL读取页面内容
 * Created by xuning on 17/6/6.
 */
public class UrlReadDemo {

    public static void main(String[] args) throws IOException {

        InputStream is = null;
        ByteArrayOutputStream baos = null;
        InputStreamReader isr = null;
        BufferedReader bis = null;

        try {
            //创建URL实例
            URL url = new URL("http://www.baidu.com");
            //获取字节的输入流
            is = url.openStream();
            /*
            baos = new ByteArrayOutputStream();
            int length = 0;
            byte[] bytes = new byte[1024];
            while((length = is.read(bytes)) != -1){
                baos.write(bytes);
            }
            System.out.println("页面内容：" + baos.toString());*/


            //将自己饿输入流转换成为字符输入流
            isr = new InputStreamReader(is, "utf-8");
            //为字符输入流添加缓冲
            bis = new BufferedReader(isr);
            String data = bis.readLine();
            System.out.println("页面内容：\n");
            while(data != null){
                System.out.println(data);
                data = bis.readLine(); //读取下一行
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(is != null) {
                is.close();
            }
            if(baos != null){
                baos.close();
            }
            if(isr != null){
                is.close();
            }
            if(bis != null){
                bis.close();
            }
        }
    }
}
