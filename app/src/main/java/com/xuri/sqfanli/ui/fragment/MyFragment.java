package com.xuri.sqfanli.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.ui.activity.CashOutActivity;
import com.xuri.sqfanli.ui.activity.MySettingActivity;
import com.xuri.sqfanli.ui.base.BaseFragment;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by AFeng on 2018/4/27.
 */

public class MyFragment extends BaseFragment {

    @ViewInject(R.id.my_setting)
    private ImageView my_setting;
    @ViewInject(R.id.my_tixian)
    private TextView my_tixian;


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

        my_tixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivityByAnim(CashOutActivity.class, false);
            }
        });


    }


}
