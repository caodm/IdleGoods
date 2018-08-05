package com.cdm.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import shareplatform.cdm.com.baselibrary.R;

/**
 * Created by Administrator on 2017/12/2 0002.
 */

public class ToolBarHelper {

    private Context mContext;
    private FrameLayout mContentView;
    private View mUserView;
    private LayoutInflater mInflater;
    private Toolbar mToolBar;


    /**
     * 1、toolbar是否悬浮在窗口之上
     * 2、toolbar的高度获取
     **/
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    public ToolBarHelper(Context context, int layoutId){
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        initContentView();
        initToolBar();
        initUserView(layoutId);
    }

    private void initContentView(){
        mContentView = new FrameLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    private void initToolBar() {
        /*通过inflater获取toolbar的布局文件*/
        View toolbar = mInflater.inflate(R.layout.toolbar, mContentView);
        mToolBar = (Toolbar) toolbar.findViewById(R.id.tool_bar);
    }

    private void initUserView(int id) {
        mUserView = mInflater.inflate(id, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);
        /*获取主题中定义的悬浮标志*/
        boolean overly = typedArray.getBoolean(0, false);
        /*获取主题中定义的toolbar的高度*/
        @SuppressLint("ResourceType")
        int toolBarSize = (int) typedArray.getDimension(1,
                (int) mContext.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();
        /*如果是悬浮状态，则不需要设置间距*/
        params.topMargin = overly ? 0 : toolBarSize;
        mContentView.addView(mUserView, params);
    }

    public FrameLayout getContentView() {
        return mContentView;
    }

    public Toolbar getToolBar() {
        return mToolBar;
    }
}
