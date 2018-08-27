package com.cdm.idlefish.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.cdm.idlefish.R;
import com.cdm.idlefish.activity.CompanyActivity;
import com.cdm.idlefish.activity.ResultActivity;
import com.cdm.idlefish.config.RequestCode;
import com.cdm.idlefish.entity.model.CompanyEntity;
import com.cdm.idlefish.entity.model.SearchInfo;
import com.cdm.idlefish.utils.binding.Bind;
import com.cdm.idlefish.widget.radapter.RLayout;
import com.cdm.idlefish.widget.radapter.RViewHolder;


/**
 * Created by wcy on 2018/1/20.
 */
@RLayout(R.layout.view_holder_suggestion)
public class SuggestionViewHolder extends RViewHolder<CompanyEntity> implements View.OnClickListener {
    @Bind(R.id.tv_suggestion)
    private TextView tvSuggestion;

    public SuggestionViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void refresh() {
        tvSuggestion.setText(Html.fromHtml(data.getName()));
    }

    @Override
    public void onClick(View v) {
        if (position == adapter.getDataList().size() - 1) {
            Activity activity = (Activity) context;
            activity.startActivityForResult(new Intent(activity, CompanyActivity.class), RequestCode.REQUEST_COMPANY);
            return;
        }
        SearchInfo searchInfo = new SearchInfo();
        searchInfo.setPost_id((String) adapter.getTag());
        searchInfo.setCode(data.getCode());
        searchInfo.setName(data.getName());
        searchInfo.setLogo(data.getLogo());
        ResultActivity.start(context, searchInfo);
    }
}
