package com.juying.warenqi.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.juying.warenqi.di.module.AccountInfoModule;

import com.juying.warenqi.mvp.ui.fragment.AccountInfoFragment;

@ActivityScope
@Component(modules = AccountInfoModule.class, dependencies = AppComponent.class)
public interface AccountInfoComponent {
    void inject(AccountInfoFragment fragment);
}