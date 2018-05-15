package com.xuri.sqfanli.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.Adapter_huaqianfenlei;
import com.xuri.sqfanli.bean.User;
import com.xuri.sqfanli.event.Callback_string;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;
import com.xuri.sqfanli.ui.fragment.HotSalesFragment;
import com.xuri.sqfanli.util.SettingConfig;
import com.xuri.sqfanli.view.Hm_StripViewPager_slidable_huaqianfenlei;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 热销排行榜页面
 */
public class HotSalesActivity extends BaseFragmentActivity {

    private RelativeLayout mTitleBarLayout;
    private Hm_StripViewPager_slidable_huaqianfenlei mHotSalesTabLayout;
    private Hm_StripViewPager_slidable_huaqianfenlei.ViewPager_ mHotSalesPager;
    private String[] mTabIdStr;
    private TabPagerAdapter mTabPagerAdapter;
    private Adapter_huaqianfenlei mHotSalesTabAdapter;
    private JSONArray mTabJsonArray;
    private String mTabIdText;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_hot_sales;
    }

    @Override
    public void initView() {
        findView();

        mHotSalesTabLayout.setTabBackgroundColor(getResources().getColor(R.color.white));// default: #f1f1f1
        mHotSalesTabLayout.setTabTitleColor(getResources().getColor(R.color._33));// default: #333333
        mHotSalesTabLayout.setTabTitleSelectedColor(getResources().getColor(R.color.tab_title_select_color));
        mHotSalesTabLayout.setNoScroll(false);// default: false
        mHotSalesTabLayout.callback_iv_all = new Callback_string() {
            @Override
            public void call(String text) {
                //这里设置宽高
                final PopupWindow pop = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                View popview = LayoutInflater.from(context).inflate(R.layout.pop_huaqianfenlei, null, false);
                pop.setContentView(popview);
                pop.showAtLocation(mTitleBarLayout, Gravity.TOP, 0, 0);

                //关闭
                ImageView iv_close = popview.findViewById(R.id.iv_close);
                TextView tv_close = popview.findViewById(R.id.tv_close);
                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pop.dismiss();
                    }
                });
                tv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pop.dismiss();
                    }
                });

                RecyclerView rv = popview.findViewById(R.id.rv);
                rv.setLayoutManager(new GridLayoutManager(context, 4));
                mHotSalesTabAdapter = new Adapter_huaqianfenlei(context);
                mHotSalesTabAdapter.setData(jsonarrayToList(mTabJsonArray));
                mHotSalesTabAdapter.setData(new Callback_string() {
                    @Override
                    public void call(String text) {
                        mHotSalesPager.setCurrentItem(Integer.valueOf(text) + 1);
                        pop.dismiss();
                    }
                });

                rv.setAdapter(mHotSalesTabAdapter);

            }
        };

        mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        mHotSalesPager = mHotSalesTabLayout.getViewPager();
        mHotSalesPager.setAdapter(mTabPagerAdapter);

        showLocalData();
    }


    public void findView() {
        mTitleBarLayout = findViewById(R.id.title_bar_layout);
        mHotSalesTabLayout = findViewById(R.id.hot_sales_tab_layout);
    }

    //加载已经从网络上面获取并保存到本地的数据
    public void showLocalData() {
        String text = SettingConfig.getInstance(context).getStringPreference("shopType/appShopType", "");
        try {
            mTabJsonArray = new JSONArray(text);
            if (mHotSalesTabAdapter != null) {
                mHotSalesTabAdapter.setData(jsonarrayToList(mTabJsonArray));
                mHotSalesTabAdapter.notifyDataSetChanged();
            }
            text = "总榜aaabbb";
            if (("1").equals(User.getInstance().getSex())) {
                mTabIdText = "104aaabbb";//男生版总榜
            } else {
                mTabIdText = "105aaabbb";//女生版总榜
            }
            for (int i = 0; i < mTabJsonArray.length(); i++) {
                text = text + mTabJsonArray.getJSONObject(i).getString("name") + "aaabbb";
                mTabIdText = mTabIdText + mTabJsonArray.getJSONObject(i).getString("id") + "aaabbb";
            }
            String ts[] = text.split("aaabbb");
            mTabIdStr = mTabIdText.split("aaabbb");
            mHotSalesTabLayout.setTabTitle(ts);
            mHotSalesTabLayout.initView(mHotSalesTabLayout.width, mHotSalesTabLayout.height);
            mTabPagerAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //讲JSONArray转化成List
    public List<JSONObject> jsonarrayToList(JSONArray jsonArray) {
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonList.add(jsonArray.getJSONObject(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonList;
    }

    //返回键监听
    public void finish(View view) {
        finish();
    }

    class TabPagerAdapter extends FragmentPagerAdapter {

        TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return HotSalesFragment.newInstance(mTabIdStr[position]);
        }

        @Override
        public int getCount() {
            return mTabIdStr == null ? 0 : mTabIdStr.length;
        }

    }

}
