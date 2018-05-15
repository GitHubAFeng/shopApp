package com.xuri.sqfanli.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.Categories;

import java.util.List;

/**
 * Created by Jay&Vi on 2018/4/17.
 * 搜索分类页面左边的父recycleview适配器
 */

public class CategoriesParentListAdpater extends RecyclerView.Adapter<CategoriesParentListAdpater.MyViewHolder> {

    private Context context;
    private List<Categories> categoriseParentList;
    private OnItemClickListener onItemClickListener;
    private int itemSelected = 0;

    public interface OnItemClickListener {
        void onItemClick(View itemView, ImageView selectedIv, TextView titleTv, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setItemSelected(int position) {
        this.itemSelected = position;
        notifyDataSetChanged();
    }

    public CategoriesParentListAdpater(Context context, List<Categories> categoriseParentList) {
        this.context = context;
        this.categoriseParentList = categoriseParentList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_categories_parent, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int position) {
        myViewHolder.categoriesParentTitleTv.setText(categoriseParentList.get(position).getName());

        if(itemSelected != -1) {
            if(itemSelected == position) {
                myViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.background));
                myViewHolder.categoriesSelectedIv.setVisibility(View.VISIBLE);
                myViewHolder.categoriesParentTitleTv.setTextColor(context.getResources().getColor(R.color.radius_orange_btn_color));
            }else {
                myViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
                myViewHolder.categoriesSelectedIv.setVisibility(View.GONE);
                myViewHolder.categoriesParentTitleTv.setTextColor(context.getResources().getColor(R.color.black));
            }
        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null) {
                    int pos = myViewHolder.getLayoutPosition();
                    onItemClickListener.onItemClick(view,
                                                    myViewHolder.categoriesSelectedIv,
                                                    myViewHolder.categoriesParentTitleTv,
                                                    pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriseParentList == null ? 0 : categoriseParentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView categoriesSelectedIv;
        TextView categoriesParentTitleTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoriesSelectedIv = itemView.findViewById(R.id.categories_selected_iv);
            categoriesParentTitleTv = itemView.findViewById(R.id.categories_parent_title_tv);
        }
    }
}
