package com.cdm.network.interf;

import com.cdm.network.model.RespModel;

import okhttp3.Request;


/*******************************************************************
 * @文件名称: HttpCallBack
 * @作者 : cdm
 * @文件描述:
 * @创建时间: 2016/6/6日 14:11
 * ******************************************************************
 */
public abstract class HttpCallBack <S> extends ResultCallback{

    public abstract void onSucceeded(S successObj);

    public abstract void onFailed(RespModel failObj);

    @Override
    public void onError(Request request, Exception e) {

    }

    @Override
    public void onResponse(Object response) {

    }

}
