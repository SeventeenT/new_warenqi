package com.juying.warenqi.mvp.presenter;

import android.app.Application;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.juying.warenqi.R;
import com.juying.warenqi.mvp.contract.TaskCenterContract;
import com.juying.warenqi.mvp.model.entity.TaskCenterTypeContent;
import com.juying.warenqi.mvp.model.entity.TaskCenterTypeTitle;
import com.juying.warenqi.mvp.ui.adapter.TaskCenterTypeAdapter;
import com.juying.warenqi.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class TaskCenterPresenter extends BasePresenter<TaskCenterContract.Model, TaskCenterContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private BaseQuickAdapter mAdapter;
    private List<TaskCenterTypeTitle> mTitleList = new ArrayList<>();

    @Inject
    public TaskCenterPresenter(TaskCenterContract.Model model, TaskCenterContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void getTaskCount() {
        if (mAdapter == null) {
            mAdapter = new TaskCenterTypeAdapter(mTitleList);
        }
        mRootView.setAdapter(mAdapter);
        mModel.getTaskCount()
                .compose(RxUtils.applySchedulers(mRootView))
                .compose(RxUtils.bindToLifecycle(mRootView))
                .flatMap(taskCount -> {
                    mTitleList.clear();
                    mTitleList.add(new TaskCenterTypeTitle(true,
                            mApplication.getString(R.string.flow_task), R.color.blue_4680fe));

                    TaskCenterTypeContent normalTask = new TaskCenterTypeContent(mApplication.getString(R.string.normal_task),
                            R.drawable.ic_routine);
                    normalTask.setTaskTaskCount(taskCount.getOrderCount());
                    mTitleList.add(new TaskCenterTypeTitle(normalTask));
                    TaskCenterTypeContent directTask = new TaskCenterTypeContent(mApplication.getString(R.string.direct_task),
                            R.drawable.ic_train);
                    directTask.setTaskTaskCount(taskCount.getZtcCount());
                    mTitleList.add(new TaskCenterTypeTitle(directTask));
                    TaskCenterTypeContent activityTask = new TaskCenterTypeContent(mApplication.getString(R.string.activity_task),
                            R.drawable.ic_activity);
                    activityTask.setTaskTaskCount(taskCount.getHdCount());
                    mTitleList.add(new TaskCenterTypeTitle(activityTask));

                    mTitleList.add(new TaskCenterTypeTitle(true,
                            mApplication.getString(R.string.buy_task), R.color.red_fb607f));

                    TaskCenterTypeContent advancePayTask = new TaskCenterTypeContent(mApplication.getString(R.string.advance_pay_task),
                            R.drawable.ic_quality);
                    advancePayTask.setTaskTaskCount(taskCount.getDfCount());
                    mTitleList.add(new TaskCenterTypeTitle(advancePayTask));
                    TaskCenterTypeContent daysTask = new TaskCenterTypeContent(mApplication.getString(R.string.several_days_task),
                            R.drawable.ic_longtime);
                    daysTask.setTaskTaskCount(taskCount.getDaysDfCount());
                    mTitleList.add(new TaskCenterTypeTitle(daysTask));
                    return Observable.just(mAdapter);
                })
                .subscribe(new ErrorHandleSubscriber<BaseQuickAdapter>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseQuickAdapter baseQuickAdapter) {
                        baseQuickAdapter.notifyDataSetChanged();
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
        this.mAdapter = null;
        this.mTitleList = null;
    }

}