package com.juying.warenqi.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.juying.warenqi.R;
import com.juying.warenqi.app.Constants;
import com.juying.warenqi.di.component.DaggerMyTaskComponent;
import com.juying.warenqi.di.module.MyTaskModule;
import com.juying.warenqi.mvp.contract.MyTaskContract;
import com.juying.warenqi.mvp.model.entity.MyTaskStateSection;
import com.juying.warenqi.mvp.presenter.MyTaskPresenter;
import com.juying.warenqi.mvp.ui.adapter.MyTaskStateAdapter;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyTaskFragment extends BaseFragment<MyTaskPresenter> implements MyTaskContract.View {


    @BindView(R.id.rv_my_task_state)
    RecyclerView rvMyTaskState;

    public static MyTaskFragment newInstance() {
        MyTaskFragment fragment = new MyTaskFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMyTaskComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myTaskModule(new MyTaskModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_task, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.getFlowTaskCount();
        mPresenter.getAdvancedPayTaskCount();
        mPresenter.getAskTaskCount();
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

    @Override
    public void setAdapter(BaseQuickAdapter adapter) {
        rvMyTaskState.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvMyTaskState.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            MyTaskStateSection item = (MyTaskStateSection) adapter1.getItem(position);
            if (item != null && !item.isHeader)
                ToastUtils.showShort(item.t.getTaskStatus());
        });

        rvMyTaskState.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                Drawable drawable = getActivity().getResources().getDrawable(R.drawable.shape_rv_divider);
                drawHorizontal(drawable, c, parent);
                drawVertical(drawable, c, parent);
            }

            public void drawHorizontal(Drawable drawable, Canvas c, RecyclerView parent) {
                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                            .getLayoutParams();
                    final int left = child.getLeft() - params.leftMargin;
                    final int right = child.getRight() + params.rightMargin
                            + drawable.getIntrinsicWidth();
                    final int top = child.getBottom() + params.bottomMargin;
                    final int bottom = top + drawable.getIntrinsicHeight();
                    drawable.setBounds(left, top, right, bottom);
                    drawable.draw(c);
                }
            }

            public void drawVertical(Drawable drawable, Canvas c, RecyclerView parent) {
                final int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);

                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                            .getLayoutParams();
                    final int top = child.getTop() - params.topMargin;
                    final int bottom = child.getBottom() + params.bottomMargin;
                    final int left = child.getRight() + params.rightMargin;
                    final int right = left + drawable.getIntrinsicWidth();

                    drawable.setBounds(left, top, right, bottom);
                    drawable.draw(c);
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                MyTaskStateAdapter parentAdapter = (MyTaskStateAdapter) parent.getAdapter();
                int itemViewType = parentAdapter.getItemViewType(itemPosition);
                switch (itemViewType) {
                    case Constants.SECTION_HEADER:
                        break;
                    case Constants.SECTION_CONTENT:
                        outRect.set(0, 1, 1, 0);
                        break;
                }
            }
        });
    }
}