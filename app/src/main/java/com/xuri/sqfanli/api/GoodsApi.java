package com.xuri.sqfanli.api;

import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.api.base.BaseApi;
import com.xuri.sqfanli.api.base.CallBackApi;
import com.xuri.sqfanli.api.base.CallBackListApi;

import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by AFeng on 2018/5/8.
 */

public class GoodsApi extends BaseApi {

    //商品详情页面底部推荐列表商品
    public List getTuiJianFromServer(int itemid, Type typeOfT, CallBackListApi callBackListApi) {
        String key = "shop/appShopRecommend";
        String local_key = key + itemid;
        String url = Constant.host + key;
        RequestParams params = new RequestParams(url);
        params.addParameter("shop.itemid", itemid);
        return super.HttpPost(params, local_key, typeOfT, callBackListApi);
    }

    //商品详情页面轮播图
    public List getTuPingFromServer(int itemid, Type typeOfT, CallBackListApi callBackListApi) {
        String key = "shop/appShopRecommend";
        String local_key = key + itemid;
        String url = Constant.host + key;
        RequestParams params = new RequestParams(url);
        params.addParameter("shop.itemid", itemid);
        return super.HttpPost(params, local_key, typeOfT, callBackListApi);
    }


}
