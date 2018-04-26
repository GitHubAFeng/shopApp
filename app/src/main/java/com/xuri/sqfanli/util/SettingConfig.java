package com.xuri.sqfanli.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingConfig {

    public static final String SETTING_FILE = "settings";

    private static SettingConfig sInstance;
    private SharedPreferences mPreferences;
    private Context mContext;

    public static SettingConfig getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SettingConfig(context.getApplicationContext());
        }
        return sInstance;
    }

    private SettingConfig(Context context) {
        mContext = context;
        mPreferences = mContext.getSharedPreferences(SETTING_FILE,
                Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor() {
        return mPreferences.edit();
    }

    public String getStringPreference(String key, String defaultValue) {
        return mPreferences.getString(key, defaultValue);
    }

    public float getStringPreference(String key, float defaultValue) {
        return mPreferences.getFloat(key, defaultValue);
    }

    public void setStringPreference(String key, String value) {
        getEditor().putString(key, value).commit();
    }

    public void setStringPreference(String key, float value) {
        getEditor().putFloat(key, value).commit();
    }

}
