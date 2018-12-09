package com.cdm.idlefish.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.widget.TextView;
import android.widget.Toast;

import com.cao.choosemorepicture.ImageLoader;
import com.cao.choosemorepicture.MultiPictureView;
import com.cdm.activity.BaseActivity;
import com.cdm.idlefish.R;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.dao.HomeDao;
import com.cdm.idlefish.entity.HomeGoodEntity;
import com.cdm.idlefish.entity.User;
import com.cdm.idlefish.http.HttpCallback;
import com.cdm.idlefish.session.Session;
import com.cdm.idlefish.utils.CDNUtil;
import com.cdm.network.interf.HttpAuthCallBack;
import com.cdm.network.model.ResultModel;
import com.goyourfly.vincent.Vincent;
import com.orhanobut.hawk.Hawk;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.ImageEngine;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NewFabuActivity extends BaseActivity implements View.OnClickListener{

    private static final int REQUEST_ADD_IMAGE = 2;
    public static final int RESULT_SELECT_CATEGORY = 3;
    private static final int REQUEST_SELECT_CATEGORY = 3;
    private int MY_PERMISSIONS_REQUEST = 20;
    MultiPictureView multiPictureView;

    private EditText mEditTextGoodsTitle,mEditTextGoodsContent,mEditTextSellPrice,
            mEditTextOriginalPrice,mEditTextPhone;
    private String mGoodsType,mGoodsTitle,mGoodsContent,mSellPrice,mOriginalPrice , mGoodsImage="",mPhone;
    private Button mBtnPublish;
    private View mFenleiView;
    private TextView mGoodsFenleiTextview;

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

        mFenleiView = $(R.id.rl_fenlei);
        mGoodsFenleiTextview = $(R.id.tv_classify);
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
        mFenleiView.setOnClickListener(this);
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
        item.setType(mGoodsType);
        if(_goodslist!=null && _goodslist.size()>0){
            //_goodslist.add(0,item);
        }
        faBuGoods(Session.getInstance().getUser().getId(),item);
        //Hawk.put(Constants.HAWK_GOODS_LIST,_goodslist);
        //Toast.makeText(this,"发布成功!",Toast.LENGTH_SHORT).show();
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
            mGoodsImage ="";
            for (int i=0;i<mList.size();i++){
                Log.i("daming","item="+mList.get(i).getPath());
                //mGoodsImage+=mList.get(i).getPath();
                int j=i+1;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                submitUserIconToQN(mList.get(i));
                if(j<mList.size()){
                    //mGoodsImage +="@";
                }
            }
            multiPictureView.addItem(mList);
        } else if(requestCode == REQUEST_SELECT_CATEGORY && resultCode == RESULT_SELECT_CATEGORY){
            mGoodsType = data.getStringExtra(Constants.GOODS_TYPE);
            mGoodsFenleiTextview.setText(Constants.GOODSCATEGORY[Integer.parseInt(mGoodsType)]);
            mGoodsType = mGoodsType+1;
            Log.i("daming","mGoodsType="+mGoodsType);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_fenlei:
                startActivityForResult(
                        new Intent(this,SelectGoodsCategoryActivity.class),
                        REQUEST_SELECT_CATEGORY);
                break;
            case R.id.btn_commit:
                publishGoods();
                break;
        }
    }


    /**
     * 上传图片到七牛平台
     */
    private void submitUserIconToQN(final Uri uri) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String filepath = getRealFilePath(getBaseContext(),uri);
                    String filename = getCutPhotoFileName();//getFileNameFromUri(filepath);
                    Log.d("SlidingFragment", "submitUserIconToQN : filename ="+filename +" ; filepath="+filepath);
                    CDNUtil.getInstance().upload(filepath.trim(), filename.trim(), true);
                    String webPath = CDNUtil.getInstance().getRemoteDownloadURL(filename.trim(), CDNUtil.TIME);//获取七牛文件路径
                    if(!TextUtils.isEmpty(mGoodsImage)){
                        mGoodsImage+="@";
                    }
                    mGoodsImage+=webPath;
                    User user = Session.getInstance().getUser();
                    Log.d("SlidingFragment", "submitUserIconToQN : webPath ="+webPath+"  ; mGoodsImage="+mGoodsImage);
                    if (user != null) {
                        //submitUserIconToServer(user.getId() + "", webPath);
                    } else {
                        Toast.makeText(NewFabuActivity.this, "上传头像失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private void faBuGoods(int userid , HomeGoodEntity entity){
        HomeDao.getInstanse().doFaBuGoods(this,userid,entity, new HttpAuthCallBack<ResultModel>(){

            @Override
            public void onSucceeded(ResultModel successObj) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewFabuActivity.this, "发布成功！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailed(ResultModel failObj) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewFabuActivity.this, "发布失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 使用系统当前日期加以调整作为物品照片的名称
     *
     * @return
     */
    private String getCutPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        String str = "WP_" + dateFormat.format(date) + ".jpg";
        return str;
    }

    private String getFileNameFromUri(String  filepath){
        return filepath.substring(filepath.lastIndexOf("/")+1,filepath.length());
    }
}
