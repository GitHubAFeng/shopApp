package com.xuri.sqfanli.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.CategoriesChildListAdpater;
import com.xuri.sqfanli.adapter.CategoriesParentListAdpater;
import com.xuri.sqfanli.bean.Categories;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.bean.ShopTypeImg;
import com.xuri.sqfanli.bean.SumShopType;
import com.xuri.sqfanli.bean.User;
import com.xuri.sqfanli.ui.activity.A_fenlei_parent;
import com.xuri.sqfanli.ui.activity.A_sousuo_shouye;
import com.xuri.sqfanli.ui.activity.A_webView_x5;
import com.xuri.sqfanli.ui.activity.GoodsDetailActivity;
import com.xuri.sqfanli.ui.base.BaseFragment;
import com.xuri.sqfanli.util.DensityUtil;
import com.xuri.sqfanli.util.SettingConfig;
import com.xuri.sqfanli.util.TaoBaoTools;
import com.xuri.sqfanli.view.LoadingDialog_logo_1;
import com.xuri.sqfanli.view.xImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索分类页面
 */
public class SearchCategoriesFragment extends BaseFragment {

    private LinearLayout mSearchLayout;
    private Banner mBanner;
    private NestedScrollView mScrollView;
    private RecyclerView mCategoriesParentRv;
    private RecyclerView mCategoriesChildRv;
    private List<Categories> mParentDataList = new ArrayList<Categories>();
    private List<Object> mBannerImagesList = new ArrayList<Object>();
    private CategoriesParentListAdpater mParentListAdpater;
    private CategoriesChildListAdpater mChildListAdpater;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_search_categories;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        findView();

        mCategoriesParentRv.setLayoutManager(new LinearLayoutManager(context));
        mParentListAdpater = new CategoriesParentListAdpater(context, mParentDataList);
        mCategoriesParentRv.setAdapter(mParentListAdpater);

        mCategoriesChildRv.setLayoutManager(new LinearLayoutManager(context));

        mParentListAdpater.setOnItemClickListener(new CategoriesParentListAdpater.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, ImageView selectedIv, TextView titleTv, int position) {
                mParentListAdpater.setItemSelected(position);
                showBanner(position);
                mChildListAdpater = new CategoriesChildListAdpater(context, mParentDataList.get(position).getRecommendList());
                mCategoriesChildRv.setAdapter(mChildListAdpater);
                mCategoriesParentRv.smoothScrollToPosition(position);
                mScrollView.smoothScrollTo(0, 0);
            }
        });

        mSearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, A_sousuo_shouye.class));
            }
        });

        showData();
        getDataForServer();
    }

    //获取分类列表数据
    private void getDataForServer() {
        String url = Constant.host + "shopType/appSumShopType";
        RequestParams params = new RequestParams(url);
        params.addParameter("shopType.sex", User.getInstance(context).getSex());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("SumShopTpyeResponse", result);
                SettingConfig.getInstance(context).setStringPreference("shopType/appSumShopType", result);
                showData();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("SumShopTypeError", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    public void showData() {
        String result = SettingConfig.getInstance(context).getStringPreference("shopType/appSumShopType", "");
        if (!TextUtils.isEmpty(result)) {
            mParentDataList.clear();
            Gson gson = new Gson();
            SumShopType sumShopType = gson.fromJson(result, SumShopType.class);
            if (sumShopType.getObject() != null) {
                mParentDataList.addAll(sumShopType.getObject());
                mParentListAdpater.notifyDataSetChanged();
                showBanner(0);
                mChildListAdpater = new CategoriesChildListAdpater(context, mParentDataList.get(0).getRecommendList());
                mCategoriesChildRv.setAdapter(mChildListAdpater);
            }
        }
    }

    public void showBanner(int position) {
        mBannerImagesList.clear();
        final List<ShopTypeImg> shopTypeImgList = mParentDataList.get(position).getShopTypeImg();
        if (shopTypeImgList != null && shopTypeImgList.size() != 0) {
            mBanner.setVisibility(View.VISIBLE);
            for (int i = 0; i < shopTypeImgList.size(); i++) {
                mBannerImagesList.add(shopTypeImgList.get(i).getImg());
            }
            mBanner.setPadding(0, DensityUtil.dip2px(context, 10), 0, 0);
            mBanner.setImageLoader(new xImageLoader());//设置图片加载器
            mBanner.setImages(mBannerImagesList);//设置图片集合
            mBanner.setIndicatorGravity(BannerConfig.RIGHT);//设置指示器位置
            mBanner.setDelayTime(5000);//设置轮播时间
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    String s = shopTypeImgList.get(position).getImgType();
                    if (s.equals("1")) {
                        Intent intent1 = new Intent(context, A_fenlei_parent.class);
                        intent1.putExtra("fenlei", shopTypeImgList.get(position).getTitle());
                        intent1.putExtra("fenleiId", String.valueOf(shopTypeImgList.get(position).getId()));
                        startActivity(intent1);

                    } else if (s.equals("2")) {
                        LoadingDialog_logo_1.show(context);
                        TaoBaoTools.getIns(context).showPage(getActivity(), shopTypeImgList.get(position).getImgUrl());

                    } else if (s.equals("3")) {
                        Intent intent3 = new Intent(context, A_webView_x5.class);
                        intent3.putExtra("url", shopTypeImgList.get(position).getImgUrl());
                        intent3.putExtra("title", "");
                        startActivity(intent3);

                    } else if (s.equals("4")) {
                        Intent intent4 = new Intent(context, GoodsDetailActivity.class);
                        Shop shop = new Shop();
                        shop.setItemid(shopTypeImgList.get(position).getItemId());
                        String jsonText = new Gson().toJson(shop);
                        intent4.putExtra("jsonText", jsonText);
                        intent4.putExtra("fromTag", 5);
                        startActivity(intent4);

                    }
                }
            });
            mBanner.start();//启动轮播
        } else {
            mBanner.setVisibility(View.GONE);
        }
    }

    public void findView() {
        mSearchLayout = view.findViewById(R.id.search_layout);
        mScrollView = view.findViewById(R.id.scroll_view);
        mBanner = view.findViewById(R.id.common_banner);
        mCategoriesParentRv = view.findViewById(R.id.categories_parent_rv);
        mCategoriesChildRv = view.findViewById(R.id.categories_child_rv);
    }
}
