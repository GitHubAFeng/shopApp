package com.xuri.sqfanli.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.event.OnLoadMoreListener;
import com.xuri.sqfanli.event.OnShaiXuanListener;
import com.xuri.sqfanli.event.OnShaixuanBtnClickListener;
import com.xuri.sqfanli.ui.activity.GoodsDetailActivity;
import com.xuri.sqfanli.view.SquareImageView;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by AFeng on 2018/5/14.
 * 子分类页面
 */

public class FenLeiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int Type_header = 0, Type_item = 1;
    Context context;
    ArrayList<Shop> shoplistDatas;   //下拉列表里面的商品数据


    String fenlei;
    String shifouzifenlei = "false";
    boolean isNoMore = false;
    String fromTag = "";

    private OnLoadMoreListener onLoadMoreListener;
    private OnShaiXuanListener onShaiXuanListener;


    public FenLeiAdapter(Context context) {
        if (shoplistDatas == null) {
            shoplistDatas = new ArrayList<Shop>();
        }
        this.context = context;
    }


    public void setOnShaiXuanListener(OnShaiXuanListener onShaiXuanListener) {
        this.onShaiXuanListener = onShaiXuanListener;
    }

    public OnShaiXuanListener getOnShaiXuanListener() {
        return onShaiXuanListener;
    }

    public void setShoplistDatas(ArrayList<Shop> shops) {
        this.shoplistDatas = shops;
    }

    public void loadMore(ArrayList<Shop> shops) {
        if (shoplistDatas == null) {
            shoplistDatas = new ArrayList<Shop>();
        }
        shoplistDatas.addAll(shops);
        if (shops.size() == 0) {
            isNoMore = true;
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    public void setData_shifouzifenlei(String shifouzifenlei) {
        this.shifouzifenlei = shifouzifenlei;
    }

    public void setData_fenlei(String fenlei) {
        this.fenlei = fenlei;
    }


    public void setFromTag(String fromTag) {
        this.fromTag = fromTag;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shangpin_huaqian, parent, false);
        return new Holder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (shoplistDatas != null && position >= shoplistDatas.size() - 1) {
            //当加载到倒数第x个的时候，开始加载后面的
            if (!isNoMore) {
                onLoadMoreListener.OnLoadMore();
            }
        }

        //商品列表

        if (shoplistDatas == null || shoplistDatas.size() == 0) return;
        Shop shop = shoplistDatas.get(position);

        final Holder h = (Holder) holder;

        x.image().bind(h.iv, shop.getItempic());

        h.tv_title.setText(shop.getItemtitle());
        h.tv_jiage.setText(shop.getItemendprice());

        //是否包邮，1为包邮，0为不包邮
        if (("1").equals(shop.getPostFree())) {
            h.baoyouIconIv.setVisibility(View.VISIBLE);
        } else {
            h.baoyouIconIv.setVisibility(View.GONE);
        }

        if (shop.getShoptype().equals("B")) {//天猫
            h.tv_yuanjia.setText("天猫价:￥" + shop.getItemprice());
        } else {
            h.tv_yuanjia.setText("淘宝价:￥" + shop.getItemprice());
        }
        h.tv_yuanjia.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        h.tv_goumaishuliang.setText("月销" + shop.getItemsale() + "件");
        h.tv_youquanjine.setText(shop.getCouponmoney() + "元券");

        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                        TaoBaoTools.getIns(context).showPage((A_fenlei_parent) context, url);
//                        LoadingDialog_logo_1.show(context);

//                    Intent intent = new Intent();
//                    intent.setClass(context, A_shangpinxiangqing.class);
//                    intent.putExtra("fromTag", fromTag);
//                    try {
//                        intent.putExtra("jsonText", ja_data.getJSONObject(position).toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    context.startActivity(intent);

                Intent intent = new Intent();
                intent.setClass(context, GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("jsonBean", shoplistDatas.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        if (shoplistDatas == null) {
            return 1;
        } else {
            return shoplistDatas.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return Type_item;
    }

    class Holder extends RecyclerView.ViewHolder {

        SquareImageView iv;
        TextView tv_jiage, tv_yuanjia, tv_title, tv_goumaishuliang, tv_youquanjine;
        ImageView baoyouIconIv;

        public Holder(View itemView) {
            super(itemView);
            iv = (SquareImageView) itemView.findViewById(R.id.iv);
            tv_jiage = (TextView) itemView.findViewById(R.id.tv_jiage);
            tv_yuanjia = (TextView) itemView.findViewById(R.id.tv_yuanjia);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_youquanjine = (TextView) itemView.findViewById(R.id.tv_youquanjine);
            tv_goumaishuliang = (TextView) itemView.findViewById(R.id.tv_goumaishuliang);
            baoyouIconIv = itemView.findViewById(R.id.baoyou_icon_iv);
        }
    }

}
