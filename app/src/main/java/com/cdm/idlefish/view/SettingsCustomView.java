package com.cdm.idlefish.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdm.idlefish.R;


/**
 * 自定义设置中带箭头的view
 */
public class SettingsCustomView extends FrameLayout {

    /**  **/
    private TextView mTitleTxtv;
    private TextView mSubTitleTxtv;
    private ImageView imageImgv;
    private ImageView arrowImgv;
    private boolean indicatorShow;

    public SettingsCustomView(@NonNull Context context) {
        super(context);
    }

    public SettingsCustomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        /** 获取自定义属性 titleText */
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArrowView, 0, 0);

        String titleText = typeArray.getString(R.styleable.ArrowView_titleText);
        Drawable drawable = typeArray.getDrawable(R.styleable.ArrowView_arrowSrc);
        indicatorShow = typeArray.getBoolean(R.styleable.ArrowView_indicatorShow, true);
        View view = LayoutInflater.from(context).inflate(R.layout.view_settings_custom_arrow, this);
        mTitleTxtv = (TextView) view.findViewById(R.id.title_txtv);
        mSubTitleTxtv = (TextView) view.findViewById(R.id.sub_title_txtv);
        imageImgv = (ImageView) view.findViewById(R.id.image_imgv);
        arrowImgv = (ImageView) view.findViewById(R.id.arrow_imgv);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        float marginLeft = 0;

        if (drawable != null) {
            marginLeft = context.getResources().getDimension(R.dimen.margin_space)
                    + getResources().getDimension(R.dimen.margin_space_half)
                    + getResources().getDimension(R.dimen.arrow_image_size);

            params.setMargins(Float.valueOf(marginLeft).intValue(), 0, 0, 0);
            imageImgv.setBackgroundDrawable(drawable);
        } else {
            marginLeft = context.getResources().getDimension(R.dimen.margin_space);
            params.setMargins(Float.valueOf(marginLeft).intValue(), 0, 0, 0);
        }

        if (indicatorShow) {
            arrowImgv.setVisibility(View.VISIBLE);
        } else {
            arrowImgv.setVisibility(View.INVISIBLE);
        }

        mTitleTxtv.setLayoutParams(params);
        mTitleTxtv.setText(titleText);
    }

    public void setIndicatorShow(boolean show) {
        indicatorShow = show;
        if (indicatorShow) {
            arrowImgv.setVisibility(View.VISIBLE);
        } else {
            arrowImgv.setVisibility(View.INVISIBLE);
        }
    }

    public void setText(String text) {
        if (mTitleTxtv == null || text == null) return;
        mTitleTxtv.setText(text);
    }

    public void setSubText(String text) {
        if (mSubTitleTxtv == null || text == null) return;
        mSubTitleTxtv.setText(text);
    }


}
