package com.juying.warenqi.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.autolayout.AutoToolbar;
import com.juying.warenqi.R;
import com.juying.warenqi.app.TabEntity;
import com.juying.warenqi.di.component.DaggerMainComponent;
import com.juying.warenqi.di.module.MainModule;
import com.juying.warenqi.mvp.contract.MainContract;
import com.juying.warenqi.mvp.model.entity.TakeTask;
import com.juying.warenqi.mvp.model.entity.TaskCount;
import com.juying.warenqi.mvp.presenter.MainPresenter;
import com.juying.warenqi.mvp.ui.fragment.AccountInfoFragment;
import com.juying.warenqi.mvp.ui.fragment.HomeFragment;
import com.juying.warenqi.mvp.ui.fragment.MyTaskFragment;
import com.juying.warenqi.mvp.ui.fragment.TaskCenterFragment;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.juying.warenqi.app.Constants.ACTIVITY_FRAGMENT_REPLACE;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.toolbar_back)
    AutoRelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.tab_main)
    CommonTabLayout tabMain;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;

    private String[] mTabTitles = {"首页", "游戏大厅", "我的游戏", "个人中心"};
    private int[] mIconUnselectedIds = {
            R.drawable.main_home, R.drawable.main_mission,
            R.drawable.main_my_task, R.drawable.main_me};
    private int[] mIconSelectedIds = {
            R.drawable.home_click, R.drawable.mission_click,
            R.drawable.my_click, R.drawable.personal_click};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<String> mTitles;
    private List<Fragment> mFragments;
    private int mCurrentPos = 0;
    private boolean hasTakenTasks;
    private boolean hasTask;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.getTaskInfo();
        mPresenter.getTaskCount();
        toolbarBack.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        for (int i = 0; i < mTabTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTabTitles[i], mIconSelectedIds[i], mIconUnselectedIds[i]));
        }

        tabMain.setTabData(mTabEntities);
        tabMain.setOnTabSelectListener(mTabSelectListener);

        if (mTitles == null) {
            mTitles = new ArrayList<>();
            mTitles.add(null);
            mTitles.add("游戏大厅");
            mTitles.add("我的游戏");
            mTitles.add(null);
        }

        HomeFragment homeFragment;
        TaskCenterFragment taskCenterFragment;
        MyTaskFragment myTaskFragment;
        AccountInfoFragment infoFragment;
        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
            taskCenterFragment = TaskCenterFragment.newInstance();
            myTaskFragment = MyTaskFragment.newInstance();
            infoFragment = AccountInfoFragment.newInstance();
        } else {
            mCurrentPos = savedInstanceState.getInt(ACTIVITY_FRAGMENT_REPLACE);
            FragmentManager fm = getSupportFragmentManager();
            homeFragment =
                    (HomeFragment) FragmentUtils.findFragment(fm, HomeFragment.class);
            taskCenterFragment =
                    (TaskCenterFragment) FragmentUtils.findFragment(fm, TaskCenterFragment.class);
            myTaskFragment =
                    (MyTaskFragment) FragmentUtils.findFragment(fm, MyTaskFragment.class);
            infoFragment =
                    (AccountInfoFragment) FragmentUtils.findFragment(fm, AccountInfoFragment.class);
        }

        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(homeFragment);
            mFragments.add(taskCenterFragment);
            mFragments.add(myTaskFragment);
            mFragments.add(infoFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.content, 0);
        tabMain.setCurrentTab(mCurrentPos);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ACTIVITY_FRAGMENT_REPLACE, mCurrentPos);
    }

    private OnTabSelectListener mTabSelectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelect(int position) {
            mCurrentPos = position;
            FragmentUtils.hideAllShowFragment(mFragments.get(mCurrentPos));
            String title = mTitles.get(mCurrentPos);
            if (TextUtils.isEmpty(title)) {
                toolbar.setVisibility(View.GONE);
            } else {
                toolbar.setVisibility(View.VISIBLE);
                toolbarTitle.setText(title);
            }
//            if (tabMain.getMsgView(position) != null) {
//                tabMain.hideMsg(position);
//            }
            if (hasTask && position != 1) {
                tabMain.showDot(1);
            } else if (position == 1) {
                tabMain.hideMsg(1);
            }

            if (hasTakenTasks && position != 2) {
                tabMain.showDot(2);
            } else if (position == 2) {
                tabMain.hideMsg(2);
            }
        }

        @Override
        public void onTabReselect(int position) {

        }
    };

    @Override
    public void onBackPressed() {
        if (mCurrentPos != 0) {
            mCurrentPos = 0;
            tabMain.setCurrentTab(mCurrentPos);
            mTabSelectListener.onTabSelect(mCurrentPos);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showMyTaskDot(TakeTask task) {
        int taskId = task.getTaskId();
        int sdTaskId = task.getSdTaskId();
        int daysTaskId = task.getDaysTaskId();
        boolean isTake = task.isTake();
        if (taskId > 0) {
            tabMain.showDot(2);
        }
        if (sdTaskId > 0 || daysTaskId > 0) {
            tabMain.showDot(2);
        }
        hasTakenTasks = taskId > 0 || sdTaskId > 0 || daysTaskId > 0;
//        if (isTake) {
//            launchActivity();
//        }
    }

    @Override
    public void showTaskCountDot(TaskCount taskCount) {
        int count = taskCount.getHdCount() +
                taskCount.getDaysDfCount() +
                taskCount.getDfCount() +
                taskCount.getOrderCount() +
                taskCount.getZtcCount();
        if (count > 0) {
            hasTask = true;
            tabMain.showDot(1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragments = null;
        mTabTitles = null;
        mTitles = null;
        mIconSelectedIds = null;
        mIconUnselectedIds = null;
    }
}