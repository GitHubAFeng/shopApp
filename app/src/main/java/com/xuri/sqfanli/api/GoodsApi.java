package com.xuri.sqfanli.api;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.api.base.BaseApi;
import com.xuri.sqfanli.api.base.CallBackDataApi;
import com.xuri.sqfanli.api.base.CallBackListApi;
import com.xuri.sqfanli.bean.MessageVo;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.bean.ShopImg;
import com.xuri.sqfanli.bean.User;

import org.xutils.http.RequestParams;

import java.util.List;
import java.util.Map;

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


    public List getGoodsFromServer(int pageNum, String goodstype, String fenleiKeyword, List<Map<String, String>> paramlist, CallBackListApi callBackListApi) {
        String key = "shop/appShop";
        String sex = User.getInstance().getSex();
        int userid = User.getInstance().getId();
        String shoptype = goodstype.equals("") ? (sex.equals("1") ? "102" : "103") : goodstype; //男102  女103
        String url = Constant.host + "shop/appShop";
        RequestParams params = new RequestParams(url);
        params.addParameter("page.curPage", pageNum);
        params.addParameter("shop.collectUserId", userid);
        params.addParameter("sex", sex);
        if (fenleiKeyword == null || fenleiKeyword.length() == 0) {
            //没有关键词则根据分类来查询，有关键词则根据关键词来查询
            params.addParameter("shop.ctype", shoptype);
        } else {
            params.addParameter("shop.itemtitle", fenleiKeyword);
        }

        if (paramlist != null && paramlist.size() > 0) {
            String params_key, params_value;
            for (int i = 0; i < paramlist.size(); i++) {
                params_key = paramlist.get(i).get("name");
                params_value = paramlist.get(i).get("value");
                params.addParameter(params_key, params_value);
                Log.d("getGoodsList", "getGoodsList: " + params_key);
                Log.d("getGoodsList", "getGoodsList: " + params_value);
            }
        }
        return super.HttpPost(params, key, new TypeToken<List<Shop>>() {
        }.getType(), callBackListApi);
    }


}
