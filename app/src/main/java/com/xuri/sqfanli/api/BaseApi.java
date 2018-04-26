package com.xuri.sqfanli.api;

import android.util.Log;

import com.xuri.sqfanli.MyAPP;
import com.xuri.sqfanli.callback.CallBackApi;
import com.xuri.sqfanli.util.SettingConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by AFeng on 2018/4/26.
 */

public class BaseApi {

    public void HttpPost(RequestParams params, String localDataKey, CallBackApi callBackApi) {
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SettingConfig.getInstance(MyAPP.getInstance()).setStringPreference(localDataKey, result);
                callBackApi.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.err.println("--------------onError:" + ex.getMessage());
                Log.e("getAdvFromServer", "onError: " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                callBackApi.onFinished();
            }
        });
    }
}
