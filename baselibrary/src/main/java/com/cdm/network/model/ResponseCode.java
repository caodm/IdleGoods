package com.cdm.network.model;

/*******************************************************************
 * @文件名称:
 * @作者 : cdm
 * @文件描述:
 * @创建时间: 2016/6/6日 16:55
 * ******************************************************************
 */
public enum  ResponseCode {
    SUCCESS(0,"请求成功"),
    FAILED(1,"请求失败"),
    UNAUTHORIZED(401,"未授权的用户");

    private int status;
    private String message;

    public int getStatus(){
        return status;
    }
    public String getMessage(){
        return message;
    }
    private ResponseCode(int status,String message){
        this.status = status;
        this.message = message;
    }
}
