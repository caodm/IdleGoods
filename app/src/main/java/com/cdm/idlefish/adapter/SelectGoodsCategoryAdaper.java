package com.cdm.idlefish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cdm.idlefish.R;
import com.cdm.idlefish.entity.GoodsCategoryEntity;


/**
 * Created by android on 18-8-8.
 */

public class SelectGoodsCategoryAdaper extends ListRecyclerAdapter<GoodsCategoryEntity,
        SelectGoodsCategoryAdaper.ViewHolder>{

    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public SelectGoodsCategoryAdaper(Context mContext){
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_select_goods_category,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mCategoryTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mCategoryTitle = (TextView) itemView.findViewById(R.id.categoryName);
        }

        public void bindView(GoodsCategoryEntity entity){
            mCategoryTitle.setText(entity.getCategoryTitle());
        }
    }
}
