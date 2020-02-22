package com.zmj.entity;

/**
 * @author mengjun
 * @date 2018/12/6 21:53
 * @desc
 */
public class FillData {
    private Long c_time;
    private String userId;
    private String imageId;
    private boolean active;
    private String galleryFigure;
    private String galleryId;
    private int likeCount;
    private String imageColorMapUrl;

    public FillData() {
    }

    public Long getC_time() {
        return c_time;
    }

    public void setC_time(Long c_time) {
        this.c_time = c_time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getGalleryFigure() {
        return galleryFigure;
    }

    public void setGalleryFigure(String galleryFigure) {
        this.galleryFigure = galleryFigure;
    }

    public String getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(String galleryId) {
        this.galleryId = galleryId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getImageColorMapUrl() {
        return imageColorMapUrl;
    }

    public void setImageColorMapUrl(String imageColorMapUrl) {
        this.imageColorMapUrl = imageColorMapUrl;
    }

    @Override
    public String toString() {
        return "FillData{" +
                "c_time=" + c_time +
                ", userId='" + userId + '\'' +
                ", imageId='" + imageId + '\'' +
                ", active=" + active +
                ", galleryFigure='" + galleryFigure + '\'' +
                ", galleryId='" + galleryId + '\'' +
                ", likeCount=" + likeCount +
                ", imageColorMapUrl='" + imageColorMapUrl + '\'' +
                '}';
    }
}
