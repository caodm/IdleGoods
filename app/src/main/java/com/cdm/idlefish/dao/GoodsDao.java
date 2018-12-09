package com.cdm.idlefish.dao;

import android.content.Context;

import com.cdm.idlefish.entity.HomeGoodEntity;
import com.cdm.network.HttpAuthHelper;
import com.cdm.network.interf.HttpAuthCallBack;

import java.util.Map;

public class GoodsDao {

    //http://47.94.95.70:8080/school/app/goodsRelease.do?memberId=&title=&
    // content=&image=&type=&phone=&weight=&sellPrice=&originalPrice
    private static GoodsDao instanse;

    private GoodsDao(){

    }

    public static GoodsDao getInstanse(){
        if(instanse == null){
            instanse = new GoodsDao();
        }
        return instanse;
    }

    public void fabuGoods(Context context,final HttpAuthCallBack<HomeGoodEntity> callBack){
        HttpAuthHelper helper = new HttpAuthHelper(){
            @Override
            public void setRequestParams(Map<String, String> params) {

            }

            @Override
            public String getRequesetURL() {
                return "/goodsRelease.do";
            }

            @Override
            public String getToken() {
                return null;
            }

            @Override
            public HttpAuthCallBack getResultCallback() {
                return null;
            }
        };
    }
}
