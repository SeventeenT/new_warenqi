package com.juying.warenqi.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.juying.warenqi.mvp.contract.HomeContract;
import com.juying.warenqi.mvp.model.api.service.HomeService;
import com.juying.warenqi.mvp.model.entity.AccountInfo;
import com.juying.warenqi.mvp.model.entity.Banner;
import com.juying.warenqi.mvp.model.entity.GainedGold;
import com.juying.warenqi.mvp.model.entity.Notice;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class HomeModel extends BaseModel implements HomeContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
                .flatMap(infoBaseBean ->
                        Observable.just(infoBaseBean.getResults()));
    }

    @Override
    public Observable<GainedGold> getGainedGold() {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getGainedGold()
                .flatMap(stringBaseBean -> {
                    String replacedResult = stringBaseBean.getResults().replace("\\", "").replace("\"{", "{").replace("}\"", "}");
                    GainedGold gainedGold = mGson.fromJson(replacedResult, GainedGold.class);
                    return Observable.just(gainedGold);
                });
    }

    @Override
    public Observable<Banner> getBanner() {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getBanner()
                .flatMap(bannerBaseBean ->
                        Observable.just(bannerBaseBean.getResults()));
    }

    @Override
    public Observable<List<Notice>> getNotices() {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getNotices()
                .flatMap(noticeBaseBean ->
                        Observable.just(noticeBaseBean.getResults()));
    }
}