package com.cdm.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RelativeLayout;

import shareplatform.cdm.com.baselibrary.R;


/*******************************************************************
 * @文件名称: UINavigationBar
 * @作者 : cdm
 * @文件描述: 顶部标题导航栏视图组件
 * @创建时间: 2016/6/11 10:19
 * ******************************************************************
 */
public class UINavigationBar extends RelativeLayout {
    protected Context mContext;

    private ImageView mBtnBack;
    private TextView mTitle;
    private Button mButton;
    private RelativeLayout mNavigation;

    private RelativeLayout mRightLayout;
    public UINavigationBar(Context context) {
        super(context);
        init(context);
    }

    public UINavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context){
        mContext = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_navigationbar, this);
        mNavigation = (RelativeLayout) findViewById(R.id.navigation_container);
        mRightLayout = (RelativeLayout) findViewById(R.id.layout_submit_view);
        mBtnBack = (ImageView) findViewById(R.id.image_back);
        mTitle = (TextView) findViewById(R.id.text_title_label);
        mButton = (Button) findViewById(R.id.btn_submit_label);

    }

    public void setNavigationBg(int bgColor){
        mNavigation.setBackgroundColor(bgColor);
    }

    public void setTitle(String title){
        mTitle.setText(title);
    }

    public void setTitleSize(int size) {
        mTitle.setTextSize(size);
    }

    public void setSubmitBtnText(String submitBtnText){
        mButton.setText(submitBtnText);
    }

    public Button getSubmitBtn(){
        return mButton;
    }

    public void showSubmitBtn(boolean showRightBtn){
        mRightLayout.setVisibility(showRightBtn ? VISIBLE : INVISIBLE);
    }

    public void showBackView(boolean showBack){
        mBtnBack.setVisibility(showBack? View.VISIBLE:View.INVISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setTextAlignment(int textAlignment ){
        mTitle.setTextAlignment(textAlignment);
    }

    public void setClickListener(OnClickListener leftListener,OnClickListener rightListener){
        if (leftListener != null){
            mBtnBack.setOnClickListener(leftListener);
        }
        if (rightListener != null){
            mButton.setOnClickListener(rightListener);
        }
    }

    public void addRightView(View view){
        if (view == null)
            return;
        mRightLayout.removeAllViews();
        mRightLayout.addView(view);
    }



}
