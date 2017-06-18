package com.juying.warenqi.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.juying.warenqi.mvp.contract.AccountInfoContract;
import com.juying.warenqi.mvp.model.AccountInfoModel;


@Module
public class AccountInfoModule {
    private AccountInfoContract.View view;

    /**
     * 构建AccountInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public AccountInfoModule(AccountInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AccountInfoContract.View provideAccountInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    AccountInfoContract.Model provideAccountInfoModel(AccountInfoModel model) {
        return model;
    }
}