package com.xuri.sqfanli.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xuri.sqfanli.R;

/**
 * Created by Jay&Vi on 2018/4/9.
 * 可调整倾斜角度的TextView
 */

public class LeanTextView extends android.support.v7.widget.AppCompatTextView {

    private int mDegrees;//倾斜角度

    public void setmDegrees(int mDegrees) {
        this.mDegrees = mDegrees;
    }

    public int getmDegrees() {
        return mDegrees;
    }

    public LeanTextView(Context context) {
        super(context);
    }

    public LeanTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LeanTextView);
        mDegrees = a.getDimensionPixelSize(R.styleable.LeanTextView_degree, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制倾斜角度
        canvas.save();
        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
        canvas.rotate(mDegrees, this.getWidth() / 2f, this.getHeight() / 2f);
        super.onDraw(canvas);
        canvas.restore();
    }
}
