package com.cdm.network;


import android.content.Context;
import android.util.Log;

import com.cdm.application.BaseApplication;
import com.cdm.network.interf.HttpCallBack;
import com.cdm.network.model.Method;
import com.cdm.network.model.RespModel;
import com.cdm.network.model.ResponseCode;
import com.cdm.utils.NetworkUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/*******************************************************************
 * @文件名称:
 * @作者 : cdm
 * @文件描述:封装HTTP请求的业务操作
 * @创建时间: 2016/6/6日 13:44
 * ******************************************************************
 */
public abstract class HttpHelper extends HttpUtils{

    public static final String TAG = HttpHelper.class.getSimpleName();

    //默认使用POST方式发送请求
    public Method getRequestMethod(){
        return Method.POST;
    }

    //发送异步HTTP请求
    public void sendRequest(Context context) {
        if (!NetworkUtils.isNetworkConnected(context)){
            //网络连接异常
            //return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("functionId",""+getFunctionId());
        setRequestParams(params);
        String url = BaseApplication.mServerConfig.getHttpServer();
        Request request;
        switch (getRequestMethod()){
            case POST:
                FormBody.Builder builder = new  FormBody.Builder();
                //FormEncodingBuilder builder = new FormEncodingBuilder();
                for (String key : params.keySet()) {
                    try {
                        builder.add(key, URLEncoder.encode(params.get(key), "UTF-8"));
                    }catch (UnsupportedEncodingException e){

                    }
                }

                RequestBody requestBody = builder.build();
                url = url + getRequesetURL();
                request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                break;
            case GET:

                StringBuffer paramStr = new StringBuffer();
                paramStr.append("?");
                for (String key : params.keySet()) {
                    try {
                        paramStr.append(key + "="
                                + URLEncoder.encode(params.get(key), "UTF-8")
                                + "&");
                    }
                    catch (UnsupportedEncodingException e){

                    }
                }
                url = url + paramStr.toString();
                request = new Request.Builder()
                        .url(url)
                        .build();
                break;
            default:
                return;
        }
        if(request!= null){
            Log.i(TAG, " HTTP: " +url);
            deliveryResult(request);
        }
    }

    //添加请求参数
    public abstract void setRequestParams(Map<String, String> params);

    //请求方法名
    public abstract String getRequesetURL();

    //功能点编号
    public abstract int getFunctionId();

    public abstract HttpCallBack getResultCallback();

    protected void deliveryResult( Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                onFailure(call.request(),e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onResponse(response);
            }

            public void onFailure(Request request, IOException e) {
                if (getResultCallback()!=null){
                    getResultCallback().onFailed(RespModel.parseError(getFunctionId(),"发送请求失败", ResponseCode.FAILED.getStatus()));
                }
            }

            public void onResponse(Response response) throws IOException {
                HttpCallBack callback = getResultCallback();
                if (callback == null){
                    return;
                }
                final String respData = response.body().string();
                try {
                    JSONObject jobj = new JSONObject(respData);
                    RespModel resp = new RespModel();
                    resp.functionId = jobj.getInt("functionId");
                    resp.status = jobj.getInt("status");
                    resp.message = URLDecoder.decode(jobj.getString("message"),"UTF-8");
                    String data = "";
                    if (jobj.has("data")){
                        data = URLDecoder.decode(jobj.getString("data"), "UTF-8");
                    }

                    if (resp.status != 0){
                        //返回失败信息
                        resp.data = data;
                        getResultCallback().onFailed(resp);
                        return;
                    }
                    else if (callback.mType == RespModel.class){
                        resp.data = data;
                        //成功了，只需要返回状态信息的请求
                        getResultCallback().onSucceeded(resp);
                        return;
                    }
                   else if (callback.mType == String.class) {
                        resp.data = data;
                    } else {
                        Gson mGson = new Gson();
                        resp.data = mGson.fromJson(data, callback.mType);
                    }
                    Log.i(TAG, " message: " +resp.message);
                    Log.i(TAG, " data: " +resp.data);
                    getResultCallback().onSucceeded(resp.data);
                }  catch (com.google.gson.JsonParseException e)//Json解析的错误
                {
                    getResultCallback().onFailed(RespModel.parseError(getFunctionId(),"数据格式不正确", ResponseCode.FAILED.getStatus()));
                }
                catch (JSONException e){
                    e.printStackTrace();
                    Log.e(TAG, "  JSONException -- e =" + e.getMessage());
                    getResultCallback().onFailed(RespModel.parseError(getFunctionId(),"协议格式不正确", ResponseCode.FAILED.getStatus()));
                }
            }
        });
    }

}
