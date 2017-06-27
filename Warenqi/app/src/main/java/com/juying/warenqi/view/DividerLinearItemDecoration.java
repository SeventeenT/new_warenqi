package com.juying.warenqi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.juying.warenqi.R;

/**
 * Created by Administrator on 2017/2/7.
 */

public class DividerLinearItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mPaddingBottom;

    public DividerLinearItemDecoration(Context context, int orientation) {
        this(context, orientation, 0, 0, 0, 0);
    }

    public DividerLinearItemDecoration(Context context, int orientation, int paddingLeft,
                                       int paddingRight, int paddingTop, int paddingBottom) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = ContextCompat.getDrawable(context, R.drawable.shape_rv_divider);
        mPaddingLeft = paddingLeft;
        mPaddingRight = paddingRight;
        mPaddingTop = paddingTop;
        mPaddingBottom = paddingBottom;
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + mPaddingLeft;
        final int right = parent.getWidth() - parent.getPaddingRight() + mPaddingRight;

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin + mPaddingTop;
            final int bottom = top + mDivider.getIntrinsicHeight() + mPaddingBottom;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop() + mPaddingTop;
        final int bottom = parent.getHeight() - parent.getPaddingBottom() + mPaddingBottom;

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin + mPaddingLeft;
            final int right = left + mDivider.getIntrinsicHeight() + mPaddingRight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
