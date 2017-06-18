package com.juying.warenqi.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.juying.warenqi.mvp.contract.LoginContract;
import com.juying.warenqi.mvp.model.api.service.LoginService;
import com.juying.warenqi.mvp.model.entity.AccountInfo;
import com.juying.warenqi.mvp.model.entity.BaseBean;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseBean<AccountInfo>> login(String username, String password) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class).login(username, password);
    }

}