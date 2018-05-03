package com.xuri.sqfanli.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.transition.Explode;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.ui.activity.MainActivity;
import com.xuri.sqfanli.ui.activity.MySettingActivity;
import com.xuri.sqfanli.ui.base.BaseFragment;
import com.xuri.sqfanli.util.StatusBarUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by AFeng on 2018/4/27.
 */

public class MyFragment extends BaseFragment {

    @ViewInject(R.id.my_setting)
    private ImageView my_setting;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


        my_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivityByAnim(MySettingActivity.class, false);

            }
        });

    }


}
