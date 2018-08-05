package com.cdm.idlefish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdm.idlefish.R;
import com.cdm.idlefish.entity.GoodsCategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class GoodsCategoryAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<GoodsCategoryEntity> mList = new ArrayList<>();

    public GoodsCategoryAdapter(Context mContext){
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public List<GoodsCategoryEntity> getmList() {
        return mList;
    }

    public void setmList(List<GoodsCategoryEntity> mList) {
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsCategoryHolder holder;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.tt_item_goods_category,null);
            holder = new GoodsCategoryHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (GoodsCategoryHolder) convertView.getTag();
        }
        if(position >= 8){
            return convertView;
        }
        holder.categoryTitle.setText(mList.get(position).getCategoryTitle());
        holder.categoryContent.setText(mList.get(position).getCategoryContent());
        holder.categoryIcon.setBackgroundResource(mList.get(position).getCategoryIcon());
        return convertView;
    }

    private class GoodsCategoryHolder{
        TextView categoryTitle;
        TextView categoryContent;
        ImageView categoryIcon;

        public GoodsCategoryHolder(View view){
            categoryTitle = (TextView) view.findViewById(R.id.tt_item_goods_category_title_name);
            categoryContent = (TextView) view.findViewById(R.id.tt_item_goods_category_title_des);
            categoryIcon = (ImageView) view.findViewById(R.id.tt_item_goods_category_title_image);
        }
    }
}
