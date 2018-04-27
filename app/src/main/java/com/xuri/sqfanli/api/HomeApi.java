package com.xuri.sqfanli.api;

import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.MyAPP;
import com.xuri.sqfanli.api.base.BaseApi;
import com.xuri.sqfanli.api.base.CallBackApi;
import com.xuri.sqfanli.util.SettingConfig;

import org.xutils.http.RequestParams;

/**
 * Created by AFeng on 2018/4/26.
 */

public class HomeApi extends BaseApi {

    public String getAdvFromServer(int sex, CallBackApi callBackApi) {
        String key = "mainAdv/appGetMainAdvList" + sex;
        String result = SettingConfig.getInstance().getStringPreference(key, "");

        String url = Constant.host + "mainAdv/appGetMainAdvList";
        RequestParams params = new RequestParams(url);
        params.addParameter("mainadv.sex", sex);
        super.HttpPost(params, key, callBackApi);
        return result;
    }

    public String getTypeFromServer(int sex, CallBackApi callBackApi) {
        String key = "shopType/appShopType" + sex;
        String result = SettingConfig.getInstance().getStringPreference(key, "");

        String url = Constant.host + "shopType/appShopType";
        RequestParams params = new RequestParams(url);
        params.addParameter("shopType.sex", sex);
        super.HttpPost(params, key, callBackApi);
        return result;
    }


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


    public String getHotFromServer(int sex, CallBackApi callBackApi) {
        String key = "sys/appHotEventsList" + sex;
        String result = SettingConfig.getInstance().getStringPreference(key, "");

        String url = Constant.host + "sys/appHotEventsList";
        RequestParams params = new RequestParams(url);
        super.HttpPost(params, key, callBackApi);
        return result;

    }


    public String getGoodsTypeFromServer(int sex, CallBackApi callBackApi) {
        String key = "shopType/appShopType" + sex;
        String result = SettingConfig.getInstance().getStringPreference(key, "");

        String url = Constant.host + "shopType/appShopType";
        RequestParams params = new RequestParams(url);
        params.addParameter("shopType.sex", sex);
        super.HttpPost(params, key, callBackApi);
        return result;
    }


}
