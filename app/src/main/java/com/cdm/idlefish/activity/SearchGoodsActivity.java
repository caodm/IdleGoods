package com.cdm.idlefish.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.cdm.idlefish.R;
import com.cdm.idlefish.adapter.HomeGoodsListAdapter;
import com.cdm.idlefish.base.BaseIdleFishActivity;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.dao.HomeDao;
import com.cdm.idlefish.entity.HomeGoodEntity;
import com.cdm.network.interf.HttpAuthCallBack;
import com.cdm.network.model.ResultModel;
import com.cdm.utils.ToastUtils;

import java.util.List;



public class SearchGoodsActivity extends BaseIdleFishActivity{

    private String type="";
    private ListView mListView;
    private HomeGoodsListAdapter mAdapter;

    private View mSearByTitle;
    private EditText mEdit;
    private View mListEmpty;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.tt_activity_search_goods;
    }

    @Override
    protected void initView() {
        ToastUtils.init(this);
        Intent intent =getIntent();
        if(intent != null){
            type = intent.getStringExtra(Constants.GOODS_TYPE);
            getGoodsListFromHttp(type);
        }

        mListView = $(R.id.list_goods);
        mAdapter = new HomeGoodsListAdapter(this);
        //mListView.setAdapter(mAdapter);

        mSearByTitle = $(R.id.top_right_container);
        mEdit = $(R.id.tt_activity_goods_search_search_content);

        mListEmpty = $(R.id.listview_empty);
    }

    @Override
    protected void initData() {
        mListView.setEmptyView(mListEmpty);
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                //intent.putExtra(Constants.GOODS_ID,mAdapter.getmList().get(position).getId()+"");
                startActivityWithoutExtras(GoodsDetailsInfoActivity.class,intent);
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
