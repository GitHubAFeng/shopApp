package com.xuri.sqfanli.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
import com.xuri.sqfanli.adapter.Adapter_fenlei_pinpa_shop_list;
import com.xuri.sqfanli.api.MyHttp;
import com.xuri.sqfanli.api.ShengQianHttp;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.event.EndLessOnScrollListener;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;
import com.xuri.sqfanli.util.PicassoHelper;

import java.util.List;

/**
 * @author 坤
 * @Description: TODO 品牌历史
 */
public class A_pinpa_shop_list extends BaseFragmentActivity {

    RecyclerView rv;
    Adapter_fenlei_pinpa_shop_list adapter;

    @Override
    public int getLayoutRes() {
        return R.layout.a_pinpa_shop;
    }

    @Override
    public void initView() {
        MobclickAgent.onEvent(context, "gezhongmingxi");
        findView();
    }

    SwipeRefreshLayout mSwipeRefreshWidget;
    Handler handler = new Handler();
    String id = "";
    String activityId = "";
    String title = "";
    String photo_src = "";
    String child_title_str = "";

    RelativeLayout xuanfu_go;
    TextView num, num_total;
    ImageView go_top_img;
    LinearLayout num_layout;

    public void findView() {

        xuanfu_go = findViewById(R.id.xuanfu_go);
        num = findViewById(R.id.num);
        num_total = findViewById(R.id.num_total);
        num_layout = findViewById(R.id.num_layout);
        go_top_img = findViewById(R.id.go_top_img);
        id = getIntent().getStringExtra("id");
        activityId = getIntent().getStringExtra("activityId");
        Log.e("wd", "id==" + id);
        Log.e("wd", "activityId==" + activityId);

        title = getIntent().getStringExtra("title");
        photo_src = getIntent().getStringExtra("photo");
        child_title_str = getIntent().getStringExtra("child_title");
        rv = (RecyclerView) findViewById(R.id.rv);

        TextView title_top_name = findViewById(R.id.title_top_name);
        TextView shop_title = findViewById(R.id.shop_title);
        TextView child_title = findViewById(R.id.child_title);
        ImageView photo = findViewById(R.id.photo);
//        DisplayImageOptions options3 = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .bitmapConfig(Bitmap.Config.ARGB_8888)   //设置图片的解码类型
//                .build();
//        ImageLoader.getInstance().displayImage(photo_src, photo, options3);
        PicassoHelper.showImageByPicasso(context, photo, photo_src);
        title_top_name.setText(title);
        shop_title.setText(title);
        child_title.setText(child_title_str);


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final LinearLayoutManager lm = new LinearLayoutManager(context) {
        };
        adapter = new Adapter_fenlei_pinpa_shop_list(context);
        adapter.setcallBack(new Adapter_fenlei_pinpa.callBack() {
            @Override
            public void id(String id) {
                Intent in = new Intent(context, GoodsDetailActivity.class);
                in.putExtra("jsonText", "" + id);
                in.putExtra("fromTag", "1");//来源品牌
                startActivity(in);
            }
        });
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        mEndLessOnScrollListener = new EndLessOnScrollListener(lm) {
            @Override
            public void onLoadMore(int currentPage) {

            }
        };

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

        go_top_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.smoothScrollToPosition(0);
            }
        });
        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) { //手指离开

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
        rv.addOnScrollListener(mEndLessOnScrollListener);

        ShengQianHttp.appBrankShopList(context, id, activityId, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                List<Shop> BrankList = new Gson().fromJson(str, new TypeToken<List<Shop>>() {
                }.getType());

                adapter.setData(BrankList);
                num_total.setText(BrankList.size() + "");
            }

            @Override
            public void onError(String str) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    EndLessOnScrollListener mEndLessOnScrollListener;

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
