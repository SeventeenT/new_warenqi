package com.juying.warenqi.mvp.model.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * <pre>
 * Author: @LvYan
 * Description:
 * Date: 2017/6/25 16:15
 * </pre>
 */
public class TaskCenterTypeTitle extends SectionEntity<TaskCenterTypeContent> {

    private int titleTagColor;

    public TaskCenterTypeTitle(TaskCenterTypeContent taskCenterTypeContent) {
        super(taskCenterTypeContent);
    }

    public TaskCenterTypeTitle(boolean isHeader, String header, int titleTagColor) {
        super(isHeader, header);
        this.titleTagColor = titleTagColor;
    }

    public int getTitleTagColor() {
        return titleTagColor;
    }

    public void setTitleTagColor(int titleTagColor) {
        this.titleTagColor = titleTagColor;
    }
}
