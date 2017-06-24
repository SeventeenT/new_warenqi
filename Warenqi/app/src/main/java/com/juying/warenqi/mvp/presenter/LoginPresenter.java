package com.juying.warenqi.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.juying.warenqi.mvp.contract.LoginContract;
import com.juying.warenqi.mvp.model.entity.AccountInfo;
import com.juying.warenqi.mvp.ui.activity.MainActivity;
import com.juying.warenqi.utils.RxUtils;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void login(String username, String password) {
        mModel.login(username, password)
                .compose(RxUtils.applySchedulers(mRootView))
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(accountInfo -> {
                    mRootView.showMessage("登录成功");
                    saveAccountInfo(accountInfo);
                    Intent intent = new Intent();
                    intent.setClass(mApplication, MainActivity.class);
                    mRootView.launchActivity(intent);
                });
    }

    private void saveAccountInfo(AccountInfo accountInfo) {
        SPUtils sp = SPUtils.getInstance();
        sp.put("isLogin", true);
        sp.put("userId", accountInfo.getId());
        sp.put("nick", accountInfo.getNick());
        sp.put("headPic", accountInfo.getHeadPic());
        sp.put("payPass", accountInfo.getPayPassword());
        sp.put("vipStatus", accountInfo.getVipStatus());
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