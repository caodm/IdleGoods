package com.cdm.idlefish.viewholder;

import android.view.View;
import android.widget.TextView;

import com.cdm.idlefish.R;
import com.cdm.idlefish.entity.model.CompanyEntity;
import com.cdm.idlefish.utils.binding.Bind;
import com.cdm.idlefish.widget.radapter.RLayout;
import com.cdm.idlefish.widget.radapter.RViewHolder;


/**
 * Created by wcy on 2018/1/20.
 */
@RLayout(R.layout.view_holder_company_index)
public class CompanyIndexViewHolder extends RViewHolder<CompanyEntity> {
    @Bind(R.id.tv_index)
    private TextView tvIndex;

    public CompanyIndexViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void refresh() {
        tvIndex.setText(data.getName());
    }
}
