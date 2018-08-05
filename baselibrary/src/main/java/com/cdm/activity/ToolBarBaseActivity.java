package com.cdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cdm.helper.ToolBarHelper;

/**
 * Created by Administrator on 2017/12/2 0002.
 */

public class ToolBarBaseActivity extends BaseActivity{

    private ToolBarHelper mToolBarHelper;

    private Toolbar mToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());
    }

    @Override
    protected int setLayoutResourceID() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolBarHelper = new ToolBarHelper(this,layoutResID) ;
        mToolBar = mToolBarHelper.getToolBar() ;
        setContentView(mToolBarHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(mToolBar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(mToolBar) ;
    }

    private void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0,0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }
}
