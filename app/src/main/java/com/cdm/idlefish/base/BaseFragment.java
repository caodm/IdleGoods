package com.cdm.idlefish.base;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;


import com.cdm.annotation.MustLogin;
import com.cdm.idlefish.session.Session;
import com.cdm.idlefish.utils.LoadingDialogUtil;
import com.cdm.idlefish.view.LoadDialog;


public class BaseFragment extends Fragment {
    private LoadDialog loadDialog;

    public static final int LOGIN_RESULT = 9000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (loadDialog == null) {
            loadDialog = LoadingDialogUtil.getLoadDialog(getActivity());
        }

    }

    /**
     * 显示加载动画
     */
    public void showLoadingView() {
        LoadingDialogUtil.showDialog(loadDialog, getActivity());
    }

    /**
     * 关闭加载动画
     */
    public void closeLoadingView() {
        LoadingDialogUtil.dismissDialog(loadDialog);
    }

    protected void startActivityWithIntent(Class<?> clazz, Intent to) {
        to.setClass(getActivity(), clazz);
        if (!Session.getInstance().isLogined()){
            MustLogin mustLogin =  clazz.getAnnotation(MustLogin.class);
            if (mustLogin != null && mustLogin.value()){
                shouldLogin(to);
                return;
            }
        }
        startActivity(to);
//        getActivity().overridePendingTransition(ren.ryt.library.R.anim.slide_in_right, ren.ryt.library.R.anim.slide_out_left);
    }
    protected void startActivityWithIntent(Intent to) {

        if (!Session.getInstance().isLogined()){
            String aClass = to.getComponent().getClassName();
            try {
                Class c = Class.forName(aClass);
                MustLogin mustLogin = (MustLogin) c.getAnnotation(MustLogin.class);
                if (mustLogin != null && mustLogin.value()){
                    shouldLogin(to);
                    return;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        startActivity(to);
//        getActivity().overridePendingTransition(ren.ryt.library.R.anim.slide_in_right, ren.ryt.library.R.anim.slide_out_left);
    }

    private Intent resultIntent;
    protected void shouldLogin(Intent intent){
        resultIntent = intent;
    }

    protected <T extends View> T $(int id,View view) {
        return (T) view.findViewById(id);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                if (resultIntent != null){
                    startActivityWithIntent(resultIntent);
                    resultIntent = null;
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void startActivityWithoutExtras(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }
}
