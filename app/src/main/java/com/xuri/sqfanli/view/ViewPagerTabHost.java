package com.xuri.sqfanli.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @Title: 集成viewpager和tabhost的一个控件（不明白的话建议不使用）
 * @Description:
 * @author 何明洋
 */
public class ViewPagerTabHost extends ViewPager {
    LinearLayout[] layout_tabs;
    TextView[] titleViews;
    ViewPagerTabHost pager = this;
    OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < layout_tabs.length; i++) {
                if (i == position) {
                    layout_tabs[i].setActivated(true);
                    titleViews[i].setTextColor(Color.parseColor("#f85725"));

                } else {
                    layout_tabs[i].setActivated(false);
                    titleViews[i].setTextColor(Color.parseColor("#333333"));
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public ViewPagerTabHost(Context context) {
        super(context);
        //重写addOnPageChangeListener
        addOnPageChangeListener(onPageChangeListener);
    }

    //从xml引用，会调用方法
    public ViewPagerTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        //重写addOnPageChangeListener
        addOnPageChangeListener(onPageChangeListener);

    }

    public void setData_TabsLayout(LinearLayout[] tabs) {
        this.layout_tabs = tabs;
        for (int i = 0; i < this.layout_tabs.length; i++) {
            final int finalI = i;
            this.layout_tabs[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(finalI);
                }
            });
        }
    }

    public void setData_titleViews(TextView[] tvs) {
        titleViews = tvs;
    }


    /**
     * 禁止滑动
     *
     * @param arg0
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
        /* return false;//super.onTouchEvent(arg0); */
//        if (noScroll)
//            return false;
//        else
//            return super.onTouchEvent(arg0);
    }

    /**
     * 禁止滑动
     *
     * @param arg0
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
//        if (noScroll)
//            return false;
//        else
//            return super.onInterceptTouchEvent(arg0);
    }

    //去除页面切换时的滑动翻页效果
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

}
