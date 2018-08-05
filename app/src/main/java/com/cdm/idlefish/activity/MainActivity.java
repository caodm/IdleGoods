package com.cdm.idlefish.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.cdm.activity.BaseActivity;
import com.cdm.idlefish.R;
import com.cdm.idlefish.application.IASApplication;
import com.cdm.idlefish.view.NavigationButtonTab;

import com.cdm.idlefish.fragment.ClassifyFragment;


public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener{

    private FragmentManager mFragmentManager = null;
    private String errorViewTag = "error_view_tag";
    private FragmentTabHost mTabHost;
    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.home_btn_selector,
            R.drawable.buy_btn_selector,
            R.drawable.service_btn_selector,
            R.drawable.event_btn_selector,
            R.drawable.event_btn_selector,};
    //Tab选项卡的文字
    private String mTextViewArray[] = {"闲鱼", "鱼塘", "发布", "消息", "我的"};
    private String mTextTittleArray[] = {"精选", "演出", "服务", "活动"};
    //Tab选项卡ID
    private String mTabIds[] = {"SaltedFish ", "FishPonds ", "Release", "Message", "MyInfo"};
    //当前tab页页面tag
    private String mCurrentTabTag;
    static int CurrentTab = 0;

    //定义数组来存放Fragment界面
    private static Class[] mFragmentArray = {ClassifyFragment.class,
            ClassifyFragment.class,
            ClassifyFragment.class,
            ClassifyFragment.class,
            ClassifyFragment.class
    };
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

        //实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.real_tab_content);
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setOnTabChangedListener(this);
        //得到fragment的个数
        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTabIds[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
        }
        mTabHost.setCurrentTab(CurrentTab);
    }


    @Override
    public void onTabChanged(String tabId) {
        this.mCurrentTabTag = tabId;
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(mCurrentTabTag);
        if (fragment != null && fragment.isHidden()) {
            showFragment(fragment);

        }
        detachOtherFragment();
    }

    private void detachOtherFragment() {
        Fragment errorViewFragment = getSupportFragmentManager().findFragmentByTag(errorViewTag);
        if (errorViewFragment != null && !errorViewFragment.isDetached()) {
            detachFragment(errorViewFragment);
        }

    }

    private View getTabItemView(int index) {
        NavigationButtonTab navigationButtonTab = new NavigationButtonTab(this);
        navigationButtonTab.setLabelText(mTextViewArray[index]);
        navigationButtonTab.setTabIconResource(mImageViewArray[index]);
        return navigationButtonTab;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
           if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                IASApplication application = (IASApplication) IASApplication.getInstance();
                application.release();
                this.finish();
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 显示fragment
     *
     * @param fragment
     */
    protected void showFragment(Fragment fragment) {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        mFragmentManager.beginTransaction().show(fragment).commit();
    }

    protected void detachFragment(Fragment fragment) {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        mFragmentManager.beginTransaction().detach(fragment).commit();
    }
}
