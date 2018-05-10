package com.xuri.sqfanli.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.bean.ShopImg;
import com.xuri.sqfanli.ui.activity.GoodsDetailActivity;
import com.xuri.sqfanli.util.PicassoHelper;
import com.xuri.sqfanli.util.TimeUtils;
import com.xuri.sqfanli.view.SquareImageView;
import com.xuri.sqfanli.view.xImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

/**
 * Created by AFeng on 2018/5/10.
 * 商品详情
 */

public class GoodsDetailV2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int type_header = 0, type_item = 1, type_tuijian = 2;

    private Context context;
    private Activity activity;
    private int screenWide = 0;

    private ArrayList<ShopImg> shopImgs;//商品详情图片
    private ArrayList<Shop> tuiJianList;   //尾部推荐商品
    Shop shop;
    ArrayList<String> bannerImgUrl;

    public GoodsDetailV2Adapter(Activity activity, Context context) {
        this.context = context;
        this.activity = activity;
        Rect outSize = new Rect();
        activity.getWindowManager().getDefaultDisplay().getRectSize(outSize);
        screenWide = outSize.width();
    }

    public void setShopImgs(ArrayList<ShopImg> shopImgs) {
        this.shopImgs = shopImgs;
    }

    public void setTuiJianList(ArrayList<Shop> shopFooterData) {
        this.tuiJianList = shopFooterData;
    }

    public void setShopData(Shop shop) {
        this.shop = shop;
    }


    public void setBannerImgUrl(String urls) {
        try {
            bannerImgUrl = new ArrayList<String>();
            String[] toubuimgs = urls.split(",");
            if (toubuimgs.length > 0) {
                for (int i = 0; i < toubuimgs.length; i++) {
                    bannerImgUrl.add(toubuimgs[i]);
                }
            }
        } catch (Exception e) {

        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == type_header) {
            View view = LayoutInflater.from(context).inflate(R.layout.header_shangpinxiangqing, parent, false);
            return new HeaderHolder(view);
        } else if (viewType == type_item) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_shangpinxiangqing, parent, false);
            return new Holder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_shangpin, parent, false);
            return new Holder_tuijian(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == type_header) {
            initHeader(holder);
        } else if (getItemViewType(position) == type_item) {
            initItem(holder, position);
        } else {
            initTuijianItem(holder, position - shopImgs.size() - 1);
        }
    }

    @Override
    public int getItemCount() {
        if (shopImgs == null) {
            return 0;
        }
        int tuijian = 0;
        if (tuiJianList != null) {
            tuijian = tuiJianList.size();
        }
        return shopImgs.size() + 1 + tuijian;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return type_header;
        } else if (position <= shopImgs.size()) {
            return type_item;
        } else {
            return type_tuijian;
        }
    }


    void initItem(RecyclerView.ViewHolder holder, int position) {
        final Holder h = (Holder) holder;
        ShopImg img = shopImgs.get(position - 1);
        PicassoHelper.showImageByPicassoFixXY(context, h.iv, img.getImagesUrl());
        ViewGroup.LayoutParams params = h.iv.getLayoutParams();
        float bili = Float.valueOf(img.getHeight()) / Float.valueOf(img.getWidth());
        params.height = (int) (bili * screenWide);
        h.iv.setLayoutParams(params);

        if (position == shopImgs.size()) {
            h.iv_tuijian.setVisibility(View.VISIBLE);
        } else {
            h.iv_tuijian.setVisibility(View.GONE);
        }
    }


    void initTuijianItem(RecyclerView.ViewHolder holder, final int pos) {
        Shop s = tuiJianList.get(pos);
        Holder_tuijian h = (Holder_tuijian) holder;
        if (h.iv != null) {
            PicassoHelper.showImageByPicasso(context, h.iv, s.getItempic());
        }
        h.tv_title.setText(s.getItemtitle());
        h.tv_jiage.setText(s.getItemendprice());
        h.tv_yuanjia.setText("￥" + s.getItemprice());
        h.tv_yuanjia.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        h.tv_goumaishuliang.setText("已销" + s.getItemsale() + "件");
        h.tv_youquanjine.setText(s.getCouponmoney() + "元券");
        final String url = s.getCouponurl();

        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("jsonText", tuiJianList.get(pos));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }


    class HeaderHolder extends RecyclerView.ViewHolder {

        TextView tv_baoyou, tv_yuanjia, tv_biaoti, tv_jiage, tv_xiaoliang, tv_youhuiquanjine, tv_youhuiquanqixian;
        Banner banner;

        public HeaderHolder(View itemView) {
            super(itemView);

            tv_baoyou = itemView.findViewById(R.id.tv_baoyou);
            tv_yuanjia = itemView.findViewById(R.id.tv_yuanjia);
            tv_biaoti = itemView.findViewById(R.id.tv_biaoti);
            tv_jiage = itemView.findViewById(R.id.tv_jiage);
            tv_xiaoliang = itemView.findViewById(R.id.tv_xiaoliang);
            tv_youhuiquanjine = itemView.findViewById(R.id.tv_youhuiquanjine);
            tv_youhuiquanqixian = itemView.findViewById(R.id.tv_youhuiquanqixian);
            banner = itemView.findViewById(R.id.banner);

        }
    }


    class Holder extends RecyclerView.ViewHolder {

        ImageView iv, iv_tuijian;

        public Holder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            iv_tuijian = (ImageView) itemView.findViewById(R.id.iv_tuijian);
        }
    }

    class Holder_tuijian extends RecyclerView.ViewHolder {

        SquareImageView iv;
        TextView tv_jiage, tv_yuanjia, tv_title, tv_goumaishuliang, tv_youquanjine;

        public Holder_tuijian(View itemView) {
            super(itemView);
            iv = (SquareImageView) itemView.findViewById(R.id.iv);
            tv_jiage = (TextView) itemView.findViewById(R.id.tv_jiage);
            tv_yuanjia = (TextView) itemView.findViewById(R.id.tv_yuanjia);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_youquanjine = (TextView) itemView.findViewById(R.id.tv_youquanjine);
            tv_goumaishuliang = (TextView) itemView.findViewById(R.id.tv_goumaishuliang);

        }
    }


    void initHeader(RecyclerView.ViewHolder holder) {
        HeaderHolder h = (HeaderHolder) holder;

        //设置图片加载器
        h.banner.setImageLoader(new xImageLoader());
        //设置指示器位置（当banner模式中有指示器时）
        h.banner.setIndicatorGravity(BannerConfig.CENTER);

        h.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
            }
        });

        //设置图片集合
        h.banner.update(bannerImgUrl);
        //banner设置方法全部调用完毕时最后调用
        h.banner.start();

        if (shop.getItemtitle().contains("包邮")) {
            h.tv_baoyou.setVisibility(View.VISIBLE);
        } else {
            h.tv_baoyou.setVisibility(View.GONE);
        }

        h.tv_biaoti.setText(shop.getItemtitle());
        h.tv_jiage.setText(shop.getItemendprice());

        h.tv_xiaoliang.setText("已有" + shop.getItemsale() + "人购买");
        h.tv_youhuiquanjine.setText(shop.getCouponmoney() + "元优惠券");
        h.tv_youhuiquanqixian.setText("截止期限:" + TimeUtils.stampToString(shop.getCouponendtime(), "yyyy-MM-dd HH:mm:ss"));
        h.tv_yuanjia.setText("原价:" + shop.getItemprice());
        h.tv_yuanjia.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        //领券


        //优惠类型

    }


}
