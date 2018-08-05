package com.cdm.idlefish.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.cdm.idlefish.R;
import com.cdm.idlefish.entity.BannerItem;
import com.cdm.idlefish.utils.OptionUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NetworkImageHolderView implements Holder<BannerItem> {
    private View view;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    @Override
    public View createView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.view_banner, null, false);
        options= OptionUtil.getInstance().BulidDisOption();
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerItem data) {
        ImageView imageView = ((ImageView) view.findViewById(R.id.banner_image));
        imageLoader.displayImage(data.getPicPath(), imageView, options);

    }
}
