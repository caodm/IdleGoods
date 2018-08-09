package com.cdm.idlefish.activity;

import android.widget.TextView;

import com.cdm.activity.BaseActivity;
import com.cdm.idlefish.R;
import com.cdm.idlefish.utils.APKVersionUtils;

/**
 * Created by android on 18-8-9.
 */

public class VersionInfoActivity extends BaseActivity{

    private TextView mVersionCode,mVersionName;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_versioninfo;
    }

    @Override
    protected void initView() {
        mVersionCode = $(R.id.version_code);
        mVersionName = $(R.id.version_name);
    }

    @Override
    protected void initData() {
        String _code = APKVersionUtils.getVersionCode(this) +"";
        mVersionCode.setText(this.getResources().getText(R.string.version_code)+_code);
        mVersionName.setText(this.getResources().getText(R.string.version_name)+
                APKVersionUtils.getVersionName(this));

    }

    @Override
    protected void initListener() {

    }
}
