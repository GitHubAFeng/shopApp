package com.xuri.sqfanli.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;
import com.xuri.sqfanli.util.StatusBarUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by AFeng on 2018/5/4.
 */

public class CashOutActivity extends BaseFragmentActivity {

    @ViewInject(R.id.my_shouyi_goback)
    private ImageView my_shouyi_goback;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_shouyi;
    }

    @Override
    public void initView() {
//        StatusBarUtil.setColor(this, Color.parseColor("#FD3E5D"));
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
//        super.initAnim(AnimType.fade); //使用进场动画

        my_shouyi_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
