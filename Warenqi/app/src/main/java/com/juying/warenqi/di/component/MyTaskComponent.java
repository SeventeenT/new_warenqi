package com.juying.warenqi.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.juying.warenqi.di.module.MyTaskModule;

import com.juying.warenqi.mvp.ui.fragment.MyTaskFragment;

@ActivityScope
@Component(modules = MyTaskModule.class, dependencies = AppComponent.class)
public interface MyTaskComponent {
    void inject(MyTaskFragment fragment);
}