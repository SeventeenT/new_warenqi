package com.juying.warenqi.mvp.model.entity;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/27 10:19
 * </pre>
 */
public class AccountInfoSectionContent {
    private String title;
    private int labelResId;
    private String extras;

    public AccountInfoSectionContent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLabelResId() {
        return labelResId;
    }

    public void setLabelResId(int labelResId) {
        this.labelResId = labelResId;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }
}
