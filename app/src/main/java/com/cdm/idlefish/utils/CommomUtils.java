package com.cdm.idlefish.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by android on 18-8-10.
 */

public class CommomUtils {

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     * @param context
     * @param phoneNum
     */
    public static void diallPhone(Context context,String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 拨打电话（直接拨打电话）,需要添加<uses-permission android:name="android.permission.CALL_PHONE" />
     * @param context
     * @param phoneNum
     */
    @SuppressLint("MissingPermission")
    public void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }
}
