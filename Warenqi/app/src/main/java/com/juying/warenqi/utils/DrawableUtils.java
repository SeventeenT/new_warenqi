package com.juying.warenqi.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * <pre>
 * Author: @Administrator
 * Description: Drawable着色
 * Date: 2017/5/22 13:09
 * </pre>
 */

public class DrawableUtils {
    /**
     * 兼容v4的drawable着色方法
     *
     * @param drawable 需要着色的drawable
     * @param colors   色值列表(如果一个就使用ColorStateList.valueOf(intColor);)
     * @return 已着色的drawable，注意此着色会改变原drawable颜色
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }
}
