package com.xuri.sqfanli.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuri.sqfanli.R;

import java.util.List;

/**
 * Created by AFeng on 2018/5/10.
 * 订单
 */

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> data;
    private View headerView;
    private int ITEM_HEADER = 1, ITEM_CONTENT = 2, ITEM_FOOTER = 3;

    public OrderAdapter(Context context, List<Object> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.item_allorder_header, parent, false);
            return new OrderAdapter.MyViewHolderHeader(view);
        } else if (viewType == ITEM_CONTENT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_allorder_content, parent, false);
            return new OrderAdapter.MyViewHolderContent(view);
        } else if (viewType == ITEM_FOOTER) {
            view = LayoutInflater.from(context).inflate(R.layout.item_allorder_footer, parent, false);
            return new OrderAdapter.MyViewHolderFooter(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof MyViewHolderHeader) {
//
//
//        } else if (holder instanceof MyViewHolderContent) {
//
//        } else (holder instanceof MyViewHolderFooter) {
//
//        }

    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        if (headerView != null) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
//        if(data.get(position) instanceof GoodsOrderInfo) {
//            return ITEM_HEADER;
//        }else if(data.get(position) instanceof OrderGoodsItem){
//            return ITEM_CONTENT;
//        }else if(data.get(position) instanceof OrderPayInfo){
//            return ITEM_FOOTER;
//        }
        return ITEM_CONTENT;
    }


    class MyViewHolderHeader extends RecyclerView.ViewHolder {
        private LinearLayout llAllOrderItem;
        private TextView tvAllOrderItemState, tvAllOrderItemid, tvAllOrderItemShopName;

        public MyViewHolderHeader(View view) {
            super(view);
            llAllOrderItem = (LinearLayout) view.findViewById(R.id.ll_item_allorder_header);
            tvAllOrderItemid = (TextView) view.findViewById(R.id.tv_item_allorder_orderid);
            tvAllOrderItemShopName = (TextView) view.findViewById(R.id.tv_item_allorder_shopname);
            tvAllOrderItemState = (TextView) view.findViewById(R.id.tv_item_allorder_state);
        }
    }


    class MyViewHolderContent extends RecyclerView.ViewHolder {
        private ImageView ivAllOrderItemPic;
        private TextView tvAllOrderItemTitle;
        private TextView tvAllOrderItemPrice;
        private LinearLayout llAllOrderItem;
        private TextView tvAllOrderItemNum;

        public MyViewHolderContent(View view) {
            super(view);
            llAllOrderItem = (LinearLayout) view.findViewById(R.id.ll_item_allorder);
            ivAllOrderItemPic = (ImageView) view.findViewById(R.id.iv_item_allorder_pic);
            tvAllOrderItemTitle = (TextView) view.findViewById(R.id.tv_item_allorder_title);
            tvAllOrderItemPrice = (TextView) view.findViewById(R.id.tv_item_allorder_item_price);
            tvAllOrderItemNum = (TextView) view.findViewById(R.id.tv_item_allorder_item_num);
        }
    }


    class MyViewHolderFooter extends RecyclerView.ViewHolder {
        private TextView tvAllOrderTotal, tvAllOrderSubmit;

        public MyViewHolderFooter(View view) {
            super(view);
            tvAllOrderTotal = (TextView) view.findViewById(R.id.tv_item_allorder_total);
            tvAllOrderSubmit = (TextView) view.findViewById(R.id.tv_item_allorder_submit);
        }
    }


}
