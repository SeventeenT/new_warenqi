package com.juying.warenqi.app;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.JsonParseException;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.http.RequestInterceptor;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.UiUtils;
import com.juying.warenqi.BuildConfig;
import com.juying.warenqi.R;
import com.juying.warenqi.http.AddCookiesInterceptor;
import com.juying.warenqi.http.ApiException;
import com.juying.warenqi.http.PersistentCookiesInterceptor;
import com.juying.warenqi.mvp.model.api.Api;
import com.juying.warenqi.mvp.model.api.cache.CommonCache;
import com.juying.warenqi.mvp.model.api.service.CommonService;
import com.juying.warenqi.mvp.model.api.service.HomeService;
import com.juying.warenqi.mvp.model.api.service.LoginService;
import com.juying.warenqi.mvp.model.api.service.MainService;
import com.juying.warenqi.mvp.ui.activity.LoginActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.weavey.loading.lib.LoadingLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.HttpException;
import timber.log.Timber;

/**
 * app的全局配置信息在此配置,需要将此实现类声明到AndroidManifest中
 */
public class GlobalConfiguration implements ConfigModule {
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        builder.baseurl(Api.APP_DOMAIN)
                .globalHttpHandler(new GlobalHttpHandler() {// 这里可以提供一个全局处理Http请求和响应结果的处理类,
                    // 这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
                    @Override
                    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                        //这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                        //重新请求token,并重新执行请求
                        Observable<Object> errorObservable = null;
                        try {
                            if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response.body().contentType())) {
                                JSONObject object = new JSONObject(httpResult);
                                int code = object.getInt("code");
                                String msg = object.getString("msg");
                                if (code != Api.SUCCESS_CODE) {
                                    errorObservable = Observable.error(new ApiException(code, msg));
                                    errorObservable
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new ErrorHandleSubscriber<Object>(((BaseApplication) context).getAppComponent().rxErrorHandler()) {

                                                @Override
                                                public void onNext(@NonNull Object o) {

                                                }
                                            });

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            return response;
                        } finally {
                            if (errorObservable != null) {
                                errorObservable.unsubscribeOn(Schedulers.io());
                            }
                        }

                        return response;
                    }

