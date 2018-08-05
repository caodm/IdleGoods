package com.cdm.idlefish.base;

import android.content.Intent;
import android.os.Bundle;

import com.cdm.activity.BaseActivity;
import com.cdm.annotation.MustLogin;
import com.cdm.idlefish.session.Session;
import com.cdm.idlefish.utils.LoadingDialogUtil;
import com.cdm.idlefish.view.LoadDialog;


public abstract class BaseIdleFishActivity extends BaseActivity {
    private LoadDialog loadDialog;
    public static final int LOGIN_RESULT = 9000;

    @Override
    protected void initView() {
        if (loadDialog == null) {
            loadDialog = LoadingDialogUtil.getLoadDialog(this);
        }
    }

    /**
     * 显示加载动画
     */
    public void showLoadingView(){
        LoadingDialogUtil.showDialog(loadDialog, this);
    }

    /**
     * 关闭加载动画
     */
    public void closeLoadingView(){
        LoadingDialogUtil.dismissDialog(loadDialog);
    }


    protected void startActivityAnimation(Intent intent){
        if (!Session.getInstance().isLogined()){
            String aClass = intent.getComponent().getClassName();
            try {
                Class c = Class.forName(aClass);
                MustLogin mustLogin = (MustLogin) c.getAnnotation(MustLogin.class);
                if (mustLogin != null && mustLogin.value()){
                    shouldLogin(intent);
                    return;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.startActivityAnimation(intent);
    }

    protected void startActivityWithoutExtras(Class<?> clazz) {
        if (!Session.getInstance().isLogined()){
            MustLogin mustLogin =  clazz.getAnnotation(MustLogin.class);
            if (mustLogin != null && mustLogin.value()){
                Intent intent = new Intent(this, clazz);
                shouldLogin(intent);
                return;
            }
        }
        super.startActivityWithoutExtras(clazz);
    }

    protected void startActivityWithoutExtras(Class<?> clazz ,Intent intent) {
        if (!Session.getInstance().isLogined()){
            MustLogin mustLogin =  clazz.getAnnotation(MustLogin.class);
            if (mustLogin != null && mustLogin.value()){
                shouldLogin(intent);
                return;
            }
        }
        super.startActivityWithIntent(clazz,intent);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        if (!Session.getInstance().isLogined()){
            MustLogin mustLogin =  clazz.getAnnotation(MustLogin.class);
            if (mustLogin != null && mustLogin.value()){
                Intent intent = new Intent(this, clazz);
                intent.putExtras(extras);
                shouldLogin(intent);
                return;
            }
        }
        super.startActivityWithExtras(clazz,extras);
    }


    private Intent resultIntent;
    protected void shouldLogin(Intent intent){
        resultIntent = intent;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                if (resultIntent != null){
                    startActivityAnimation(resultIntent);
                    resultIntent = null;
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
