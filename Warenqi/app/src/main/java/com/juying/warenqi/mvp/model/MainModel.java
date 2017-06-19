package com.juying.warenqi.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.juying.warenqi.mvp.contract.MainContract;
import com.juying.warenqi.mvp.model.api.service.MainService;
import com.juying.warenqi.mvp.model.entity.BaseBean;
import com.juying.warenqi.mvp.model.entity.TakeTask;
import com.juying.warenqi.mvp.model.entity.TaskCount;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MainModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseBean<TakeTask>> getTaskInfo() {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getTaskInfo(SPUtils.getInstance().getInt("userId"));
    }

    @Override
    public Observable<BaseBean<TaskCount>> getTaskCount() {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getTaskCount();
    }
}