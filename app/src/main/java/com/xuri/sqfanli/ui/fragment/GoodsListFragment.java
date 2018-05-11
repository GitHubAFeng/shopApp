package com.xuri.sqfanli.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.GoodsListAdapter;
import com.xuri.sqfanli.adapter.GoodsTypeAdapter;
import com.xuri.sqfanli.api.HomeApi;
import com.xuri.sqfanli.api.base.CallBackApi;
import com.xuri.sqfanli.api.base.CallBackListApi;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.bean.ShopType;
import com.xuri.sqfanli.ui.activity.GoodsDetailActivity;
import com.xuri.sqfanli.ui.base.BaseFragment;
import com.xuri.sqfanli.view.PagerLayoutManager.PagerGridLayoutManager;
import com.xuri.sqfanli.view.PagerLayoutManager.PagerGridSnapHelper;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AFeng on 2018/4/26.
 * 首页
 */

public class GoodsListFragment extends BaseFragment {

    GoodsListAdapter home_adapter;

    boolean isLoading = false;
    int currentPage = 1; //当前页数
    List<Shop> shoplistDatas = new ArrayList<Shop>();   //下拉列表里面的商品数据
    List<ShopType> shopTypes = new ArrayList<ShopType>(); //按钮组数据

    GoodsTypeAdapter goodsTypeAdapter;
    int scrollY = 0; //用于向上按钮
    HomeApi homeApi = new HomeApi();

    Boolean is_show_tab = false;  //分类tab是否显示
    String goodsType; //商品分类

    @ViewInject(R.id.layout_refresh)
    SwipeRefreshLayout layout_refresh;
    @ViewInject(R.id.rv)
    private RecyclerView rv;
    @ViewInject(R.id.layout_loadMore)
    LinearLayout layout_loadMore;
    @ViewInject(R.id.iv_xiangshang)
    ImageView iv_xiangshang;
    @ViewInject(R.id.goods_type_rv)
    private RecyclerView goods_type_rv;


    public static GoodsListFragment newInstance(String gt) {
        Bundle args = new Bundle();
        args.putString("goodsType", gt);
        GoodsListFragment goodsListFragment = new GoodsListFragment();
        goodsListFragment.setArguments(args);
        return goodsListFragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_goodslist;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (getArguments() != null) {
            goodsType = getArguments().getString("goodsType");
        }

        initGoodsRv();
        initType();

        loadData();
    }

    //商品列表
    void initGoodsRv() {
        home_adapter = new GoodsListAdapter(R.layout.item_shangpin_home, shoplistDatas);

        layout_loadMore.setVisibility(View.GONE); //加载更多
        iv_xiangshang.setVisibility(View.GONE);//向上按钮，默认隐藏
        iv_xiangshang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv.smoothScrollToPosition(0);
            }
        });
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY = scrollY + dy;

                if (scrollY > 2500) {
                    iv_xiangshang.setVisibility(View.VISIBLE);
                } else {
                    iv_xiangshang.setVisibility(View.GONE);
                }

            }
        });
        //防止数据加载过程滑动错误
        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isLoading) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        //下拉刷新
        layout_refresh.setColorSchemeColors(getResources().getColor(R.color.refresh_layout_color));
        layout_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onDataRefresh();
            }
        });

        home_adapter.setEnableLoadMore(true);
        home_adapter.setUpFetchEnable(true);
        home_adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {

            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                getGoodsList(currentPage, false, true);
            }
        }, rv);
        //点击事件
        home_adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Shop item = home_adapter.getData().get(position);
                goToActivity(GoodsDetailActivity.class, "jsonText", item, false);
            }
        });

        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setAdapter(home_adapter);
        //添加单元格分隔线
//        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                outRect.bottom = 1;
//            }
//        });

    }

    void initType() {
        goodsTypeAdapter = new GoodsTypeAdapter(R.layout.item_goods_type, shopTypes);

        int mColumns = 4; //列数
        int mRows = 2; //行数
        PagerGridLayoutManager mBtnsManager = new PagerGridLayoutManager(mRows, mColumns, PagerGridLayoutManager.HORIZONTAL);
        PagerGridSnapHelper pageSnapHelper = new PagerGridSnapHelper(); // 设置滚动辅助工具
        pageSnapHelper.attachToRecyclerView(goods_type_rv);

//        GridLayoutManager mBtnsManager = new GridLayoutManager(getContext(),4);
        goods_type_rv.setLayoutManager(mBtnsManager);
        goodsTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getContext(), "item" + position + " 被点击了", Toast.LENGTH_SHORT).show();
            }
        });

        goods_type_rv.setAdapter(goodsTypeAdapter);
    }

    void getGoodsList(int page, final Boolean isRefresh, final Boolean isLoadMore) {

        List listdata = homeApi.getGoodsFromServer(page, goodsType, null, new CallBackListApi() {
            @Override
            public void onSuccess(List o) {
                if (!isLoadMore) {
                    shoplistDatas.clear();
                }
                shoplistDatas.addAll(o);
                home_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFinished() {
                if (isRefresh) {
                    layout_refresh.setRefreshing(false);
                }
                if (isLoadMore) {
                    isLoading = false;
                    layout_loadMore.setVisibility(View.GONE);
                    layout_refresh.setRefreshing(false);
                    home_adapter.loadMoreComplete();
                }
            }
        });

        if (listdata == null || listdata.size() == 0 || isRefresh || isLoadMore) return;
        shoplistDatas.clear();
        shoplistDatas.addAll(listdata);
        home_adapter.notifyDataSetChanged();

    }

    void getType(int classify, final Boolean isRefresh) {
        List datalist = homeApi.getTypeFromServer(classify, new CallBackListApi() {
            @Override
            public void onSuccess(List o) {
                if (o == null || o.size() == 0) return;
                ArrayList<ShopType> data = (ArrayList<ShopType>) o;
                shopTypes.clear();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getId().equals(goodsType)) {
                        shopTypes.addAll(data.get(i).getList());
                        break;
                    }
                }
                goodsTypeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFinished() {

            }
        });

        if (datalist == null || datalist.size() == 0 || isRefresh) return;
        ArrayList<ShopType> data = (ArrayList<ShopType>) datalist;
        shopTypes.clear();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(goodsType)) {
                shopTypes.addAll(data.get(i).getList());
                break;
            }
        }
        goodsTypeAdapter.notifyDataSetChanged();

    }

    void loadData() {
        getGoodsList(1, false, false);
        getType(1, false);
    }


    //下拉刷新
    void onDataRefresh() {
        if (!layout_refresh.isRefreshing()) {
            layout_refresh.setRefreshing(true);
        }

        getType(1, true);


        getGoodsList(1, true, false);

    }

}
