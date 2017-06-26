package com.juying.warenqi.mvp.model.api.service;

import com.juying.warenqi.mvp.model.entity.AdvancedPayTaskCount;
import com.juying.warenqi.mvp.model.entity.AskTaskCount;
import com.juying.warenqi.mvp.model.entity.BaseBean;
import com.juying.warenqi.mvp.model.entity.FlowTaskCount;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/26 14:19
 * </pre>
 */
public interface MyTaskStateService {

    @GET("app/flow/buyerTasksForAppNew")
    Observable<BaseBean<FlowTaskCount>> getFlowTaskCount();

    @GET("app/sdsub/findSDDaysTaskStatusCount")
    Observable<BaseBean<AdvancedPayTaskCount>> getAdvancedPayTaskCount();

    @GET("app/questionTask/listQuestionTaskCount")
    Observable<BaseBean<AskTaskCount>> getAskTaskCount();

}
