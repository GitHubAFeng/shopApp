package com.xuri.sqfanli.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.Adapter_huaqianfenlei;
import com.xuri.sqfanli.bean.User;
import com.xuri.sqfanli.event.Callback_string;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;
import com.xuri.sqfanli.ui.fragment.JiukuaijiuBaoyouFragment;
import com.xuri.sqfanli.util.SettingConfig;
import com.xuri.sqfanli.view.Hm_StripViewPager_slidable_huaqianfenlei;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class JiukuaijiuBaoyouActivity extends BaseFragmentActivity {

    private RelativeLayout mTitleBarLayout;
    private Hm_StripViewPager_slidable_huaqianfenlei mTabLayout;
    private Hm_StripViewPager_slidable_huaqianfenlei.ViewPager_ mTabPager;
    private String[] mTabIdStr;
    private TabPagerAdapter mTabPagerAdapter;
    private Adapter_huaqianfenlei mDataTabAdapter;
    private JSONArray mTabJsonArray;
    private String mTabIdText = "";
    private String mFenleiID;
    private String mFenlei;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_jiukuaijiu_baoyou;
    }

    @Override
    public void initView() {
        findView();
        mFenlei = getIntent().getStringExtra("fenlei");
        mFenleiID = getIntent().getStringExtra("fenleiId");

        mTabLayout.setTabTitle(new String[] {""});
        mTabLayout.setTabBackgroundColor(getResources().getColor(R.color.white));// default: #f1f1f1
        mTabLayout.setTabTitleColor(getResources().getColor(R.color._33));// default: #333333
        mTabLayout.setTabTitleSelectedColor(getResources().getColor(R.color.tab_title_select_color));
        mTabLayout.setNoScroll(false);// default: false
        mTabLayout.callback_iv_all = new Callback_string() {
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
                mDataTabAdapter = new Adapter_huaqianfenlei(context);
                mDataTabAdapter.setData(jsonarrayToList(mTabJsonArray));
                mDataTabAdapter.setData(new Callback_string() {
                    @Override
                    public void call(String text) {
                        mTabPager.setCurrentItem(Integer.valueOf(text));
                        pop.dismiss();
                    }
                });

                rv.setAdapter(mDataTabAdapter);

            }
        };

        mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        mTabPager = mTabLayout.getViewPager();
        mTabPager.setAdapter(mTabPagerAdapter);

        getDataFromServer();
    }

    public void findView() {
        mTitleBarLayout = findViewById(R.id.title_bar_layout);
        mTabLayout = findViewById(R.id.tab_layout);
    }

    public void getDataFromServer() {
        String url = Constant.host + "shopType/appShopType";
        RequestParams params = new RequestParams(url);
        params.addParameter("shopType.sex", User.getInstance(context).getSex());
        params.addParameter("shopType.classify", mFenleiID);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("BaoyouTabResponse", result);
                SettingConfig.getInstance(context).setStringPreference("shopType/appShopType_" + mFenlei, result);
                showLocalData();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("BaoyouTabError", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {}

            @Override
            public void onFinished() {}
        });
    }

    //加载已经从网络上面获取并保存到本地的数据
    public void showLocalData() {
        String text = SettingConfig.getInstance(context).getStringPreference("shopType/appShopType_" + mFenlei, "");
        try {
            mTabJsonArray = new JSONArray(text);
            if (mDataTabAdapter != null) {
                mDataTabAdapter.setData(jsonarrayToList(mTabJsonArray));
                mDataTabAdapter.notifyDataSetChanged();
            }
            text = "";
            for (int i = 0; i < mTabJsonArray.length(); i++) {
                text = text + mTabJsonArray.getJSONObject(i).getString("name") + "aaabbb";
                mTabIdText = mTabIdText + mTabJsonArray.getJSONObject(i).getString("id") + "aaabbb";
            }
            String ts[] = text.split("aaabbb");
            mTabIdStr = mTabIdText.split("aaabbb");
            mTabLayout.setTabTitle(ts);
            mTabLayout.initView(mTabLayout.width, mTabLayout.height);
            mTabPagerAdapter.notifyDataSetChanged();;

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

    //前往搜索页面
    public void gotoSearch(View view) {
        startActivity(new Intent(this, A_sousuo_shouye.class));
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
            return JiukuaijiuBaoyouFragment.newInstance(mTabIdStr[position], mFenleiID, mFenleiID);
        }

        @Override
        public int getCount() {
            return mTabIdStr == null ? 0 : mTabIdStr.length;
        }

    }

}
