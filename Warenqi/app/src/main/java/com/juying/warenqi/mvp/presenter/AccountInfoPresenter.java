package com.juying.warenqi.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.juying.warenqi.R;
import com.juying.warenqi.mvp.contract.AccountInfoContract;
import com.juying.warenqi.mvp.model.entity.AccountInfoSection;
import com.juying.warenqi.mvp.model.entity.AccountInfoSectionContent;
import com.juying.warenqi.mvp.ui.adapter.AccountInfoAdapter;
import com.juying.warenqi.utils.RxUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class AccountInfoPresenter extends BasePresenter<AccountInfoContract.Model, AccountInfoContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private AccountInfoAdapter mAdapter;
    private List<AccountInfoSection> mList = new ArrayList<>();

    @Inject
    public AccountInfoPresenter(AccountInfoContract.Model model, AccountInfoContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void getMoneyInfo() {
        if (mAdapter == null) {
            mAdapter = new AccountInfoAdapter(mList);
        }
        mRootView.setAdapter(mAdapter);
        mModel.getGoldInfo()
                .compose(RxUtils.applySchedulers(mRootView))
                .compose(RxUtils.bindToLifecycle(mRootView))
                .flatMap(accountInfo -> {
                    mRootView.setMoneyInfo(accountInfo);
                    mList.clear();
                    AccountInfoSection header1 = new AccountInfoSection(true, null);
                    mList.add(header1);
                    AccountInfoSection personalInfoSection = new AccountInfoSection(new AccountInfoSectionContent(mApplication.getResources().getString(R.string.personal_info)));
                    AccountInfoSection taobaoAccountInfoSection = new AccountInfoSection(new AccountInfoSectionContent(mApplication.getResources().getString(R.string.taobao_account)));
                    AccountInfoSection realNameAuthSection = new AccountInfoSection(new AccountInfoSectionContent(mApplication.getResources().getString(R.string.real_name_auth)));
                    AccountInfoSectionContent huabeiAccount = new AccountInfoSectionContent(mApplication.getResources().getString(R.string.huabei_account_auth));
                    huabeiAccount.setLabelResId(R.drawable.pic_new);
                    AccountInfoSection huabeiAccountAuthSection = new AccountInfoSection(huabeiAccount);
                    AccountInfoSection bankAccountSection = new AccountInfoSection(new AccountInfoSectionContent(mApplication.getResources().getString(R.string.my_bank_account)));
                    AccountInfoSection takeSettingsSection = new AccountInfoSection(new AccountInfoSectionContent(mApplication.getResources().getString(R.string.take_settings)));
                    mList.add(personalInfoSection);
                    mList.add(taobaoAccountInfoSection);
                    mList.add(realNameAuthSection);
                    mList.add(huabeiAccountAuthSection);
                    mList.add(bankAccountSection);
                    mList.add(takeSettingsSection);
                    AccountInfoSection header2 = new AccountInfoSection(true, null);
                    mList.add(header2);
                    AccountInfoSection capitalDetailSection = new AccountInfoSection(new AccountInfoSectionContent(mApplication.getResources().getString(R.string.capital_detail)));
                    AccountInfoSectionContent withdrawGold = new AccountInfoSectionContent(mApplication.getResources().getString(R.string.withdraw_gold));
                    withdrawGold.setExtras(BigDecimal.valueOf(accountInfo.getIngot()).movePointLeft(2).toPlainString());
                    AccountInfoSection withdrawGoldSection = new AccountInfoSection(withdrawGold);
                    AccountInfoSectionContent withdrawDeposit = new AccountInfoSectionContent(mApplication.getResources().getString(R.string.withdraw_deposit));
                    withdrawDeposit.setExtras(BigDecimal.valueOf(accountInfo.getDeposit()).movePointLeft(2).toPlainString());
                    AccountInfoSection withdrawDepositSection = new AccountInfoSection(withdrawDeposit);
                    AccountInfoSection inviteDetailSection = new AccountInfoSection(new AccountInfoSectionContent(mApplication.getResources().getString(R.string.invite_detail)));
                    mList.add(capitalDetailSection);
                    mList.add(withdrawGoldSection);
                    mList.add(withdrawDepositSection);
                    mList.add(inviteDetailSection);
                    AccountInfoSection header3 = new AccountInfoSection(true, null);
                    mList.add(header3);
                    AccountInfoSection feedbackSection = new AccountInfoSection(new AccountInfoSectionContent(mApplication.getResources().getString(R.string.feedback)));
                    AccountInfoSection noticesSection = new AccountInfoSection(new AccountInfoSectionContent(mApplication.getResources().getString(R.string.notices)));
                    AccountInfoSection helpCenterSection = new AccountInfoSection(new AccountInfoSectionContent(mApplication.getResources().getString(R.string.help_center)));
                    AccountInfoSection shopSection = new AccountInfoSection(new AccountInfoSectionContent(mApplication.getResources().getString(R.string.one_yuan_shop)));
                    mList.add(feedbackSection);
                    mList.add(noticesSection);
                    mList.add(helpCenterSection);
                    mList.add(shopSection);
                    return Observable.just(mAdapter);
                })
                .subscribe(new ErrorHandleSubscriber<AccountInfoAdapter>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull AccountInfoAdapter accountInfoAdapter) {
                        accountInfoAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}