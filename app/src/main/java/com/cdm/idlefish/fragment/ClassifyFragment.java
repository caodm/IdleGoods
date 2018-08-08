package com.cdm.idlefish.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cdm.idlefish.R;
import com.cdm.idlefish.activity.SearchGoodsActivity;
import com.cdm.idlefish.adapter.GoodsCategoryAdapter;
import com.cdm.idlefish.base.BaseFragment;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.entity.GoodsCategoryEntity;

import java.util.ArrayList;
import java.util.List;


/*******************************************************************
 * @文件名称: EventFragment
 * @作者 : yangx
 * @文件描述:活动
 * @创建时间: 2016/7/18
 * ******************************************************************
 */
public class ClassifyFragment extends BaseFragment {
    public static final String TAG = "EventFragment";
    private View mSearchGoods;
    private GridView mGridView;
    private GoodsCategoryAdapter mAdapter;
    private List<GoodsCategoryEntity> mList = new ArrayList<>();
    private String[] mStrContent = {"美妈爱萌宝", "闲置也时尚", "满足温馨生活", "包你满意",
            "知识就是力量", "最闪耀最任性", "女神美美哒", "共享之乐尽在拿趣",""};
    private String[] mStrTitle =  {"母婴频道","品牌女士","生活服务","鞋靴箱包","图书","手机数码","个护化妆","旅游出行",""};
    private int[] mId = {
            R.drawable.goods_type_maternal,R.drawable.goods_type_shoes_clothes,R.drawable.goods_type_livelihood,
            R.drawable.goods_type_luggage,R.drawable.goods_type_books,R.drawable.goods_type_digital,
            R.drawable.goods_type_beauty,R.drawable.goods_type_others ,R.drawable.goods_type_others};

    private View view;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.tt_activity_goods_category, container, false);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        initListener();
    }

    private void initView(View view){
        mSearchGoods = $(R.id.tt_activity_goods_category_search,view);
        mGridView = $(R.id.tt_fragment_gridview,view);
        mAdapter = new GoodsCategoryAdapter(getContext());
    }

    private void initData(){
        for(int i=0;i<mStrContent.length;i++){
            GoodsCategoryEntity entity = new GoodsCategoryEntity();
            entity.setCategoryTitle(mStrTitle[i]);
            entity.setCategoryContent(mStrContent[i]);
            entity.setCategoryIcon(mId[i]);
            mList.add(entity);
        }
        mAdapter.setmList(mList);
        mGridView.setAdapter(mAdapter);
    }

    private void initListener() {
        mSearchGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithoutExtras(SearchGoodsActivity.class);
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>=8){
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(Constants.GOODS_TYPE,""+(position+1));
                startActivityWithIntent(SearchGoodsActivity.class,intent);
            }
        });
    }
}
