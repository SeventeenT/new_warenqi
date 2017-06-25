package com.juying.warenqi.mvp.ui.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.juying.warenqi.R;
import com.juying.warenqi.mvp.model.entity.TaskCenterTypeTitle;
import com.juying.warenqi.mvp.ui.holder.BaseAutoLayoutHolder;
import com.juying.warenqi.utils.DrawableUtils;

import java.util.List;

/**
 * <pre>
 * Author: @LvYan
 * Description:
 * Date: 2017/6/25 16:14
 * </pre>
 */
public class TaskCenterTypeAdapter extends BaseSectionQuickAdapter<TaskCenterTypeTitle, BaseAutoLayoutHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public TaskCenterTypeAdapter(List<TaskCenterTypeTitle> data) {
        super(R.layout.item_task_type_content, R.layout.task_type_header, data);
    }

    @Override
    protected void convertHead(BaseAutoLayoutHolder helper, TaskCenterTypeTitle item) {
        helper.setText(R.id.tv_task_type_title, item.header);
        TextView textView = helper.getView(R.id.tv_task_type_title);
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.shape_vertical_rect_tag);
        Drawable wrap = DrawableUtils.tintDrawable(drawable,
                ColorStateList.valueOf(mContext.getResources().getColor(item.getTitleTagColor())));
        wrap.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(wrap, null, null, null);
    }

    @Override
    protected void convert(BaseAutoLayoutHolder helper, TaskCenterTypeTitle item) {
        helper.setText(R.id.tv_task_type_name, item.t.getTaskType())
                .setVisible(R.id.tv_task_count, item.t.getTaskTaskCount() > 0)
                .setText(R.id.tv_task_count, String.valueOf(item.t.getTaskTaskCount()));
        TextView textView = helper.getView(R.id.tv_task_type_name);
        Drawable drawable = mContext.getResources().getDrawable(item.t.getTaskTaskIconResId());
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
    }
}
