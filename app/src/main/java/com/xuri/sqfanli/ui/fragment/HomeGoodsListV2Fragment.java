package com.xuri.sqfanli.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.HomeBtnsAdapter;
import com.xuri.sqfanli.adapter.HomeGoodsListAdapter;
import com.xuri.sqfanli.api.HomeApi;
import com.xuri.sqfanli.api.base.CallBackApi;
import com.xuri.sqfanli.api.base.CallBackDataApi;
import com.xuri.sqfanli.bean.Adv;
import com.xuri.sqfanli.bean.HotGoods;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.bean.ShopType;
import com.xuri.sqfanli.event.MessageEvent;
import com.xuri.sqfanli.ui.activity.GoodsDetailActivity;
import com.xuri.sqfanli.ui.base.BaseFragment;
import com.xuri.sqfanli.view.PagerLayoutManager.PagerGridLayoutManager;
import com.xuri.sqfanli.view.PagerLayoutManager.PagerGridSnapHelper;
import com.xuri.sqfanli.view.xImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AFeng on 2018/5/7.
 * 首页版本2
 */

public class HomeGoodsListV2Fragment extends BaseFragment {

    View headerView;
    private HomeGoodsListAdapter home_adapter;
    private PagerGridLayoutManager mBtnsManager;
    private HomeBtnsAdapter homeBtnsAdapter;
    boolean isLoading = false; //是否正在加载商品列表
    int currentPage = 1; //当前下拉商品页数
    int currentHotPage = 1; //当前热门商品页数
    int userSex = 1; //男1  女2
    List<Shop> shoplistDatas = new ArrayList<Shop>();   //下拉列表里面的商品数据
    List<String> images = new ArrayList<String>(); //轮播图片
    List<ShopType> shopTypes = new ArrayList<ShopType>(); //按钮组数据
    int scrollY = 0; //用于向上按钮
    HomeApi homeApi = new HomeApi();

    Boolean is_show_tab = false;  //分类tab是否显示

    private Banner banner;
    private RecyclerView btnRv;
    @ViewInject(R.id.layout_refresh)
    SwipeRefreshLayout layout_refresh;
    @ViewInject(R.id.rv)
    private RecyclerView rv;
    @ViewInject(R.id.layout_loadMore)
    LinearLayout layout_loadMore;
    @ViewInject(R.id.iv_xiangshang)
    ImageView iv_xiangshang;

