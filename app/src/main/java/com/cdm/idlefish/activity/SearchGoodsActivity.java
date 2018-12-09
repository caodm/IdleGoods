package com.cdm.idlefish.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.cdm.idlefish.R;
import com.cdm.idlefish.adapter.HomeGoodsListAdapter;
import com.cdm.idlefish.base.BaseIdleFishActivity;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.dao.HomeDao;
import com.cdm.idlefish.entity.HomeGoodEntity;
import com.cdm.idlefish.session.Session;
import com.cdm.network.interf.HttpAuthCallBack;
import com.cdm.network.model.ResultModel;
import com.cdm.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.ItemClickSupport;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;



public class SearchGoodsActivity extends BaseIdleFishActivity{

    private int type;
    private XRecyclerView mListView;
    private HomeGoodsListAdapter mAdapter;

    private View mSearByTitle;
    private EditText mEdit;
    private View mListEmpty;

    private int pageNo=0;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.tt_activity_search_goods;
    }

    @Override
    protected void initView() {
        ToastUtils.init(this);
        Intent intent =getIntent();
        if(intent != null){
            type = intent.getIntExtra(Constants.GOODS_TYPE,0);
            //getGoodsListFromHttp(type);
        }

        mListView = $(R.id.list_goods);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(layoutManager);

        mListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mListView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);


        mAdapter = new HomeGoodsListAdapter(this);
        //mListView.setAdapter(mAdapter);

        mSearByTitle = $(R.id.top_right_container);
        mEdit = $(R.id.tt_activity_goods_search_search_content);
        mListView.setAdapter(mAdapter);
        mListEmpty = $(R.id.listview_empty);

        Log.i("daming", "initView: type ="+type);
    }

    @Override
    protected void initData() {
        //mListView.setEmptyView(mListEmpty);
    }

    @Override
    protected void initListener() {
        mSearByTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                getGoodsListFromHttpByTitle(mEdit.getText().toString().trim());

            }
        });
        ItemClickSupport.addTo(mListView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent();
                //intent.putExtra(Constants.GOODS_ID,mAdapter.getmList().get(position).getId()+"");
                //startActivityWithoutExtras(GoodsDetailsInfoActivity.class,intent);

                intent.putExtra(Constants.GOODS_ID,mAdapter.get(position-1).getId()+"");
                intent.putExtra("GoodsBean",mAdapter.get(position-1));
                startActivityWithIntent(GoodsDetailsInfoActivity.class,intent);
            }

        });


        mListView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNo = 0 ;
                mAdapter.clear();
                getGoodsFromHttp(type);
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        mListView.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                pageNo++ ;
                //getGoodsFromHttp(pageNo);
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        mListView.loadMoreComplete();
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getGoodsFromHttp(type);
    }

    private void getGoodsFromHttp(int type){
        HomeDao.getInstanse().doGetGoodsList(this,0,type, Session.getInstance().getUser().getId(),
                new HttpAuthCallBack<List<HomeGoodEntity>>() {
                    @Override
                    public void onSucceeded(final List<HomeGoodEntity> successObj) {
                        if(successObj==null || successObj.size()<1){
                            return ;
                        }
                        Log.i("daming", "getGoodsFromHttp onSucceeded: successObj ="+successObj.toString());
                        runOnUiThread(new Thread(){
                            @Override
                            public void run() {
                                mAdapter.add(successObj);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onFailed(final ResultModel failObj) {
                        runOnUiThread(new Thread(){
                            @Override
                            public void run() {
                                ToastUtils.getInstance().showToast(failObj.getMessage());
                                mListView.setEmptyView(mListEmpty);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
    }

    private void getGoodsListFromHttp(String str){
        if(str == null || TextUtils.isEmpty(str)){
            return ;
        }
        showLoadingView();
        HomeDao.getInstanse().doGetGoodsListFromType(this, str,
                new HttpAuthCallBack<List<HomeGoodEntity>>() {

            @Override
            public void onSucceeded(final List<HomeGoodEntity> successObj) {
                closeLoadingView();
                if(successObj==null || successObj.size()<1){
                    return ;
                }
                runOnUiThread(new Thread(){
                    @Override
                    public void run() {
                       // mAdapter.setmList(successObj);
                      //  mListView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
            @Override
            public void onFailed(final ResultModel failObj) {
                closeLoadingView();
            }
        });
    }

    private void getGoodsListFromHttpByTitle(String title){
        if(title == null || TextUtils.isEmpty(title)){
            return ;
        }
        showLoadingView();
        HomeDao.getInstanse().doGetGoodsListFromTitle(this, title,
                new HttpAuthCallBack<List<HomeGoodEntity>>() {
                    @Override
                    public void onSucceeded(final List<HomeGoodEntity> successObj) {
                        closeLoadingView();
                        Log.d("SearchGoodsActivity", " getGoodsListFromHttpByTitle successObj:" + successObj);
                        if(successObj==null || successObj.size()<1){
                            return ;
                        }
                        runOnUiThread(new Thread(){
                            @Override
                            public void run() {
                               // mAdapter.setmList(successObj);
                                //mListView.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onFailed(final ResultModel failObj) {
                        closeLoadingView();
                    }
                });
    }
}
