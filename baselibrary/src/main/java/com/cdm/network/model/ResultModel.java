package com.cdm.network.model;

/*******************************************************************
 * @文件名称: ResultModel
 * @作者 : cdm
 * @文件描述: 返回结果数据模型
 * @创建时间: 2016/7/20日 11:02
 * ******************************************************************
 */
public class ResultModel<T> {
    /**
     * 返回码 int	0：成功，1：失败提示信息
     */
    private int code = ResponseCode.SUCCESS.getStatus();

    /**
     * 返回结果描述
     */
    private String msg;

    /**
     * 返回内容 具体业务数据
     */
    public T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg){
        this.msg = msg;
    }

    public T getContent() {
        return data;
    }

    public static ResultModel parseError(String msg,int status){
        ResultModel respModel = new ResultModel();
        respModel.msg = msg;
        respModel.code = status;
        return respModel;
    }
}