    //热门推荐
    TextView home_hot_title_1;
    TextView home_hot_title_2;
    TextView home_hot_title_3;
    TextView home_hot_desc_1;
    TextView home_hot_desc_2;
    TextView home_hot_desc_3;
    ImageView home_hot_img_1;
    ImageView home_hot_img_2;
    ImageView home_hot_img_3;


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
            headerView = View.inflate(getContext(), R.layout.header_home_v2, null);
        }

        initBanner();
        initBtn();
        initHot();
        initGoodsRv();

    }


    //商品列表
    void initGoodsRv() {
        layout_loadMore.setVisibility(View.GONE); //加载更多
        iv_xiangshang.setVisibility(View.GONE);//向上按钮，默认隐藏
        iv_xiangshang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv.smoothScrollToPosition(0);
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

        home_adapter = new HomeGoodsListAdapter(R.layout.item_shangpin_home, shoplistDatas);
        home_adapter.addHeaderView(headerView); //头部

        home_adapter.setEnableLoadMore(true);
        home_adapter.setUpFetchEnable(true);
        home_adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
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
            }
        }, rv);

        //点击事件
        home_adapter.setOnItemClickListener(
                new BaseQuickAdapter.OnItemClickListener() {
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
                Toast.makeText(getContext(), position + "", Toast.LENGTH_SHORT).show();
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

        home_hot_title_1 = headerView.findViewById(R.id.home_hot_title_1);
        home_hot_title_2 = headerView.findViewById(R.id.home_hot_title_2);
        home_hot_title_3 = headerView.findViewById(R.id.home_hot_title_3);
        home_hot_desc_1 = headerView.findViewById(R.id.home_hot_desc_1);
        home_hot_desc_2 = headerView.findViewById(R.id.home_hot_desc_2);
        home_hot_desc_3 = headerView.findViewById(R.id.home_hot_desc_3);
        home_hot_img_1 = headerView.findViewById(R.id.home_hot_img_1);
        home_hot_img_2 = headerView.findViewById(R.id.home_hot_img_2);
        home_hot_img_3 = headerView.findViewById(R.id.home_hot_img_3);

        String text = homeApi.getHotFromServer(currentHotPage, userSex, new CallBackApi() {
            @Override
            public void onSuccess(String result) {
                HotGoods datas = new Gson().fromJson(result, HotGoods.class);
                home_hot_desc_1.setText(datas.getRankingName());
                home_hot_desc_2.setText(datas.getTodFaddish());
                home_hot_desc_3.setText(datas.getTodUpdate());
                x.image().bind(home_hot_img_1, datas.getRankingImg());
                x.image().bind(home_hot_img_2, datas.getTodFaddishImg());
                x.image().bind(home_hot_img_3, datas.getTodUpdateImg());

            }

            @Override
            public void onFinished() {

            }
        });

        if (TextUtils.isEmpty(text)) return;
        HotGoods datas = new Gson().fromJson(text, HotGoods.class);
        home_hot_desc_1.setText(datas.getRankingName());
        home_hot_desc_2.setText(datas.getTodFaddish());
        home_hot_desc_3.setText(datas.getTodUpdate());
        x.image().bind(home_hot_img_1, datas.getRankingImg());
        x.image().bind(home_hot_img_2, datas.getTodFaddishImg());
        x.image().bind(home_hot_img_3, datas.getTodUpdateImg());

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

        Adv _adv = homeApi.getAdvFromServer(userSex, new CallBackDataApi() {
            @Override
            public void onSuccess(Object o) {
                final Adv adv = (Adv) o;
                if (adv == null || adv.getMainAdvList() == null) return;
                images.clear();
                for (int i = 0; i < adv.getMainAdvList().size(); i++) {
                    images.add(adv.getMainAdvList().get(i).getAdvImg());
                }
                //设置图片集合
                banner.update(images);
            }

            @Override
            public void onFinished() {

            }
        });

        if (_adv == null || _adv.getMainAdvList() == null) return;
        images.clear();
        for (int i = 0; i < _adv.getMainAdvList().size(); i++) {
            images.add(_adv.getMainAdvList().get(i).getAdvImg());
        }
        //设置图片集合
        banner.update(images);

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
        homeApi.getAdvFromServer(userSex, new CallBackDataApi() {
            @Override
            public void onSuccess(Object o) {
                final Adv adv = (Adv) o;
                if (adv == null || adv.getMainAdvList() == null) return;
                images.clear();
                for (int i = 0; i < adv.getMainAdvList().size(); i++) {
                    images.add(adv.getMainAdvList().get(i).getAdvImg());
                }
                //设置图片集合
                banner.update(images);
            }

            @Override
            public void onFinished() {

            }
        });


        //热门
        homeApi.getHotFromServer(1, userSex, new CallBackApi() {
            @Override
            public void onSuccess(String result) {
                HotGoods datas = new Gson().fromJson(result, HotGoods.class);
                home_hot_desc_1.setText(datas.getRankingName());
                home_hot_desc_2.setText(datas.getTodFaddish());
                home_hot_desc_3.setText(datas.getTodUpdate());
                x.image().bind(home_hot_img_1, datas.getRankingImg());
                x.image().bind(home_hot_img_2, datas.getTodFaddishImg());
                x.image().bind(home_hot_img_3, datas.getTodUpdateImg());

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


}
