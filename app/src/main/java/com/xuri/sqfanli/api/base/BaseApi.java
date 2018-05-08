package com.xuri.sqfanli.api.base;

import android.util.Log;

import com.google.gson.Gson;
import com.xuri.sqfanli.util.SettingConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by AFeng on 2018/4/26.
 */

public class BaseApi {


    public String HttpPost(RequestParams params, final String localDataKey, final CallBackApi callBackApi) {
        String local_result = SettingConfig.getInstance().getStringPreference(localDataKey, "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SettingConfig.getInstance().setStringPreference(localDataKey, result);
                callBackApi.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.err.println("--------------onError:" + ex.getMessage());
                Log.e("HttpPost", "onError: " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                callBackApi.onFinished();
            }
        });

        return local_result;
    }

    public <T> T HttpPost(RequestParams params, final String localDataKey, final Class<T> t, final CallBackDataApi callBackDataApi) {
        String local_result = SettingConfig.getInstance().getStringPreference(localDataKey, "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SettingConfig.getInstance().setStringPreference(localDataKey, result);
                T target = new Gson().fromJson(result, t);
                callBackDataApi.onSuccess(target);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.err.println("--------------onError:" + ex.getMessage());
                Log.e("HttpPost", "onError: " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                callBackDataApi.onFinished();
            }
        });

        return new Gson().fromJson(local_result, t);
    }


    public List HttpPost(RequestParams params, final String localDataKey, final Type typeOfT, final CallBackListApi callBackListApi) {
        String local_result = SettingConfig.getInstance().getStringPreference(localDataKey, "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SettingConfig.getInstance().setStringPreference(localDataKey, result);
                List target = new Gson().fromJson(result, typeOfT);
                callBackListApi.onSuccess(target);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.err.println("--------------onError:" + ex.getMessage());
                Log.e("HttpPost", "onError: " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                callBackListApi.onFinished();
            }
        });

        return new Gson().fromJson(local_result, typeOfT);
    }


}
