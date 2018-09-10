package com.cdm.idlefish.dao;

import android.content.Context;

import com.cdm.idlefish.entity.BannerItem;
import com.cdm.idlefish.entity.User;
import com.cdm.network.HttpAuthHelper;
import com.cdm.network.interf.HttpAuthCallBack;

import java.util.List;
import java.util.Map;


public class LoginDao {

    private static LoginDao instanse;

    private LoginDao(){

    }

    public static LoginDao getInstanse(){
        if(instanse == null){
            instanse = new LoginDao();
        }
        return instanse;
    }

    /**
     * 登录接口
     * @param context
     * @param name
     * @param pw
     * @param callBack
     */
    public void doLogin(Context context,final String name,final String pw, final HttpAuthCallBack<User> callBack ) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
                params.put("name",name);
                params.put("password",pw);
            }

            @Override
            public String getRequesetURL() {
                return "/userManager/login";
            }

            @Override
            public String getToken() {
                return "";
            }

            @Override
            public HttpAuthCallBack getResultCallback() {
                return callBack;
            }
        };
        helper.sendRequest(context);
    }

    public void doRegister(Context context,final String name,final String pw,
                           final HttpAuthCallBack<User> callBack ) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
                params.put("name",name);
                params.put("password",pw);
            }

            @Override
            public String getRequesetURL() {
                return "/appIndex/register";
            }

            @Override
            public String getToken() {
                return "";
            }

            @Override
            public HttpAuthCallBack getResultCallback() {
                return callBack;
            }
        };
        helper.sendRequest(context);
    }

    public void doGetBannerList(Context context, final HttpAuthCallBack<List<BannerItem>> callBack ) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
            }

            @Override
            public String getRequesetURL() {
                return "/appIndex/SlidesList";
            }

            @Override
            public String getToken() {
                return "";
            }

            @Override
            public HttpAuthCallBack getResultCallback() {
                return callBack;
            }
        };
        helper.sendRequest(context);
    }
}
