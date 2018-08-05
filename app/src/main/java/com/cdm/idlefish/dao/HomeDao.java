package com.cdm.idlefish.dao;

import android.content.Context;

import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.entity.HomeGoodEntity;
import com.cdm.network.HttpAuthHelper;
import com.cdm.network.interf.HttpAuthCallBack;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/10/22 0022.
 */
public class HomeDao {

    private static HomeDao instanse;

    private HomeDao(){

    }

    public static HomeDao getInstanse(){
        if(instanse == null){
            instanse = new HomeDao();
        }
        return instanse;
    }

    /**
     * 获取物品列表
     * @param context
     * @param callBack
     */
    public void doGetGoodsList(Context context, final HttpAuthCallBack<List<HomeGoodEntity>> callBack ) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
            }

            @Override
            public String getRequesetURL() {
                return "/appIndex/GoodsList";
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

    /**
     * 根据类型搜索
     * @param context
     * @param callBack
     */
    public void doGetGoodsListFromType(Context context,final String type,
                                       final HttpAuthCallBack<List<HomeGoodEntity>> callBack ) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
                params.put(Constants.GOODS_TYPE,type);
            }

            @Override
            public String getRequesetURL() {
                return "/appIndex/getGoodsByType";
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

    /**
     * 根据标题查找
     * @param context
     * @param title
     * @param callBack
     */
    public void doGetGoodsListFromTitle(Context context,final String title,
                                       final HttpAuthCallBack<List<HomeGoodEntity>> callBack ) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
                params.put(Constants.GOODS_TITLE,title);
            }

            @Override
            public String getRequesetURL() {
                return "/appIndex/getGoodsByTitle";
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

    /**
     * 根据id获取
     * @param context
     * @param id
     * @param callBack
     */
    public void doGetGoodsById(Context context,final String id,
                                        final HttpAuthCallBack<HomeGoodEntity> callBack ) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
                params.put(Constants.GOODS_ID,id);
            }

            @Override
            public String getRequesetURL() {
                return "/appIndex/getGoodsDetail";
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
