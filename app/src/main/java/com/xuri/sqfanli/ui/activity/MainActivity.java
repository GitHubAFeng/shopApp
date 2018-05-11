package com.xuri.sqfanli.ui.activity;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.MainTabAdapter;
import com.xuri.sqfanli.ui.base.BaseFragment;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;
import com.xuri.sqfanli.ui.fragment.Fragment2;
import com.xuri.sqfanli.ui.fragment.Fragment3;
import com.xuri.sqfanli.ui.fragment.HomeV2Fragment;
import com.xuri.sqfanli.ui.fragment.MyFragment;
import com.xuri.sqfanli.view.NoScrollViewPager;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity {

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<CustomTabEntity>();
    private List<BaseFragment> views = new ArrayList<BaseFragment>();
    private String[] mTitles = {"首页", "花钱", "分类", "我的"};
    private int[] mIconUnselectIds = {
            R.drawable.home_no_select,
            R.drawable.home_tab_huaqiang_no,
            R.drawable.home_tab_type_no,
            R.drawable.select_no_my
    };
    private int[] mIconSelectIds = {
            R.drawable.home_select,
            R.drawable.home_tab_huaqiang,
            R.drawable.home_tab_type,
            R.drawable.select_my
    };

    @ViewInject(R.id.vp_vpager)
    private NoScrollViewPager mViewPager;
    @ViewInject(R.id.main_home_tab)
    private CommonTabLayout commonTabLayout;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }


    @Override
    public void initView() {

        super.initAnim(AnimType.explode);
        initBar();
        initTab();


    }


    void initTab() {

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        views.add(new HomeV2Fragment());
        views.add(new Fragment2());
        views.add(new Fragment3());
        views.add(new MyFragment());
        mViewPager.setOffscreenPageLimit(4); //允许保留的页面，防止首页被重绘
        mViewPager.setAdapter(new MainTabAdapter(getSupportFragmentManager(), views, mTitles));
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    void initBar() {
        //自定义状态栏颜色
//        StatusBarUtil.setTranslucent(this); //透明
//        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.white)); //白色
    }


    class TabEntity implements CustomTabEntity {
        public String title;
        public int selectedIcon;
        public int unSelectedIcon;

        public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return selectedIcon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelectedIcon;
        }
    }


}