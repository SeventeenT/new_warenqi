package com.juying.warenqi.app;

import android.content.Context;
import android.widget.ImageView;

import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
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
        GlideImageConfig
                .builder()
                .url((String) path)
                .imageView(imageView)
                .build();
    }
}
