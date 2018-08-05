package com.cdm.idlefish.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.cdm.idlefish.R;
import com.cdm.idlefish.application.IASApplication;
import com.cdm.idlefish.fragment.ClassifyFragment;
import com.cdm.idlefish.fragment.HomeFragment;
import com.cdm.idlefish.fragment.MessageFragment;
import com.cdm.idlefish.fragment.PersonSettingsFragment;
import com.cdm.idlefish.utils.GeometryUtil;
import com.cdm.utils.ToastUtils;
import com.startsmake.mainnavigatetabbar.widget.MainNavigateTabBar;


public class MainActivity2 extends FragmentActivity {

    private static final String TAG_PAGE_HOME = "首页";
    private static final String TAG_PAGE_CITY = "分类";
    private static final String TAG_PAGE_PUBLISH = "发布";
    private static final String TAG_PAGE_MESSAGE = "消息";
    private static final String TAG_PAGE_PERSON = "我的";

    private MainNavigateTabBar mNavigateTabBar;

    private boolean isOpen = false;
    private int width;
    private int height;
    private long exitTime = 0;
    private AppCompatImageView[] imgs;
    private PointF[] points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());

        WindowManager wm = (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();


        mNavigateTabBar = (MainNavigateTabBar) findViewById(R.id.mainTabBar);
        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);
        mNavigateTabBar.addTab(HomeFragment.class, new MainNavigateTabBar.TabParam(R.drawable.tt_tab_shouye_nor, R.drawable.tt_tab_shouye_press, TAG_PAGE_HOME));
        mNavigateTabBar.addTab(ClassifyFragment.class, new MainNavigateTabBar.TabParam(R.drawable.tt_rang_nor, R.drawable.tt_rang_press, TAG_PAGE_CITY));
        mNavigateTabBar.addTab(null, new MainNavigateTabBar.TabParam(0, 0, TAG_PAGE_PUBLISH));
        mNavigateTabBar.addTab(MessageFragment.class, new MainNavigateTabBar.TabParam(R.drawable.tt_message_nor, R.drawable.tt_message_press, TAG_PAGE_MESSAGE));
        mNavigateTabBar.addTab(PersonSettingsFragment.class, new MainNavigateTabBar.TabParam(R.drawable.tt_tab_my_nor, R.drawable.tt_tab_my_press, TAG_PAGE_PERSON));

        float viewHeight = (float) (-200);
        float lindeX = (float) (-width / 5);
        float rindeX = (float) (width / 5);

        points = new PointF[2];
        points[0] = new PointF(lindeX, viewHeight);
        points[1] = new PointF(rindeX, viewHeight);

        @SuppressLint("WrongViewCast")
        AppCompatImageView leftView = (AppCompatImageView) findViewById(R.id.imageView1);
        AppCompatImageView rightView = (AppCompatImageView) findViewById(R.id.imageView2);
        imgs = new AppCompatImageView[]{leftView, rightView};
        ToastUtils.init(this);
    }

    protected int setLayoutResourceID() {
        return R.layout.activity_main_2;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigateTabBar.onSaveInstanceState(outState);
    }


    public void onClickPublish(View v) {
        startIconAnimation();
    }

    public void onClickPublishrequirement(View view){
        startActivity(new Intent(this,NewFabuActivity.class));
        startIconAnimation();
    }

    public void onClickPublishPhoto(View view){
        startActivity(new Intent(this,NewFabuActivity.class));
        startIconAnimation();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                IASApplication application = IASApplication.getIASApplication();
                application.release();
                this.finish();
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startIconAnimation(){
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 100f);
        anim.setDuration(500);
        anim.setInterpolator(new OvershootInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isOpen = !isOpen;
            }
        });
        anim.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentValue = (float) animation.getAnimatedValue();
                        if (!isOpen) {
                            openAnimation(imgs[0], currentValue, points[0], 180, 1.5f);
                            openAnimation(imgs[1], currentValue, points[1], 180, 1.5f);
                        } else {
                            closeAnimation(imgs[0], currentValue, points[0], 180, 1.5f);
                            closeAnimation(imgs[1], currentValue, points[1], 180, 1.5f);
                        }

                    }
                }
        );
        anim.start();
    }

    /**
     * @param v
     * @param value 进度条 max100
     * @param endP  结束坐标
     * @param angle 旋转度数
     * @param size  放大倍数
     */
    public void openAnimation(View v, float value, PointF endP, float angle, float size) {
        v.setRotation((float) (-angle + (angle / 100) * value));
        if (size / 100 * value < size) {
            if (size / 100 * value >= 1) {
                v.setScaleX(size / 100 * value);
                v.setScaleY(size / 100 * value);
            }
        }
        if (value >= 0) {
            PointF moveP = GeometryUtil.getPointByPercent(new PointF(0, 0), endP, value / 100);
            v.setTranslationX(moveP.x);
            v.setTranslationY(moveP.y);
        }
    }

    public void closeAnimation(View v, float value, PointF endP, float angle, float size) {
        value = 100 - value;
        v.setRotation((float) (-angle + (angle / 100) * value));
        if (size / 100 * value < size) {
            if (size / 100 * value >= 1) {
                v.setScaleX(size / 100 * value);
                v.setScaleY(size / 100 * value);
            } else {
                v.setScaleX(1);
                v.setScaleY(1);
            }
        }

        if (value < 100 && value > 0f) {
            PointF moveP = GeometryUtil.getPointByPercent(new PointF(0, 0), endP, value / 100);
            v.setTranslationX(moveP.x);
            v.setTranslationY(moveP.y);
        } else if (value < 0) {
            PointF moveP = GeometryUtil.getPointByPercent(new PointF(0, 0), endP, 0);
            v.setTranslationX(moveP.x);
            v.setTranslationY(moveP.y);
        }
    }
}
