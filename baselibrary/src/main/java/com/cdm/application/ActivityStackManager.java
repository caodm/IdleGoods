package com.cdm.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Stack;

/**
 * Activity本地堆栈管理器
 */
public class ActivityStackManager {
    //activity的栈集
    private static Stack<Activity> activityStack;
    //实例
    private static ActivityStackManager instance;

    //构造函数
    private ActivityStackManager() {
    }

    //获得实例
    public static ActivityStackManager getInstance() {
        if (instance == null) {
            instance = new ActivityStackManager();
        }
        return instance;
    }

    // 退出栈顶Activity
    public void popActivity(Activity activity) {
        if (activity != null) {
            // 在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    // 获得当前栈顶Activity
    public Activity currentActivity() {
        Activity activity = null;
        if (activityStack!=null && !activityStack.empty())
            activity = activityStack.lastElement();
        return activity;
    }

    // 将当前Activity推入栈中
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    // 退出栈中所有Activity
    public void popAllActivityExceptOne(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 退出应用程序
     */
    public void exitActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 退出应用程序,清除所有堆栈中的Activity
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public void exitApplication(Context context) {
        try {
            exitActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * 获取应用 activity的数量
     *
     * @return
     */
    public int getStackSize() {
        if (activityStack != null) {
            return activityStack.size();
        } else {
            return 0;
        }
    }
}
