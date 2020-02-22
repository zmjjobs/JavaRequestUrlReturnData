package com.zmj.entity;

import java.util.List;

/**
 * @author mengjun
 * @date 2018/12/8 15:27
 * @desc
 */
public class Gallery {
    public String id;
    public String title_localization_id;
    public String title_localization_default;
    public String cover_img_path;
    public String title_color;
    public String cover_img_path_rev;
    public String title_color_rev;
    public List<Volume> volumes;

    @Override
    public String toString() {
        return "Gallerie{" +
                "id='" + id + '\'' +
                ", title_localization_id='" + title_localization_id + '\'' +
                ", title_localization_default='" + title_localization_default + '\'' +
                ", cover_img_path='" + cover_img_path + '\'' +
                ", title_color='" + title_color + '\'' +
                ", cover_img_path_rev='" + cover_img_path_rev + '\'' +
                ", title_color_rev='" + title_color_rev + '\'' +
                ", volumes=" + volumes +
                '}';
    }
}
