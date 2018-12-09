package com.cdm.idlefish.dao;

import android.content.Context;
import android.util.Log;

import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.entity.HomeGoodEntity;
import com.cdm.network.HttpAuthHelper;
import com.cdm.network.interf.HttpAuthCallBack;
import com.cdm.network.model.ResultModel;

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
    public void doGetGoodsList(Context context,final int page,final int type,final int memberId,
                               final HttpAuthCallBack<List<HomeGoodEntity>> callBack ) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
                params.put("page",page+"");
                params.put("type",type+"");
                params.put("memberId",memberId+"");
            }

            @Override
            public String getRequesetURL() {
                return "/goodsList.do";
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
     * 收藏物品
     * @param context
     * @param callBack
     */
    public void doCollectGoods(Context context,final int memberId,final int goodsId ,final int type,
                               final HttpAuthCallBack<ResultModel> callBack ) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
                params.put("memberId",memberId+"");
                params.put("goodsId",goodsId+"");
                params.put("type",type+"");
            }

            @Override
            public String getRequesetURL() {
                return "/collect.do";
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

    public void doFaBuGoods(Context context,final int userid,final HomeGoodEntity entity,
                               final HttpAuthCallBack<ResultModel> callBack ) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
                params.put("memberId",userid+"");
                params.put("title",entity.getTitle()+"");
                params.put("content",entity.getContent()+"");
                params.put("image",entity.getImage()+"");
                params.put("type",entity.getType()+"");
                params.put("phone",entity.getPhone()+"");
                params.put("weight",entity.getWeight()+"");
                params.put("sellPrice",entity.getSellPrice()+"");
                params.put("originalPric",entity.getOriginalPrice()+"");
                Log.i("daming","   userid = "+userid +"entity="+entity.toString());
            }

            @Override
            public String getRequesetURL() {
                return "/goodsRelease.do";
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
