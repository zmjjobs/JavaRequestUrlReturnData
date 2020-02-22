package com.zmj.entity;

/**
 * @author mengjun
 * @date 2018/12/6 20:35
 * @desc
 */
public class Category {
    private String id;
    private String figure;
    private String name;

    public Category() {
    }

    public Category(String id, String figure, String name) {
        this.id = id;
        this.figure = figure;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", figure='" + figure + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
