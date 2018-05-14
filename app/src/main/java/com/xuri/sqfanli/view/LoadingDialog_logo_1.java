package com.xuri.sqfanli.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuri.sqfanli.R;


/**
 * @author 何明洋
 * @Title: ‘加载中’的弹出，文字：你负责花钱，我复制存
 * @Description:
 */
public class LoadingDialog_logo_1 {
    public static Dialog dialog;
    public static boolean run = false;
    public static int sss = 500;

    public static void show(Context ctx) {

        View view = LayoutInflater.from(ctx.getApplicationContext()).inflate(R.layout.loading_dialog_2, null);

        dialog = new Dialog(ctx, R.style.alibc_auth_dialog);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        ImageView animationIV = view.findViewById(R.id.seach_go);
        run = true;
        bianda(animationIV);
        dialog.show();
    }

    /**
     * 购物车加载框
     *
     * @param ctx
     */
    public static void show_bike(Context ctx) {

        View view = LayoutInflater.from(ctx.getApplicationContext()).inflate(R.layout.loading_dialog_2, null);

        dialog = new Dialog(ctx, R.style.alibc_auth_dialog);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        TextView title = view.findViewById(R.id.title);
        title.setText("余额淘正在标记该件商品");
        view.findViewById(R.id.logo_taobao).setVisibility(View.GONE);
        ImageView animationIV = view.findViewById(R.id.seach_go);
        run = true;
        bianda(animationIV);
        dialog.show();
    }


    public static void bianda(final ImageView animationIV) {

        AnimationSet animationSet = new AnimationSet(true);
            /*
                参数解释：
                    第一个参数：X轴水平缩放起始位置的大小（fromX）。1代表正常大小
                    第二个参数：X轴水平缩放完了之后（toX）的大小，0代表完全消失了
                    第三个参数：Y轴垂直缩放起始时的大小（fromY）
                    第四个参数：Y轴垂直缩放结束后的大小（toY）
                    第五个参数：pivotXType为动画在X轴相对于物件位置类型
                    第六个参数：pivotXValue为动画相对于物件的X坐标的开始位置
                    第七个参数：pivotXType为动画在Y轴相对于物件位置类型
                    第八个参数：pivotYValue为动画相对于物件的Y坐标的开始位置

                   （第五个参数，第六个参数），（第七个参数,第八个参数）是用来指定缩放的中心点
                    0.5f代表从中心缩放
             */
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1, 0.7f, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //3秒完成动画
        scaleAnimation.setDuration(1000);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(scaleAnimation);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (run) {

                    bianxiao(animationIV);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        animationIV.startAnimation(animationSet);
    }

    public static void bianxiao(final ImageView animationIV) {
        AnimationSet animationSet = new AnimationSet(true);
            /*
                参数解释：
                    第一个参数：X轴水平缩放起始位置的大小（fromX）。1代表正常大小
                    第二个参数：X轴水平缩放完了之后（toX）的大小，0代表完全消失了
                    第三个参数：Y轴垂直缩放起始时的大小（fromY）
                    第四个参数：Y轴垂直缩放结束后的大小（toY）
                    第五个参数：pivotXType为动画在X轴相对于物件位置类型
                    第六个参数：pivotXValue为动画相对于物件的X坐标的开始位置
                    第七个参数：pivotXType为动画在Y轴相对于物件位置类型
                    第八个参数：pivotYValue为动画相对于物件的Y坐标的开始位置

                   （第五个参数，第六个参数），（第七个参数,第八个参数）是用来指定缩放的中心点
                    0.5f代表从中心缩放
             */
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.7f, 1, 0.7f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //3秒完成动画
        scaleAnimation.setDuration(1000);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(scaleAnimation);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (run) {

                    bianda(animationIV);

                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationIV.startAnimation(animationSet);
    }


    public static void close() {
        if (dialog != null) {
            dialog.dismiss();
            run = false;
        }
    }
}