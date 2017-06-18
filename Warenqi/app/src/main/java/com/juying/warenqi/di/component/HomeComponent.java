package com.juying.warenqi.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.juying.warenqi.di.module.HomeModule;

import com.juying.warenqi.mvp.ui.fragment.HomeFragment;

@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment fragment);
}