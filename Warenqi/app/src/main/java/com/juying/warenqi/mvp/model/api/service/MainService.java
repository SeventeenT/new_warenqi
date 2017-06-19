package com.juying.warenqi.mvp.model.api.service;

import com.juying.warenqi.mvp.model.entity.BaseBean;
import com.juying.warenqi.mvp.model.entity.TakeTask;
import com.juying.warenqi.mvp.model.entity.TaskCount;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/19 12:00
 * </pre>
 */
public interface MainService {

    @GET("app/findAccountByUser")
    Observable<BaseBean<TakeTask>> getTaskInfo(@Query("userId") long userId);

    @GET("user/task/flowTaskCount")
    Observable<BaseBean<TaskCount>> getTaskCount();

}
