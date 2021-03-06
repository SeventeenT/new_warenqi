package com.juying.warenqi.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.juying.warenqi.R;
import com.juying.warenqi.di.component.DaggerTaskCenterComponent;
import com.juying.warenqi.di.module.TaskCenterModule;
import com.juying.warenqi.mvp.contract.TaskCenterContract;
import com.juying.warenqi.mvp.presenter.TaskCenterPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class TaskCenterFragment extends BaseFragment<TaskCenterPresenter> implements TaskCenterContract.View {


    @BindView(R.id.rv_task_types)
    RecyclerView rvTaskTypes;

    public static TaskCenterFragment newInstance() {
        TaskCenterFragment fragment = new TaskCenterFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerTaskCenterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .taskCenterModule(new TaskCenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_center, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.getTaskCount();
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

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

    }

    @OnClick(R.id.btn_auto_take_task)
    public void onViewClicked() {
    }

    @Override
    public void setAdapter(BaseQuickAdapter adapter) {
        rvTaskTypes.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvTaskTypes.setAdapter(adapter);
    }
}