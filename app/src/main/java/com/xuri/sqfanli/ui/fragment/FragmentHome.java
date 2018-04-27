package com.xuri.sqfanli.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.AdapterHomeViewPager;
import com.xuri.sqfanli.api.HomeApi;
import com.xuri.sqfanli.api.UserApi;
import com.xuri.sqfanli.api.base.CallBackApi;
import com.xuri.sqfanli.bean.ShopType;
import com.xuri.sqfanli.ui.base.BaseFragment;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AFeng on 2018/4/26.
 * 首页
 */

public class FragmentHome extends BaseFragment {

    AdapterHomeViewPager adapterHomeViewPager;
    int userSex = 1; //男1  女2

    HomeApi homeApi = new HomeApi();
    UserApi userApi = new UserApi();

    List<Fragment> mViewPagerFragments = new ArrayList<>(); //滑动页面列表
    List<String> mViewPagerTitles = new ArrayList<>();//滑动页面标题（分类）

    @ViewInject(R.id.main_viewPager)
    ViewPager mMainViewPager;
    @ViewInject(R.id.main_tab)
    SlidingTabLayout mMainTabLayout;


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

        initViewPager();
        appUpdateUser();

    }

    //滑动页面
    void initViewPager() {

        adapterHomeViewPager = new AdapterHomeViewPager(getFragmentManager(), mViewPagerFragments, mViewPagerTitles);
        mMainViewPager.setAdapter(adapterHomeViewPager);
        mMainTabLayout.setViewPager(mMainViewPager);
        mMainTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {


            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mMainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mMainTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mMainViewPager.setCurrentItem(0);

        showViewPager();

    }

    void showViewPager() {

        String text = homeApi.getGoodsTypeFromServer(userSex, new CallBackApi() {
            @Override
            public void onSuccess(String result) {
                List<ShopType> data = new Gson().fromJson(result, new TypeToken<List<ShopType>>() {
                }.getType());
                mViewPagerTitles.clear();
                mViewPagerFragments.clear();
                for (int i = 0; i < data.size(); i++) {
                    mViewPagerTitles.add(data.get(i).getName());
                    if (i == 0) {
                        mViewPagerFragments.add(new FragmentHomeGoodsList());
                    } else {
                        mViewPagerFragments.add(new Fragment2());
                    }
                }
                adapterHomeViewPager.notifyDataSetChanged();
                mMainTabLayout.setViewPager(mMainViewPager);
            }

            @Override
            public void onFinished() {

            }
        });


        //先显示本地
        if (text != "" || text != null) {
            List<ShopType> data = new Gson().fromJson(text, new TypeToken<List<ShopType>>() {
            }.getType());
            mViewPagerTitles.clear();
            mViewPagerFragments.clear();
            for (int i = 0; i < data.size(); i++) {
                mViewPagerTitles.add(data.get(i).getName());
                if (i == 0) {
                    mViewPagerFragments.add(new FragmentHomeGoodsList());
                } else {
                    mViewPagerFragments.add(new Fragment2());
                }
            }
            adapterHomeViewPager.notifyDataSetChanged();
            mMainTabLayout.setViewPager(mMainViewPager);
        }

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

}
