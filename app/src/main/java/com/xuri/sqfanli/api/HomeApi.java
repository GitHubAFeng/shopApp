package com.xuri.sqfanli.api;

import com.google.gson.reflect.TypeToken;
import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.api.base.BaseApi;
import com.xuri.sqfanli.api.base.CallBackApi;
import com.xuri.sqfanli.api.base.CallBackDataApi;
import com.xuri.sqfanli.api.base.CallBackListApi;
import com.xuri.sqfanli.bean.Adv;
import com.xuri.sqfanli.bean.HotGoods;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.bean.ShopType;
import com.xuri.sqfanli.bean.User;
import com.xuri.sqfanli.util.SettingConfig;

import org.xutils.http.RequestParams;

import java.util.List;
import java.util.Map;

/**
 * Created by AFeng on 2018/4/26.
 */

public class HomeApi extends BaseApi {

    //轮播广告
    public String getAdvFromServer(int sex, CallBackApi callBackApi) {
        String key = "mainAdv/appGetMainAdvList" + sex;
        String result = SettingConfig.getInstance().getStringPreference(key, "");

        String url = Constant.host + "mainAdv/appGetMainAdvList";
        RequestParams params = new RequestParams(url);
        params.addParameter("mainadv.sex", sex);
        super.HttpPost(params, key, callBackApi);
        return result;
    }

    //按钮组
    public String getTypeFromServer(int sex, CallBackApi callBackApi) {
        String key = "shopType/appShopType" + sex;
        String result = SettingConfig.getInstance().getStringPreference(key, "");

        String url = Constant.host + "shopType/appShopType";
        RequestParams params = new RequestParams(url);
        params.addParameter("shopType.sex", sex);
        super.HttpPost(params, key, callBackApi);
        return result;
    }

    //商品
    public String getGoodsFromServer(int pageNum, int sex, CallBackApi callBackApi) {
        String key = "shop/appShop" + sex;
        String result = SettingConfig.getInstance().getStringPreference(key, "");

        String shoptype = sex == 1 ? "102" : "103"; //男102  女103
        String url = Constant.host + "shop/appShop";
        RequestParams params = new RequestParams(url);
        params.addParameter("page.curPage", pageNum);
        params.addParameter("shop.ctype", shoptype);
//        params.addParameter("shop.collectUserId", User.getInstance(context).getId());
        super.HttpPost(params, key, callBackApi);
        return result;

    }

    //热门
    public String getHotFromServer(int pageNum, int sex, CallBackApi callBackApi) {
//        String key = "shop/appShop";
        String key = "sys/appHotEventsList";
        String local_key = key + sex;
        String result = SettingConfig.getInstance().getStringPreference(local_key, "");
        String url = Constant.host + key;
        String shoptype = sex == 1 ? "102" : "103"; //男102  女103
        RequestParams params = new RequestParams(url);
        params.addParameter("page.curPage", pageNum);
        params.addParameter("shop.ctype", shoptype);
        super.HttpPost(params, local_key, callBackApi);
        return result;
    }

    //商品分类
    public String getGoodsTypeFromServer(int sex, CallBackApi callBackApi) {
        String key = "shopType/appShopType" + sex;
        String result = SettingConfig.getInstance().getStringPreference(key, "");

        String url = Constant.host + "shopType/appShopType";
        RequestParams params = new RequestParams(url);
        params.addParameter("shopType.sex", sex);
        super.HttpPost(params, key, callBackApi);
        return result;
    }


    //---------------------   版本2   ------------------------//


    /**
     * 获取首页热门推荐信息
     *
     * @param callBackDataApi
     * @return
     */
    public HotGoods getHotFromServer(CallBackDataApi callBackDataApi) {
        String key = "sys/appHotEventsList";
        String url = Constant.host + key;
        RequestParams params = new RequestParams(url);
        Object data = super.HttpPost(params, key, HotGoods.class, callBackDataApi);
        return (HotGoods) data;
    }


    /**
     * 获取商品轮播图片以及版本信息
     *
     * @param callBackDataApi
     * @return
     */
    public Adv getAdvFromServer(CallBackDataApi callBackDataApi) {
        String key = "mainAdv/appGetMainAdvList";
        String sex = User.getInstance().getSex(); //性别 1男2女
        String url = Constant.host + key;
        RequestParams params = new RequestParams(url);
        params.addParameter("mainadv.sex", sex); //男版女版
        Object data = super.HttpPost(params, key, Adv.class, callBackDataApi);
        return (Adv) data;
    }

    /**
     * 取商品分类
     *
     * @param classify        类型 1完整带子分类，2不带子分类
     * @param callBackListApi
     * @return
     */
    public List getTypeFromServer(int classify, CallBackListApi callBackListApi) {
        String key = "shopType/appShopType";
        String sex = User.getInstance().getSex(); //性别 1男2女
        String url = Constant.host + key;
        RequestParams params = new RequestParams(url);
        params.addParameter("shopType.sex", sex);
        params.addParameter("shopType.classify", classify);
        return super.HttpPost(params, key, new TypeToken<List<ShopType>>() {
        }.getType(), callBackListApi);
    }


    /**
     * 获取商品列表
     *
     * @param pageNum         页码
     * @param paramlist       筛选参数
     * @param callBackListApi
     * @return
     */
    public List getGoodsFromServer(int pageNum, String goodstype, List<Map<String, String>> paramlist, CallBackListApi callBackListApi) {
        String key = "shop/appShop";
        String sex = User.getInstance().getSex();
        int userid = User.getInstance().getId();
        String shoptype = goodstype.equals("") ? (sex.equals("1") ? "102" : "103") : goodstype; //男102  女103
        String url = Constant.host + "shop/appShop";
        RequestParams params = new RequestParams(url);
        params.addParameter("page.curPage", pageNum);
        params.addParameter("shop.ctype", shoptype);
        params.addParameter("shop.collectUserId", userid);
        params.addParameter("sex", sex);
        if (paramlist != null && paramlist.size() > 0) {
            String params_key, params_value;
            for (int i = 0; i < paramlist.size(); i++) {
                params_key = paramlist.get(i).get("name");
                params_value = paramlist.get(i).get("value");
                params.addParameter(params_key, params_value);
            }
        }
        return super.HttpPost(params, key, new TypeToken<List<Shop>>() {
        }.getType(), callBackListApi);
    }


}
