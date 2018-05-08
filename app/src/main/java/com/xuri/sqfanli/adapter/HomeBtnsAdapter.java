package com.xuri.sqfanli.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.ShopType;

import org.xutils.x;

import java.util.List;

/**
 * Created by AFeng on 2018/5/3.
 */

public class HomeBtnsAdapter extends BaseQuickAdapter<ShopType, BaseViewHolder> {
    public HomeBtnsAdapter(int layoutResId, @Nullable List<ShopType> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopType item) {
        helper.setText(R.id.tv_title, item.getName());
        x.image().bind((ImageView) helper.getView(R.id.tv_img), item.getImg());
    }
}
