package com.cdm.idlefish.config;

import android.text.TextUtils;
import android.util.Log;

import com.cdm.idlefish.application.IASApplication;
import com.cdm.network.interf.IServerConfig;
import com.orhanobut.hawk.Hawk;

/**
 * ******************************************************************
 *
 * @文件名称: Server
 * @作者 :
 * @文件描述: 存储服务器地址配置
 * @创建时间: 2016/5/5日 9:53
 * ******************************************************************
 */
public class Server implements IServerConfig {

    private static Server instance;
    private static final String SERVER_IP = "server_ip";
    private static final String SERVER_PORT = "server_port";
    private static String ip = "47.94.95.70";
    private static String port = "8080";

    //服务器地址配置

    private Server() {
        initServer();
    }

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    @Override
    public String getHttpServer() {
        Log.v("IP", ip);
//        return "http://" + ip  + "/school";//FHMYSQL
        return "http://" + ip + ":" + port + "/school/app";//FHMYSQL
    }

    private void initServer() {
        if (!Hawk.isBuilt()) {
            IASApplication.getIASApplication().initKVStorage();
        }
        if (Hawk.contains(SERVER_IP)) {
            String sip = Hawk.get(SERVER_IP);

            if (!TextUtils.isEmpty(sip)) {
                ip = sip;
            }
        }
        if (Hawk.contains(SERVER_PORT)) {
            String sport = Hawk.get(SERVER_PORT);
            if (!TextUtils.isEmpty(sport)) {
                port = sport;
            }
        }
    }

    public void setServerIp(String sip) {
        if (!TextUtils.isEmpty(sip)) {
            Hawk.put(SERVER_IP, sip);
            ip = sip;
        }
    }

    public void setServerPort(String sport) {
        if (!TextUtils.isEmpty(sport)) {
            Hawk.put(SERVER_PORT, sport);
            port = sport;
        }
    }

    public String getServerIp() {
        return ip;
    }

    public String getServerPort() {
        return port;
    }


}
