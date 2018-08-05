package com.cdm.idlefish.session;

import com.cdm.idlefish.entity.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by user on 2016/10/20.
 */
public class Session {
    private static Session instance;

    private String token = "";
    private User user;
    private double mMoneyTemp;

    private DisplayImageOptions options;

    private boolean buyTicketSuccess = false;//是否购票成功

    private Session() {

    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }


    public void setUser(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public boolean isLogined(){
        if (user == null){
            return false;
        }
        return true;
    }

    public String getToken() {
        if (user != null && user.getName() != null) {
            return user.getId() + "_" + token;
        }
        return "-100_" + token;
    }

    public boolean isBuyTicketSuccess() {
        return buyTicketSuccess;
    }

    public void setBuyTicketSuccess(boolean buyTicketSuccess) {
        this.buyTicketSuccess = buyTicketSuccess;
    }

    public void setMoneyTemp(double temp){
        this.mMoneyTemp = temp;
    }

    public double getmMoneyTemp(){
        return mMoneyTemp;
    }

    public void clearMoneyTemp(){
        this.mMoneyTemp=0;
    }

    public DisplayImageOptions getOptions() {
        if (options == null) {
            options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)     //启用内存缓存
                    .cacheOnDisk(true)       //启用外存缓存
                    .considerExifParams(true)//启用EXIF和JPEG图像格式
                    .build();
        }
        return options;
    }
    public void setOptions(DisplayImageOptions options) {
        this.options = options;
    }

    public void clear() {
        user = null;
        token = "";
        buyTicketSuccess = false;
        options=null;
    }
}
