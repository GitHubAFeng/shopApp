package com.xuri.sqfanli.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.util.PicassoHelper;


import java.util.List;

public class Adapter_fenlei_pinpa_shop_list extends RecyclerView.Adapter<Adapter_fenlei_pinpa_shop_list.Holder> {
    public List<Shop> ja;
    Context context;

    public Adapter_fenlei_pinpa_shop_list(Context context) {
        this.context = context;
    }

    public void setData(List<Shop> ja) {
        this.ja = ja;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.a_pinpa_shop_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Shop map = ja.get(position);
        Holder mHolder = (Holder) holder;

        mHolder.title.setText(map.getItemtitle());
        mHolder.tianmao_price.setText("天猫价 ￥" + map.getItemprice());
        mHolder.num.setText("月销 " + map.getItemsale());
        mHolder.price.setText("￥" + map.getItemendprice());
        if (map.getCouponType().equals("1"))//是优惠券
        {
            mHolder.zhekou_text.setText("券后价");
            mHolder.zhekou.setText("立即抢购");
        } else {
            mHolder.zhekou_text.setText("折扣价");
            mHolder.zhekou.setText("" + map.getDiscount() + "折");
        }

//        ImageLoader.getInstance().displayImage(map.getItempic(), mHolder.src, options3);
        PicassoHelper.showImageByPicasso(context, mHolder.src, map.getItempic());
        mHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mcallBack != null) {

                    Gson gson = new Gson();
                    String obj = gson.toJson(map);
                    mcallBack.id(obj);
                }

            }
        });
    }

//    DisplayImageOptions options3 = new DisplayImageOptions.Builder()
//            .cacheInMemory(true)
//            .bitmapConfig(Bitmap.Config.ARGB_8888)   //设置图片的解码类型
//            .build();


    @Override
    public int getItemCount() {
        if (ja == null) {
            return 0;
        }
        return ja.size();
    }

    Adapter_fenlei_pinpa.callBack mcallBack;

    public void setcallBack(Adapter_fenlei_pinpa.callBack mcallBack) {
        this.mcallBack = mcallBack;
    }

    class Holder extends RecyclerView.ViewHolder {
        LinearLayout view;
        ImageView src;
        TextView title, tianmao_price, num, price, zhekou, zhekou_text;

        public Holder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            view = itemView.findViewById(R.id.view);
            zhekou_text = itemView.findViewById(R.id.zhekou_text);
            tianmao_price = itemView.findViewById(R.id.tianmao_price);
            num = itemView.findViewById(R.id.num);
            price = itemView.findViewById(R.id.price);
            zhekou = itemView.findViewById(R.id.zhekou);
            src = itemView.findViewById(R.id.src);
        }
    }
}
