package com.xuri.sqfanli.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.Shop;

import org.xutils.x;

import java.util.List;

/**
 * Created by AFeng on 2018/5/2.
 */

public class AdapterHomeHot extends BaseQuickAdapter<Shop, BaseViewHolder> {


    public AdapterHomeHot(int layoutResId, @Nullable List<Shop> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Shop item) {

        x.image().bind(helper.getView(R.id.home_hot_img), item.getItempic());

        String title = item.getItemtitle();
        if (title.length() > 8) {
            title = title.substring(0, 8);
            title = title + "...";
        }

        helper.setText(R.id.home_hot_title, title);
        helper.setText(R.id.home_hot_take, "立减" + item.getCouponmoney() + "元");
        helper.setText(R.id.home_hot_price, "￥" + item.getItemendprice());
    }
}
