package com.digg.warenqiseller.http;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/6 15:28
 * </pre>
 */
public class PersistentCookiesInterceptor implements Interceptor {
    private Context context;

    public PersistentCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            //最近在学习RxJava,这里用了RxJava的相关API大家可以忽略,用自己逻辑实现即可.大家可以用别的方法保存cookie数据
            Observable.just(originalResponse.headers("Set-Cookie"))
                    .map(strings -> {
                        String cookie = "";
                        for (String string : strings) {
                            if (string.contains("TOMCAT_SESSION")) {
                                cookie = string;
                            }
                        }
                        return cookie;
                    })
                    .subscribe(s -> cookieBuffer.append(s).append(";"));
            SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cookie", cookieBuffer.toString());
            editor.apply();
        }

        return originalResponse;
    }

}
