package com.juying.warenqi.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.juying.warenqi.mvp.contract.MyTaskContract;
import com.juying.warenqi.mvp.model.api.service.MyTaskStateService;
import com.juying.warenqi.mvp.model.entity.AdvancedPayTaskCount;
import com.juying.warenqi.mvp.model.entity.AskTaskCount;
import com.juying.warenqi.mvp.model.entity.FlowTaskCount;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class MyTaskModel extends BaseModel implements MyTaskContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MyTaskModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<FlowTaskCount> getFlowTaskCount() {
        return mRepositoryManager
                .obtainRetrofitService(MyTaskStateService.class)
                .getFlowTaskCount()
                .flatMap(flowTaskCountBaseBean ->
                        Observable.just(flowTaskCountBaseBean.getResults()));
    }

    @Override
    public Observable<AdvancedPayTaskCount> getAdvancedPayTaskCount() {
        return mRepositoryManager
                .obtainRetrofitService(MyTaskStateService.class)
                .getAdvancedPayTaskCount()
                .flatMap(advancedPayTaskCountBaseBean ->
                        Observable.just(advancedPayTaskCountBaseBean.getResults()));
    }

    @Override
    public Observable<AskTaskCount> getAskTaskCount() {
        return mRepositoryManager
                .obtainRetrofitService(MyTaskStateService.class)
                .getAskTaskCount()
                .flatMap(askTaskCountBaseBean ->
                        Observable.just(askTaskCountBaseBean.getResults()));
    }
}