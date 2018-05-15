package com.xuri.sqfanli.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * @Title: 一个通用的Adapter父类，包含‘加载更多’的功能
 * @Description: 此类中包含加载更多和显示商品信息的功能，如果商品信息需要特殊处理，重写相应的方法即可，
 * header部分此类不做处理，全部由子类重写。
 * @author 何明洋
 */

public class Adapter_parent_with_loadmore extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int Type_header = 0, Type_item = 1;
    Context context;
    Activity activity;
    JSONArray ja_data;
    Callback_string callback;
    boolean isNoMore = false;

    public Adapter_parent_with_loadmore(Activity activity, Context context) {
        this.context = context;
        this.activity = activity;
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
        }else{
            this.ja_data = ja_data;
        }

        //更新lastMax
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Type_header) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_fenlei_header, parent, false);
            return new Holder_header(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_shangpin, parent, false);
            return new Holder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //需要一个变量记录上次的数量，如果当前数量大于上次数量，则继续加载
        //注意返回数量为0时
        if (position!=0 && position >= ja_data.length() - 1) {//当加载到倒数第x个的时候，开始加载后面的
            if (isNoMore == false) {
                callback.call("");

//                try {
//                    String cid = String.valueOf(ja_data.getJSONObject(ja_data.length() - 1).getInt("cid"));
//                    callback.call(cid);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

        }


        int type = getItemViewType(position);
        if (type == Type_header) {
            initHeader(holder);
        } else {
            initItem(holder, position);
        }
    }

    void initHeader(RecyclerView.ViewHolder holder) {

    }

    void initItem(RecyclerView.ViewHolder holder, final int pos) {
        try {
            Shop shop = new Gson().fromJson(ja_data.getJSONObject(pos - 1).toString(), Shop.class);
            final Holder h = (Holder) holder;
            if (h.iv != null) {
                //CommonMethod.showImage_picasso(context, h.iv, shop.getItempic());
                x.image().bind(h.iv, shop.getItempic());
            }
            h.tv_title.setText(shop.getItemtitle());
            h.tv_jiage.setText(shop.getItemendprice());
            h.tv_yuanjia.setText("￥" + shop.getItemprice());
            h.tv_yuanjia.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            h.tv_goumaishuliang.setText("已销" + shop.getItemsale() + "件");
            h.tv_youquanjine.setText(shop.getCouponmoney() + "元券");
            final String url = shop.getCouponurl();

            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    TaoBaoTools.getIns(context).showPage(activity, url);
//                    MobclickAgent.onEvent(context, "dianjishangpin");
//                    LoadingDialog_logo_1.show(context);


                    Intent intent = new Intent();
                    intent.setClass(context, GoodsDetailActivity.class);
                    try {
                        intent.putExtra("jsonText", ja_data.getJSONObject(pos - 1).toString());
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
            ja_data = new JSONArray();
            return 1;
        } else {
            return ja_data.length() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Type_header;
        } else {
            return Type_item;
        }
    }

    class Holder extends RecyclerView.ViewHolder {

        SquareImageView iv;
        TextView tv_jiage, tv_yuanjia, tv_title, tv_goumaishuliang,tv_youquanjine;

        public Holder(View itemView) {
            super(itemView);
            iv = (SquareImageView) itemView.findViewById(R.id.iv);
            tv_jiage = (TextView) itemView.findViewById(R.id.tv_jiage);
            tv_yuanjia = (TextView) itemView.findViewById(R.id.tv_yuanjia);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_youquanjine = (TextView) itemView.findViewById(R.id.tv_youquanjine);
            tv_goumaishuliang = (TextView) itemView.findViewById(R.id.tv_goumaishuliang);

        }
    }

    class Holder_header extends RecyclerView.ViewHolder {

        public Holder_header(View itemView) {
            super(itemView);
        }
    }
}
