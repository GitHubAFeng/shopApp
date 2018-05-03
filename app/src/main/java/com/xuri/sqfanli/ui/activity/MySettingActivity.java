package com.xuri.sqfanli.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Explode;
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

        super.initAnim(AnimType.explode);
        my_setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                goToActivity(MainActivity.class, "setting-finish", 3, true);
//                goToMainActivity();
                goToActivityByAnim(MainActivity.class, true);
            }
        });

    }


}
