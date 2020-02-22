package com.zmj.crawler;

import com.alibaba.fastjson.JSON;
import com.zmj.constant.Constant;
import com.zmj.entity.Category;
import com.zmj.entity.ImageInfo;
import com.zmj.utils.FileUtil;
import com.zmj.utils.IOUtil;

import java.io.*;
import java.util.*;

/**
 * @author mengjun
 * @date 2018/12/6 20:22
 * @desc 网址 CWAPI_CATEGORYID_URL
 */
public class CwapiCrawler {


    private static List<String> fetchCategoryIdList(String result){
        List<String> categorieIds = new ArrayList<>();
        String data = result.substring(result.indexOf("["),result.lastIndexOf("]")+1);
        if (data != null) {
            List<Category> categoryList = JSON.parseArray(data, Category.class);
            for (int i = 0,len = categoryList.size();i < len;i++) {
                categorieIds.add(categoryList.get(i).getId());
            }
        } else {
            System.out.println("获取categoryIDs时 data = null");
        }
        return categorieIds;
    }


    /**
     * 获取DirectoryName与ImagePath的Map
     * @param rootPath 本地存储文件的根目录
     * @param pageSize 每页下载大小
     * @param categoryIdsPath
     * @param directoryNameImagePathMapFilePath
     * @return
     */
    private static HashMap<String, ArrayList<String>> fetchDirectoryNameImagePathMap(String rootPath,int pageSize,String categoryIdsPath,String directoryNameImagePathMapFilePath) throws IOException {
        HashMap<String, ArrayList<String>> categoryImagePathMap = null;
        Object object = FileUtil.readObject(directoryNameImagePathMapFilePath);
        if (object != null) {
            categoryImagePathMap = (HashMap<String, ArrayList<String>>) object;
            if (categoryImagePathMap.size() > 0) return categoryImagePathMap;
        } else {
            categoryImagePathMap = new HashMap<>();
        }
        Set<String> localExistCategoryIdSet = getLocalExistCategoryIds(categoryIdsPath); //获取本地已经下载完毕的categoryIDs

        String result = IOUtil.SendGet(Constant.CWAPI_CATEGORYID_URL); // 访问链接并获取页面内容

        if (result == null || "".equals(result)) {
            System.out.println("发送URL请求的结果没有拿到，url=" + Constant.CWAPI_CATEGORYID_URL);
            return categoryImagePathMap;
        }
        if (!result.contains("\"message\":\"OK\"")) {
            System.out.println("发送URL请求失败或有误，url=" + Constant.CWAPI_CATEGORYID_URL);
            return categoryImagePathMap;
        }
        List<String> categoryIdList = fetchCategoryIdList(result); //通过URL拿到所有的categoryIDs

        for (int i = 0,len = categoryIdList.size();i < len;i++) {
            String categoryId = categoryIdList.get(i);
            int offset = 0;
            //如果文件中已经记录这个categoryID，那么就不用请求了，证明此时它对应的图片已经全部下载完毕
            if (localExistCategoryIdSet.contains(categoryId)) continue;

            String directoryName = rootPath+categoryId.replaceAll(":", "_");
            FileUtil.createDirectory(directoryName); //创建这个categoryID对应的所有图片下载目录

            //分页一直取，直到取完为止
            boolean flag = true;
            ArrayList<String> imagePathList = new ArrayList<>();
            while(flag) {
                String imageRequestPath = Constant.CWAPI_IMAGEPATH_URL_PREFIX + "?size="+pageSize+"&offset="+offset+"&categoryId=" + categoryId;
                result = IOUtil.SendGet(imageRequestPath);
                if (result == null || "".equals(result)) {
                    System.out.println("imageRequestPath=" + imageRequestPath + ",时，result为空");
                    flag = false;
                } else {
                    String imageListStr = result.substring(result.indexOf("["),result.lastIndexOf("]")+1);
                    if (imageListStr == null) {
                        System.out.println("imageRequestPath=" + imageRequestPath + ",result=" + result + ",时，imageListStr为空");
                        flag = false;
                    } else {
                        List<ImageInfo> imageInfoList = JSON.parseArray(imageListStr, ImageInfo.class);
                        if (imageInfoList == null || imageInfoList.size() ==0) {
                            System.out.println("imageRequestPath=" + imageRequestPath + ",result=" + result + ",转成ImageInfo对象集合时，imageInfoList为空");
                            flag = false;
                        } else {
                            for (int j = 0 ,length = imageInfoList.size();j < length;j++) {
                                ImageInfo imageInfo = imageInfoList.get(j);
                                String figure = imageInfo.getFigure();
                                String png = imageInfo.getPng();
                                imagePathList.add(figure);
                                imagePathList.add(png);
                            }
                            categoryImagePathMap.put(directoryName,imagePathList);
                            offset+=pageSize;
                        }
                    }
                }

            }
        }
        FileUtil.writeObject(categoryImagePathMap,directoryNameImagePathMapFilePath);
        return categoryImagePathMap;
    }

