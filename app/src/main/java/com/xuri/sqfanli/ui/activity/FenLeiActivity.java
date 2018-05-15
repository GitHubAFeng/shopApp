package com.xuri.sqfanli.ui.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.FenLeiAdapter;
import com.xuri.sqfanli.api.GoodsApi;
import com.xuri.sqfanli.api.base.CallBackListApi;
import com.xuri.sqfanli.bean.ShaiXuanBean;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.event.OnLoadMoreListener;
import com.xuri.sqfanli.event.OnShaiXuanListener;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;
import com.xuri.sqfanli.view.LoadingDialog_logo_1;

import org.apache.commons.lang3.StringUtils;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AFeng on 2018/5/14.
 * 子分类页面
 */

public class FenLeiActivity extends BaseFragmentActivity {


    GoodsApi goodsApi;
    FenLeiAdapter adapter;
    ArrayList<Shop> shoplistDatas = new ArrayList<Shop>();   //下拉列表里面的商品数据
    OnShaiXuanListener onShaiXuanListener;
    int scrollY = 0;
    List<Map<String, String>> shaixuantiaojian = new ArrayList<Map<String, String>>();
    int dangqianyeshu = 1;
    private String jiagebiaoqian = "";
    private String shaixuantiaojianStr = "";
    private String fenleiKeyword = ""; //关键词
    String fenlei = "", fenleiId = "";


    @ViewInject(R.id.rv)
    RecyclerView rv;
    boolean isLoading = false;
    @ViewInject(R.id.title_name_tv)
    TextView tv_title;
    boolean isFirstResume = true;
    @ViewInject(R.id.layout_loadMore)
    LinearLayout layout_loadMore;
    @ViewInject(R.id.iv_xiangshang)
    ImageView iv_xiangshang;
    @ViewInject(R.id.layout_refresh)
    SwipeRefreshLayout layout_refresh;
    @ViewInject(R.id.layout_jiage)
    private LinearLayout layout_jiage;
    @ViewInject(R.id.layout_shaixuan)
    private LinearLayout layout_shaixuan;
    @ViewInject(R.id.iv_jiage)
    private ImageView iv_jiage;
    @ViewInject(R.id.tv_zonghe)
    private TextView tv_zonghe;
    @ViewInject(R.id.tv_xiaoliang)
    private TextView tv_xiaoliang;
    @ViewInject(R.id.tv_jiage)
    private TextView tv_jiage;
    @ViewInject(R.id.tv_shaixuan)
    private TextView tv_shaixuan;


    @Override
    public int getLayoutRes() {
        return R.layout.a_fenlei_parent;
    }


