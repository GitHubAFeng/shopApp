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
 * 网络请求基类
 */

public class BaseApi {


    /**
     * POST请求网络并返回JSON字符串，自动本地缓存
     * @param params 网络请求参数
     * @param localDataKey 本地缓存KEY
     * @param callBackApi 网络请求数据回调
     * @return 返回本地缓存数据
     */
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

    /**
     * POST请求网络返回JSON字符串
     * @param params 网络请求参数
     * @param callBackApi 网络请求数据回调
     */
    public void HttpPost(RequestParams params, final CallBackApi callBackApi) {
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
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
    }


    /**
     * POST请求网络并返回bean数据实体，自动本地缓存
     * @param params 网络请求参数
     * @param localDataKey 本地缓存KEY
     * @param t bean数据实体类
     * @param callBackDataApi 网络请求数据回调
     * @param <T> bean数据实体
     * @return 返回本地缓存数据
     */
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

    /**
     * POST请求网络并返回bean数据实体
     * @param params 网络请求参数
     * @param t bean数据实体类
     * @param callBackDataApi 网络请求数据回调
     * @param <T> bean数据实体
     */
    public <T> void HttpPost(RequestParams params, final Class<T> t, final CallBackDataApi callBackDataApi) {
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
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
    }


    /**
     * POST请求网络并返回bean数据实体列表list，自动本地缓存
     * @param params 网络请求参数
     * @param localDataKey 本地缓存KEY
     * @param typeOfT bean数据实体列表list
     * @param callBackListApi 网络请求数据回调
     * @return 返回本地缓存数据
     */
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

    /**
     * POST请求网络并返回bean数据实体列表list
     * @param params 网络请求参数
     * @param typeOfT bean数据实体列表list
     * @param callBackListApi 网络请求数据回调
     */
    public void HttpPost(RequestParams params, final Type typeOfT, final CallBackListApi callBackListApi) {

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
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

    }


}
