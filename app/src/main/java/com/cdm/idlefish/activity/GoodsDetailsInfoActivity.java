package com.cdm.idlefish.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdm.idlefish.R;
import com.cdm.idlefish.adapter.GoodViewPagerAdapter;
import com.cdm.idlefish.base.BaseIdleFishActivity;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.dao.HomeDao;
import com.cdm.idlefish.entity.HomeGoodEntity;
import com.cdm.idlefish.utils.CommomUtils;
import com.cdm.idlefish.utils.StringUtil;
import com.cdm.idlefish.view.CircleImageView;
import com.cdm.network.interf.HttpAuthCallBack;
import com.cdm.network.model.ResultModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;



public class GoodsDetailsInfoActivity extends BaseIdleFishActivity{

    private List<ImageView> mListImg = new ArrayList<>();
    private RelativeLayout mViewImgLayout ;
    private LinearLayout  mViewGoodsInfoLayout;
    private ViewPager mViewPager;
    private GoodViewPagerAdapter mAdapter;
    private HomeGoodEntity goodsEntity;
    private String[] mStrArr = null;
    private CircleImageView mUserIcon;
    private TextView mUserName,mType,mTitle,mContent,mWeight,mSellprice,mOriginprice,mPhone;
    private String goodsId = "";
    private TextView mTxtViewpagerIndex;
    private Button mPhoneBtn;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.tt_activity_goods_details;
    }

    @Override
    protected void initView() {
        setTitle("详情");
        mViewImgLayout = $(R.id.tt_view_goods_image_stub);
        mViewGoodsInfoLayout = $(R.id.tt_view_goods_detail_stub);
        mViewPager = (ViewPager) mViewImgLayout.findViewById(R.id.tt_view_goods_image_viewpager);
        mAdapter = new GoodViewPagerAdapter();
        mUserIcon = (CircleImageView) mViewGoodsInfoLayout.findViewById(R.id.tt_view_goods_detail_owner_image);
        mUserName = (TextView) mViewGoodsInfoLayout.findViewById(R.id.tt_view_goods_detail_owner_name);
        mTxtViewpagerIndex = (TextView) mViewImgLayout.findViewById(R.id.tt_view_goods_viewpager_index);

        mType = (TextView) mViewGoodsInfoLayout.findViewById(R.id.tt_view_goods_detail_categroy);
        mTitle = (TextView) mViewGoodsInfoLayout.findViewById(R.id.tt_view_goods_detail_title);
        mContent = (TextView) mViewGoodsInfoLayout.findViewById(R.id.tt_view_goods_detail_description);
        mWeight = (TextView) mViewGoodsInfoLayout.findViewById(R.id.tt_goods_detail_info_weight_volume);
        mSellprice = (TextView) mViewGoodsInfoLayout.findViewById(R.id.tt_goods_detail_info_sellprice);
        mOriginprice = (TextView) mViewGoodsInfoLayout.findViewById(R.id.tt_goods_detail_info_originprice);
        mPhone = (TextView) mViewGoodsInfoLayout.findViewById(R.id.tt_goods_detail_info_phone);

        mPhoneBtn =  $(R.id.tt_goods_detail_user_operation);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(intent!=null){
            goodsId = intent.getStringExtra(Constants.GOODS_ID);
            goodsEntity = intent.getParcelableExtra("GoodsBean");
//            getEntityByIdFromHttp(goodsId);
            updateEntity();
        }
    }

    private void updateEntity(){
        //更新goods的轮播图
        mStrArr = StringUtil.splitString(goodsEntity.getImage(), Constants.SPLIT_TAG);
        if(mStrArr!=null || mStrArr.length<1){
            for(int i=0;i< mStrArr.length;i++){
                ImageView view = new ImageView(this);
                ImageLoader.getInstance().displayImage(mStrArr[i],view);
                mListImg.add(view);
            }
            mAdapter.setmListImg(mListImg);
            mViewPager.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mTxtViewpagerIndex.setText("1/"+mStrArr.length);
        } else {
            //更新轮播图的下标
            mTxtViewpagerIndex.setText("0/0");
        }
        //更新头像和昵称
        ImageLoader.getInstance().displayImage(goodsEntity.getUser_icon(),mUserIcon);
        mUserName.setText(goodsEntity.getUser_name());
        mType.setText(goodsEntity.getType()+"");
        mTitle.setText(goodsEntity.getTitle());
        mContent.setText(goodsEntity.getContent());
        mWeight.setText(goodsEntity.getWeight());
        mSellprice.setText(goodsEntity.getSellPrice()+"");
        mOriginprice.setText(goodsEntity.getOriginalPrice()+"");
        mPhone.setText(goodsEntity.getPhone());

    }

    @Override
    protected void initListener() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //更新轮播图的下标
                mTxtViewpagerIndex.setText((position+1)+"/"+mStrArr.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     *
     * @param view
     */
    public void onCallPhone(View view){
        CommomUtils.diallPhone(this,goodsEntity.getPhone());
    }

    private void getEntityByIdFromHttp(String id){
        if(id == null || TextUtils.isEmpty(id)){
            return ;
        }
        showLoadingView();
        HomeDao.getInstanse().doGetGoodsById(this, id, new HttpAuthCallBack<HomeGoodEntity>() {
            @Override
            public void onSucceeded(final HomeGoodEntity successObj) {
                closeLoadingView();
                if(successObj == null){
                    return ;
                }
                runOnUiThread(new Thread(){
                    @Override
                    public void run() {
                        goodsEntity = null;
                        goodsEntity = successObj;
                        updateEntity();
                    }
                });
            }

            @Override
            public void onFailed(ResultModel failObj) {
                closeLoadingView();
            }
        });
    }
}
