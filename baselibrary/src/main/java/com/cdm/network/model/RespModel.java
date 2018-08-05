package com.cdm.network.model;

/*******************************************************************
 * @文件名称:
 * @作者 :
 * @文件描述:
 * @创建时间: 2016/6/6日 16:02
 * ******************************************************************
 */
public class RespModel<T> {
    //功能点编号
    public int functionId;
    //应答描述
    public String message;
    //应答码：int	0：成功，1：失败提示信息
    public int status =  ResponseCode.SUCCESS.getStatus();
    //具体业务数据
    public T data;

    public static RespModel parseError(int functionId,String msg,int status){
        RespModel respModel = new RespModel();
        respModel.functionId = functionId;
        respModel.message = msg;
        respModel.status = status;
        return respModel;
    }
}
