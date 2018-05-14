package com.xuri.sqfanli.util;

import android.view.View;
import android.widget.LinearLayout;

import com.xuri.sqfanli.R;

/**
 * Created by AFeng on 2018/5/14.
 */

public class CommonMethod {

    /**
     * 用于量取header中，综合、价格、销量，筛选栏距离顶部的Y轴距离
     * @param headerView 顶部布局
     * @return 距离
     */
    public static int getDistanceY(View headerView) {
        LinearLayout shaixuanLayout = headerView.findViewById(R.id.shaixuan_root_layout);
        int[] location = new int[2];
        shaixuanLayout.getLocationOnScreen(location);
        int y = location[1];
        return y;
    }



}
