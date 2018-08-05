package com.cdm.application;

import android.app.Application;

import com.cdm.network.interf.IServerConfig;
import com.cdm.utils.ToastUtils;


/**
 * Created by
 * Date:2016/6/3
 * Time:10:59
 */
public class BaseApplication extends Application {
    private static BaseApplication mInstance;

    public static IServerConfig mServerConfig;
    /**
     * Activity 栈管理器
     */
    private ActivityStackManager mActivityStackManager = null;

    public ActivityStackManager getActivityStackManager() {
        return mActivityStackManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ToastUtils.init(this);
        mActivityStackManager = ActivityStackManager.getInstance();
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    public static void setServerConfig(IServerConfig serverConfig){
        mServerConfig = serverConfig;
    }

    /**
     * 资源释放
     */
    public void release() {
        //清除Activity堆栈
        mActivityStackManager.exitActivity();
        //释放单例变量
        System.gc();
        System.runFinalization();
    }

}
