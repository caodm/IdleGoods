package com.cdm.idlefish.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cao.choosemorepicture.ImageLoader;
import com.cao.choosemorepicture.MultiPictureView;
import com.cdm.activity.BaseActivity;
import com.cdm.idlefish.R;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.entity.HomeGoodEntity;
import com.goyourfly.vincent.Vincent;
import com.orhanobut.hawk.Hawk;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.ImageEngine;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class NewFabuActivity extends BaseActivity implements View.OnClickListener{

    private static final int REQUEST_ADD_IMAGE = 2;
    private int MY_PERMISSIONS_REQUEST = 20;
    MultiPictureView multiPictureView;

    private EditText mEditTextGoodsTitle,mEditTextGoodsContent,mEditTextSellPrice,
            mEditTextOriginalPrice,mEditTextPhone;
    private String mGoodsTitle , mGoodsContent,mSellPrice,mOriginalPrice , mGoodsImage,mPhone;
    private Button mBtnPublish;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_new_fabu;
    }

    @Override
    protected void initView() {
        multiPictureView = (MultiPictureView) findViewById(R.id.multiple_image);
        // 如果不想显示删除按钮，可以这样写
//        multiPictureView.setDeleteResource(0);
        multiPictureView.setDeleteResource(R.drawable.ic_delete_24dp);
        multiPictureView.setAddClickCallback(new MultiPictureView.AddClickCallback() {
            @Override
            public void onAddClick(View view) {
                addImage();
            }
        });
        mEditTextGoodsTitle = $(R.id.tv_fabu_title);
        mEditTextGoodsContent = $(R.id.et_des);
        mEditTextSellPrice = $(R.id.et_sale_price);
        mEditTextOriginalPrice = $(R.id.et_buy_price);
        mEditTextPhone = $(R.id.et_phone);

        mBtnPublish = $(R.id.btn_commit);
    }

    @Override
    protected void initData() {
        MultiPictureView.Companion.setImageLoader((ImageLoader)(new ImageLoader() {
            public void loadImage(@NotNull ImageView image, @NotNull Uri uri) {
                Context var10000 = image.getContext();
                Vincent.with(var10000).load(uri).
                        placeholder(R.drawable.ic_placeholder_loading).
                        error(R.drawable.ic_placeholder_loading).into(image);
            }
        }));
    }

    @Override
    protected void initListener() {
        mBtnPublish.setOnClickListener(this);
    }

    /**
     * 发布商品
     */
    private void publishGoods(){
        if (!validate()){
            Toast.makeText(this,"需要输入的内容不能为空!",Toast.LENGTH_SHORT).show();
            return;
        }
        HomeGoodEntity item = new HomeGoodEntity();
        int _id = 0;
        List<HomeGoodEntity> _goodslist = new ArrayList<>();
        if (Hawk.contains(Constants.HAWK_GOODS_LIST)){
            _goodslist = (List<HomeGoodEntity>)Hawk.get(Constants.HAWK_GOODS_LIST);
            _id=_goodslist.size();
        }
        Log.i("daming", " publishGoods _id:" + _id);
        item.setId(_id);
        item.setUser_id(_id);
        item.setImage(mGoodsImage);
        item.setTitle(mGoodsTitle);
        item.setContent(mGoodsContent);
        item.setSellPrice(Float.parseFloat(mSellPrice));
        item.setOriginalPrice(Float.parseFloat(mOriginalPrice));
        item.setPhone(mPhone);
        if(_goodslist!=null && _goodslist.size()>0){
            _goodslist.add(0,item);
        }
        Hawk.put(Constants.HAWK_GOODS_LIST,_goodslist);
        Toast.makeText(this,"发布成功!",Toast.LENGTH_SHORT).show();
    }

    private boolean validate(){
        boolean isVaidate = true;
        mGoodsTitle = mEditTextGoodsTitle.getText().toString();
        mGoodsContent= mEditTextGoodsContent.getText().toString();
        mSellPrice = mEditTextSellPrice.getText().toString();
        mOriginalPrice = mEditTextOriginalPrice.getText().toString();
        mPhone = mEditTextPhone.getText().toString();

        if(TextUtils.isEmpty(mGoodsTitle)){
            isVaidate= false;
            mEditTextGoodsTitle.setError("标题不能为空");
        }
        if(TextUtils.isEmpty(mGoodsContent)){
            isVaidate= false;
            mEditTextGoodsContent.setError("描述不能为空");
        }
        if(TextUtils.isEmpty(mSellPrice)){
            isVaidate= false;
            mEditTextSellPrice.setError("售价不能为空");
        }
        if(TextUtils.isEmpty(mOriginalPrice)){
            isVaidate= false;
            mEditTextOriginalPrice.setError("原价不能为空");
        }
        if(TextUtils.isEmpty(mPhone)){
            isVaidate= false;
            mEditTextPhone.setError("联系方式不能为空");
        }
        return isVaidate;
    }

    void addImage() {
        if (ContextCompat.checkSelfPermission(NewFabuActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        } else {
            Matisse.from(NewFabuActivity.this)
                    .choose(MimeType.allOf())
                    .maxSelectable(5 - multiPictureView.getCount())
                    .thumbnailScale(0.85f)
                    .imageEngine(new ImageEngine() {
                        @Override
                        public void loadThumbnail(Context context, int i, Drawable drawable, ImageView imageView, Uri uri) {
                            Vincent.with(context)
                                    .load(uri)
                                    .placeholder(R.drawable.ic_placeholder_loading)
                                    .error(R.drawable.ic_placeholder_loading)
                                    .into(imageView);
                        }

                        @Override
                        public void loadAnimatedGifThumbnail(Context context, int i, Drawable drawable, ImageView imageView, Uri uri) {

                            Vincent.with(context)
                                    .load(uri)
                                    .placeholder(R.drawable.ic_placeholder_loading)
                                    .error(R.drawable.ic_placeholder_loading)
                                    .into(imageView);
                        }

                        @Override
                        public void loadImage(Context context, int i, int i1, ImageView imageView, Uri uri) {

                            Vincent.with(context)
                                    .load(uri)
                                    .placeholder(R.drawable.ic_placeholder_loading)
                                    .error(R.drawable.ic_placeholder_loading)
                                    .into(imageView);
                        }

                        @Override
                        public void loadAnimatedGifImage(Context context, int i, int i1, ImageView imageView, Uri uri) {

                            Vincent.with(context)
                                    .load(uri)
                                    .placeholder(R.drawable.ic_placeholder_loading)
                                    .error(R.drawable.ic_placeholder_loading)
                                    .into(imageView);
                        }

                        @Override
                        public boolean supportAnimatedGif() {
                            return false;
                        }
                    })
                    .forResult(REQUEST_ADD_IMAGE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addImage();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_IMAGE && resultCode == RESULT_OK) {
            List<Uri> mList = Matisse.obtainResult(data);
            for (int i=0;i<mList.size();i++){
                Log.i("daming","item="+mList.get(i).getPath());
                mGoodsImage+=mList.get(i).getPath();
                int j=i+1;
                if(j<mList.size()){
                    mGoodsImage +="@";
                }
            }
            multiPictureView.addItem(mList);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_commit:
                publishGoods();
                break;
        }
    }
}
