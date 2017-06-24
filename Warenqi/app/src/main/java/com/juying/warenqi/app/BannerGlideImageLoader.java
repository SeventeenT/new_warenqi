package com.juying.warenqi.app;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.juying.warenqi.mvp.model.entity.ParsedBanner;
import com.youth.banner.loader.ImageLoader;

/**
 * <pre>
 * Author: @LvYan
 * Description:
 * Date: 2017/6/18 15:31
 * </pre>
 */
public class BannerGlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(((ParsedBanner) path).getImgUrl())
                .into(imageView);
//        GlideImageConfig
//                .builder()
//                .url(((ParsedBanner) path).getImgUrl())
//                .imageView(imageView)
//                .build();
    }
}
