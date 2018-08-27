package com.cdm.idlefish.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdm.idlefish.R;
import com.cdm.idlefish.entity.model.SearchResult;
import com.cdm.idlefish.utils.ViewUtils;
import com.cdm.idlefish.utils.binding.Bind;
import com.cdm.idlefish.widget.radapter.RLayout;
import com.cdm.idlefish.widget.radapter.RViewHolder;


/**
 * Created by wcy on 2018/1/20.
 */
@RLayout(R.layout.view_holder_search_result)
public class ResultViewHolder extends RViewHolder<SearchResult.ResultItem> {
    @Bind(R.id.line)
    private View line;
    @Bind(R.id.iv_logistics)
    private ImageView ivLogistics;
    @Bind(R.id.tv_time)
    private TextView tvTime;
    @Bind(R.id.tv_detail)
    private TextView tvDetail;

    public ResultViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void refresh() {
        tvTime.setText(data.getTime());
        tvDetail.setText(data.getContext());
        boolean first = (position == 0);
        line.setPadding(0, ViewUtils.dip2px(context, first ? 12 : 0), 0, 0);
        ivLogistics.setSelected(first);
        tvTime.setSelected(first);
        tvDetail.setSelected(first);
    }
}