                    // 这里可以在请求服务器之前可以拿到request,做一些操作比如给request统一添加token或者header以及参数加密等操作
                    @Override
                    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
//                         如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的requeat如增加header,不做操作则直接返回request参数
                        return chain.request().newBuilder().header("secret", BuildConfig.SECRET)
                                .build();
                    }
                })
                .responseErrorListener((context1, t) -> {
                    /* 用来提供处理所有错误的监听
                       rxjava必要要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法),此监听才生效 */
                    Logger.t("Catch-Error").w(t.getMessage());
                    //这里不光是只能打印错误,还可以根据不同的错误作出不同的逻辑处理
                    String msg = "未知错误";
                    if (t instanceof UnknownHostException || t instanceof ConnectException) {
                        msg = "网络不可用";
                    } else if (t instanceof SocketTimeoutException) {
                        msg = "请求网络超时";
                    } else if (t instanceof ApiException) {
                        msg = handleServerErrorStatus((ApiException) t, context1);
                    } else if (t instanceof HttpException) {
                        HttpException httpException = (HttpException) t;
                        msg = convertStatusCode(httpException);
                    } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException) {
                        msg = "数据解析错误";
                    }
                    UiUtils.snackbarText(msg);
                })
                .gsonConfiguration((context1, gsonBuilder) -> {//这里可以自己自定义配置Gson的参数
                    gsonBuilder
                            .serializeNulls()//支持序列化null的参数
                            .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
                })
                .retrofitConfiguration((context1, retrofitBuilder) -> {//这里可以自己自定义配置Retrofit的参数,甚至你可以替换系统配置好的okhttp对象
//                    retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());//比如使用fastjson替代gson
                })
                .okhttpConfiguration((context1, okhttpBuilder) -> {//这里可以自己自定义配置Okhttp的参数
                    okhttpBuilder.writeTimeout(10, TimeUnit.SECONDS);
                })
                .addInterceptor(new PersistentCookiesInterceptor(context))
                .addInterceptor(new AddCookiesInterceptor(context))
                .rxCacheConfiguration((context1, rxCacheBuilder) -> {//这里可以自己自定义配置RxCache的参数
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                });
    }

    @Override
    public void registerComponents(Context context, IRepositoryManager repositoryManager) {
        repositoryManager.injectRetrofitService(
                CommonService.class,
                LoginService.class,
                MainService.class,
                HomeService.class);
        repositoryManager.injectCacheService(CommonCache.class);
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppDelegate.Lifecycle> lifecycles) {
        // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(new AppDelegate.Lifecycle() {

            @Override
            public void onCreate(Application application) {
                if (BuildConfig.DEBUG) {//Logger日志打印
                    Timber.plant(new Timber.DebugTree());
                    Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder()
                            .tag("Warenqi-New").build()));
                }
                //leakCanary内存泄露检查
                ((App) application).getAppComponent().extras().put(RefWatcher.class.getName(),
                        BuildConfig.DEBUG ? LeakCanary.install(application) : RefWatcher.DISABLED);
                //utils库初始化
                Utils.init(application);
                //友盟推送初始化
                PushAgent pushAgent = PushAgent.getInstance(application);
                pushAgent.register(new IUmengRegisterCallback() {

                    @Override
                    public void onSuccess(String s) {
                        Logger.w("device_token:" + s);
                    }

                    @Override
                    public void onFailure(String s, String s1) {
                        Logger.d("umeng register failure : " + s + ",\n" + s1);
                    }
                });
                //使用自定义view显示推送通知
                //pushAgent.setMessageHandler(mMessageHandler);
                pushAgent.setNotificationClickHandler(mUmengNotificationClickHandler);

                MobclickAgent.openActivityDurationTrack(true);
                MobclickAgent.setScenarioType(application, MobclickAgent.EScenarioType.E_UM_NORMAL);

                LoadingLayout.getConfig()
                        .setErrorText("出错啦~请稍后重试！")
                        .setEmptyText("抱歉，暂无数据")
                        .setNoNetworkText("无网络连接，请检查您的网络···")
                        .setErrorImage(R.drawable.ic_error)
                        .setEmptyImage(R.drawable.ic_empty)
                        .setNoNetworkImage(R.drawable.ic_no_network)
                        .setAllTipTextColor(R.color.grey_333)
                        .setAllTipTextSize(14)
                        .setReloadButtonText("点我重试哦")
                        .setReloadButtonTextSize(14)
                        .setReloadButtonTextColor(R.color.grey_333)
                        .setReloadButtonWidthAndHeight(150, 40);
            }

            @Override
            public void onTerminate(Application application) {

            }
        });
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Logger.w(activity + " - onActivityCreated");
                //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
                if (activity.findViewById(R.id.toolbar) != null) {
                    if (activity instanceof AppCompatActivity) {
                        ((AppCompatActivity) activity).setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
                        ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.toolbar));
                            activity.getActionBar().setDisplayShowTitleEnabled(false);
                        }
                    }
                }
                if (activity.findViewById(R.id.toolbar_title) != null) {
                    ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
                }
                if (activity.findViewById(R.id.toolbar_back) != null) {
                    activity.findViewById(R.id.toolbar_back).setOnClickListener(v -> activity.onBackPressed());
                }

                PushAgent.getInstance(activity).onAppStart();
            }


            @Override
            public void onActivityStarted(Activity activity) {
                Logger.w(activity + " - onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Logger.w(activity + " - onActivityResumed");
                MobclickAgent.onResume(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Logger.w(activity + " - onActivityPaused");
                MobclickAgent.onPause(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Logger.w(activity + " - onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Logger.w(activity + " - onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Logger.w(activity + " - onActivityDestroyed");
            }
        });
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建是重复利用已经创建的Fragment。
                // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
                // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
                f.setRetainInstance(true);
            }

            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                ((RefWatcher) ((App) f.getActivity().getApplication())
                        .getAppComponent()
                        .extras()
                        .get(RefWatcher.class.getName()))
                        .watch(f);
            }
        });
    }

    private String handleServerErrorStatus(ApiException e, Context context1) {
        if (e.getCode() == -1) {
            ToastUtils.showShort("测试到");
        } else if (e.getCode() == 561) {
            ToastUtils.showShort("请重新登录");
            SPUtils.getInstance().put("isLogin", false);
            AppManager appManager = ((BaseApplication) context1).getAppComponent().appManager();
            appManager.killAll();
            appManager.startActivity(LoginActivity.class);
        }
        return e.getMessage();
    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }

    private UmengNotificationClickHandler mUmengNotificationClickHandler = new UmengNotificationClickHandler() {
        @Override
        public void dealWithCustomAction(Context context, UMessage msg) {
            Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
        }
    };

    private UmengMessageHandler mMessageHandler = new UmengMessageHandler() {
        @Override
        public Notification getNotification(Context context, UMessage msg) {
            switch (msg.builder_id) {
                case 1:
                    Notification.Builder builder = new Notification.Builder(context);
                    RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
                            R.layout.notification_view);
                    myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                    myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                    myNotificationView.setImageViewBitmap(R.id.notification_large_icon,
                            getLargeIcon(context, msg));
                    myNotificationView.setImageViewResource(R.id.notification_small_icon,
                            getSmallIconId(context, msg));
                    builder.setContent(myNotificationView)
                            .setSmallIcon(getSmallIconId(context, msg))
                            .setTicker(msg.ticker)
                            .setAutoCancel(true);

                    return builder.getNotification();
                default:
                    //默认为0，若填写的builder_id并不存在，也使用默认。
                    return super.getNotification(context, msg);
            }
        }
    };

}
