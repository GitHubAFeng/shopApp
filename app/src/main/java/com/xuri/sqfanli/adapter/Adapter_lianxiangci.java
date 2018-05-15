package com.xuri.sqfanli.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.xuri.sqfanli.R;
import com.xuri.sqfanli.event.Callback_string;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * @Title: 搜索-联想词的列表
 * @Description:
 * @author 何明洋
 */

public class Adapter_lianxiangci extends RecyclerView.Adapter<Adapter_lianxiangci.Holder> {

    Context context;

    JSONArray ja;
    Callback_string callback;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int pos, String keyword);
    }

    public Adapter_lianxiangci(Context context) {
        this.context = context;
    }

    public void setData(String jsonText) {
        try {
            ja = new JSONArray(jsonText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(Callback_string callback) {
        this.callback = callback;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_lianxiangci, parent, false);
        return new Holder(v);

    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {


        try {
            final String text = ja.getJSONObject(position).optString("keyword");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    callback.call(text);
                    if(onItemClickListener != null) {
                        int pos = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(view, pos, text);
                    }
                }
            });
            holder.tv.setText(text);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        if (ja == null) {
            return 0;
        }
        return ja.length();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView tv;

        public Holder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
