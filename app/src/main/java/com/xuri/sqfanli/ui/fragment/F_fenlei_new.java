package com.xuri.sqfanli.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.Adapter_fenlei_pinpa;
import com.xuri.sqfanli.api.MyHttp;
import com.xuri.sqfanli.api.ShengQianHttp;
import com.xuri.sqfanli.bean.Brank;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.ui.activity.A_sousuo_shouye;
import com.xuri.sqfanli.ui.activity.GoodsDetailActivity;
import com.xuri.sqfanli.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 邓盛坤
 * @Description: TODO 新的分类
 * 品牌
 */
public class F_fenlei_new extends BaseFragment {
    RecyclerView rv;
    Adapter_fenlei_pinpa adapter;
    RelativeLayout xuanfu_go;
    TextView num, num_total;
    ImageView go_top_img;
    LinearLayout num_layout;
    LinearLayoutManager lm;
    List<HashMap<String, Object>> list;

    @Override
    public int getLayoutRes() {
        return R.layout.f_fenlei_new;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        MobclickAgent.onEvent(context, "F_fenlei_new");
        list = new ArrayList<HashMap<String, Object>>();
        xuanfu_go = view.findViewById(R.id.xuanfu_go);


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);


        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) xuanfu_go.getLayoutParams();
        lp2.topMargin = (int) (dm.heightPixels * 0.75);
        xuanfu_go.setLayoutParams(lp2);


        num = view.findViewById(R.id.num);
        num_total = view.findViewById(R.id.num_total);
        num_layout = view.findViewById(R.id.num_layout);
        go_top_img = view.findViewById(R.id.go_top_img);
        rv = view.findViewById(R.id.rv);
        lm = new LinearLayoutManager(context) {
        };
        adapter = new Adapter_fenlei_pinpa(context);
        adapter.setcallBack(new Adapter_fenlei_pinpa.callBack() {
            @Override
            public void id(String id) {
                Intent in = new Intent(context, GoodsDetailActivity.class);
                in.putExtra("jsonText", "" + id);
                in.putExtra("fromTag", "1");//来源品牌
                startActivity(in);

            }
        });
        view.findViewById(R.id.seach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, A_sousuo_shouye.class);
                startActivity(in);
            }
        });

        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition() + 1;

                    System.out.println(lastItemPosition + "   ");

                    num.setText(lastItemPosition + "");
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                System.out.println(" onScrollStateChanged  ");
                if (AbsListView.OnScrollListener.SCROLL_STATE_IDLE == newState) {

                    int n = lm.findFirstCompletelyVisibleItemPosition();
                    ;
                    if (n != 0)//不在顶部 需要显示悬浮按钮
                    {
                        xuanfu_go.setVisibility(View.VISIBLE);
                    } else {
                        xuanfu_go.setVisibility(View.GONE);
                    }

                    num_layout.setVisibility(View.GONE);
                    go_top_img.setVisibility(View.VISIBLE);
                }


            }

        });

        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) { //手指离开
//                     int n=lm.findFirstCompletelyVisibleItemPosition();;
//                     if(n!=0)//不在顶部 需要显示悬浮按钮
//                     {
//                         xuanfu_go.setVisibility(View.VISIBLE);
//                     }else
//                     {
//                         xuanfu_go.setVisibility(View.GONE);
//                     }
//
//                     num_layout.setVisibility(View.GONE);
//                     go_top_img.setVisibility(View.VISIBLE);
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE)//显示
                {

                    xuanfu_go.setVisibility(View.VISIBLE);
                    num_layout.setVisibility(View.VISIBLE);
                    go_top_img.setVisibility(View.GONE);

                }
                return false;
            }
        });
        go_top_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.smoothScrollToPosition(0);
            }
        });

        ShengQianHttp.appBrankList(context, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                List<Brank> BrankList = new Gson().fromJson(str, new TypeToken<List<Brank>>() {
                }.getType());
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("type", "0");
                map.put("obj", BrankList);
                list.add(map);
                for (int i = 0; i < BrankList.size(); i++) {
                    Brank mBrank = BrankList.get(i);
                    map = new HashMap<String, Object>();
                    map.put("type", "1");
                    map.put("title", mBrank.getTitle());
                    map.put("subTitle", mBrank.getSubTitle());

                    map.put("id", mBrank.getId());
                    map.put("activityId", mBrank.getActivityId());

                    map.put("photo", mBrank.getLogo());
                    map.put("obj", mBrank.getBankShop());
                    list.add(map);
                    List<Shop> tuiShop = mBrank.getTuiShop();

                    for (int z = 0; z < tuiShop.size(); z++) {
                        map = new HashMap<String, Object>();
                        map.put("type", "2");
                        map.put("obj", tuiShop.get(z));
                        list.add(map);
                    }
                }
                adapter.setData(list);
                num_total.setText(list.size() + "");
            }

            @Override
            public void onError(String str) {
            }

            @Override
            public void onFinished() {
            }
        });


    }

}
