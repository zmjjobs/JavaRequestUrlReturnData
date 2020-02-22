package com.zmj.utils;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mengjun
 * @date 2018/12/7 15:59
 * @desc
 */
public class FileUtil {

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     * @param fileName
     * @return
     */
    public static Set<String> readFileByLines(String fileName) {
        Set<String> set = new HashSet<>();
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    set.add(tempString.trim());
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
        }
        return set;
    }

    public static File createDirectory(String path){
        File file=new File(path);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        file.setExecutable(true);
        return file;
    }


    public static boolean writeObject(Object object,String filePath) {
        ObjectOutputStream objectOutputStream = null;
        try {
            FileOutputStream outStream = new FileOutputStream(filePath);
            objectOutputStream = new ObjectOutputStream(outStream);
            objectOutputStream.writeObject(object);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static Object readObject(String filePath){
        ObjectInputStream objectInputStream = null;
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                FileInputStream freader = new FileInputStream(filePath);
                objectInputStream = new ObjectInputStream(freader);
                return objectInputStream.readObject();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String RegexString(String targetStr, String patternStr) {
        // 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容
        // 相当于埋好了陷阱匹配的地方就会掉下去
        Pattern pattern = Pattern.compile(patternStr);
        // 定义一个matcher用来做匹配
        Matcher matcher = pattern.matcher(targetStr);
        // 如果找到了
        if (matcher.find()) {
            // 打印出结果
            return matcher.group(1);
        }
        return "Nothing";
    }
}
