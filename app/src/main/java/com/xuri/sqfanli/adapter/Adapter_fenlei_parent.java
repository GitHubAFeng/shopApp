package com.xuri.sqfanli.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.event.Callback_string;
import com.xuri.sqfanli.ui.activity.GoodsDetailActivity;
import com.xuri.sqfanli.view.SquareImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.xutils.x;

/**
 * @author 何明洋
 * @Title: 分类详情页
 * @Description: 所有的分类都用这个页面，包括子分类。比如首页-搜券-女装，进入的就是这里
 */

public class Adapter_fenlei_parent extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int Type_header = 0, Type_item = 1;
    Context context;
    JSONArray ja_data;
    int lastMax = 0;
    Callback_string callback;
    String fenlei;
    String shifouzifenlei = "false";
    boolean isNoMore = false;
    String fromTag = "";

    Callback_string callback_shaixuan;

    public Adapter_fenlei_parent(Context context) {
        this.context = context;
    }

    public void setData_callback_shaixuan(Callback_string callback) {
        this.callback_shaixuan = callback;
    }

    public Callback_string getCallback_shaixuan() {
        return callback_shaixuan;
    }

    public void setData_shifouzifenlei(String shifouzifenlei) {
        this.shifouzifenlei = shifouzifenlei;
    }

    public void setData_fenlei(String fenlei) {
        this.fenlei = fenlei;
    }

    public void setData(JSONArray ja_data, boolean append) {
        if (this.ja_data == null) {
            this.ja_data = new JSONArray();
        }
        if (append) {
            //合并
            for (int i = 0; i < ja_data.length(); i++) {
                try {
                    this.ja_data.put(ja_data.getJSONObject(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            this.ja_data = ja_data;
        }

        //更新lastMax
//        if (append) {
//            lastMax = ja_data.length();
//        }
        if (ja_data.length() == 0) {
            isNoMore = true;
        }
    }

    public JSONArray getData() {
        return ja_data;
    }

    public void setData(Callback_string callback) {
        this.callback = callback;
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
        //需要一个变量记录上次的数量，如果当前数量大于上次数量，则继续加载
        //注意返回数量为0时
//        if (position >= ja_data.length() - 1 && position > 9) {//当加载到倒数第x个的时候，开始加载后面的;少于9个，不加载
//            if (ja_data.length() > lastMax) {
//                callback.call("");
//            }
//
//        }
        if (ja_data != null && position >= ja_data.length() - 1) {//当加载到倒数第x个的时候，开始加载后面的
            if (isNoMore == false) {
                callback.call("");
            }
        }

        try {
            Shop shop = new Gson().fromJson(ja_data.getJSONObject(position).toString(), Shop.class);
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

                    Intent intent = new Intent();
                    intent.setClass(context, GoodsDetailActivity.class);
                    intent.putExtra("fromTag", fromTag);
                    try {
                        intent.putExtra("jsonText", ja_data.getJSONObject(position).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    context.startActivity(intent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        if (ja_data == null) {
            return 0;
        } else {
            return ja_data.length();
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
