package com.xuri.sqfanli;

import android.app.Application;

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

    }

}
