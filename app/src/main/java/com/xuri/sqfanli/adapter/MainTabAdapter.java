package com.xuri.sqfanli.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xuri.sqfanli.ui.base.BaseFragment;

import java.util.List;

/**
 * Created by AFeng on 2018/5/3.
 * 主页底部Tab
 */

public class MainTabAdapter extends FragmentPagerAdapter {
    List<BaseFragment> viewList;
    String[] _mTitles;

    public MainTabAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainTabAdapter(FragmentManager fm, List<BaseFragment> views, String[] mTitles) {
        super(fm);
        this.viewList = views;
        this._mTitles = mTitles;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return _mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return viewList.get(position);
    }
}

