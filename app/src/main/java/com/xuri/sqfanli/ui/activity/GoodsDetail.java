package com.xuri.sqfanli.ui.activity;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.api.GoodsApi;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;

/**
 * Created by AFeng on 2018/5/8.
 */

public class GoodsDetail extends BaseFragmentActivity {

    GoodsApi goodsApi = new GoodsApi();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initView() {

//        Shop shop = goodsApi.getTuPingFromServer()

    }
}
