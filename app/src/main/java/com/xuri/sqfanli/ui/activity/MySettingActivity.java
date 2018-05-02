package com.xuri.sqfanli.ui.activity;

import android.view.View;
import android.widget.ImageView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by AFeng on 2018/5/2.
 */

public class MySettingActivity extends BaseFragmentActivity {

    @ViewInject(R.id.my_setting_back)
    private ImageView my_setting_back;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_setting;
    }

    @Override
    public void initView() {

        my_setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onBackPressed();
                finish();
//                goToActivity(MainActivity.class);
            }
        });

    }


}
