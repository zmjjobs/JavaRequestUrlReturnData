package com.zmj.crawler;

import com.alibaba.fastjson.JSON;
import com.zmj.constant.Constant;
import com.zmj.entity.Colorfy;
import com.zmj.entity.Gallery;
import com.zmj.entity.Painting;
import com.zmj.entity.Volume;
import com.zmj.utils.FileUtil;
import com.zmj.utils.IOUtil;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author mengjun
 * @date 2018/12/8 15:40
 * @desc Colorfy
 */
public class ColorfyCrawler {
    public static String run (String rootPath){
        String galleries3DUrl = Constant.COLORFY_GALLERY_URL_PREFIX + "galleries3D.json";
        String imagePathKeywordSetFilePath = rootPath + "/imagePathKeywordSet.txt"; //存放图片路径关键字的文件
        String successKeywordSetFilePath = rootPath + "/successKeywordSet.txt"; //存放成功下载的关键字的文件
        File imagePathKeywordSetFile = new File(imagePathKeywordSetFilePath);
        if (!imagePathKeywordSetFile.exists() || imagePathKeywordSetFile.isDirectory()) {
            try {
                imagePathKeywordSetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return "创建imagePathKeywordSet.txt时报错";
            }
        }
        File successKeywordSetFile = new File(successKeywordSetFilePath);
        if (!successKeywordSetFile.exists() || successKeywordSetFile.isDirectory()) {
            try {
                successKeywordSetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return "创建successKeywordSet.txt时报错";
            }
        }

        Set<String> imagePathKeywordSet = null;
        try {
            imagePathKeywordSet = fetchImagePathKeywordSet(galleries3DUrl, imagePathKeywordSetFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return "Get请求异常，url=" + galleries3DUrl;
        }
        if (imagePathKeywordSet == null || imagePathKeywordSet.size() == 0) return "imagePathKeywordSet == 0";
        Set<String> successKeywordSet = (Set<String>) FileUtil.readObject(successKeywordSetFilePath);
        if (successKeywordSet == null) successKeywordSet = new HashSet<>();
        boolean flag = false;
        for (String keyword : imagePathKeywordSet) {

            if (keyword != null) {
                if (successKeywordSet.contains(keyword)) continue;
                String jpgPath = Constant.COLORFY_GALLERY_URL_PREFIX + "thumbnails/"+keyword+".jpg";
                flag = IOUtil.downloadPicture(jpgPath, rootPath + keyword + ".jpg", 5);
                if (!flag) break; //如果有一个报错，则停止图片下载
                String pngPath = Constant.COLORFY_GALLERY_URL_PREFIX + "images/" + keyword + ".png";
                flag = IOUtil.downloadPicture(pngPath, rootPath + keyword + ".png", 5);
                if (!flag) break; //如果有一个报错，则停止图片下载
                successKeywordSet.add(keyword);
                FileUtil.writeObject(successKeywordSet,successKeywordSetFilePath);
            }

        }
        if (flag && successKeywordSet.size() > 0) {
            imagePathKeywordSet.removeAll(successKeywordSet);
            FileUtil.writeObject(imagePathKeywordSet,imagePathKeywordSetFilePath);
            return "success!!!!!!!!!!!!!图片下载完毕!!!!!!!!!!!!!!!!!!!!!";
        }
        return "----------------end---------------";
    }

    private static Set<String> fetchImagePathKeywordSet(String galleries3DUrl,String imagePathKeywordSetFilePath) throws IOException {
        Set<String> imagePathKeywordSet = (Set<String>)FileUtil.readObject(imagePathKeywordSetFilePath);
        if (imagePathKeywordSet != null && imagePathKeywordSet.size() > 0) {
            return imagePathKeywordSet;
        }
        imagePathKeywordSet = new HashSet<>();
        String result = IOUtil.SendGet(galleries3DUrl);
        if (result == null && "".equals(result)) {
            System.out.println("请求结果为空，url=" + galleries3DUrl);
            return imagePathKeywordSet;
        }
        if (result.contains("Error")) {
            System.out.println("url可能有误，url=" + galleries3DUrl);
            return imagePathKeywordSet;
        }

        Colorfy colorfy = JSON.parseObject(result, Colorfy.class);
        if (colorfy != null) {
            List<Gallery> galleries = colorfy.galleries;
            if (galleries != null) {
                for (int i = 0,len = galleries.size();i < len;i++) {
                    Gallery gallery = galleries.get(i);
                    List<Volume> volumes = gallery.volumes;
                    if (volumes != null) {
                        for (int j = 0,length = volumes.size(); j < length;j++) {
                            Volume volume = volumes.get(j);
                            if (volume != null) {
                                List<Painting> paintings = volume.paintings;
                                if(paintings != null) {
                                    for (int n = 0,size = paintings.size();n < size;n++) {
                                        Painting painting = paintings.get(n);
                                        if (painting != null) {
                                            imagePathKeywordSet.add(painting.img_path);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (imagePathKeywordSet.size() > 0) {
            FileUtil.writeObject(imagePathKeywordSet,imagePathKeywordSetFilePath);
        }
        return imagePathKeywordSet;
    }
}
