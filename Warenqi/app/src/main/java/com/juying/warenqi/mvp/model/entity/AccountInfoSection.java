package com.juying.warenqi.mvp.model.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/27 10:40
 * </pre>
 */
public class AccountInfoSection extends SectionEntity<AccountInfoSectionContent> {
    public AccountInfoSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public AccountInfoSection(AccountInfoSectionContent accountInfoSectionContent) {
        super(accountInfoSectionContent);
    }
}
