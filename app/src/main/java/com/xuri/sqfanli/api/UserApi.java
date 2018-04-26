package com.xuri.sqfanli.api;

import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.callback.CallBackApi;

import org.xutils.http.RequestParams;

/**
 * Created by AFeng on 2018/4/26.
 */

public class UserApi extends BaseApi {

    public void updateUserByUserName(String username, CallBackApi callBackApi) {
        String key = "user/appUpdateUser";

        String url = Constant.host + "user/appUpdateUser";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("user.uname", username);
        super.HttpPost(params, key, callBackApi);
    }


}
