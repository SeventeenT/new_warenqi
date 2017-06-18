package com.juying.warenqi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.juying.warenqi.R;
import com.juying.warenqi.di.component.DaggerLoginComponent;
import com.juying.warenqi.di.module.LoginModule;
import com.juying.warenqi.mvp.contract.LoginContract;
import com.juying.warenqi.mvp.presenter.LoginPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

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
        boolean isLogin = SPUtils.getInstance().getBoolean("isLogin");
        if (isLogin) {
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            launchActivity(intent);
        }
        btnLogin.setEnabled(false);
        Observable<CharSequence> usernameObservable = RxTextView.textChanges(etUsername);
        Observable<CharSequence> pwdObservable = RxTextView.textChanges(etPassword);

        Observable
                .combineLatest(usernameObservable, pwdObservable,
                        (charSequence, charSequence2) ->
                                !StringUtils.isEmpty(charSequence) &&
                                        !StringUtils.isEmpty(charSequence2))
                .subscribe(aBoolean -> RxView.enabled(btnLogin).accept(aBoolean));

        RxView.clicks(btnLogin)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> mPresenter.login(etUsername.getText().toString(), etPassword.getText().toString()));
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
        killMyself();
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R.id.btn_contact_service, R.id.btn_forget_password, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_contact_service:
                break;
            case R.id.btn_forget_password:
                break;
            case R.id.btn_register:
                break;
        }
    }
}