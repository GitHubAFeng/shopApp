package com.xuri.sqfanli.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.HomeBtnsAdapter;
import com.xuri.sqfanli.adapter.HomeGoodsListAdapter;
import com.xuri.sqfanli.adapter.HomeHotAdapter;
import com.xuri.sqfanli.api.HomeApi;
import com.xuri.sqfanli.api.base.CallBackApi;
import com.xuri.sqfanli.bean.Adv;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.bean.ShopType;
import com.xuri.sqfanli.event.MessageEvent;
import com.xuri.sqfanli.ui.base.BaseFragment;
import com.xuri.sqfanli.util.StatusBarUtil;
import com.xuri.sqfanli.view.PagerLayoutManager.PagerGridLayoutManager;
import com.xuri.sqfanli.view.PagerLayoutManager.PagerGridSnapHelper;
import com.xuri.sqfanli.view.timeout.TimeViewComm;
import com.xuri.sqfanli.view.xImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AFeng on 2018/4/26.
 * 首页
 */

public class HomeGoodsListFragment extends BaseFragment {

    View headerView;
    private HomeGoodsListAdapter home_adapter;
    private PagerGridLayoutManager mBtnsManager;
    private PagerGridLayoutManager mHotManager;
    private HomeHotAdapter adapterHomeHot;
    private HomeBtnsAdapter homeBtnsAdapter;
    boolean isLoading = false; //是否正在加载商品列表
    int currentPage = 1; //当前下拉商品页数
    int currentHotPage = 1; //当前热门商品页数
    int userSex = 1; //男1  女2
    List<Shop> shoplistDatas = new ArrayList<>();   //下拉列表里面的商品数据
    List<Shop> hotlistDatas = new ArrayList<>();   //热卖里面的商品数据
    List<String> images = new ArrayList<>(); //轮播图片
    List<ShopType> shopTypes = new ArrayList<>(); //按钮组数据
    int scrollY = 0; //用于向上按钮
    HomeApi homeApi = new HomeApi();

    Boolean is_show_tab = false;  //分类tab是否显示

