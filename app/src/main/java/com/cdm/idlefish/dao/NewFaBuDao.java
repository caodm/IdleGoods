package com.cdm.idlefish.dao;

import android.content.Context;

import com.cdm.idlefish.entity.HomeGoodEntity;
import com.cdm.network.HttpAuthHelper;
import com.cdm.network.interf.HttpAuthCallBack;

import java.util.List;
import java.util.Map;

public class NewFaBuDao {

    private static NewFaBuDao instanse;

    private NewFaBuDao(){

    }

    public static NewFaBuDao getInstanse(){
        if(instanse == null){
            instanse = new NewFaBuDao();
        }
        return instanse;
    }


    /**
     * 发布物品
     * @param context
     * @param callBack
     */
    public void doGetGoodsList(Context context, final HomeGoodEntity entry, final HttpAuthCallBack<List<HomeGoodEntity>> callBack ) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
                params.put("user_id",entry.getUser_id()+"");
                params.put("title",entry.getTitle());
                params.put("content",entry.getContent());
                params.put("image",entry.getImage());
                params.put("type",entry.getType());
                params.put("phone",entry.getPhone());
                params.put("weight",entry.getWeight());
                params.put("sellPrice",entry.getSellPrice()+"");
                params.put("originalPrice",entry.getOriginalPrice()+"");
            }

            @Override
            public String getRequesetURL() {
                return "/appIndex/newfabu";
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
