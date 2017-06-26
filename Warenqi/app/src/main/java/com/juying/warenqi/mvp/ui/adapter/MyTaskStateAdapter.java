package com.juying.warenqi.mvp.ui.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.juying.warenqi.R;
import com.juying.warenqi.mvp.model.entity.MyTaskStateSection;
import com.juying.warenqi.mvp.ui.holder.BaseAutoLayoutHolder;
import com.juying.warenqi.utils.DrawableUtils;

import java.util.List;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/26 13:02
 * </pre>
 */
public class MyTaskStateAdapter extends BaseSectionQuickAdapter<MyTaskStateSection, BaseAutoLayoutHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MyTaskStateAdapter(List<MyTaskStateSection> data) {
        super(R.layout.item_my_task_status_content, R.layout.task_type_header, data);
    }

    @Override
    protected void convertHead(BaseAutoLayoutHolder helper, MyTaskStateSection item) {
        helper.setText(R.id.tv_task_type_title, item.header);
        TextView textView = helper.getView(R.id.tv_task_type_title);
        Drawable drawable = mContext.getApplicationContext()
                .getResources().getDrawable(R.drawable.shape_vertical_rect_tag);
        Drawable wrap = DrawableUtils.tintDrawable(drawable,
                ColorStateList.valueOf(mContext.getApplicationContext()
                        .getResources().getColor(item.getTitleTagColor())));
        wrap.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(wrap, null, null, null);
    }

    @Override
    protected void convert(BaseAutoLayoutHolder helper, MyTaskStateSection item) {
        SpannableStringBuilder spannableStringBuilder = new SpanUtils().appendLine(item.t.getTaskTaskCount() + "单")
                .appendLine()
                .append(item.t.getTaskType()).setForegroundColor(Color.parseColor("#999999"))
                .create();
        helper.setText(R.id.tv_my_task_state_content, spannableStringBuilder);
    }
}