    private Banner banner;
    private RecyclerView btnRv;
    private RecyclerView hotRv;
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
        return R.layout.fragment_home_goodslist;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        onInitPage();
    }

    //初始化页面
    void onInitPage() {
        //动态嵌入布局
        if (headerView == null) {
            headerView = View.inflate(getContext(), R.layout.heard_home, null);
        }
        StatusBarUtil.setTranslucentForImageView(getActivity(), 0, null);

        TimeViewComm hotTime = headerView.findViewById(R.id.home_time);
        hotTime.startTime(22, 02, 14);

        initBanner();
        initBtn();
        initHot();
        initGoodsRv();

    }


    //商品列表
    void initGoodsRv() {
        layout_loadMore.setVisibility(View.GONE); //加载更多
        iv_xiangshang.setVisibility(View.GONE);//向上按钮，默认隐藏
        iv_xiangshang.setOnClickListener(view -> rv.smoothScrollToPosition(0));
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
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY = scrollY + dy;

                if (scrollY > 500) {
                    if (!is_show_tab) {
                        EventBus.getDefault().post(new MessageEvent("VISIBLE"));
                        is_show_tab = true;
                    }
                } else {
                    if (is_show_tab) {
                        EventBus.getDefault().post(new MessageEvent("GONE"));
                        is_show_tab = false;
                    }
                }

                if (scrollY > 2500) {
                    iv_xiangshang.setVisibility(View.VISIBLE);
                } else {
                    iv_xiangshang.setVisibility(View.GONE);
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

        home_adapter = new HomeGoodsListAdapter(R.layout.item_home_goods, shoplistDatas);
        home_adapter.addHeaderView(headerView); //头部

        home_adapter.setEnableLoadMore(true);
        home_adapter.setUpFetchEnable(true);
        home_adapter.setOnLoadMoreListener(() -> {
            if (layout_refresh.isRefreshing()) {
                return;
            }
            currentPage++;
            homeApi.getGoodsFromServer(currentPage, userSex, new CallBackApi() {
                @Override
                public void onSuccess(String result) {
                    List<Shop> datas = new Gson().fromJson(result, new TypeToken<List<Shop>>() {
                    }.getType());
                    shoplistDatas.addAll(datas);
                    home_adapter.notifyDataSetChanged();
                }

                @Override
                public void onFinished() {
                    home_adapter.loadMoreComplete();
                    isLoading = false;
                    layout_loadMore.setVisibility(View.GONE);
                    layout_refresh.setRefreshing(false);
                }
            });
        }, rv);
        //点击事件
        home_adapter.setOnItemClickListener((adapter1, view, position) -> {
            Shop item = home_adapter.getData().get(position);
            Intent intent = new Intent();
//                        intent.setClass(context, A_shangpinxiangqing.class); //详情页面
            intent.putExtra("jsonText", item.toString());
//            context.startActivity(intent);
            Toast.makeText(context, "position：" + position, Toast.LENGTH_SHORT).show();

        });

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
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

    //轮播
    void initBanner() {
        banner = headerView.findViewById(R.id.banner);

        //设置图片加载器
        banner.setImageLoader(new xImageLoader());

        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.LEFT);

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
            }
        });

        showAdv();

        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    //类型按钮组合
    void initBtn() {
        btnRv = headerView.findViewById(R.id.recycler_view);
        homeBtnsAdapter = new HomeBtnsAdapter(R.layout.item_home_btn, shopTypes);

        int mRows = 2; //行数
        int mColumns = 5; //列数
        mBtnsManager = new PagerGridLayoutManager(mRows, mColumns, PagerGridLayoutManager.HORIZONTAL);
        // 水平分页布局管理器
        btnRv.setLayoutManager(mBtnsManager);
        // 设置滚动辅助工具
        PagerGridSnapHelper pageSnapHelper = new PagerGridSnapHelper();
        pageSnapHelper.attachToRecyclerView(btnRv);

        homeBtnsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getContext(), "item" + position + " 被点击了", Toast.LENGTH_SHORT).show();
            }
        });

        btnRv.setAdapter(homeBtnsAdapter);
        showType();

    }

    //热门推荐
    void initHot() {

        hotRv = headerView.findViewById(R.id.home_rv_hot);
        mHotManager = new PagerGridLayoutManager(1, 5, PagerGridLayoutManager.HORIZONTAL);
        // 水平分页布局管理器
        hotRv.setLayoutManager(mHotManager);
        // 设置滚动辅助工具
        PagerGridSnapHelper hotPageSnapHelper = new PagerGridSnapHelper();
        hotPageSnapHelper.attachToRecyclerView(hotRv);

        adapterHomeHot = new HomeHotAdapter(R.layout.item_home_hot, hotlistDatas);
        adapterHomeHot.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int count = adapterHomeHot.getItemCount(); //item数量
            }
        });

        adapterHomeHot.setEnableLoadMore(true);
        adapterHomeHot.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentHotPage++;
                homeApi.getHotFromServer(currentHotPage, userSex, new CallBackApi() {
                    @Override
                    public void onSuccess(String result) {
                        List<Shop> datas = new Gson().fromJson(result, new TypeToken<List<Shop>>() {
                        }.getType());
                        hotlistDatas.addAll(datas);
                        adapterHomeHot.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinished() {
                        adapterHomeHot.loadMoreComplete();
                    }
                });
            }
        }, hotRv);

        hotRv.setAdapter(adapterHomeHot);
        showHot();
    }

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

    void showAdv() {

        String text = homeApi.getAdvFromServer(userSex, new CallBackApi() {
            @Override
            public void onSuccess(String result) {
                Adv adv = new Gson().fromJson(result, Adv.class);
                images.clear();
                for (int i = 0; i < adv.getMainAdvList().size(); i++) {
                    images.add(adv.getMainAdvList().get(i).getAdvImg());
                }
                //设置图片集合
                banner.setImages(images);
            }

            @Override
            public void onFinished() {

            }
        });


        //先显示本地
        if (text != "" || text != null) {
            Adv adv = new Gson().fromJson(text, Adv.class);
            images.clear();
            for (int i = 0; i < adv.getMainAdvList().size(); i++) {
                images.add(adv.getMainAdvList().get(i).getAdvImg());
            }
            //设置图片集合
            banner.setImages(images);
        }

    }

    void showType() {

        String text = homeApi.getTypeFromServer(userSex, new CallBackApi() {
            @Override
            public void onSuccess(String result) {
                List<ShopType> datas = new Gson().fromJson(result, new TypeToken<List<ShopType>>() {
                }.getType());
                shopTypes.clear();
                shopTypes.addAll(datas);
                homeBtnsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFinished() {

            }
        });

        //先显示本地
        if (text != "" || text != null) {
            List<ShopType> datas = new Gson().fromJson(text, new TypeToken<List<ShopType>>() {
            }.getType());
            shopTypes.clear();
            shopTypes.addAll(datas);
            homeBtnsAdapter.notifyDataSetChanged();
        }

    }

    void showHot() {
        String text = homeApi.getHotFromServer(currentHotPage, userSex, new CallBackApi() {
            @Override
            public void onSuccess(String result) {
                List<Shop> datas = new Gson().fromJson(result, new TypeToken<List<Shop>>() {
                }.getType());
                hotlistDatas.clear();
                hotlistDatas.addAll(datas);
                adapterHomeHot.notifyDataSetChanged();

            }

            @Override
            public void onFinished() {

            }
        });

        if (text == "" || text == null) return;
        List<Shop> datas = new Gson().fromJson(text, new TypeToken<List<Shop>>() {
        }.getType());
        hotlistDatas.clear();
        hotlistDatas.addAll(datas);
        adapterHomeHot.notifyDataSetChanged();

    }

    //下拉刷新
    void onDataRefresh() {
        if (!layout_refresh.isRefreshing()) {
            layout_refresh.setRefreshing(true);
        }

        //按钮组
        homeApi.getTypeFromServer(userSex, new CallBackApi() {
            @Override
            public void onSuccess(String result) {
                List<ShopType> datas = new Gson().fromJson(result, new TypeToken<List<ShopType>>() {
                }.getType());
                shopTypes.clear();
                shopTypes.addAll(datas);
                homeBtnsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFinished() {

            }
        });

        //轮播
        homeApi.getAdvFromServer(userSex, new CallBackApi() {
            @Override
            public void onSuccess(String result) {
                Adv adv = new Gson().fromJson(result, Adv.class);
                images.clear();
                for (int i = 0; i < adv.getMainAdvList().size(); i++) {
                    images.add(adv.getMainAdvList().get(i).getAdvImg());
                }
                //设置图片集合
                banner.setImages(images);
            }

            @Override
            public void onFinished() {

            }
        });

        //热门
        homeApi.getHotFromServer(1, userSex, new CallBackApi() {
            @Override
            public void onSuccess(String result) {
                List<Shop> datas = new Gson().fromJson(result, new TypeToken<List<Shop>>() {
                }.getType());
                hotlistDatas.clear();
                hotlistDatas.addAll(datas);
                adapterHomeHot.notifyDataSetChanged();

            }

            @Override
            public void onFinished() {
                currentHotPage = 1;
            }
        });

        //商品
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
                currentPage = 1;
                layout_refresh.setRefreshing(false);
            }
        });
    }

    //防止空白页面
    @Override
    protected void onVisibleToUser() {
        if (shoplistDatas.size() > 0) return;
        onInitPage();
    }
}
