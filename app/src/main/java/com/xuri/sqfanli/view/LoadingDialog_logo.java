package com.xuri.sqfanli.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.xuri.sqfanli.R;


/**
 * @Title: ‘加载中’的弹出，图案为吉祥物
 * @Description:
 * @author 何明洋
 */
public class LoadingDialog_logo {
    static PopupWindow pop;

    public static void show(Context context, View parent) {
        pop = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);//这里设置宽高
        View view = LayoutInflater.from(context).inflate(R.layout.item_loading, null);
        ImageView iv_logo = (ImageView) view.findViewById(R.id.iv_logo);
        pop.setContentView(view);
        pop.setOutsideTouchable(false);//点击pop外部的空间，可以取消pop
        pop.setBackgroundDrawable(new ColorDrawable(-(000000)));//需要这行，才能保证setOutsideTouchable生效
        pop.showAtLocation(parent, Gravity.CENTER, 0, 0);

//        RotateAnimation rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        LinearInterpolator lin = new LinearInterpolator();
//        rotate.setInterpolator(lin);
//        rotate.setDuration(1000);//设置动画持续时间
//        rotate.setRepeatCount(-1);//设置重复次数
//        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//        rotate.setStartOffset(10);//执行前的等待时间
//        iv_logo.setAnimation(rotate);
        iv_logo.setBackgroundResource(R.drawable.loading_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv_logo.getBackground();
        animationDrawable.start();
    }

    public static void cancel() {
        if (pop != null) {
            pop.dismiss();

        }
    }
}
