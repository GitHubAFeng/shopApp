package com.xuri.sqfanli.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.Adapter_home_goodslist;
import com.xuri.sqfanli.adapter.Adapter_home_goodslist_parent;
import com.xuri.sqfanli.adapter.btnsAdapter;
import com.xuri.sqfanli.api.HomeApi;
import com.xuri.sqfanli.api.UserApi;
import com.xuri.sqfanli.bean.Adv;
import com.xuri.sqfanli.bean.HotGoods;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.callback.CallBackApi;
import com.xuri.sqfanli.ui.base.BaseFragment;
import com.xuri.sqfanli.view.PagerLayoutManager.PagerGridLayoutManager;
import com.xuri.sqfanli.view.PagerLayoutManager.PagerGridSnapHelper;
import com.xuri.sqfanli.view.xImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AFeng on 2018/4/26.
 * 首页
 */

public class FragmentHome extends BaseFragment {

    View headerView;
    Adapter_home_goodslist home_adapter;
    private PagerGridLayoutManager mLayoutManager;
    private btnsAdapter mBtnsAdapter;
    boolean isLoading = false;
    int currentPage = 1; //当前页数
    int userSex = 1; //男1  女2
    List<Shop> shoplistDatas = new ArrayList<>();   //下拉列表里面的商品数据
    List<String> images = new ArrayList<>(); //轮播图片
    int scrollY = 0; //用于向上按钮
    HomeApi homeApi = new HomeApi();
    UserApi userApi = new UserApi();

    private Banner banner;
    private RecyclerView btnRv;
    @ViewInject(R.id.layout_refresh)
    SwipeRefreshLayout layout_refresh;
    @ViewInject(R.id.rv)
    private RecyclerView rv;
    @ViewInject(R.id.layout_loadMore)
    LinearLayout layout_loadMore;

    ImageView hot_todfaddishmg;
    TextView hot_pinkage;
    ImageView hot_rankingimg;
    TextView hot_rankingname;
    ImageView hot_todupdateimg;
    TextView hot_todupdate;

    @ViewInject(R.id.iv_xiangshang)
    ImageView iv_xiangshang;


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

        //动态嵌入布局
        if (headerView == null) {
            headerView = View.inflate(getContext(), R.layout.heard_home, null);
        }

        initBanner();
        initBtn();
        initHot();
        initGoodsRv();
        appUpdateUser();

    }

    //商品列表
    void initGoodsRv() {
        layout_loadMore.setVisibility(View.GONE); //加载更多
        iv_xiangshang.setVisibility(View.GONE);//向上按钮，默认隐藏
        iv_xiangshang.setOnClickListener(view -> rv.smoothScrollToPosition(0));
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

        //下拉刷新
        layout_refresh.setColorSchemeColors(getResources().getColor(R.color.refresh_layout_color));
        layout_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        //头部
        home_adapter = new Adapter_home_goodslist(R.layout.item_shangpin_home, shoplistDatas);
        home_adapter.addHeaderView(headerView);

        home_adapter.setEnableLoadMore(true);
        home_adapter.setUpFetchEnable(true);
        home_adapter.setOnLoadMoreListener(() -> {
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

        int mRows = 2; //行数
        int mColumns = 5; //列数
        mLayoutManager = new PagerGridLayoutManager(mRows, mColumns, PagerGridLayoutManager.HORIZONTAL);
        // 水平分页布局管理器
        btnRv.setLayoutManager(mLayoutManager);
        // 设置滚动辅助工具
        PagerGridSnapHelper pageSnapHelper = new PagerGridSnapHelper();
        pageSnapHelper.attachToRecyclerView(btnRv);

        mBtnsAdapter = new btnsAdapter();
        mBtnsAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int count = mBtnsAdapter.getItemCount(); //item数量
            }
        });

        btnRv.setAdapter(mBtnsAdapter);
        showType();

    }

    //热闹推荐
    void initHot() {
        hot_todfaddishmg = headerView.findViewById(R.id.hot_todfaddishmg);
        hot_pinkage = headerView.findViewById(R.id.hot_pinkage);
        hot_rankingimg = headerView.findViewById(R.id.hot_rankingimg);
        hot_rankingname = headerView.findViewById(R.id.hot_rankingname);
        hot_todupdateimg = headerView.findViewById(R.id.hot_todupdateimg);
        hot_todupdate = headerView.findViewById(R.id.hot_todupdate);

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
                try {
                    JSONArray ja = new JSONArray(result);
                    mBtnsAdapter.setData(ja);
                    mBtnsAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinished() {

            }
        });


        //先显示本地
        if (text != "" || text != null) {
            try {
                JSONArray ja = new JSONArray(text);
                mBtnsAdapter.setData(ja);
                mBtnsAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    void showHot() {
        String text = homeApi.getHotFromServer(userSex, new CallBackApi() {
            @Override
            public void onSuccess(String result) {
                HotGoods hot = new Gson().fromJson(result, HotGoods.class);
                ImageOptions imageOptions = new ImageOptions.Builder()
                        .setLoadingDrawableId(R.drawable.shangpintupian_moren)
                        .setUseMemCache(true)
                        .build();

                hot_pinkage.setText(hot.getPinkage());
                hot_rankingname.setText(hot.getRankingName());
                hot_todupdate.setText(hot.getTodUpdate());
                x.image().bind(hot_todfaddishmg, hot.getTodFaddishImg(), imageOptions);
                x.image().bind(hot_rankingimg, hot.getRankingImg(), imageOptions);
                x.image().bind(hot_todupdateimg, hot.getTodUpdateImg(), imageOptions);

            }

            @Override
            public void onFinished() {

            }
        });

        if (text == "" || text == null) return;

        HotGoods hot = new Gson().fromJson(text, HotGoods.class);
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setLoadingDrawableId(R.drawable.shangpintupian_moren)
                .setUseMemCache(true)
                .build();
        hot_pinkage.setText(hot.getPinkage());
        hot_rankingname.setText(hot.getRankingName());
        hot_todupdate.setText(hot.getTodUpdate());
        x.image().bind(hot_todfaddishmg, hot.getTodFaddishImg(), imageOptions);
        x.image().bind(hot_rankingimg, hot.getRankingImg(), imageOptions);
        x.image().bind(hot_todupdateimg, hot.getTodUpdateImg(), imageOptions);

    }

    void appUpdateUser() {
        userApi.updateUserByUserName("", new CallBackApi() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
