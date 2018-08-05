package com.cdm.network.interf;

import com.cdm.network.model.Method;

import java.util.Map;


/*******************************************************************
*@文件名称:AssetsManager
*@作者:cdm
*@文件描述: 抽象HTTP请求的业务封装
*@创建时间:2016/6/6日 11:54
*******************************************************************
*/

public abstract class AbstractHttpRequest {

    //默认使用POST方式发送请求
    public Method getRequestMethod(){
        return Method.POST;
    }

    public abstract void setRequestParams(Map<String, String> params);

    public abstract String getRequesetURL();

}
