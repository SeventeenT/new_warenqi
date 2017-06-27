package com.juying.warenqi.mvp.ui.adapter;

import android.text.TextUtils;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.juying.warenqi.R;
import com.juying.warenqi.mvp.model.entity.AccountInfoSection;
import com.juying.warenqi.mvp.ui.holder.BaseAutoLayoutHolder;

import java.util.List;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/27 10:09
 * </pre>
 */
public class AccountInfoAdapter extends BaseSectionQuickAdapter<AccountInfoSection, BaseAutoLayoutHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public AccountInfoAdapter(List<AccountInfoSection> data) {
        super(R.layout.item_account_info_content, R.layout.account_info_divide_header, data);
    }

    @Override
    protected void convertHead(BaseAutoLayoutHolder helper, AccountInfoSection item) {

    }

    @Override
    protected void convert(BaseAutoLayoutHolder helper, AccountInfoSection item) {
        SpanUtils append = new SpanUtils().append(item.t.getTitle());
        if (item.t.getLabelResId() != 0) {
            append.appendSpace(4);
            append.appendImage(item.t.getLabelResId(), SpanUtils.ALIGN_CENTER);
        }
        helper.setText(R.id.tv_account_item_title, append.create())
                .setText(R.id.tv_account_item_extra,
                        TextUtils.isEmpty(item.t.getExtras()) ? "" : item.t.getExtras());
    }
}
