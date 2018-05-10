package com.xuri.sqfanli.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.GoodsListAdapter;
import com.xuri.sqfanli.api.HomeApi;
import com.xuri.sqfanli.api.base.CallBackApi;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.ui.activity.GoodsDetailActivity;
import com.xuri.sqfanli.ui.base.BaseFragment;

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
    int userSex = 1; //男1  女2
    List<Shop> shoplistDatas = new ArrayList<Shop>();   //下拉列表里面的商品数据

    int scrollY = 0; //用于向上按钮
    HomeApi homeApi = new HomeApi();

    Boolean is_show_tab = false;  //分类tab是否显示

    @ViewInject(R.id.layout_refresh)
    SwipeRefreshLayout layout_refresh;
    @ViewInject(R.id.rv)
    private RecyclerView rv;
    @ViewInject(R.id.layout_loadMore)
    LinearLayout layout_loadMore;

    @ViewInject(R.id.iv_xiangshang)
    ImageView iv_xiangshang;


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_goodslist;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        initGoodsRv();

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
                homeApi.getGoodsFromServer(currentPage, userSex, new CallBackApi() {
                    @Override
                    public void onSuccess(String result) {
                        List<Shop> datas = new Gson().fromJson(result, new TypeToken<List<Shop>>() {
                        }.getType());
                        shoplistDatas.addAll(datas);
                        home_adapter.notifyDataSetChanged();
                        home_adapter.loadMoreComplete();
                    }

                    @Override
                    public void onFinished() {
                        isLoading = false;
                        layout_loadMore.setVisibility(View.GONE);
                        layout_refresh.setRefreshing(false);
                    }
                });
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

        showGoodsList();
    }

    //获取第一页数据
    void showGoodsList() {
        String text = homeApi.getGoodsFromServer(1, userSex, new CallBackApi() {
            @Override
            public void onSuccess(String result) {
                List<Shop> datas = new Gson().fromJson(result, new TypeToken<List<Shop>>() {
                }.getType());
                shoplistDatas.clear();
                shoplistDatas.addAll(datas);
                home_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFinished() {
                isLoading = false;
                layout_loadMore.setVisibility(View.GONE);
                layout_refresh.setRefreshing(false);
            }
        });

        if (text == "" || text == null) return;
        List<Shop> datas = new Gson().fromJson(text, new TypeToken<List<Shop>>() {
        }.getType());

        shoplistDatas.addAll(datas);
        home_adapter.notifyDataSetChanged();

    }

    //下拉刷新
    void onDataRefresh() {
        if (!layout_refresh.isRefreshing()) {
            layout_refresh.setRefreshing(true);
        }
        homeApi.getGoodsFromServer(1, userSex, new CallBackApi() {
            @Override
            public void onSuccess(String result) {
                List<Shop> datas = new Gson().fromJson(result, new TypeToken<List<Shop>>() {
                }.getType());
                shoplistDatas.clear();
                shoplistDatas.addAll(datas);
                home_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFinished() {
                layout_refresh.setRefreshing(false);
            }
        });
    }

}
