package com.cdm.idlefish.entity;

/*******************************************************************
 * @文件名称: BannerItem
 * @作者 : sht
 * @文件描述: 轮播图实体类
 * @创建时间: 2016/7/14
 * ******************************************************************
 */
public class BannerItem {

    private int id;
    private String pic_path;
    private String name;
    private String click;
    private String appUrl;
    private String appCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicPath() {
        return pic_path;
    }

    public void setPicPath(String picPath) {
        this.pic_path = picPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClickType() {
        return click;
    }

    public void setClickType(String clickType) {
        this.click = clickType;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    @Override
    public String toString() {
        return "BannerItem{" +
                "id=" + id +
                ", picPath='" + pic_path + '\'' +
                ", name='" + name + '\'' +
                ", clickType=" + click +
                ", appUrl='" + appUrl + '\'' +
                ", appCode='" + appCode + '\'' +
                '}';
    }
}
