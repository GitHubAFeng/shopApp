package com.xuri.sqfanli.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.event.Callback_string;
import com.xuri.sqfanli.util.CommonMethod;

import org.xutils.x;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Jay&Vi on 2018/4/8.
 * 热销排行榜页面商品列表Adapter
 */

public class HotSalesListAdapter extends Adapter_parent_with_loadmore {

    private ArrayList<Shop> shopList;
    private boolean isNoMore;
    private Callback_string callback;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HotSalesListAdapter(Activity activity, Context context, ArrayList<Shop> shopList) {
        super(activity, context);
        this.shopList = shopList;
    }

    public void setCallback(Callback_string callback) {
        this.callback = callback;
    }

    public Callback_string getCallback() {
        return callback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_shangpin_hot_sales, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (shopList.size() == 0) {
            isNoMore = true;
        }

        if (shopList != null && position >= shopList.size() - 1) {//当加载到倒数第x个的时候，开始加载后面的
            if (!isNoMore) {
                callback.call("");
            }
        }

        final MyViewHolder myViewHolder = (MyViewHolder) holder;

        if (onItemClickListener != null) {
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = myViewHolder.getLayoutPosition();
                    onItemClickListener.onItemClick(view, pos);
                }
            });
        }

        switch (position) {
            case 0:
                myViewHolder.otherRankingLayout.setVisibility(View.GONE);
                myViewHolder.topThreeIv.setVisibility(View.VISIBLE);
                myViewHolder.topThreeIv.setImageResource(R.drawable.ranking_1);
                break;
            case 1:
                myViewHolder.otherRankingLayout.setVisibility(View.GONE);
                myViewHolder.topThreeIv.setVisibility(View.VISIBLE);
                myViewHolder.topThreeIv.setImageResource(R.drawable.ranking_2);
                break;
            case 2:
                myViewHolder.otherRankingLayout.setVisibility(View.GONE);
                myViewHolder.topThreeIv.setVisibility(View.VISIBLE);
                myViewHolder.topThreeIv.setImageResource(R.drawable.ranking_3);
                break;
            default:
                myViewHolder.otherRankingLayout.setVisibility(View.VISIBLE);
                myViewHolder.topThreeIv.setVisibility(View.GONE);
                myViewHolder.rankingNumberTv.setText(String.valueOf(position + 1));
                break;
        }

        //商品图片
        x.image().bind(myViewHolder.shangpinPicIv, shopList.get(position).getItempic());

        //商品名称
        CommonMethod.xianshishangpinbiaotiheshangchenglogo(context, myViewHolder.shangpinNameTv,
                shopList.get(position).getShoptype(),
                shopList.get(position).getItemtitle());

        //商品原价
        String itemprice = shopList.get(position).getItemprice();
        BigDecimal yuanjia = BigDecimal.valueOf(Double.valueOf(itemprice));
        if (shopList.get(position).getShoptype().equals("B")) {
            myViewHolder.shangpinYuanjiaTv.setText("天猫价:￥" + yuanjia.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        } else {
            myViewHolder.shangpinYuanjiaTv.setText("淘宝价:￥" + yuanjia.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        }

        //购买数量
        myViewHolder.goumaishuliangTv.setText("月销" + shopList.get(position).getItemsale() + "件");

        //商品券后价
        myViewHolder.shangpinJiageTv.setText(shopList.get(position).getItemendprice());

        //优惠券金额
        myViewHolder.youhuiquanJineTv.setText(shopList.get(position).getCouponmoney() + "元券");
    }

    @Override
    public int getItemCount() {
        return shopList == null ? 0 : shopList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView shangpinPicIv;
        TextView shangpinNameTv;
        TextView shangpinYuanjiaTv;
        TextView goumaishuliangTv;
        TextView shangpinJiageTv;
        TextView youhuiquanJineTv;
        ImageView topThreeIv;
        RelativeLayout otherRankingLayout;
        TextView rankingNumberTv;

        MyViewHolder(View itemView) {
            super(itemView);
            shangpinPicIv = itemView.findViewById(R.id.shangpin_pic_iv);
            shangpinNameTv = itemView.findViewById(R.id.shangpin_name_tv);
            shangpinYuanjiaTv = itemView.findViewById(R.id.shangpin_yuanjia_tv);
            goumaishuliangTv = itemView.findViewById(R.id.shangpin_goumaishuliang_tv);
            shangpinJiageTv = itemView.findViewById(R.id.shangpin_jiage_tv);
            youhuiquanJineTv = itemView.findViewById(R.id.youhuiquan_jine_tv);
            topThreeIv = itemView.findViewById(R.id.top_three_iv);
            otherRankingLayout = itemView.findViewById(R.id.other_ranking_layout);
            rankingNumberTv = itemView.findViewById(R.id.ranking_number_tv);
        }
    }

}
