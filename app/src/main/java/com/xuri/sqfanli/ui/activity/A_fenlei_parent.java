package com.xuri.sqfanli.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.Adapter_fenlei_parent;
import com.xuri.sqfanli.bean.User;
import com.xuri.sqfanli.event.Callback_string;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;
import com.xuri.sqfanli.util.SettingConfig;
import com.xuri.sqfanli.util.TaoBaoTools;
import com.xuri.sqfanli.view.LoadingDialog_logo_1;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类详情页的父类
 * <p>
 * String fenlei = "", fenleiId = "";
 * Created by Administrator on 2017/10/23.
 */

public class A_fenlei_parent extends BaseFragmentActivity {

    Adapter_fenlei_parent adapter;
    RecyclerView rv;
    String fenlei = "", fenleiId = "";
    Callback_string callback;
    boolean isLoading = false;
    TextView tv_title;
    Handler mHandler = new Handler();
    boolean isFirstResume = true;
    LinearLayout layout_loadMore;
    ImageView iv_xiangshang;
    int scrollY = 0;
    List<Map<String, String>> shaixuantiaojian = new ArrayList<Map<String, String>>();
    int dangqianyeshu = 1;
    SwipeRefreshLayout layout_refresh;
    private LinearLayout layout_jiage, layout_shaixuan;
    private ImageView iv_jiage;
    private TextView tv_zonghe, tv_xiaoliang, tv_jiage, tv_shaixuan;
    private ImageButton searchBtn;
    private String jiagebiaoqian = "";
    private Callback_string callback_shaixuan;
    private String shaixuantiaojianStr = "";
    private String fenleiKeyword = "";

    @Override
    public int getLayoutRes() {
        return R.layout.a_fenlei_parent;
    }

    public void findView() {
        searchBtn = findViewById(R.id.search_btn);
        rv = findViewById(R.id.rv);
        tv_title = findViewById(R.id.title_name_tv);
        layout_loadMore = findViewById(R.id.layout_loadMore);
        iv_xiangshang = findViewById(R.id.iv_xiangshang);
        layout_refresh = findViewById(R.id.layout_refresh);
        layout_jiage = findViewById(R.id.layout_jiage);
        layout_shaixuan = findViewById(R.id.layout_shaixuan);
        iv_jiage = findViewById(R.id.iv_jiage);
        tv_zonghe = findViewById(R.id.tv_zonghe);
        tv_xiaoliang = findViewById(R.id.tv_xiaoliang);
        tv_jiage = findViewById(R.id.tv_jiage);
        tv_shaixuan = findViewById(R.id.tv_shaixuan);
    }

    @Override
    public void initView() {
        findView();

        //子分类
        String shifouzifenlei = getIntent().getStringExtra("shifouzifenlei");

        //刷新
        layout_refresh.setColorSchemeColors(Color.parseColor("#f85725"));
        layout_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer("1");
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

        if (searchBtn != null) {
            if (isShowSearch) {
                searchBtn.setVisibility(View.VISIBLE);
                searchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(context, A_sousuo_shouye.class));
                    }
                });
            } else {
                if (searchBtn.getVisibility() == View.VISIBLE) {
                    searchBtn.setVisibility(View.GONE);
                }
            }
        }


        adapter = new Adapter_fenlei_parent(context);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        rv.setLayoutManager(layoutManager);
        callback = new Callback_string() {
            @Override
            public void call(String text) {
                //text 是cid
                if (isLoading == false) {
                    isLoading = true;
                    layout_loadMore.setVisibility(View.VISIBLE);
                    dangqianyeshu = dangqianyeshu + 1;
                    getDataFromServer(String.valueOf(dangqianyeshu));
                }
            }
        };
        adapter.setData(callback);
        adapter.setData_fenlei(fenlei);
        adapter.setData_shifouzifenlei(shifouzifenlei);

//筛选
        adapter.setData_callback_shaixuan(new Callback_string() {
            @Override
            public void call(String text) {
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

                    String zuidijia = StringUtils.substringBetween(text, "zuidijia", ",");
                    String zuigaojia = StringUtils.substringBetween(text, "zuigaojia", ",");
                    if (zuidijia.equals("") == false) {
                        Map<String, String> map1 = new HashMap<String, String>();
                        map1.put("name", "shop.low_price");
                        map1.put("value", zuidijia);
                        shaixuantiaojian.add(map1);
                    }
                    if (zuigaojia.equals("") == false) {
                        Map<String, String> map1 = new HashMap<String, String>();
                        map1.put("name", "shop.high_price");
                        map1.put("value", zuigaojia);
                        shaixuantiaojian.add(map1);
                    }

                }

                getDataFromServer("1");
            }
        });

        rv.setAdapter(adapter);

        showLocalData(false, "");
        getDataFromServer("1");
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

    void showLocalData(boolean append, String text) {
        if (append == false && shaixuantiaojian.size() == 0) {
            text = SettingConfig.getInstance(context).getStringPreference("shop/appList_" + fenlei, "");
        }
        if (text == null || text.length() == 0) return;
        try {
            JSONArray ja = new JSONArray(text);
            adapter.setData(ja, append);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void getDataFromServer(final String cid) {
        String url = Constant.host + "shop/appShop";
        RequestParams params = new RequestParams(url);
        params.addParameter("page.curPage", cid);
        if (fenleiKeyword == null || fenleiKeyword.length() == 0) {//没有关键词则根据分类来查询，有关键词则根据关键词来查询
            params.addParameter("shop.ctype", fenleiId);
        } else {
            params.addParameter("shop.itemtitle", fenleiKeyword);
        }
        params.addParameter("shop.collectUserId", User.getInstance(context).getId());
        params.addParameter("shop.shopSex", User.getInstance(context).getSex());
        System.err.println("------------shaixuantiaojian.size():" + shaixuantiaojian.size());
        for (int i = 0; i < shaixuantiaojian.size(); i++) {
            String key = shaixuantiaojian.get(i).get("name");
            String value = shaixuantiaojian.get(i).get("value");
            System.err.println("--------------key:" + key + "  value:" + value);
            params.addParameter(key, value);
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("") && fenleiKeyword != null && fenleiKeyword.length() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("url", "https://ai.m.taobao.com/search.html?q=" + fenleiKeyword + "&pid=" + TaoBaoTools.TaoBaoKeId);
                    intent.setClass(context, A_aitaobao.class);
                    A_fenlei_parent.this.startActivity(intent);
                }
                if (cid.equals("1")) {
                    if (shaixuantiaojian.size() == 0) {
                        SettingConfig.getInstance(context).setStringPreference("shop/appList_" + fenlei, result);
                    }
                    showLocalData(false, result);
                } else {
                    showLocalData(true, result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                isLoading = false;
                layout_loadMore.setVisibility(View.GONE);
                layout_refresh.setRefreshing(false);

            }
        });

    }

    /**
     * 价格、筛选header控件里面的按钮操作方法
     */
    public void shaixuanLayoutOnClick() {
        callback_shaixuan = adapter.getCallback_shaixuan();

        //综合
        tv_zonghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback_shaixuan.call("zonghe");
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
                callback_shaixuan.call("xiaoliang");
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
                    callback_shaixuan.call("jiage-di");
                    jiagebiaoqian = "di";
                    iv_jiage.setImageResource(R.drawable.ic_result_down);
                } else {
                    callback_shaixuan.call("jiage-gao");
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
                        callback_shaixuan.call(shaixuantiaojianStr);
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

}
