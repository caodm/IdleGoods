package com.cdm.idlefish.dao;

import android.content.Context;

import com.cdm.idlefish.entity.BannerItem;
import com.cdm.idlefish.entity.User;
import com.cdm.idlefish.session.Session;
import com.cdm.network.HttpAuthHelper;
import com.cdm.network.interf.HttpAuthCallBack;
import com.cdm.network.model.ResultModel;

import java.util.List;
import java.util.Map;


public class LoginDao {

    //0:保密，2：女 1：男
    public static final String SEX_SECREAT = "0";
    public static final String SEX_WOMEN = "2";
    public static final String SEX_MEN = "1";

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
                return "/login.do";
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

    public void doRegister(Context context,final String name,final String pw,final String nickname,final int sex,
                           final HttpAuthCallBack<User> callBack ) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
                params.put("loginName",name);
                params.put("password",pw);
                params.put("nickName",nickname);
                params.put("sex",sex+"");
            }

            @Override
            public String getRequesetURL() {
                return "/register.do";
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

    public void doRegister2(Context context,final String name,final String pw,final String nickname,final int sex,
                           final HttpAuthCallBack<String> callBack ) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
                params.put("loginName",name);
                params.put("password",pw);
                params.put("nickName",nickname);
                params.put("sex",sex+"");
            }

            @Override
            public String getRequesetURL() {
                return "/register.do";
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

    /**
     * @param sex
     * @return
     */
    public static String getSexIndex(String sex) {
        if (sex == null) {
            return null;
        }
        if (sex.equals("保密")) {
            return SEX_SECREAT;
        } else if (sex.equals("男")) {
            return SEX_MEN;
        } else if (sex.equals("女")) {
            return SEX_WOMEN;
        }
        return null;
    }

    public static int getSexIndexFrom(String sex) {
        if (sex == null) {
            return -1;
        }
        if (sex.equals("0")) {
            return 0;
        } else if (sex.equals("1")) {
            return 1;
        } else if (sex.equals("2")) {
            return 2;
        }
        return -1;
    }

    public static String getSexIndex(int sex) {
        if (sex == 0) {
            return "保密";
        } else if (sex == 1) {
            return "男";
        } else if (sex == 2) {
            return "女";
        }
        return "";
    }

    /**
     * 上传头像接口
     *
     * @param context
     * @param callBack
     */
    public void upLoaderIcon(Context context, final String userid, final String urlpath,
                             final HttpAuthCallBack<ResultModel> callBack) {
        HttpAuthHelper helper = new HttpAuthHelper() {
            @Override
            public void setRequestParams(Map<String, String> params) {
                params.put("userId", userid);
                params.put("urlpath", urlpath);
                //http://localhost:8080/school/app/updateIcon.do?userId=1&urlpath=Icon
            }

            @Override
            public String getRequesetURL() {
                return "/updateIcon.do";
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
