package com.juying.warenqi.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.juying.warenqi.mvp.contract.MainContract;
import com.juying.warenqi.mvp.model.entity.BaseBean;
import com.juying.warenqi.mvp.model.entity.TakeTask;
import com.juying.warenqi.mvp.model.entity.TaskCount;
import com.juying.warenqi.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void getTaskInfo() {
        mModel.getTaskInfo()
                .compose(RxUtils.applySchedulers(mRootView))
                .compose(RxUtils.bindToLifecycle(mRootView))
                .map(BaseBean::getResults)
                .subscribe(new ErrorHandleSubscriber<TakeTask>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull TakeTask takeTask) {
                        mRootView.showMyTaskDot(takeTask);
                    }
                });
    }

    public void getTaskCount() {
        mModel.getTaskCount()
                .compose(RxUtils.applySchedulers(mRootView))
                .compose(RxUtils.bindToLifecycle(mRootView))
                .map(BaseBean::getResults)
                .subscribe(new ErrorHandleSubscriber<TaskCount>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull TaskCount taskCount) {
                        mRootView.showTaskCountDot(taskCount);
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