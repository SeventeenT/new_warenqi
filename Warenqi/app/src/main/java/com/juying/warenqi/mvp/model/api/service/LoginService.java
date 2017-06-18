package com.digg.warenqiseller.mvp.model.api.service;


import com.digg.warenqiseller.mvp.model.entity.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/6 10:42
 * </pre>
 */
public interface LoginService {

    @FormUrlEncoded
    @POST("/app/login")
    Observable<BaseBean<String>> login(@Field("nick") String username, @Field("pass") String password);

}
