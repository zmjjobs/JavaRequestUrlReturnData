package com.zmj.entity;

import java.util.List;

/**
 * @author mengjun
 * @date 2018/12/8 15:20
 * @desc
 */
public class Colorfy {
    public String id;
    public String title_localization_id;
    public String title_color;
    public List<Gallery> galleries;

    @Override
    public String toString() {
        return "Colorfy{" +
                "id='" + id + '\'' +
                ", title_localization_id='" + title_localization_id + '\'' +
                ", title_color='" + title_color + '\'' +
                ", galleries=" + galleries +
                '}';
    }
}
