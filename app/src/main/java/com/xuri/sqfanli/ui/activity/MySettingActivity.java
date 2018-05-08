package com.xuri.sqfanli.ui.activity;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;
import com.xuri.sqfanli.util.StatusBarUtil;

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

        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white));
//        StatusBarUtil.setColor(this, Color.parseColor("#FFFFFF"));
//        StatusBarUtil.setTranslucentForImageView(this, 0, null);

        super.initAnim(AnimType.fade); //使用进场动画
        my_setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                goToActivity(MainActivity.class, "setting-finish", 3, true);
//                goToMainActivity();
//                goToActivityByAnim(MainActivity.class, true);
                finish();
            }
        });

    }


}
