package com.juying.warenqi.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.juying.warenqi.di.module.TaskCenterModule;

import com.juying.warenqi.mvp.ui.fragment.TaskCenterFragment;

@ActivityScope
@Component(modules = TaskCenterModule.class, dependencies = AppComponent.class)
public interface TaskCenterComponent {
    void inject(TaskCenterFragment fragment);
}