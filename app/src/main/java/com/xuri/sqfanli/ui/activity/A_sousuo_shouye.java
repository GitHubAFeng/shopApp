package com.xuri.sqfanli.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.Adapter_lianxiangci;
import com.xuri.sqfanli.bean.Search;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;
import com.xuri.sqfanli.util.SettingConfig;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 何明洋
 * @Title: 搜券-搜索的第一个页面
 * @Description:
 */

public class A_sousuo_shouye extends BaseFragmentActivity {

    TagFlowLayout fl_lishi, fl_dajiadouzaisou;
    EditText et_keyword;
    ImageView tv_qingchu;
    LinearLayout layout_lishi;
    private Button searchBtn;
    private RecyclerView mLianxiangciRv;
    private List<String> lishi;
    private List<Search> mHotSearchList;
    private Adapter_lianxiangci mLianxiangciAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.a_sousuo_shouye;
    }

    @Override
    public void initView() {
        findView();

        mLianxiangciAdapter = new Adapter_lianxiangci(context);
        mLianxiangciRv.setLayoutManager(new LinearLayoutManager(context));
        mLianxiangciRv.setAdapter(mLianxiangciAdapter);
        mLianxiangciAdapter.setOnItemClickListener(new Adapter_lianxiangci.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos, String keyword) {
                sousuo(keyword, keyword);
                mLianxiangciRv.setVisibility(View.GONE);
            }
        });

        et_keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = et_keyword.getText().toString().trim();
                    sousuo(searchText, searchText);
                    return true;
                }
                return false;
            }
        });

        //搜索
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = et_keyword.getText().toString().trim();
                sousuo(searchText, searchText);
            }
        });

        String sousuolishi = SettingConfig.getInstance(context).getStringPreference("sousuolishi", "");
        String[] ssls = sousuolishi.split(",");
        lishi = new ArrayList<String>();
        for (int i = 0; i < ssls.length; i++) {
            if (ssls[i].equals("") == false) {
                lishi.add(ssls[i]);
            }

        }
        gengxinlishi();
        fl_lishi.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                et_keyword.setText(lishi.get(position));
//                searchBtn.callOnClick();
                sousuo(lishi.get(position), lishi.get(position));
                return false;
            }
        });

        //清除搜索历史
        tv_qingchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lishi = new ArrayList<String>();
                gengxinlishi();
            }
        });

        et_keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    mLianxiangciRv.setVisibility(View.VISIBLE);
                    getLianxiangci(charSequence.toString());
                } else {
                    mLianxiangciRv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        showLocalData_dajiadouzaisou();
        getDataFromServer_dajiadouzaisou();

    }

    /**
     * 搜索
     *
     * @param searchTtile 商品列表页面显示的标题
     * @param text        具体搜索的关键字
     */
    private void sousuo(String searchTtile, String text) {
        if (text.equals("")) {
            Toast.makeText(context, "请输入搜索内容", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setClass(context, A_fenlei_parent.class);
        intent.putExtra("fenlei", searchTtile);
        intent.putExtra("fenleiId", "");
        intent.putExtra("fenleiKeyword", text);
        intent.putExtra("isShowSearch", false);
        startActivity(intent);

        //更新历史
        for (int i = lishi.size() - 1; i > -1; i--) {
            if (lishi.get(i).equals(text)) {
                lishi.remove(i);
            }
        }
        lishi.add(0, text);
        gengxinlishi();

    }

    void gengxinlishi() {
        System.err.println("------------lishi:" + lishi.size());
        if (lishi.size() != 0) {
            fl_lishi.setAdapter(new TagAdapter<String>(lishi) {
                @Override
                public View getView(FlowLayout parent, int position, final String s) {
                    View v = LayoutInflater.from(context).inflate(R.layout.item_sousuojilu, null);
                    TextView tv = v.findViewById(R.id.tv);
                    tv.setText(s);
                    return v;
                }
            });
        }
        String lstext = "";
        for (int i = 0; i < lishi.size(); i++) {
            lstext = lstext + lishi.get(i) + ",";
        }
        SettingConfig.getInstance(context).setStringPreference("sousuolishi", lstext);

        if (lishi.size() == 0) {
            layout_lishi.setVisibility(View.GONE);
        } else {
            layout_lishi.setVisibility(View.VISIBLE);
        }
    }

    void showLocalData_dajiadouzaisou() {
        String text = SettingConfig.getInstance(context).getStringPreference("appSearch", "");

        try {
            Gson gson = new Gson();
            mHotSearchList = gson.fromJson(text, new TypeToken<List<Search>>() {
            }.getType());

            if (mHotSearchList.size() != 0) {
                et_keyword.setHint(mHotSearchList.get(0).getSearch());
                fl_dajiadouzaisou.setAdapter(new TagAdapter<Search>(mHotSearchList) {
                    @Override
                    public View getView(FlowLayout parent, int position, Search search) {
                        View v = LayoutInflater.from(context).inflate(R.layout.item_sousuojilu, null);
                        TextView tv = v.findViewById(R.id.tv);
                        if ("1".equals(search.getSign())) {
                            tv.setTextColor(getResources().getColor(R.color.deep_radius_red_btn_color));
                        }
                        tv.setText(search.getSearch());
                        return v;
                    }
                });
            }

            fl_dajiadouzaisou.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    sousuo(mHotSearchList.get(position).getSearch(), mHotSearchList.get(position).getKeyword());
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void getDataFromServer_dajiadouzaisou() {
        String url = Constant.host + "shop/appSearch";
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SettingConfig.getInstance(context).setStringPreference("appSearch", result);
                showLocalData_dajiadouzaisou();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void getLianxiangci(String text) {
        String url = Constant.host + "shop/appSeekShop";
        RequestParams params = new RequestParams(url);
        params.addParameter("shopName", et_keyword.getText().toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("SearchKeywordResponse", result);
                mLianxiangciAdapter.setData(result);
                mLianxiangciAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("SearchKeywordError:", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    public void findView() {
        fl_lishi = findViewById(R.id.fl_lishi);
        fl_dajiadouzaisou = findViewById(R.id.fl_dajiadouzaisou);
        et_keyword = findViewById(R.id.et_keyword);
        tv_qingchu = findViewById(R.id.tv_qingchu);
        searchBtn = findViewById(R.id.search_btn);
        mLianxiangciRv = findViewById(R.id.lianxiangci_rv);
        layout_lishi = findViewById(R.id.layout_lishi);
    }

    public void finish(View view) {
        finish();
    }
}
