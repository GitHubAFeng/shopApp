package com.xuri.sqfanli.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

public abstract class BaseFragmentActivity extends FragmentActivity {
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
        MobclickAgent.onEvent(context, TAG);
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
        Toast.makeText(BaseFragmentActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}