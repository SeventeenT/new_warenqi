package com.juying.warenqi.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.juying.warenqi.R;
import com.juying.warenqi.app.BannerGlideImageLoader;
import com.juying.warenqi.di.component.DaggerHomeComponent;
import com.juying.warenqi.di.module.HomeModule;
import com.juying.warenqi.mvp.contract.HomeContract;
import com.juying.warenqi.mvp.model.entity.AccountInfo;
import com.juying.warenqi.mvp.model.entity.GainedGold;
import com.juying.warenqi.mvp.model.entity.Notice;
import com.juying.warenqi.mvp.model.entity.ParsedBanner;
import com.juying.warenqi.mvp.presenter.HomePresenter;
import com.orhanobut.logger.Logger;
import com.sunfusheng.marqueeview.MarqueeView;
import com.weavey.loading.lib.LoadingLayout;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {


    @BindView(R.id.tv_my_gold)
    TextView tvMyGold;
    @BindView(R.id.ll_my_gold_container)
    LinearLayout llMyGoldContainer;
    @BindView(R.id.tv_my_deposit)
    TextView tvMyDeposit;
    @BindView(R.id.ll_my_deposit_container)
    LinearLayout llMyDepositContainer;
    @BindView(R.id.ll_money_container)
    LinearLayout llMoneyContainer;
    @BindView(R.id.tv_today_income)
    TextView tvTodayIncome;
    @BindView(R.id.mv_notification)
    MarqueeView mvNotification;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initRequest();
        banner.setImageLoader(new BannerGlideImageLoader());
        mLoadingLayout.setOnReloadListener(v -> initRequest());
    }

    private void initRequest() {
        mPresenter.getGoldInfo();
        mPresenter.getGainedGold();
        mPresenter.getBanner();
        mPresenter.getNotices();
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {
        mLoadingLayout.setStatus(LoadingLayout.Loading);
    }

    @Override
    public void hideLoading() {
        mLoadingLayout.setStatus(LoadingLayout.Success);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    public void setGoldInfo(AccountInfo goldInfo) {
        long ingot = goldInfo.getIngot();
        tvMyGold.setText(BigDecimal.valueOf(ingot).movePointLeft(2).toPlainString());
        long deposit = goldInfo.getDeposit();
        tvMyDeposit.setText(BigDecimal.valueOf(deposit).movePointLeft(2).toPlainString());
    }

    public void setGainedGold(GainedGold gold) {
        long ingotProcess = gold.getIngotProcess();
        String estimatedIncomeStr = BigDecimal.valueOf(ingotProcess).movePointLeft(2).toPlainString();
        long todayIngot = gold.getTodayIngot();
        String todayIncomeStr = BigDecimal.valueOf(todayIngot).movePointLeft(2).toPlainString();
        long pledgeIngot = gold.getPledgeIngot();
        String depositStr = BigDecimal.valueOf(pledgeIngot).movePointLeft(2).toPlainString();
        tvTodayIncome.setText(String.format(getString(R.string.estimate_income),
                estimatedIncomeStr, todayIncomeStr, depositStr));
    }

    public void setBanner(com.juying.warenqi.mvp.model.entity.Banner banner) {

        List<ParsedBanner> banners = new ArrayList<>();
        if (!TextUtils.isEmpty(banner.getBannerImg1())) {
            ParsedBanner banner1 = new ParsedBanner();
            banner1.setImgUrl(banner.getBannerImg1());
            banner1.setImgLinkUrl(banner.getBannerLink1());
            banners.add(banner1);
        }

        if (!TextUtils.isEmpty(banner.getBannerImg2())) {
            ParsedBanner banner2 = new ParsedBanner();
            banner2.setImgUrl(banner.getBannerImg2());
            banner2.setImgLinkUrl(banner.getBannerLink2());
            banners.add(banner2);
        }

        if (!TextUtils.isEmpty(banner.getBannerImg3())) {
            ParsedBanner banner3 = new ParsedBanner();
            banner3.setImgUrl(banner.getBannerImg3());
            banner3.setImgLinkUrl(banner.getBannerLink3());
            banners.add(banner3);
        }

        if (!TextUtils.isEmpty(banner.getBannerImg4())) {
            ParsedBanner banner4 = new ParsedBanner();
            banner4.setImgUrl(banner.getBannerImg4());
            banner4.setImgLinkUrl(banner.getBannerLink4());
            banners.add(banner4);
        }

        if (!TextUtils.isEmpty(banner.getBannerImg5())) {
            ParsedBanner banner5 = new ParsedBanner();
            banner5.setImgUrl(banner.getBannerImg5());
            banner5.setImgLinkUrl(banner.getBannerLink5());
            banners.add(banner5);
        }

        this.banner.setImages(banners);
        this.banner.start();
        this.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Logger.d(banners.get(position).getImgUrl());
                Logger.d(banners.get(position).getImgLinkUrl());
            }
        });
    }

    public void setNotices(List<Notice> notices) {
        Observable.just(notices)
                .map(noticeList -> {
                    List<String> list = new ArrayList<>();
                    for (Notice notice : noticeList) {
                        list.add(notice.getTitle());
                    }
                    return list;
                })
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull List<String> strings) throws Exception {
                        mvNotification.setNotices(strings);
                        mvNotification.start();
                    }
                });
        mvNotification.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
        mvNotification.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
        mvNotification.stopFlipping();
    }

    @OnClick({R.id.btn_online_service, R.id.btn_more_notifications})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_online_service:
                break;
            case R.id.btn_more_notifications:
                break;
        }
    }

    @Override
    public void showError() {
        mLoadingLayout.setStatus(LoadingLayout.Error);
    }
}