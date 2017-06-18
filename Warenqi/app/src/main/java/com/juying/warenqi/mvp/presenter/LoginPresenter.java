package com.digg.warenqiseller.mvp.presenter;

import android.app.Application;

import com.digg.warenqiseller.mvp.contract.LoginContract;
import com.digg.warenqiseller.mvp.model.entity.BaseBean;
import com.digg.warenqiseller.utils.RxUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


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
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean<String> stringBaseBean) {
//                        mRootView.showMessage(stringBaseBean.toString());
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