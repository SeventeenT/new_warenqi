package com.juying.warenqi.mvp.model.api.service;

import com.juying.warenqi.mvp.model.entity.AccountInfo;
import com.juying.warenqi.mvp.model.entity.Banner;
import com.juying.warenqi.mvp.model.entity.BaseBean;
import com.juying.warenqi.mvp.model.entity.Notice;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * <pre>
 * Author: @LvYan
 * Description:
 * Date: 2017/6/24 15:14
 * </pre>
 */
public interface HomeService {

    @GET("app/index")
    Observable<BaseBean<AccountInfo>> getGoldInfo();

    @GET("user/task/findUserIngot")
    Observable<BaseBean<String>> getGainedGold();

    @GET("app/getAppConfigs")
    Observable<BaseBean<Banner>> getBanner();

    @GET("app/noticeTopList")
    Observable<BaseBean<List<Notice>>> getNotices();

}
