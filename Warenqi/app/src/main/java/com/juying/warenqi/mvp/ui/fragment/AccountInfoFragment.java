package com.juying.warenqi.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.juying.warenqi.R;
import com.juying.warenqi.app.Constants;
import com.juying.warenqi.di.component.DaggerAccountInfoComponent;
import com.juying.warenqi.di.module.AccountInfoModule;
import com.juying.warenqi.mvp.contract.AccountInfoContract;
import com.juying.warenqi.mvp.model.entity.AccountInfo;
import com.juying.warenqi.mvp.model.entity.AccountInfoSection;
import com.juying.warenqi.mvp.presenter.AccountInfoPresenter;
import com.juying.warenqi.mvp.ui.adapter.AccountInfoAdapter;
import com.weavey.loading.lib.LoadingLayout;

import java.math.BigDecimal;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class AccountInfoFragment extends BaseFragment<AccountInfoPresenter> implements AccountInfoContract.View {


    @BindView(R.id.rv_account_info)
    RecyclerView rvAccountInfo;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    ImageView ivBlurredAvatar;
    TextView tvAccountInfo;
    TextView tvGold;
    TextView tvDeposit;
    private ImageView btnService;
    private ImageView btnSettings;

    public static AccountInfoFragment newInstance() {
        AccountInfoFragment fragment = new AccountInfoFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerAccountInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .accountInfoModule(new AccountInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_info, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.getMoneyInfo();
        rvAccountInfo.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                Drawable drawable = getActivity().getResources().getDrawable(R.drawable.shape_rv_divider);
                drawHorizontal(drawable, c, parent);
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

            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                AccountInfoAdapter parentAdapter = (AccountInfoAdapter) parent.getAdapter();
                int itemViewType = parentAdapter.getItemViewType(itemPosition);
                switch (itemViewType) {
                    case Constants.SECTION_HEADER:
                        outRect.set(0, 0, 0, 0);
                        break;
                    case Constants.SECTION_CONTENT:
                        int headerLayoutCount = parentAdapter.getHeaderLayoutCount();
                        if (itemPosition - 1 - headerLayoutCount >= 0
                                && parentAdapter.getItem(itemPosition - 1 - headerLayoutCount).isHeader) {
                            outRect.set(SizeUtils.dp2px(15), 0, 0, 0);
                        } else if (itemPosition + 1 - headerLayoutCount <= parentAdapter.getItemCount()
                                && parentAdapter.getItem(itemPosition + 1 - headerLayoutCount) != null
                                && parentAdapter.getItem(itemPosition + 1 - headerLayoutCount).isHeader) {
                            outRect.set(SizeUtils.dp2px(15), 0, 0, 0);
                        } else if (itemPosition == parentAdapter.getItemCount() - headerLayoutCount) {
                            outRect.set(SizeUtils.dp2px(15), 0, 0, 0);
                        } else {
                            outRect.set(SizeUtils.dp2px(15), 1, 0, 1);
                        }
                        break;
                }
            }
        });
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
        loadingLayout.setStatus(LoadingLayout.Loading);
    }

    @Override
    public void hideLoading() {
        loadingLayout.setStatus(LoadingLayout.Success);
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
        rvAccountInfo.setAdapter(adapter);
        rvAccountInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            AccountInfoSection item = (AccountInfoSection) adapter1.getItem(position);
            if (item != null && !item.isHeader) {
                ToastUtils.showShort(item.t.getTitle());
            }
        });

        View header = View.inflate(getContext(), R.layout.item_account_info_header, null);
        tvGold = ((TextView) header.findViewById(R.id.tv_gold));
        tvDeposit = (TextView) header.findViewById(R.id.tv_deposit);
        ivBlurredAvatar = (ImageView) header.findViewById(R.id.iv_blurred_avatar);
        tvAccountInfo = (TextView) header.findViewById(R.id.tv_account_info);
        btnService = ((ImageView) header.findViewById(R.id.btn_service));
        btnSettings = ((ImageView) header.findViewById(R.id.btn_settings));
        tvGold.setOnClickListener(mOnClickListener);
        tvDeposit.setOnClickListener(mOnClickListener);
        btnService.setOnClickListener(mOnClickListener);
        btnSettings.setOnClickListener(mOnClickListener);
        adapter.addHeaderView(header);
    }

    @Override
    public void setMoneyInfo(AccountInfo info) {
        long ingot = info.getIngot();
        String goldStr = BigDecimal.valueOf(ingot).movePointLeft(2).toString();
        long deposit = info.getDeposit();
        String depositStr = BigDecimal.valueOf(deposit).movePointLeft(2).toString();
        tvGold.setText(new SpanUtils()
                .appendImage(R.drawable.jinbi)
                .appendLine()
                .append("金币：").setFontSize(15, true)
                .append(goldStr)
                .setForegroundColor(getResources().getColor(R.color.red_fb607f))
                .create());

        tvDeposit.setText(new SpanUtils()
                .appendImage(R.drawable.personal_benjin)
                .appendLine()
                .append("本金：").setFontSize(15, true)
                .append(depositStr)
                .setForegroundColor(getResources().getColor(R.color.red_fb607f))
                .create());
    }

    @Override
    public void showError() {
        loadingLayout.setStatus(LoadingLayout.Error);
    }

    private ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            ToastUtils.showShort("事件触发了");
        }

        @Override
        public void updateDrawState(TextPaint ds) {

        }
    };

    private View.OnClickListener mOnClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_service:
                break;
            case R.id.btn_settings:
                break;
            case R.id.tv_gold:
                break;
            case R.id.tv_deposit:
                break;
        }
    };

}