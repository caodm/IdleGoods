package com.cdm.idlefish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdm.idlefish.R;
import com.cdm.idlefish.entity.HomeGoodEntity;
import com.cdm.idlefish.session.Session;
import com.cdm.idlefish.utils.StringUtil;
import com.cdm.idlefish.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/7/1.
 */
public class HomeGoodsListAdapter extends ListRecyclerAdapter<HomeGoodEntity,HomeGoodsListAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<HomeGoodEntity> mList = new ArrayList<>();

    public HomeGoodsListAdapter(Context mContext){
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.tt_item_new_goods_info,null);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindTo(get(position));

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView topline;
        CircleImageView usericon;
        TextView flag;
        ImageView image1;
        ImageView image2;
        ImageView image3;
        ImageView image;
        TextView content;
        Button operation;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView.findViewById(R.id.tt_goods_info_goods_image_layout);
            topline = (ImageView) itemView.findViewById(R.id.tt_goods_info_owner_user_image_topline);
            title = (TextView) itemView.findViewById(R.id.tt_goods_info_owner_user_name);
            usericon = (CircleImageView) itemView.findViewById(R.id.tt_goods_info_owner_user_image);
            flag = (TextView) itemView.findViewById(R.id.tt_goods_info_flag);
            image1 = (ImageView) itemView.findViewById(R.id.tt_goods_info_goods_image_1);
            image2 = (ImageView) itemView.findViewById(R.id.tt_goods_info_goods_image_2);
            image3 = (ImageView) itemView.findViewById(R.id.tt_goods_info_goods_image_3);
            image = (ImageView) itemView.findViewById(R.id.tt_goods_info_goods_image);
            content = (TextView) itemView.findViewById(R.id.tt_goods_info_goods_des);
            operation = (Button) itemView.findViewById(R.id.tt_item_goods_info_operation);
        }

        public void bindTo(HomeGoodEntity entity){
            DisplayImageOptions options = Session.getInstance().getOptions();
            if(options!=null){
                ImageLoader.getInstance().displayImage(entity.getTopLine(),topline,options);
                ImageLoader.getInstance().displayImage(entity.getUser_icon(),usericon,options);
            }else{
                ImageLoader.getInstance().displayImage(entity.getTopLine(),topline);
                ImageLoader.getInstance().displayImage(entity.getUser_icon(),usericon);
            }
            content.setText(entity.getContent());
            title.setText(entity.getTitle());
            String[] str = StringUtil.splitString(entity.getImage(),"@");
            if(str == null){
                view.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
            }
            if(str.length == 1){
                view.setVisibility(View.GONE);
                ImageLoader.getInstance().displayImage(str[0],image,options);
            }else if(str.length == 2){
                image.setVisibility(View.GONE);
                ImageLoader.getInstance().displayImage(str[0],image1,options);
                ImageLoader.getInstance().displayImage(str[1],image2,options);
                image3.setVisibility(View.INVISIBLE);
            }else if(str.length >= 3){
                image.setVisibility(View.GONE);
                ImageLoader.getInstance().displayImage(str[0],image1,options);
                ImageLoader.getInstance().displayImage(str[1],image2,options);
                ImageLoader.getInstance().displayImage(str[2],image3,options);
            }
        }
    }
}
