package com.xuri.sqfanli.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.ShopType;
import com.xuri.sqfanli.callback.Callback_string;

import org.json.JSONArray;
import org.json.JSONException;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by AFeng on 2018/4/24.
 */

public class btnsAdapter extends RecyclerView.Adapter<btnsAdapter.MyViewHolder> {

    JSONArray data;
    Callback_string callback;
    ImageOptions imageOptions;

    public ImageOptions getImageOptions() {
        if (imageOptions == null) {
            imageOptions = new ImageOptions.Builder()
                    .setLoadingDrawableId(R.drawable.shangpintupian_moren)
                    .setUseMemCache(true)
                    .setCircular(true)
                    .build();
        }
        return imageOptions;
    }

    public JSONArray getData() {
        return this.data;
    }

    public void setData(JSONArray ja_data) {
        if (this.data == null) {
            this.data = new JSONArray();
        }
        this.data = ja_data;
    }

    public void setData(Callback_string callback) {
        this.callback = callback;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //    Log.i("GCS", "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_home_btn, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            Log.d("onBindViewHolder", "onBindViewHolder: " + data);
            if (data == null) return;
            ShopType type = new Gson().fromJson(data.getJSONObject(position).toString(), ShopType.class);
            holder.tv_title.setText(type.getName());
            x.image().bind(holder.tv_image, type.getImg(), getImageOptions());
            Log.d("getImg", type.getImg());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "item" + position + " 被点击了", Toast.LENGTH_SHORT).show();
                    notifyItemChanged(position);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 1;
        } else {
            return data.length() + 1;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView tv_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_image = itemView.findViewById(R.id.tv_img);
        }
    }
}