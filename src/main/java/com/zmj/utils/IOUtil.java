package com.zmj.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author mengjun
 * @date 2018/12/8 15:50
 * @desc
 */
public class IOUtil {

    /**
     * 下载图片并将图片保存到哪里
     * @param urlStr
     * @param path 下载到位置包括文件名的全名
     */
    public static boolean downloadPicture(String urlStr,String path,int sleepSecond) {
        URL url = null;
        DataInputStream dataInputStream = null;
        FileOutputStream fileOutputStream = null;
        File file = null;
        try {
            file = new File(path);
            if (file.exists()) {
                FileInputStream fi = new FileInputStream(file);
                BufferedImage sourceImg = ImageIO.read(fi);//判断图片是否损坏
                if (sourceImg != null) return true;//如果文件存在且完好，就不用下载了
            }
            file.setExecutable(true);
            file.setWritable(true);
            url = new URL(urlStr);
            dataInputStream = new DataInputStream(url.openStream());
            fileOutputStream = new FileOutputStream(file);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[9999999];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
        } catch (Exception e) {
            System.out.println("urlStr=" + urlStr + ",path=" + path + ",downloadPicture时发生错误！下载失败！");
            if (file != null) {
                file.delete(); //如果发生错误，则下载的文件有可能有损，所以要删除
            }
            e.printStackTrace();
        } finally {
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            Thread.sleep(sleepSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 发送Get请求
     * @param url
     * @return
     */
    public static String SendGet(String url) throws IOException {
        // 定义一个字符串用来存储网页内容
        String result = "";
        // 定义一个缓冲字符输入流
        BufferedReader in = null;
        // 将string转成url对象
        URL realUrl = new URL(url);
        // 初始化一个链接到那个url的连接
        URLConnection connection = realUrl.openConnection();
        connection.setConnectTimeout(1000*60);//连接1分钟
        connection.setReadTimeout(1000*60*10);//读取10分钟
        // 开始实际的连接
        connection.connect();
        // 初始化 BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        // 用来临时存储抓取到的每一行的数据
        String line;
        while ((line = in.readLine()) != null) {
            // 遍历抓取到的每一行并将其存储到result里面
            result += line;
        }
        if (in != null) {
            in.close();
        }
        return result;
    }
}
