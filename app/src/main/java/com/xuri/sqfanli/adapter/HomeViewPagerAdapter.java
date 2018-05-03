package com.xuri.sqfanli.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by AFeng on 2018/4/27.
 * 首页商品滑动页面
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mAdapterFragments;
    private List<String> mAdapterTitles;

    public HomeViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.mAdapterFragments = fragments;
        this.mAdapterTitles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return mAdapterFragments.get(i);
    }

    @Override
    public int getCount() {
        return mAdapterFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mAdapterTitles.get(position);
    }
}
