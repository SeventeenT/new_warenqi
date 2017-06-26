package com.juying.warenqi.mvp.presenter;

import android.app.Application;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.juying.warenqi.R;
import com.juying.warenqi.mvp.contract.MyTaskContract;
import com.juying.warenqi.mvp.model.entity.MyTaskStateContent;
import com.juying.warenqi.mvp.model.entity.MyTaskStateSection;
import com.juying.warenqi.mvp.ui.adapter.MyTaskStateAdapter;
import com.juying.warenqi.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class MyTaskPresenter extends BasePresenter<MyTaskContract.Model, MyTaskContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private BaseQuickAdapter mAdapter;
    private List<MyTaskStateSection> mList = new ArrayList<>();
    private Observable<BaseQuickAdapter> baseQuickAdapterObservable1;
    private Observable<BaseQuickAdapter> baseQuickAdapterObservable2;

    @Inject
    public MyTaskPresenter(MyTaskContract.Model model, MyTaskContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
        mList.clear();
    }

    public void getFlowTaskCount() {
//        if (mAdapter == null) {
//            mAdapter = new MyTaskStateAdapter(mList);
//        }
//        mRootView.setAdapter(mAdapter);
        baseQuickAdapterObservable1 = mModel.getFlowTaskCount()
                .compose(RxUtils.applySchedulers(mRootView))
                .compose(RxUtils.bindToLifecycle(mRootView))
                .flatMap(flowTaskCount -> {
                    mList.add(new MyTaskStateSection(true,
                            mApplication.getString(R.string.flow_task), R.color.blue_4680fe));

                    MyTaskStateContent waitOperateStatus = new MyTaskStateContent(mApplication.getString(R.string.wait_operate_task_status),
                            flowTaskCount.getWAIT());
                    MyTaskStateContent sellerOperateStatus = new MyTaskStateContent(mApplication.getString(R.string.seller_operating_task_status),
                            flowTaskCount.getSELLER_PROCESS());
                    MyTaskStateContent appealStatus = new MyTaskStateContent(mApplication.getString(R.string.appeal_task_status),
                            flowTaskCount.getAPPEAL());
                    MyTaskStateContent finishedStatus = new MyTaskStateContent(mApplication.getString(R.string.finished_task_status),
                            flowTaskCount.getFINISHED());
                    MyTaskStateContent canceledStatus = new MyTaskStateContent(mApplication.getString(R.string.canceled_task_status),
                            flowTaskCount.getCANCEL());
                    mList.add(new MyTaskStateSection(waitOperateStatus));
                    mList.add(new MyTaskStateSection(sellerOperateStatus));
                    mList.add(new MyTaskStateSection(appealStatus));
                    mList.add(new MyTaskStateSection(finishedStatus));
                    mList.add(new MyTaskStateSection(canceledStatus));
                    return Observable.just(mAdapter);
                });
//        baseQuickAdapterObservable1
//                .subscribe(new ErrorHandleSubscriber<BaseQuickAdapter>(mErrorHandler) {
//                    @Override
//                    public void onNext(@NonNull BaseQuickAdapter baseQuickAdapter) {
//                        baseQuickAdapter.notifyDataSetChanged();
//                    }
//                });
    }

    public void getAdvancedPayTaskCount() {
        if (mAdapter == null) {
            mAdapter = new MyTaskStateAdapter(mList);
        }
        mRootView.setAdapter(mAdapter);
        baseQuickAdapterObservable2 = mModel.getAdvancedPayTaskCount()
                .compose(RxUtils.applySchedulers(mRootView))
                .compose(RxUtils.bindToLifecycle(mRootView))
                .flatMap(payTaskCount -> {
                    mList.add(new MyTaskStateSection(true,
                            mApplication.getString(R.string.advance_pay_task), R.color.red_fb607f));

                    MyTaskStateContent waitOperateStatus = new MyTaskStateContent(mApplication.getString(R.string.wait_operate_task_status),
                            payTaskCount.getWAIT().getCount());
                    MyTaskStateContent sellerOperateStatus = new MyTaskStateContent(mApplication.getString(R.string.seller_operating_task_status),
                            payTaskCount.getSELLER_PROCESS().getCount());
                    MyTaskStateContent waitCommentStatus = new MyTaskStateContent(mApplication.getString(R.string.wait_comment_task_status),
                            payTaskCount.getWAIT_COMMENT().getCount());
                    MyTaskStateContent appealStatus = new MyTaskStateContent(mApplication.getString(R.string.appeal_task_status),
                            payTaskCount.getAPPEAL().getCount());
                    MyTaskStateContent finishedStatus = new MyTaskStateContent(mApplication.getString(R.string.finished_task_status),
                            payTaskCount.getFINISHED().getCount());
                    MyTaskStateContent canceledStatus = new MyTaskStateContent(mApplication.getString(R.string.canceled_task_status),
                            payTaskCount.getCANCEL().getCount());
                    mList.add(new MyTaskStateSection(waitOperateStatus));
                    mList.add(new MyTaskStateSection(sellerOperateStatus));
                    mList.add(new MyTaskStateSection(waitCommentStatus));
                    mList.add(new MyTaskStateSection(appealStatus));
                    mList.add(new MyTaskStateSection(finishedStatus));
                    mList.add(new MyTaskStateSection(canceledStatus));
                    return Observable.just(mAdapter);
                });
//        baseQuickAdapterObservable2
//                .subscribe(new ErrorHandleSubscriber<BaseQuickAdapter>(mErrorHandler) {
//                    @Override
//                    public void onNext(@NonNull BaseQuickAdapter baseQuickAdapter) {
//                        baseQuickAdapter.notifyDataSetChanged();
//                    }
//                });
    }

    public void getAskTaskCount() {
        if (mAdapter == null) {
            mAdapter = new MyTaskStateAdapter(mList);
        }
        mRootView.setAdapter(mAdapter);
        mModel.getAskTaskCount()
                .compose(RxUtils.applySchedulers(mRootView))
                .compose(RxUtils.bindToLifecycle(mRootView))
                .flatMap(askTaskCount -> {
                    mList.add(new MyTaskStateSection(true,
                            mApplication.getString(R.string.ask_task), R.color.yellow_ffb64d));

                    MyTaskStateContent waitOperateStatus = new MyTaskStateContent(mApplication.getString(R.string.wait_operate_task_status),
                            askTaskCount.getWAIT_BUYER_SUBMIT());
                    MyTaskStateContent sellerOperateStatus = new MyTaskStateContent(mApplication.getString(R.string.seller_operating_task_status),
                            askTaskCount.getWAIT_SELLER());
                    MyTaskStateContent appealStatus = new MyTaskStateContent(mApplication.getString(R.string.appeal_task_status),
                            askTaskCount.getAPPEAL());
                    MyTaskStateContent finishedStatus = new MyTaskStateContent(mApplication.getString(R.string.finished_task_status),
                            askTaskCount.getFINISHED());
                    MyTaskStateContent canceledStatus = new MyTaskStateContent(mApplication.getString(R.string.canceled_task_status),
                            askTaskCount.getCANCEL());
                    mList.add(new MyTaskStateSection(waitOperateStatus));
                    mList.add(new MyTaskStateSection(sellerOperateStatus));
                    mList.add(new MyTaskStateSection(appealStatus));
                    mList.add(new MyTaskStateSection(finishedStatus));
                    mList.add(new MyTaskStateSection(canceledStatus));
                    return Observable.just(mAdapter);
                })
                .startWith(baseQuickAdapterObservable2)
                .startWith(baseQuickAdapterObservable1)
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
        this.mList = null;
    }

}