package com.xuri.sqfanli.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.HotSalesListAdapter;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.bean.User;
import com.xuri.sqfanli.event.Callback_string;
import com.xuri.sqfanli.ui.activity.GoodsDetailActivity;
import com.xuri.sqfanli.ui.base.BaseFragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 热销排行榜内部滑动的fragment
 */
public class HotSalesFragment extends BaseFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mHotSalesListRv;
    private ImageView mGoBackTopIv;
    private LinearLayout mLoadMoreLayout;
    private String mTabTitleID;
    private HotSalesListAdapter mHotSalesListAdapter;
    private ArrayList<Shop> mShopList = new ArrayList<Shop>();
    private Gson mGson = new Gson();
    private boolean isLoading = false;
    private int scrollY = 0;
    private int mCurrentPageNum = 1;

    public static HotSalesFragment newInstance(String tabTitleID) {
        Bundle args = new Bundle();
        args.putString("tabTitleID",tabTitleID);
        HotSalesFragment hotSalesFragment = new HotSalesFragment();
        hotSalesFragment.setArguments(args);
        return hotSalesFragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_hot_sales;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView(Bundle savedInstanceState) {
        findView();

        mTabTitleID = getArguments().getString("tabTitleID");

        Callback_string callback = new Callback_string() {
            @Override
            public void call(String text) {
                if(!isLoading) {
                    isLoading = true;
                    mLoadMoreLayout.setVisibility(View.VISIBLE);
                    mCurrentPageNum = mCurrentPageNum + 1;
                    getHotSalesList(String.valueOf(mCurrentPageNum));
                }
            }
        };

        mLoadMoreLayout.setVisibility(View.GONE);
        mHotSalesListAdapter = new HotSalesListAdapter(getActivity(), getContext(), mShopList);
        mHotSalesListAdapter.setCallback(callback);
        mHotSalesListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mHotSalesListRv.setAdapter(mHotSalesListAdapter);
        mHotSalesListAdapter.setOnItemClickListener(new HotSalesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mShopList != null && mShopList.size() > 0) {
                    Intent intent = new Intent();
                    intent.setClass(context, GoodsDetailActivity.class);
                    String jsonString = mGson.toJson(mShopList.get(position));
                    intent.putExtra("jsonText", jsonString);
                    intent.putExtra("fromTag", "3");
                    startActivity(intent);
                }
            }
        });

        //下拉刷新
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.refresh_layout_color));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mShopList.clear();
                getHotSalesList("1");
            }
        });

        mHotSalesListRv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mRefreshLayout.isRefreshing();
            }
        });

        mHotSalesListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY = scrollY + dy;
                if (scrollY > 2500) {
                    mGoBackTopIv.setVisibility(View.VISIBLE);
                } else {
                    mGoBackTopIv.setVisibility(View.GONE);
                }
            }
        });

        mGoBackTopIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHotSalesListRv.smoothScrollToPosition(0);
            }
        });

        getHotSalesList("1");
    }

    public void findView() {
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mHotSalesListRv = view.findViewById(R.id.hot_sales_list_rv);
        mGoBackTopIv = view.findViewById(R.id.go_back_top_iv);
        mLoadMoreLayout = view.findViewById(R.id.load_more_layout);
    }

    //获取热销排行榜列表数据
    public void getHotSalesList(String pageNumber) {
        String url = Constant.host + "shop/appShop";
        RequestParams params = new RequestParams(url);
        params.addParameter("page.curPage", pageNumber);
        params.addParameter("shop.ctype", mTabTitleID);
        params.addParameter("shop.collectUserId", User.getInstance().getId());
        params.addParameter("shop.shopBang", "1");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("HotSaleSListResponer", result);
                ArrayList<Shop> shopList = mGson.fromJson(result, new TypeToken<ArrayList<Shop>>(){}.getType());
                mShopList.addAll(shopList);//动态更新list数据，否则刷新Adapter会没有效果
                mHotSalesListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("HotSalesListError", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {}

            @Override
            public void onFinished() {
                isLoading = false;
                mLoadMoreLayout.setVisibility(View.GONE);
                mRefreshLayout.setRefreshing(false);
            }
        });
    }
}
