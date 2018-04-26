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
 */

public class Adapter_home_goodslist extends BaseQuickAdapter<Shop, BaseViewHolder> {


    public Adapter_home_goodslist(int layoutResId, @Nullable List<Shop> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Shop item) {

        x.image().bind((ImageView) helper.getView(R.id.iv), item.getItempic());
        Log.d("convert", "convert: "+item.getItemtitle());
        helper.setText(R.id.tv_jiage, item.getItemtitle());
        helper.setText(R.id.tv_jiage, item.getItemendprice());

        ImageView baoyouIconIv = helper.getView(R.id.baoyou_icon_iv);
        //是否包邮，1为包邮，0为不包邮
        if (("1").equals(item.getPostFree())) {
            baoyouIconIv.setVisibility(View.VISIBLE);
        } else {
            baoyouIconIv.setVisibility(View.GONE);
        }

        TextView tv_yuanjia = helper.getView(R.id.tv_yuanjia);

        if (item.getShoptype().equals("B")) {//天猫
            tv_yuanjia.setText("天猫价:￥" + item.getItemprice());
        } else {
            tv_yuanjia.setText("淘宝价:￥" + item.getItemprice());
        }
        tv_yuanjia.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.tv_youquanjine, "月销" + item.getItemsale() + "件");
        helper.setText(R.id.tv_goumaishuliang, item.getCouponmoney() + "元券");

    }
}
