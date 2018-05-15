package com.xuri.sqfanli.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.Brank;
import com.xuri.sqfanli.ui.activity.A_pinpa_shop_list;
import com.xuri.sqfanli.util.PicassoHelper;
import com.xuri.sqfanli.util.PicassoTransform;

import java.util.List;

public class Adapter_fenlei_pinpa_top extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Brank> ja;
    Context context;

    public Adapter_fenlei_pinpa_top(Context context) {
        this.context = context;
    }

    public void setData(List<Brank> ja) {
        this.ja = ja;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_fenlei_pinpa_3, parent, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Brank map = ja.get(position);
        Holder mHolder = (Holder) holder;

        mHolder.title.setText(map.getTitle());
        PicassoHelper.showImageRound(context, mHolder.src, map.getLogo());
//        ImageLoader.getInstance().displayImage(map.getLogo(), mHolder.src, options3);
        mHolder.src.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, A_pinpa_shop_list.class);
                in.putExtra("id", "" + map.getId());
                in.putExtra("activityId", "" + map.getActivityId());
                in.putExtra("photo", "" + map.getLogo());
                in.putExtra("title", "" + map.getTitle());
                in.putExtra("child_title", "" + map.getSubTitle());
                context.startActivity(in);
            }
        });

    }

//    DisplayImageOptions options3 = new DisplayImageOptions.Builder()
//            .cacheInMemory(true)
//            .bitmapConfig(Bitmap.Config.ARGB_8888)   //设置图片的解码类型
//            .displayer(new RoundedBitmapDisplayer(20))
//            .build();

    @Override
    public int getItemCount() {
        if (ja == null) {
            return 0;
        }
        if (ja.size() >= 12) {
            return 12;
        }
        return ja.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView src;
        TextView title;

        public Holder(View itemView) {
            super(itemView);
            src = itemView.findViewById(R.id.src);
            title = itemView.findViewById(R.id.title);

        }
    }
}