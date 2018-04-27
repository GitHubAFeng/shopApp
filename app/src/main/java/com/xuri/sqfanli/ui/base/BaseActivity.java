package com.xuri.sqfanli.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

public abstract class BaseActivity extends Activity {
    public Context context;
    protected String TAG;

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

    public void toast(String text) {
        Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}