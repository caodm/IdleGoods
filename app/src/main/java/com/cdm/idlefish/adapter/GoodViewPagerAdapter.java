package com.cdm.idlefish.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class GoodViewPagerAdapter extends PagerAdapter{


    private List<ImageView> mListImg = new ArrayList<>();

    public GoodViewPagerAdapter(){

    }

    @Override
    public int getCount() {
        return mListImg.size();
    }

    public List<ImageView> getmListImg() {
        return mListImg;
    }

    public void setmListImg(List<ImageView> mListImg) {
        this.mListImg = mListImg;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mListImg.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mListImg.get(position);
        container.addView(view);
        return view;
    }

}
