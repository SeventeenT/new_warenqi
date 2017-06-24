package com.juying.warenqi.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.juying.warenqi.mvp.contract.HomeContract;
import com.juying.warenqi.mvp.model.entity.AccountInfo;
import com.juying.warenqi.mvp.model.entity.Banner;
import com.juying.warenqi.mvp.model.entity.GainedGold;
import com.juying.warenqi.mvp.model.entity.Notice;
import com.juying.warenqi.mvp.ui.fragment.HomeFragment;
import com.juying.warenqi.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void getGoldInfo() {
        mModel.getGoldInfo()
                .compose(RxUtils.applySchedulers(mRootView))
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<AccountInfo>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull AccountInfo accountInfo) {
                        ((HomeFragment) mRootView).setGoldInfo(accountInfo);
                    }
                });
    }

    public void getGainedGold() {
        mModel.getGainedGold()
                .compose(RxUtils.applySchedulers(mRootView))
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GainedGold>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull GainedGold gainedGold) {
                        ((HomeFragment) mRootView).setGainedGold(gainedGold);
                    }
                });
    }

    public void getBanner() {
        mModel.getBanner()
                .compose(RxUtils.applySchedulers(mRootView))
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Banner>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull Banner banner) {
                        ((HomeFragment) mRootView).setBanner(banner);
                    }
                });
    }

    public void getNotices() {
        mModel.getNotices()
                .compose(RxUtils.applySchedulers(mRootView))
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<Notice>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<Notice> noticeList) {
                        ((HomeFragment) mRootView).setNotices(noticeList);
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