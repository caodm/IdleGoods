package com.cdm.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.cdm.view.UINavigationBar;

import shareplatform.cdm.com.baselibrary.R;


@SuppressLint("NewApi")
public abstract class BaseFragment extends Fragment {

    private View mContentView;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private FrameLayout mainBody;
    protected UINavigationBar navigationBar;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = View.inflate(getActivity(), R.layout.activity_base,null);
        mContext = getContext();
        mProgressDialog = new ProgressDialog(getMContext());
        mProgressDialog.setCanceledOnTouchOutside(false);
        navigationBar = $(R.id.navigationBar);
        mainBody = $(R.id.main_body);
        setContentView(setLayoutResourceID());
        init();
        setUpView();
        setUpData();
        initListener();
        return mContentView;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void setContentView(int layoutResId){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutResId,mainBody);
    }

    public void setContentView(View view){
        mainBody.removeAllViews();
        mainBody.addView(view);
    }

    /**
   *设置标题
     * @param title
   */
    public void setTitle(String title){
        if (title !=null){
            navigationBar.setTitle(title);
        }
    }
    /**
    *是否显示标题栏
    * @param show
    */
    public void showTitleBar(boolean show){
        navigationBar.setVisibility(show?View.VISIBLE:View.GONE);
    }

    /**
     * 是否显示返回按钮
     * @param show
     */
    public void  showBackView(boolean show){
        navigationBar.showBackView(show);
    }

    /**
     * 显示右按钮标签文字
     * @param label 标签文字
     * @param showBtn 是否显示右按钮
     */
    public void showRightBtn(String label,boolean showBtn){
        navigationBar.showSubmitBtn(showBtn);
        navigationBar.setSubmitBtnText(label);
    }

    protected abstract int setLayoutResourceID();

    protected void setUpData() {

    }

    protected void init() {

    }

    protected void setUpView() {
    }

    protected void initListener(){
        navigationBar.setClickListener(backBtnListener,submitBtnListener);
    }

    private View.OnClickListener backBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackClick(v);
        }
    };

    private View.OnClickListener submitBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onSubmitClick(v);
        }
    };

    protected void onBackClick(View view){

    }

    protected void onSubmitClick(View view){

    }

    protected <T extends View> T $(int id) {
        return (T) mContentView.findViewById(id);
    }

    // protected abstract View setContentView(LayoutInflater inflater, ViewGroup container);

    protected View getContentView() {
        return mContentView;
    }

    public Context getMContext() {
        return mContext;
    }

    protected ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }
}
