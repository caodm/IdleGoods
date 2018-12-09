package com.cdm.network;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cdm.application.BaseApplication;
import com.cdm.network.interf.HttpAuthCallBack;
import com.cdm.network.model.Method;
import com.cdm.network.model.ResponseCode;
import com.cdm.network.model.ResultModel;
import com.cdm.utils.NetworkUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
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
 * @文件名称: HttpAuthHelper
 * @作者 :
 * @文件描述:封装HTTP请求的业务操作支持token权限认证
 * @创建时间: 2016/7/20日 13:44
 * ******************************************************************
 */
public abstract class HttpAuthHelper extends HttpUtils {

    public static final String TAG = HttpAuthHelper.class.getSimpleName();

    private Context mContext;
    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";
    /**
     * token失效广播
     */
    public static final String ACTION_UNAUTHORIZED = "cdm.http.unauthorized";

    //默认使用POST方式发送请求
    public Method getRequestMethod() {
        return Method.POST;
    }

    //发送异步HTTP请求
    public void sendRequest(Context context) {
        if (!NetworkUtils.isNetworkConnected(context)) {
//            网络连接异常
            getResultCallback().onFailed(ResultModel.parseError("网络连接异常", ResponseCode.FAILED.getStatus()));
            return;
        }
        mContext = context;
        Map<String, String> params = new HashMap<>();
        setRequestParams(params);
        String url = BaseApplication.mServerConfig.getHttpServer();
        Request request;
        switch (getRequestMethod()) {
            case POST:
                FormBody.Builder builder = new  FormBody.Builder();
                //FormEncodingBuilder builder = new FormEncodingBuilder();
                builder.add("method", "post");
                for (String key : params.keySet()) {
//                    try {
//                        builder.add(key, URLEncoder.encode(params.get(key), "UTF-8"));
//                    }catch (UnsupportedEncodingException e){
//
//                    }
                    builder.add(key, params.get(key));
                }

                RequestBody requestBody = builder.build();
                url = url + getRequesetURL();
                request = new Request.Builder()
                        .addHeader(AUTHORIZATION, getToken())
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
                    } catch (UnsupportedEncodingException e) {

                    }
                }
                url = url + getRequesetURL() + paramStr.toString();
                request = new Request.Builder()
                        .addHeader(AUTHORIZATION, getToken())
                        .url(url)
                        .build();
                break;
            default:
                return;
        }
        if (request != null) {
            Log.i(TAG, " HTTP: " + url);
            deliveryResult(request);
        }
    }


    //添加请求参数
    public abstract void setRequestParams(Map<String, String> params);

    //请求方法名
    public abstract String getRequesetURL();

    //登录权限Token信息
    public abstract String getToken();

    public abstract HttpAuthCallBack getResultCallback();

    protected void deliveryResult(Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                onFailure(call.request(),e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onResponse(response);
            }

            public void onFailure(Request request, IOException e) {
                if (getResultCallback() != null) {
                    getResultCallback().onFailed(ResultModel.parseError("发送请求失败",
                            ResponseCode.FAILED.getStatus()));
                }
            }

            public void onResponse(Response response) throws IOException {
                HttpAuthCallBack callback = getResultCallback();
                if (callback == null) {
                    return;
                }
                int code = response.code();//HTTP status code
                if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {//token无效，或未登录授权
                    getResultCallback().onFailed(ResultModel.parseError("登录失效，或账号在其他设备登录",
                            ResponseCode.UNAUTHORIZED.getStatus()));

                    Intent intent = new Intent();
                    intent.setAction(ACTION_UNAUTHORIZED);
                    mContext.sendBroadcast(intent);
                    return;
                }
                final String respData = response.body().string();
                try {
                    JSONObject jobj = new JSONObject(respData);
                    ResultModel resp = new ResultModel();
                    resp.setCode(jobj.getInt("code"));
                    resp.setMessage(URLDecoder.decode(jobj.getString("msg"), "UTF-8"));
                    String data = "";
                    if (jobj.has("data")) {
                        data = URLDecoder.decode(jobj.getString("data"), "UTF-8");
                    }

                    if (code != 200) {
                        //返回失败信息
                        resp.data = data;
                        getResultCallback().onFailed(resp);
                        return;
                    } else if (callback.mType == ResultModel.class) {
                        resp.data = data;
                        //成功了，只需要返回状态信息的请求
                        getResultCallback().onSucceeded(resp);
                        return;
                    } else if (callback.mType == String.class) {
                        resp.data = data;
                    } else {
//                        GsonBuilder gb = new GsonBuilder();
//                        gb.registerTypeAdapter(String.class, new JSONConverter());
//                        Gson mGson = gb.create();
                        Gson mGson = new Gson();
                        resp.data = mGson.fromJson(data, callback.mType);
                    }
                    Log.i(TAG, " message: " + resp.getMessage());
                    Log.i(TAG, " data: " + resp.data);
                    if (resp.getCode() == ResponseCode.FAILED.getStatus()){
                        getResultCallback().onFailed(resp);
                    } else if(resp.getCode() == ResponseCode.SUCCESS.getStatus()){
                        getResultCallback().onSucceeded(resp.data);
                    }
                } catch (com.google.gson.JsonParseException e)//Json解析的错误
                {
                    getResultCallback().onFailed(ResultModel.parseError("数据解析错误",
                            ResponseCode.FAILED.getStatus()));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "  JSONException -- e =" + e.getMessage());
                    getResultCallback().onFailed(ResultModel.parseError("服务器连接异常",
                            ResponseCode.FAILED.getStatus()));
                }
            }
        });
    }

}
