package com.xuri.sqfanli.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

import java.io.Serializable;

public abstract class BaseActivity extends Activity {
    public Context context;
    protected String TAG;
    protected boolean mIsLoadedData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(getLayoutRes());
        TAG = this.getClass().getSimpleName();
        x.view().inject(this);
        initView();
        MobclickAgent.onEvent(context, TAG); //友盟用户分析
    }

    /**
     * 设置布局资源
     */
    public abstract int getLayoutRes();

    /**
     * 初始化界面
     */
    public abstract void initView();

    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsLoadedData) {
            mIsLoadedData = true;
            onLazyLoadOnce();
        }
    }

    /**
     * 懒加载一次。如果只想在对用户可见时才加载数据，并且只加载一次数据，在子类中重写该方法
     */
    protected void onLazyLoadOnce() {
    }

    public void goToActivity(Class<?> target) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        this.startActivity(intent);
    }

    /**
     * 传递数据对象到新启动的Activity
     *
     * @param target 要启动的Activity
     * @param key    键值
     * @param event  要传递的对象，必须Serializable化
     */
    public void goToActivity(Class<?> target, String key, Serializable event) {

        Intent intent = new Intent();
        intent.setClass(this, target);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, event);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    /**
     * 接收 来自 源Activity 的数据对象
     *
     * @param key
     * @return
     */
    public Object getSerializDataByKey(String key) {

        try {
            Intent intent = this.getIntent();
            return intent.getSerializableExtra(key);
        } catch (NullPointerException e) {
            Log.e(TAG, "getSerializDataByKey: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "getSerializDataByKey: " + e.getMessage());
        }
        return null;
    }

    public void toast(String text) {
        Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}