package com.xuri.sqfanli.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.Shop;

import org.xutils.x;

import java.util.List;

/**
 * Created by AFeng on 2018/4/26.
 * 商品列表
 */

public class HomeGoodsListAdapter extends BaseQuickAdapter<Shop, BaseViewHolder> {


    public HomeGoodsListAdapter(int layoutResId, @Nullable List<Shop> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Shop item) {

        x.image().bind((ImageView) helper.getView(R.id.iv2), item.getItempic());

        String title = item.getItemtitle();
        if (title.length() > 18) {
            title = title.substring(0, 18);
            title = title + "...";
        }

        helper.setText(R.id.tv_title, title);
        helper.setText(R.id.tv_jiage, item.getItemendprice());
        helper.setText(R.id.tv_youquanjine, item.getCouponmoney()+ "元券");
        helper.setText(R.id.tv_goumaishuliang, "月销" + item.getItemsale() + "件");

        TextView tv_yuanjia = helper.getView(R.id.tv_yuanjia);

        if (item.getShoptype().equals("B")) {//天猫
            tv_yuanjia.setText("天猫价:￥" + item.getItemprice());
        } else {
            tv_yuanjia.setText("淘宝价:￥" + item.getItemprice());
        }
        tv_yuanjia.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

    }
}
