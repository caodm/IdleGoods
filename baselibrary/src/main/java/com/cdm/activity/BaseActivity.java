package com.cdm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import shareplatform.cdm.com.baselibrary.R;

/**
 * Created by Administrator on 2017/12/2 0002.
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());
        this.inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideSoftKeyboard();
    }

    protected <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }

    protected abstract int setLayoutResourceID();

    protected abstract void initView() ;

    protected abstract void initData();

    protected abstract void initListener();

    protected void startActivityWithoutExtras(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivity(intent);
    }

    protected void startActivityWithIntent(Class<?> clazz, Intent to) {
        to.setClass(this,clazz);
        startActivity(to);
    }

    protected void startActivityAnimation(Intent intent){
        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    /**
     * 隐藏输入法
     */
    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
