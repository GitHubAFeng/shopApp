package com.xuri.sqfanli.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;

import com.flyco.tablayout.SlidingTabLayout;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.HomeViewPagerAdapter;
import com.xuri.sqfanli.api.HomeApi;
import com.xuri.sqfanli.api.UserApi;
import com.xuri.sqfanli.api.base.CallBackApi;
import com.xuri.sqfanli.api.base.CallBackListApi;
import com.xuri.sqfanli.bean.ShopType;
import com.xuri.sqfanli.event.MessageEvent;
import com.xuri.sqfanli.ui.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AFeng on 2018/5/8.
 * 首页版本2
 */

public class HomeV2Fragment extends BaseFragment {

    HomeViewPagerAdapter adapterHomeViewPager;
    int left = 0;  //tab的x坐标
    int top = 0;   //tab的y坐标
    HomeApi homeApi = new HomeApi();
    UserApi userApi = new UserApi();

    List<Fragment> mViewPagerFragments = new ArrayList<Fragment>(); //滑动页面列表
    List<String> mViewPagerTitles = new ArrayList<String>();//滑动页面标题（分类）

    @ViewInject(R.id.main_viewPager)
    ViewPager mMainViewPager;
    @ViewInject(R.id.main_tab)
    SlidingTabLayout mMainTabLayout;
//    @ViewInject(R.id.toolbar)
//    private Toolbar toolbar;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home_v2;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        initViewPager();
        Log.d(TAG, "initView: ");
    }

    //滑动页面
    void initViewPager() {
        left = mMainTabLayout.getLeft();
        top = mMainTabLayout.getTop();

        adapterHomeViewPager = new HomeViewPagerAdapter(getFragmentManager(), mViewPagerFragments, mViewPagerTitles);
        mMainViewPager.setAdapter(adapterHomeViewPager);
        mMainTabLayout.setViewPager(mMainViewPager);
        mMainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 0) {
                    mMainTabLayout.setVisibility(View.VISIBLE);
                } else {
                    mMainTabLayout.setVisibility(View.GONE);
                }
                mMainTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mMainViewPager.setCurrentItem(0);

        getVpData();

    }

    void getVpData() {
        List datalist = homeApi.getTypeFromServer(1, new CallBackListApi() {
            @Override
            public void onSuccess(List o) {
                setVpData(o);
            }

            @Override
            public void onFinished() {

            }
        });

        setVpData(datalist);

    }

    void setVpData(List o) {
        if (o == null || o.size() == 0) return;
        ArrayList<ShopType> data = (ArrayList<ShopType>) o;

        mViewPagerTitles.clear();
        mViewPagerFragments.clear();
        mViewPagerTitles.add("首页");
        mViewPagerFragments.add(new HomeGoodsListV2Fragment());
        for (int i = 0; i < data.size(); i++) {
            mViewPagerTitles.add(data.get(i).getName());
            mViewPagerFragments.add(GoodsListFragment.newInstance(data.get(i).getId()));
        }
        adapterHomeViewPager.notifyDataSetChanged();
        mMainTabLayout.setViewPager(mMainViewPager);
    }

    void showTabAnim() {
        TranslateAnimation translate = new TranslateAnimation(left, left, top + 50, top);
        translate.setDuration(1000);
        mMainTabLayout.startAnimation(translate);
    }


    void appUpdateUser() {
        userApi.updateUserByUserName("", new CallBackApi() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFinished() {

            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    //动画事件侦听
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getMessage().equals("VISIBLE")) {
            showTabAnim();
            mMainTabLayout.setVisibility(View.VISIBLE);
        } else {
            mMainTabLayout.setVisibility(View.GONE);
        }
    }


}
