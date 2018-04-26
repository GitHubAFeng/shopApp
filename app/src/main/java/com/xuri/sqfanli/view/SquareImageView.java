package com.xuri.sqfanli.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @Title: 正方形的imageview
 * @Description:
 * @author 何明洋
 */

@SuppressLint("AppCompatCustomView")
public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //传入参数widthMeasureSpec、heightMeasureSpec
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
