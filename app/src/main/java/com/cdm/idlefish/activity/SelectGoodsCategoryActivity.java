package com.cdm.idlefish.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cdm.activity.BaseActivity;
import com.cdm.idlefish.R;
import com.cdm.idlefish.adapter.SelectGoodsCategoryAdaper;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.entity.GoodsCategoryEntity;
import com.jcodecraeer.xrecyclerview.ItemClickSupport;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * Created by android on 18-8-8.
 */

public class SelectGoodsCategoryActivity extends BaseActivity{

    private XRecyclerView mXRecyclerView;
    private SelectGoodsCategoryAdaper mAdapter;
    private ArrayList<GoodsCategoryEntity> mCategoryList = new ArrayList<>();

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_select_goods_category;
    }

    @Override
    protected void initView() {
        mXRecyclerView = $(R.id.recyclerview);
        mAdapter = new SelectGoodsCategoryAdaper(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);

        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
    }

    @Override
    protected void initData() {
        mXRecyclerView.setAdapter(mAdapter);
        addCategoryData();
    }

    private void addCategoryData() {
        for (int i=0;i< Constants.GOODSCATEGORY.length;i++){
            GoodsCategoryEntity entity = new GoodsCategoryEntity();
            entity.setCategoryTitle(Constants.GOODSCATEGORY[i]);
            mCategoryList.add(entity);
        }
        mAdapter.add(mCategoryList);
    }

    @Override
    protected void initListener() {
        ItemClickSupport.addTo(mXRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (position<=0){
                    return ;
                }
                Intent intent = new Intent();
                //类型用数字传递，mCategoryList.get(position-1).getCategoryTitle()
                intent.putExtra(Constants.GOODS_TYPE,""+(position-1));
                setResult(NewFabuActivity.RESULT_SELECT_CATEGORY,intent);
                finish();
            }
        });
    }
}
