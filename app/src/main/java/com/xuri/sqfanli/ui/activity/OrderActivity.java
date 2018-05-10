package com.xuri.sqfanli.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.HomeViewPagerAdapter;
import com.xuri.sqfanli.adapter.OrderViewPagerAdapter;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;
import com.xuri.sqfanli.ui.fragment.OrderAllFragment;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AFeng on 2018/5/10.
 */

public class OrderActivity extends BaseFragmentActivity {

    OrderViewPagerAdapter orderViewPagerAdapter;
    List<Fragment> mViewPagerFragments = new ArrayList<Fragment>(); //滑动页面列表
    List<String> mViewPagerTitles = new ArrayList<String>();//滑动页面标题（分类）

    int left = 0;  //tab的x坐标
    int top = 0;   //tab的y坐标

    @ViewInject(R.id.order_vp)
    ViewPager order_vp;
    @ViewInject(R.id.order_tab)
    SlidingTabLayout order_tab;
    @ViewInject(R.id.ib_back)
    ImageView ib_back;
    @ViewInject(R.id.ib_close)
    ImageView ib_close;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_order;
    }

    @Override
    public void initView() {

        initListener();
        initViewPager();


    }

    void initListener() {
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //滑动页面
    void initViewPager() {

        mViewPagerTitles.add("全部");
        mViewPagerTitles.add("待付款");
        mViewPagerTitles.add("待发货");
        mViewPagerTitles.add("待收货");
        mViewPagerTitles.add("待评价");

        mViewPagerFragments.add(new OrderAllFragment());
        mViewPagerFragments.add(new OrderAllFragment());
        mViewPagerFragments.add(new OrderAllFragment());
        mViewPagerFragments.add(new OrderAllFragment());
        mViewPagerFragments.add(new OrderAllFragment());


        left = order_tab.getLeft();
        top = order_tab.getTop();

        orderViewPagerAdapter = new OrderViewPagerAdapter(getSupportFragmentManager(), mViewPagerFragments, mViewPagerTitles);
        order_vp.setAdapter(orderViewPagerAdapter);
        order_tab.setViewPager(order_vp);

        order_vp.setCurrentItem(0);


    }


}