    @Override
    public void initView() {
        if (goodsApi == null) {
            goodsApi = new GoodsApi();
        }

        //子分类
        String shifouzifenlei = getIntent().getStringExtra("shifouzifenlei");

        //刷新
        layout_refresh.setColorSchemeColors(Color.parseColor("#f85725"));
        layout_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onDataRefresh();
            }
        });

        layout_loadMore.setVisibility(View.GONE);
        fenlei = getIntent().getStringExtra("fenlei");
        fenleiId = getIntent().getStringExtra("fenleiId");
        fenleiKeyword = getIntent().getStringExtra("fenleiKeyword");
        boolean isShowSearch = getIntent().getBooleanExtra("isShowSearch", true);

        MobclickAgent.onEvent(context, "fenlei" + fenleiId);

        //向上按钮
        iv_xiangshang.setVisibility(View.GONE);//向上按钮，默认隐藏
        iv_xiangshang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv.smoothScrollToPosition(0);
            }
        });
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                int[] location = new int[2];
//                mShaixuanLayout.getLocationOnScreen(location);
//                shaixuanLayoutY = location[1];
//            }
        });

        //页面标题
        tv_title.setText(fenlei);

        adapter = new FenLeiAdapter(context);
        adapter.setShoplistDatas(shoplistDatas);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        rv.setLayoutManager(layoutManager);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void OnLoadMore() {

                if (!isLoading) {
                    isLoading = true;
                    layout_loadMore.setVisibility(View.VISIBLE);
                    dangqianyeshu = dangqianyeshu + 1;
                    getGoodsList(dangqianyeshu, false, true);

                }

            }
        });

        adapter.setData_fenlei(fenlei);
        adapter.setData_shifouzifenlei(shifouzifenlei);

        //筛选
        adapter.setOnShaiXuanListener(new OnShaiXuanListener() {
            @Override
            public void call(String text, ShaiXuanBean shaiXuanBean) {
                shaixuantiaojian = new ArrayList<Map<String, String>>();
                if (text.equals("zonghe")) {
                    Map<String, String> map1 = new HashMap<String, String>();
                    map1.put("name", "shop.sort_type");
                    map1.put("value", "1");
                    shaixuantiaojian.add(map1);
                } else if (text.equals("xiaoliang")) {
                    Map<String, String> map1 = new HashMap<String, String>();
                    map1.put("name", "shop.sort_type");
                    map1.put("value", "2");
                    shaixuantiaojian.add(map1);
                } else if (text.equals("jiage-di")) {
                    Map<String, String> map1 = new HashMap<String, String>();
                    map1.put("name", "shop.sort_type");
                    map1.put("value", "3");
                    shaixuantiaojian.add(map1);
                } else if (text.equals("jiage-gao")) {
                    Map<String, String> map1 = new HashMap<String, String>();
                    map1.put("name", "shop.sort_type");
                    map1.put("value", "4");
                    shaixuantiaojian.add(map1);
                } else {
                    //,shangjia_tianmao,,zhekou2,,zuidijia12,,zuigaojia33,
                    if (text.contains("shangjia_tianmao")) {
                        Map<String, String> map1 = new HashMap<String, String>();
                        map1.put("name", "shop.shoptype");
                        map1.put("value", "B");
                        shaixuantiaojian.add(map1);
                    }
                    if (text.contains("zhekou1")) {
                        Map<String, String> map1 = new HashMap<String, String>();
                        map1.put("name", "shop.discount");
                        map1.put("value", "1");
                        shaixuantiaojian.add(map1);
                    }

                    if (text.contains("zhekou2")) {
                        Map<String, String> map1 = new HashMap<String, String>();
                        map1.put("name", "shop.discount");
                        map1.put("value", "2");
                        shaixuantiaojian.add(map1);
                    }

                    if (text.contains("zhekou3")) {
                        Map<String, String> map1 = new HashMap<String, String>();
                        map1.put("name", "shop.discount");
                        map1.put("value", "3");
                        shaixuantiaojian.add(map1);
                    }

//                    String zuidijia = StringUtils.substringBetween(text, "zuidijia", ",");
//                    String zuigaojia = StringUtils.substringBetween(text, "zuigaojia", ",");

                    String zuidijia = shaiXuanBean != null ? shaiXuanBean.getZuidijia() : "";
                    String zuigaojia = shaiXuanBean != null ? shaiXuanBean.getZuigaojia() : "";

                    if (!zuidijia.equals("")) {
                        Map<String, String> map1 = new HashMap<String, String>();
                        map1.put("name", "shop.low_price");
                        map1.put("value", zuidijia);
                        shaixuantiaojian.add(map1);
                    }
                    if (!zuigaojia.equals("")) {
                        Map<String, String> map1 = new HashMap<String, String>();
                        map1.put("name", "shop.high_price");
                        map1.put("value", zuigaojia);
                        shaixuantiaojian.add(map1);
                    }

                }

                getGoodsList(1, true, false);
            }
        });

        rv.setAdapter(adapter);

        shaixuanLayoutOnClick();
    }

    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onPageStart("fenlei" + fenleiId);

        isFirstResume = false;
        LoadingDialog_logo_1.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("fenlei" + fenleiId);
    }


    //商品列表
    void getGoodsList(int page, final Boolean isRefresh, final Boolean isLoadMore) {
        List listdata = goodsApi.getGoodsFromServer(page, fenleiId, fenleiKeyword, shaixuantiaojian, new CallBackListApi() {
            @Override
            public void onSuccess(List o) {
                if (isLoadMore) {
                    adapter.loadMore((ArrayList<Shop>) o);
                } else if (isRefresh) {
                    shoplistDatas.clear();
                    shoplistDatas.addAll((ArrayList<Shop>) o);
                } else {
                    shoplistDatas.addAll((ArrayList<Shop>) o);
                }
                adapter.notifyDataSetChanged();
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
                }
            }
        });

        if (listdata == null || listdata.size() == 0 || isRefresh || isLoadMore || shaixuantiaojian.size() == 0)
            return;
        shoplistDatas.clear();
        shoplistDatas.addAll(listdata);
        adapter.notifyDataSetChanged();
    }


    /**
     * 价格、筛选header控件里面的按钮操作方法
     */
    public void shaixuanLayoutOnClick() {
        onShaiXuanListener = adapter.getOnShaiXuanListener();

        //综合
        tv_zonghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShaiXuanListener.call("zonghe", null);
                jiagebiaoqian = "";
                iv_jiage.setImageResource(R.drawable.ic_result_disable);
                tv_zonghe.setTextColor(Color.parseColor("#ff6742"));
                tv_xiaoliang.setTextColor(Color.parseColor("#555555"));
                tv_jiage.setTextColor(Color.parseColor("#555555"));
                tv_shaixuan.setTextColor(Color.parseColor("#555555"));
                rv.smoothScrollToPosition(0);
            }
        });

        //销量
        tv_xiaoliang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShaiXuanListener.call("xiaoliang", null);
                jiagebiaoqian = "";
                iv_jiage.setImageResource(R.drawable.ic_result_disable);
                tv_zonghe.setTextColor(Color.parseColor("#555555"));
                tv_xiaoliang.setTextColor(Color.parseColor("#ff6742"));
                tv_jiage.setTextColor(Color.parseColor("#555555"));
                tv_shaixuan.setTextColor(Color.parseColor("#555555"));
                rv.smoothScrollToPosition(0);
            }
        });

        //价格
        layout_jiage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jiagebiaoqian.equals("gao")) {
                    onShaiXuanListener.call("jiage-di", null);
                    jiagebiaoqian = "di";
                    iv_jiage.setImageResource(R.drawable.ic_result_down);
                } else {
                    onShaiXuanListener.call("jiage-gao", null);
                    jiagebiaoqian = "gao";
                    iv_jiage.setImageResource(R.drawable.ic_result_up);
                }
                tv_zonghe.setTextColor(Color.parseColor("#555555"));
                tv_xiaoliang.setTextColor(Color.parseColor("#555555"));
                tv_jiage.setTextColor(Color.parseColor("#ff6742"));
                tv_shaixuan.setTextColor(Color.parseColor("#555555"));
                rv.smoothScrollToPosition(0);
            }

        });

        //筛选
        layout_shaixuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View v = LayoutInflater.from(context).inflate(R.layout.dialog_shaixuan, null, false);
                builder.setView(v);
                final AlertDialog dialog = builder.create();
                dialog.show();

                final TextView tv_shangjia_quanbu = v.findViewById(R.id.tv_shangjia_quanbu);
                final TextView tv_shangjia_tianmao = v.findViewById(R.id.tv_shangjia_tianmao);
                final TextView tv_zhekou1 = v.findViewById(R.id.tv_zhekou1);
                final TextView tv_zhekou2 = v.findViewById(R.id.tv_zhekou2);
                final TextView tv_zhekou3 = v.findViewById(R.id.tv_zhekou3);
                TextView tv_qingkong = v.findViewById(R.id.tv_qingkong);
                TextView tv_queren = v.findViewById(R.id.tv_queren);
                final EditText et_zuidijia = v.findViewById(R.id.et_zuidijia);
                final EditText et_zuigaojia = v.findViewById(R.id.et_zuigaojia);

                //初始化
                if (shaixuantiaojian.contains("shangjia_tianmao")) {
                    tv_shangjia_tianmao.setBackgroundResource(R.drawable.shape_shaixuan);
                    tv_shangjia_tianmao.setTextColor(Color.parseColor("#ff6742"));
                } else {
                    tv_shangjia_quanbu.setBackgroundResource(R.drawable.shape_shaixuan);
                    tv_shangjia_quanbu.setTextColor(Color.parseColor("#ff6742"));
                }
                if (shaixuantiaojian.contains("zhekou1")) {
                    tv_zhekou1.setBackgroundResource(R.drawable.shape_shaixuan);
                    tv_zhekou1.setTextColor(Color.parseColor("#ff6742"));
                }
                if (shaixuantiaojian.contains("zhekou2")) {
                    tv_zhekou2.setBackgroundResource(R.drawable.shape_shaixuan);
                    tv_zhekou2.setTextColor(Color.parseColor("#ff6742"));
                }
                if (shaixuantiaojian.contains("zhekou3")) {
                    tv_zhekou3.setBackgroundResource(R.drawable.shape_shaixuan);
                    tv_zhekou3.setTextColor(Color.parseColor("#ff6742"));
                }
                String zuidijia = StringUtils.substringBetween(shaixuantiaojianStr, "zuidijia", ",");
                String zuigaojia = StringUtils.substringBetween(shaixuantiaojianStr, "zuigaojia", ",");
                et_zuidijia.setText(zuidijia);
                et_zuigaojia.setText(zuigaojia);

                //点击全部商家
                tv_shangjia_quanbu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shaixuantiaojianStr = shaixuantiaojianStr + ",shangjia_quanbu,";
                        shaixuantiaojianStr = shaixuantiaojianStr.replace(",shangjia_tianmao,", "");

                        tv_shangjia_quanbu.setBackgroundResource(R.drawable.shape_shaixuan);
                        tv_shangjia_quanbu.setTextColor(Color.parseColor("#ff6742"));

                        tv_shangjia_tianmao.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                        tv_shangjia_tianmao.setTextColor(Color.parseColor("#555555"));
                    }
                });

                //点击天猫商家
                tv_shangjia_tianmao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shaixuantiaojianStr = shaixuantiaojianStr + ",shangjia_tianmao,";
                        shaixuantiaojianStr = shaixuantiaojianStr.replace(",shangjia_quanbu,", "");

                        tv_shangjia_quanbu.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                        tv_shangjia_quanbu.setTextColor(Color.parseColor("#555555"));

                        tv_shangjia_tianmao.setBackgroundResource(R.drawable.shape_shaixuan);
                        tv_shangjia_tianmao.setTextColor(Color.parseColor("#ff6742"));
                    }
                });
                //折扣1
                tv_zhekou1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shaixuantiaojianStr = shaixuantiaojianStr + ",zhekou1,";
                        shaixuantiaojianStr = shaixuantiaojianStr.replace(",zhekou2,", "");
                        shaixuantiaojianStr = shaixuantiaojianStr.replace(",zhekou3,", "");

                        tv_zhekou1.setBackgroundResource(R.drawable.shape_shaixuan);
                        tv_zhekou1.setTextColor(Color.parseColor("#ff6742"));
                        tv_zhekou2.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                        tv_zhekou2.setTextColor(Color.parseColor("#555555"));
                        tv_zhekou3.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                        tv_zhekou3.setTextColor(Color.parseColor("#555555"));
                    }
                });
                //折扣2
                tv_zhekou2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shaixuantiaojianStr = shaixuantiaojianStr + ",zhekou2,";
                        shaixuantiaojianStr = shaixuantiaojianStr.replace(",zhekou1,", "");
                        shaixuantiaojianStr = shaixuantiaojianStr.replace(",zhekou3,", "");
                        tv_zhekou1.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                        tv_zhekou1.setTextColor(Color.parseColor("#555555"));
                        tv_zhekou2.setBackgroundResource(R.drawable.shape_shaixuan);
                        tv_zhekou2.setTextColor(Color.parseColor("#ff6742"));
                        tv_zhekou3.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                        tv_zhekou3.setTextColor(Color.parseColor("#555555"));
                    }
                });
                //折扣3
                tv_zhekou3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shaixuantiaojianStr = shaixuantiaojianStr + ",zhekou3,";
                        shaixuantiaojianStr = shaixuantiaojianStr.replace(",zhekou1,", "");
                        shaixuantiaojianStr = shaixuantiaojianStr.replace(",zhekou2,", "");
                        tv_zhekou1.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                        tv_zhekou1.setTextColor(Color.parseColor("#555555"));
                        tv_zhekou2.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                        tv_zhekou2.setTextColor(Color.parseColor("#555555"));
                        tv_zhekou3.setBackgroundResource(R.drawable.shape_shaixuan);
                        tv_zhekou3.setTextColor(Color.parseColor("#ff6742"));
                    }
                });
                //清空
                tv_qingkong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shaixuantiaojianStr = "";

                        tv_shangjia_quanbu.setBackgroundResource(R.drawable.shape_shaixuan);
                        tv_shangjia_quanbu.setTextColor(Color.parseColor("#ff6742"));
                        tv_shangjia_tianmao.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                        tv_shangjia_tianmao.setTextColor(Color.parseColor("#555555"));

                        tv_zhekou1.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                        tv_zhekou1.setTextColor(Color.parseColor("#555555"));
                        tv_zhekou2.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                        tv_zhekou2.setTextColor(Color.parseColor("#555555"));
                        tv_zhekou3.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                        tv_zhekou3.setTextColor(Color.parseColor("#555555"));

                        et_zuidijia.setText("");
                        et_zuigaojia.setText("");

                    }
                });
                //确认
                tv_queren.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shaixuantiaojianStr = shaixuantiaojianStr + ",zuidijia" + et_zuidijia.getText() + ",";
                        shaixuantiaojianStr = shaixuantiaojianStr + ",zuigaojia" + et_zuigaojia.getText() + ",";

                        String zuidijia = String.valueOf(et_zuidijia.getText());
                        String zuigaojia = String.valueOf(et_zuigaojia.getText());
                        ShaiXuanBean data = new ShaiXuanBean();
                        data.setZuidijia(zuidijia);
                        data.setZuigaojia(zuigaojia);
                        onShaiXuanListener.call("shaixuantiaojian", data);

                        dialog.cancel();
                        iv_jiage.setImageResource(R.drawable.ic_result_disable);
                        tv_zonghe.setTextColor(Color.parseColor("#555555"));
                        tv_xiaoliang.setTextColor(Color.parseColor("#555555"));
                        tv_jiage.setTextColor(Color.parseColor("#555555"));
                        tv_shaixuan.setTextColor(Color.parseColor("#ff6742"));
                        rv.smoothScrollToPosition(0);
                    }
                });
            }
        });
    }

    public void finish(View view) {
        finish();
    }


    //下拉刷新
    void onDataRefresh() {
        if (!layout_refresh.isRefreshing()) {
            layout_refresh.setRefreshing(true);
        }

        getGoodsList(1, true, false);

    }

}