    private static Set<String> getLocalExistCategoryIds(String categoryIdsPath){
        return FileUtil.readFileByLines(categoryIdsPath);
    }

    public static String run(String rootPath) {
        if (rootPath== null) return "请指定保存文件路径";
//        String rootPath = "C:/Users/11591/Desktop/CwapiCrawler/"; //本地存储的根路径
        String directoryNameImagePathMapFilePath = rootPath + "/directoryNameImagePathMap.txt"; //存放还需要再次下载的图片路径（可能因为网络中断或报错导致下载失败）
        String categoryIdsPath = rootPath + "/categoryIds.txt"; //存放已经完成下载图片任务的categoryIds
        int pageSize = 20; //每页请求个数
        int sleepSecond = 5; //每下载一张图片的间隔为5s

        File file = new File(categoryIdsPath);
        if (!file.exists() || file.isDirectory()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return "创建categoryIds.txt时报错";
            }
        }
        PrintStream printStream = null;
        try {
            FileOutputStream out = new FileOutputStream(file,true);
            printStream = new PrintStream(out,true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (printStream != null) {
                printStream.close();
            }
            return "将categoryIds.txt导入到打印流时报错";
        }

        HashMap<String, ArrayList<String>> categoryImagePathMap = null;
        try {
            categoryImagePathMap = fetchDirectoryNameImagePathMap(rootPath, pageSize, categoryIdsPath,directoryNameImagePathMapFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return "sendGet请求时出错";
        }
        if (categoryImagePathMap.size() == 0) return "categoryImagePathMap.size() == 0";
        boolean flag = false;
        ArrayList<String> successDirectoryNameList = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : categoryImagePathMap.entrySet()) {
            String directoryName = entry.getKey();
            ArrayList<String> imagePathList = entry.getValue();

            for (int i = 0,len = imagePathList.size();i < len;i++) {
                String imagePath = imagePathList.get(i);
                String localImageFullPath = directoryName + imagePath.substring(imagePath.lastIndexOf("/"),imagePath.length());
                flag = IOUtil.downloadPicture(imagePath, localImageFullPath,sleepSecond);
                if (!flag) break; //如果有一个报错，则停止这个categoryID的图片下载
            }

            if (flag) {
                String categoryId = directoryName.substring(directoryName.lastIndexOf("/") + 1,directoryName.length()).replaceAll("_",":");
                printStream.append(categoryId).append("\n");
                successDirectoryNameList.add(directoryName);
            }
        }
        if (successDirectoryNameList.size() > 0) {
            for (int i = 0,len = successDirectoryNameList.size();i < len;i++) {
                categoryImagePathMap.remove(successDirectoryNameList.get(i));
            }
            FileUtil.writeObject(categoryImagePathMap,directoryNameImagePathMapFilePath);
        }

        if (printStream != null) {
            printStream.close();
        }
        return "success！！！！！！！！抓取完毕！！！！！！！！！！！！！！！！！";
    }
}
