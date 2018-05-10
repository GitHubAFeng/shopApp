package com.xuri.sqfanli.api;

import com.google.gson.reflect.TypeToken;
import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.api.base.BaseApi;
import com.xuri.sqfanli.api.base.CallBackListApi;
import com.xuri.sqfanli.bean.Order;
import com.xuri.sqfanli.bean.User;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AFeng on 2018/5/10.
 * 订单
 */

public class OrderApi extends BaseApi {

    public ArrayList getOrderFromServer(String status, CallBackListApi callBackListApi) {
        String userid = User.getInstance().getId().toString();

        String key = "order/appList";
        String local_key = key + "_" + userid + "_" + status;
        String url = Constant.host + key;
        RequestParams params = new RequestParams(url);
        params.addParameter("order.userId", userid);
        params.addParameter("order.orderStatus", status);
        return (ArrayList) super.HttpPost(params, local_key, new TypeToken<List<Order>>() {
        }.getType(), callBackListApi);
    }


}
