package com.xuri.sqfanli;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.xuri.sqfanli.util.CommonMethod;
import com.xuri.sqfanli.view.LoadingDialog_logo;

import org.xutils.x;

public class MyAPP extends Application {
    private static MyAPP instance;

    public static MyAPP getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        instance = this;

//        initAlibc();

    }

    public static String getImei(Context context) {
        //TelephonyManager mTm = (TelephonyManager) context.getApplicationContext().getSystemService(context.getApplicationContext().TELEPHONY_SERVICE);
        //return mTm.getDeviceId();
        return CommonMethod.getIMEI(context);
    }

    /**
     * 获取application中指定的meta-data。本例中，调用方法时key就是UMENG_CHANNEL
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }

        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.get(key) + "";
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Log.i("", key + "渠道" + resultData);

        return resultData;
    }


    void initAlibc(){
        //电商SDK初始化
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                System.err.println("-------------淘宝初始化成功");
                LoadingDialog_logo.cancel();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(MyAPP.this, "初始化失败,错误码=" + code + " / 错误消息=" + msg, Toast.LENGTH_SHORT).show();
                System.err.println("-------------" + "淘宝初始化失败,错误码=" + code + " / 错误消息=" + msg);
            }
        });
    }

}
