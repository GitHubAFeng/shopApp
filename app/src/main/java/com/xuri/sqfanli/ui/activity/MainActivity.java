package com.xuri.sqfanli.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.ui.base.BaseFragment;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;
import com.xuri.sqfanli.ui.fragment.Fragment2;
import com.xuri.sqfanli.ui.fragment.Fragment3;
import com.xuri.sqfanli.ui.fragment.HomeFragment;
import com.xuri.sqfanli.ui.fragment.MyFragment;
import com.xuri.sqfanli.view.ViewPagerTabHost;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity {
    @ViewInject(R.id.vp_vpager)
    private ViewPagerTabHost viewPager;
    @ViewInject(R.id.layout_tab1)
    private LinearLayout layout_tab1;
    @ViewInject(R.id.layout_tab2)
    private LinearLayout layout_tab2;
    @ViewInject(R.id.layout_tab3)
    private LinearLayout layout_tab3;
    @ViewInject(R.id.layout_tab4)
    private LinearLayout layout_tab4;
    @ViewInject(R.id.tv_shouye)
    private TextView tv_shouye;
    @ViewInject(R.id.tv_huaqian)
    private TextView tv_huaqian;
    @ViewInject(R.id.tv_fenlei)
    private TextView tv_fenlei;
    @ViewInject(R.id.tv_my)
    private TextView tv_my;

    private List<BaseFragment> views;
    private LinearLayout[] layout_tabs;
    private TextView[] tv_titles;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }


    @Override
    public void initView() {
        initBar();

        layout_tabs = new LinearLayout[]{layout_tab1, layout_tab2, layout_tab3, layout_tab4};
        tv_titles = new TextView[]{tv_shouye, tv_huaqian, tv_fenlei, tv_my};

        views = new ArrayList<BaseFragment>();
        views.add(new HomeFragment());
        views.add(new Fragment2());
        views.add(new Fragment3());
        views.add(new MyFragment());
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(), views);
        viewPager.setData_TabsLayout(layout_tabs);
        viewPager.setData_titleViews(tv_titles);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        layout_tab1.setActivated(true);
    }

    void initBar() {
        //自定义状态栏颜色
//        StatusBarUtil.setTranslucent(this); //透明
//        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.white)); //白色
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<BaseFragment> mListViews;

        public MyViewPagerAdapter(FragmentManager fm, List<BaseFragment> list) {
            super(fm);
            this.mListViews = list;
        }

        @Override
        public Fragment getItem(int position) {
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int arg0) {

        }
    }
}