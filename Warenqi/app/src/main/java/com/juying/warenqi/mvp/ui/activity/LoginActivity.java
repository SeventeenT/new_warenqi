package com.digg.warenqiseller.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digg.warenqiseller.R;
import com.digg.warenqiseller.di.component.DaggerLoginComponent;
import com.digg.warenqiseller.di.module.LoginModule;
import com.digg.warenqiseller.mvp.contract.LoginContract;
import com.digg.warenqiseller.mvp.presenter.LoginPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.iv)
    ImageView iv;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this,
                GlideImageConfig.builder().url("http://p4.so.qhimgs1.com/t0164fdb3551929d96a.jpg").imageView(iv).build()
        );
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    public void onClick(View view) {
        mPresenter.login("aaaa", "a123456");
    }
}