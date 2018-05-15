package com.xuri.sqfanli.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.ShopType;
import com.xuri.sqfanli.ui.activity.A_fenlei_parent;
import com.xuri.sqfanli.util.CommonMethod;

import java.util.List;

/**
 * Created by Jay&Vi on 2018/4/17.
 * 搜索分类页面右边的子recycleview里面的网格recycleview适配器
 */

public class CategoriesGridAdpater extends RecyclerView.Adapter<CategoriesGridAdpater.MyViewHolder> {

    private Context context;
    private List<ShopType> categoriseGridList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CategoriesGridAdpater(Context context, List<ShopType> categoriseGridList) {
        this.context = context;
        this.categoriseGridList = categoriseGridList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_fenlei, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
        CommonMethod.showImage_picasso(context, myViewHolder.categoriesGridIv, categoriseGridList.get(position).getImg());
        myViewHolder.categoriesGridTv.setText(categoriseGridList.get(position).getName());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = myViewHolder.getLayoutPosition();
                Intent intent3 = new Intent(context, A_fenlei_parent.class);
                intent3.putExtra("fenlei", categoriseGridList.get(pos).getName());
                intent3.putExtra("fenleiId", categoriseGridList.get(pos).getId());
                context.startActivity(intent3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriseGridList == null ? 0 : categoriseGridList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView categoriesGridIv;
        TextView categoriesGridTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoriesGridIv = itemView.findViewById(R.id.iv);
            categoriesGridTv = itemView.findViewById(R.id.tv);
        }
    }
}
