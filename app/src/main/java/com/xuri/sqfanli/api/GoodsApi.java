package com.xuri.sqfanli.api;

import com.google.gson.reflect.TypeToken;
import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.api.base.BaseApi;
import com.xuri.sqfanli.api.base.CallBackDataApi;
import com.xuri.sqfanli.api.base.CallBackListApi;
import com.xuri.sqfanli.bean.MessageVo;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.bean.ShopImg;

import org.xutils.http.RequestParams;

import java.util.List;

/**
 * Created by AFeng on 2018/5/8.
 */

public class GoodsApi extends BaseApi {

    //商品详情页面底部推荐列表商品
    public List getTuiJianFromServer(String itemid, CallBackListApi callBackListApi) {
        String key = "shop/appShopRecommend";
        String local_key = key + itemid;
        String url = Constant.host + key;
        RequestParams params = new RequestParams(url);
        params.addParameter("shop.itemid", itemid);
        return super.HttpPost(params, local_key, new TypeToken<List<Shop>>() {
        }.getType(), callBackListApi);
    }

    //商品详情页面(图片)
    public List getTuPingFromServer(String itemid, CallBackListApi callBackListApi) {
        String key = "shop/appShopImg";
        String local_key = key + itemid;
        String url = Constant.host + key;
        RequestParams params = new RequestParams(url);
        params.addParameter("shopImg.itemid", itemid);
        return super.HttpPost(params, local_key, new TypeToken<List<ShopImg>>() {
        }.getType(), callBackListApi);
    }

    //轮播图片
    public MessageVo getBannerFromServer(String itemid, CallBackDataApi callBackDataApi) {
        String key = "shop/appShopHeadImg";
        String local_key = key + itemid;
        String url = Constant.host + key;
        RequestParams params = new RequestParams(url);
        params.addParameter("shop.itemid", itemid);
        return super.HttpPost(params, local_key, MessageVo.class, callBackDataApi);
    }



}
