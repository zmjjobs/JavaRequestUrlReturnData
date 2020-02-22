package com.zmj.entity;

import java.util.List;

/**
 * @author mengjun
 * @date 2018/12/6 21:58
 * @desc
 */
public class ImageInfo {
    private List<FillData> fillData;
    private String figure;
    private boolean isNew;
    private String region;
    private boolean free;
    private String imageId;
    private String pdf;
    private String categoryId;
    private String png;
    private Integer order;
    private boolean isDailyFree;
    private boolean actived;
    private Long dailyFreeStart;

    public List<FillData> getFillData() {
        return fillData;
    }

    public void setFillData(List<FillData> fillData) {
        this.fillData = fillData;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isDailyFree() {
        return isDailyFree;
    }

    public void setDailyFree(boolean dailyFree) {
        isDailyFree = dailyFree;
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public Long getDailyFreeStart() {
        return dailyFreeStart;
    }

    public void setDailyFreeStart(Long dailyFreeStart) {
        this.dailyFreeStart = dailyFreeStart;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "fillData=" + fillData +
                ", figure='" + figure + '\'' +
                ", isNew=" + isNew +
                ", region='" + region + '\'' +
                ", free=" + free +
                ", imageId='" + imageId + '\'' +
                ", pdf='" + pdf + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", png='" + png + '\'' +
                ", order=" + order +
                ", isDailyFree=" + isDailyFree +
                ", actived=" + actived +
                ", dailyFreeStart=" + dailyFreeStart +
                '}';
    }
}
