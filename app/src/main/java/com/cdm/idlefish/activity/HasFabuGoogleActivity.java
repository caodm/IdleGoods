package com.cdm.idlefish.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cdm.activity.BaseActivity;
import com.cdm.idlefish.R;
import com.cdm.idlefish.adapter.HomeGoodsListAdapter;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.entity.HomeGoodEntity;
import com.jcodecraeer.xrecyclerview.ItemClickSupport;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

public class HasFabuGoogleActivity extends BaseActivity {


    private XRecyclerView mXRecyclerView;
    private HomeGoodsListAdapter mGoodAdapter;
    private int pageNo = 1;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_has_fabu;
    }

    @Override
    protected void initView() {
        mXRecyclerView = (XRecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);

        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        mGoodAdapter = new HomeGoodsListAdapter(this);

        mXRecyclerView.setAdapter(mGoodAdapter);
    }

    @Override
    protected void initData() {
        updateDataFromHttp();
    }

    private void updateDataFromHttp(){
        List<HomeGoodEntity> list = new ArrayList<>();
        if(Hawk.contains(Constants.HAWK_GOODS_LIST)){
            list = Hawk.get(Constants.HAWK_GOODS_LIST);
        }
        Log.i("daming"," ---- "+list);
        if(list==null && list.size()<1){
            return;
        }
        if(pageNo == 1){
            mXRecyclerView.refreshComplete();
            mGoodAdapter.clear();
            mGoodAdapter.add(list);
        } else {
            mXRecyclerView.loadMoreComplete();
            mGoodAdapter.add(list);
        }
    }

    @Override
    protected void initListener() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNo = 1 ;
                //getGoodsFromHttp();
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        mXRecyclerView.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
//                pageNo++ ;
//                getGoodsFromHttp();
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        mXRecyclerView.loadMoreComplete();
                    }
                }, 1000);
            }
        });

        ItemClickSupport.addTo(mXRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if(position<3){
                    return ;
                }
                Intent intent = new Intent();
                intent.putExtra(Constants.GOODS_ID,mGoodAdapter.get(position).getId()+"");
                intent.putExtra("GoodsBean",mGoodAdapter.get(position));
                startActivityWithIntent(GoodsDetailsInfoActivity.class,intent);
            }
        });
    }
}
