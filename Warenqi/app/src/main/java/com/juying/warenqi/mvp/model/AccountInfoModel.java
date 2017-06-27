package com.juying.warenqi.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.juying.warenqi.mvp.contract.AccountInfoContract;
import com.juying.warenqi.mvp.model.api.service.HomeService;
import com.juying.warenqi.mvp.model.entity.AccountInfo;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class AccountInfoModel extends BaseModel implements AccountInfoContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public AccountInfoModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<AccountInfo> getGoldInfo() {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getGoldInfo()
                .flatMap(accountInfoBaseBean ->
                        Observable.just(accountInfoBaseBean.getResults()));
    }
}