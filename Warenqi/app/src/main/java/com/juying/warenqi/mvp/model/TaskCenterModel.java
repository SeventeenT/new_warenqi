package com.juying.warenqi.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.juying.warenqi.mvp.contract.TaskCenterContract;
import com.juying.warenqi.mvp.model.api.service.MainService;
import com.juying.warenqi.mvp.model.entity.TaskCount;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class TaskCenterModel extends BaseModel implements TaskCenterContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public TaskCenterModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<TaskCount> getTaskCount() {
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .getTaskCount()
                .flatMap(taskCountBaseBean ->
                        Observable.just(taskCountBaseBean.getResults()));
    }
}