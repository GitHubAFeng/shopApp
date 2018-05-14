package com.xuri.sqfanli.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.adapter.GoodsDetailV2Adapter;
import com.xuri.sqfanli.api.GoodsApi;
import com.xuri.sqfanli.api.base.CallBackDataApi;
import com.xuri.sqfanli.api.base.CallBackListApi;
import com.xuri.sqfanli.bean.MessageVo;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.bean.ShopImg;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AFeng on 2018/5/8.
 * 商品详情页面
 */

public class GoodsDetailActivity extends BaseFragmentActivity {

    GoodsApi goodsApi = new GoodsApi();
    GoodsDetailV2Adapter goodsDetailV2Adapter;
    GridLayoutManager layoutManager;

    Shop shop; //当前商品
    int scrollY = 0; //用于向上按钮

    @ViewInject(R.id.ib_back)
    ImageView ib_back;
    @ViewInject(R.id.goods_detail_rv)
    RecyclerView goods_detail_rv;
    @ViewInject(R.id.iv_xiangshang)
    ImageView iv_xiangshang;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initView() {

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initRv();
        getData();

    }


    void getData() {
        shop = (Shop) this.getSerializDataByKey("jsonText");
        if (shop == null) return;
        goodsDetailV2Adapter.setShopData(shop);

        goodsApi.getTuPingFromServer(shop.getItemid(), new CallBackListApi() {
            @Override
            public void onSuccess(List o) {
                final ArrayList<ShopImg> data = (ArrayList<ShopImg>) o;
                goodsDetailV2Adapter.setShopImgs(data);
                goodsDetailV2Adapter.notifyDataSetChanged();
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (position <= data.size()) {
                            return 2;
                        } else {
                            return 1;
                        }
                    }
                });
            }

            @Override
            public void onFinished() {

            }
        });
        goodsApi.getTuiJianFromServer(shop.getItemid(), new CallBackListApi() {
            @Override
            public void onSuccess(List o) {
                ArrayList<Shop> data = (ArrayList<Shop>) o;
                goodsDetailV2Adapter.setTuiJianList(data);
                goodsDetailV2Adapter.notifyDataSetChanged();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    void initRv() {
        iv_xiangshang.setVisibility(View.GONE);//向上按钮，默认隐藏
        iv_xiangshang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goods_detail_rv.smoothScrollToPosition(0);
            }
        });

        goods_detail_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY += dy;
                if (scrollY > 2500) {
                    iv_xiangshang.setVisibility(View.VISIBLE);
                } else {
                    iv_xiangshang.setVisibility(View.GONE);
                }
            }
        });

        goodsDetailV2Adapter = new GoodsDetailV2Adapter(this, this);

        layoutManager = new GridLayoutManager(context, 2);
        //第一行1列，其他2列
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 2;
            }
        });
        goods_detail_rv.setLayoutManager(layoutManager);
        goods_detail_rv.setAdapter(goodsDetailV2Adapter);

        goods_detail_rv.setAdapter(goodsDetailV2Adapter);

    }


}
