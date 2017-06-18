package com.juying.warenqi.di.component;

import com.digg.warenqiseller.di.module.LoginModule;
import com.digg.warenqiseller.mvp.ui.activity.LoginActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}