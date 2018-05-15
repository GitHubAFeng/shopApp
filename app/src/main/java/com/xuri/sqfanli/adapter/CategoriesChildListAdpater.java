package com.xuri.sqfanli.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.RecommendList;

import java.util.List;

/**
 * Created by Jay&Vi on 2018/4/17.
 * 搜索分类页面右边的子recycleview适配器
 */

public class CategoriesChildListAdpater extends RecyclerView.Adapter<CategoriesChildListAdpater.MyViewHolder> {

    private Context context;
    private List<RecommendList> categoriseChildList;
    private CategoriesGridAdpater gridAdpater;

    public CategoriesChildListAdpater(Context context, List<RecommendList> categoriseChildList) {
        this.context = context;
        this.categoriseChildList = categoriseChildList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_categories_child, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.categoriesChildTitleTv.setText(categoriseChildList.get(position).getName());
        holder.categoriesGridRv.setLayoutManager(new GridLayoutManager(context, 3));
        gridAdpater = new CategoriesGridAdpater(context, categoriseChildList.get(position).getShopType());
        holder.categoriesGridRv.setAdapter(gridAdpater);
    }

    @Override
    public int getItemCount() {
        return categoriseChildList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoriesChildTitleTv;
        RecyclerView categoriesGridRv;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoriesChildTitleTv = itemView.findViewById(R.id.categories_child_tv);
            categoriesGridRv = itemView.findViewById(R.id.categories_child_grid_rv);
        }
    }
}
