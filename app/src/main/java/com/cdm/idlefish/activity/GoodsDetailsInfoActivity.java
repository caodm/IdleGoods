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
import android.widget.Toast;

import com.cdm.idlefish.R;
import com.cdm.idlefish.adapter.GoodViewPagerAdapter;
import com.cdm.idlefish.base.BaseIdleFishActivity;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.dao.HomeDao;
import com.cdm.idlefish.entity.HomeGoodEntity;
import com.cdm.idlefish.session.Session;
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
    private TextView mUserName,mType,mTitle,mContent,mWeight,mSellprice,mOriginprice,mPhone,mCollect;
    private String goodsId = "";
    private TextView mTxtViewpagerIndex;
    private Button mPhoneBtn;
    private View mCollectView;
    private int mCoollect;

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

        mCollectView = $(R.id.tt_goods_detail_comment);
        mCollect= $(R.id.tt_goods_detail_comment_cnt);

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
        if(mStrArr!=null && mStrArr.length>=1){
            for(int i=0;i< mStrArr.length;i++){
                ImageView view = new ImageView(this);
                ImageLoader.getInstance().displayImage(
                        TextUtils.isEmpty(mStrArr[i])?mStrArr[i]:mStrArr[i].trim(),view);
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
        ImageLoader.getInstance().displayImage(
                TextUtils.isEmpty(goodsEntity.getUser_icon())?goodsEntity.getUser_icon():
                        goodsEntity.getUser_icon().trim(),mUserIcon);
        mUserName.setText(goodsEntity.getUser_name());
        int type = Integer.parseInt(goodsEntity.getType());
        if(type>0){
            type = type-1;
        }else {
            type = 0;
        }
        mType.setText(Constants.GOODSCATEGORY[type]);
        mTitle.setText(goodsEntity.getTitle());
        mContent.setText(goodsEntity.getContent());
        mWeight.setText(goodsEntity.getWeight());
        mSellprice.setText(goodsEntity.getSellPrice()+"");
        mOriginprice.setText(goodsEntity.getOriginalPrice()+"");
        mPhone.setText(goodsEntity.getPhone());

        mCoollect = Integer.parseInt(goodsEntity.getCollect());

        if("0".equals(goodsEntity.getCollect())){
            mCollect.setText(R.string.hava_collect);
        }else {
            mCollect.setText(R.string.collect);
        }


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
        mCollectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCoollect == 0) colectGoods(1);
                else colectGoods(0);
            }
        });
    }

    private void colectGoods(final int type){
        HomeDao.getInstanse().doCollectGoods(this, Session.getInstance().getUser().getId(),
                goodsEntity.getId(), type, new HttpAuthCallBack<ResultModel>() {
                    @Override
                    public void onSucceeded(ResultModel successObj) {
                        mCoollect=type;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(mCoollect == 0){
                                    mCollect.setText(R.string.hava_collect);
                                    Toast.makeText(GoodsDetailsInfoActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    mCollect.setText(R.string.collect);
                                    Toast.makeText(GoodsDetailsInfoActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                    @Override
                    public void onFailed(final ResultModel failObj) {
                        //mCoollect=1;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(GoodsDetailsInfoActivity.this,failObj.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
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
