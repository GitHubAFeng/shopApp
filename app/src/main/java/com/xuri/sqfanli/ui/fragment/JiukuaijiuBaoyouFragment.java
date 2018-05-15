package com.xuri.sqfanli.ui.fragment;


import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
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

import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.Adapter_fenlei_parent;
import com.xuri.sqfanli.bean.User;
import com.xuri.sqfanli.event.Callback_string;
import com.xuri.sqfanli.ui.base.BaseFragment;
import com.xuri.sqfanli.util.SettingConfig;

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
 * 9块9包邮界面
 */
public class JiukuaijiuBaoyouFragment extends BaseFragment {

    private LinearLayout layout_jiage, layout_shaixuan;
    private ImageView iv_jiage;
    private TextView tv_zonghe, tv_xiaoliang, tv_jiage, tv_shaixuan;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mDataListRv;
    private ImageView mGoBackTopIv;
    private LinearLayout mLoadMoreLayout;
    private String mTabTitleID;
    private String mFenlei;
    private String mFenLeiID;
    private boolean isLoading = false;
    private int scrollY = 0;
    private int mCurrentPageNum = 1;
    private List<Map<String, String>> shaixuantiaojian = new ArrayList<Map<String, String>>();
    private String jiagebiaoqian = "";
    private Callback_string callback_shaixuan;
    private String shaixuantiaojianStr = "";
    private Adapter_fenlei_parent mDataListAdapter;

    public static JiukuaijiuBaoyouFragment newInstance(String tabTitleID, String fenlei, String fenleiId) {
        Bundle args = new Bundle();
        args.putString("tabTitleID", tabTitleID);
        args.putString("fenlei", fenlei);
        args.putString("fenleiId", fenleiId);
        JiukuaijiuBaoyouFragment jiukuaijiuBaoyouFragment = new JiukuaijiuBaoyouFragment();
        jiukuaijiuBaoyouFragment.setArguments(args);
        return jiukuaijiuBaoyouFragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_jiukuaijiu_baoyou;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        findView();

        mTabTitleID = getArguments().getString("tabTitleID");
        mFenlei = getArguments().getString("fenlei");
        mFenLeiID = getArguments().getString("fenleiId");

        Callback_string callback = new Callback_string() {
            @Override
            public void call(String text) {
                if (!isLoading) {
                    isLoading = true;
                    mLoadMoreLayout.setVisibility(View.VISIBLE);
                    mCurrentPageNum = mCurrentPageNum + 1;
                    getDataList(String.valueOf(mCurrentPageNum));
                }
            }
        };

        mLoadMoreLayout.setVisibility(View.GONE);

        mDataListAdapter = new Adapter_fenlei_parent(context);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        mDataListRv.setLayoutManager(layoutManager);
        mDataListAdapter.setData(callback);
        mDataListAdapter.setData_fenlei(mFenlei);
        mDataListAdapter.setFromTag("2");

        //下拉刷新
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.refresh_layout_color));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataList("1");
            }
        });

        mDataListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                mDataListRv.smoothScrollToPosition(0);
            }
        });

        //筛选
        mDataListAdapter.setData_callback_shaixuan(new Callback_string() {
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
                getDataList("1");
            }
        });

        mDataListRv.setAdapter(mDataListAdapter);


        getDataList("1");
        shaixuanLayoutOnClick();
    }

    public void findView() {
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mDataListRv = view.findViewById(R.id.data_list_rv);
        mGoBackTopIv = view.findViewById(R.id.go_back_top_iv);
        mLoadMoreLayout = view.findViewById(R.id.load_more_layout);
        layout_jiage = view.findViewById(R.id.layout_jiage);
        layout_shaixuan = view.findViewById(R.id.layout_shaixuan);
        iv_jiage = view.findViewById(R.id.iv_jiage);
        tv_zonghe = view.findViewById(R.id.tv_zonghe);
        tv_xiaoliang = view.findViewById(R.id.tv_xiaoliang);
        tv_jiage = view.findViewById(R.id.tv_jiage);
        tv_shaixuan = view.findViewById(R.id.tv_shaixuan);
    }

    //获取列表数据
    public void getDataList(final String pageNumber) {
        String url = Constant.host + "shop/appShop";
        RequestParams params = new RequestParams(url);
        params.addParameter("page.curPage", pageNumber);
        params.addParameter("shop.ctype", mTabTitleID);
        params.addParameter("shop.collectUserId", User.getInstance(context).getId());
        params.addParameter("shop.shopSex", User.getInstance(context).getSex());
        System.err.println("------------shaixuantiaojian.size():" + shaixuantiaojian.size());
        for (int i = 0; i < shaixuantiaojian.size(); i++) {
            String key = shaixuantiaojian.get(i).get("name");
            String value = shaixuantiaojian.get(i).get("value");
            System.err.println("--------------key:" + key + "  value:" + value);
            params.addParameter(key, value);
        }

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("DataListResponse", result);
                if (("1").equals(pageNumber)) {
                    if (shaixuantiaojian.size() == 0) {
                        SettingConfig.getInstance(context).setStringPreference("shop/appList_" + mFenlei, result);
                    }
                    showLocalData(false, result);
                } else {
                    showLocalData(true, result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("DataListError", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                isLoading = false;
                mLoadMoreLayout.setVisibility(View.GONE);
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    void showLocalData(boolean append, String text) {
        if (!append && shaixuantiaojian.size() == 0) {
            text = SettingConfig.getInstance(context).getStringPreference("shop/appList_" + mFenlei, "");
        }
        try {
            JSONArray ja = new JSONArray(text);
            mDataListAdapter.setData(ja, append);
            mDataListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 价格、筛选header控件里面的按钮操作方法
     */
    public void shaixuanLayoutOnClick() {
        callback_shaixuan = mDataListAdapter.getCallback_shaixuan();

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
                mDataListRv.smoothScrollToPosition(0);
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
                mDataListRv.smoothScrollToPosition(0);
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
                mDataListRv.smoothScrollToPosition(0);
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
                        mDataListRv.smoothScrollToPosition(0);
                    }
                });
            }
        });
    }

    public void finish(View view) {
        getActivity().finish();
    }
}
