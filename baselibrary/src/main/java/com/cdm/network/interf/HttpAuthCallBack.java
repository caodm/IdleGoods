package com.cdm.network.interf;

import com.cdm.network.model.ResultModel;

import okhttp3.Request;


/*******************************************************************
 * @文件名称: HttpAuthCallBack
 * @作者 : cdm
 * @文件描述:
 * @创建时间: 2016/7/20日 11:11
 * ******************************************************************
 */
public abstract class HttpAuthCallBack<S> extends ResultCallback{

    public abstract void onSucceeded(S successObj);

    public abstract void onFailed(ResultModel failObj);

    @Override
    public void onError(Request request, Exception e) {

    }

    @Override
    public void onResponse(Object response) {

    }

}
