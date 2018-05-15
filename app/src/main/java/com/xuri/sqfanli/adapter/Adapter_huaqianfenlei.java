package com.xuri.sqfanli.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.event.Callback_string;
import com.xuri.sqfanli.util.PicassoHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */

public class Adapter_huaqianfenlei extends RecyclerView.Adapter<Adapter_huaqianfenlei.Holder> {

    Context context;
    List<JSONObject> data;
    Callback_string callback;

    public Adapter_huaqianfenlei(Context context) {
        this.context = context;
    }

    public void setData(List<JSONObject> data) {
        this.data = data;
    }

    public void setData(Callback_string callback) {
        this.callback = callback;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_fenlei, parent, false);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        try {
            String name = data.get(position).getString("name");
            String img = data.get(position).getString("img");
            holder.tv.setText(name);
            PicassoHelper.showImageByPicasso(context, holder.iv, img);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.call(String.valueOf(position));
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
        ;

        public Holder(View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.tv);

        }
    }
}
