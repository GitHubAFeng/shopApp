package com.xuri.sqfanli.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

import java.io.Serializable;

public abstract class BaseFragmentActivity extends FragmentActivity {
    public Context context;
    protected String TAG;
    protected boolean mIsLoadedData = false;

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

    //region 使用转场动画
    protected void initAnim(AnimType type) {
        //        explode：从场景的中心移入或移出
        //        slide：从场景的边缘移入或移出
        //        fade：调整透明度产生渐变效果

        // 转场动画
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            switch (type) {
                case explode:
                    Explode explode = new Explode();
                    explode.setDuration(500);
                    explode.setMode(Visibility.MODE_IN);
                    getWindow().setExitTransition(explode);  //退出一个Activity的效果
                    getWindow().setEnterTransition(explode);   //进入一个Activity的效果
                    break;
                case slide:
                    Slide slide = new Slide();
                    slide.setDuration(500);
                    //设置为进入
                    slide.setMode(Visibility.MODE_IN);
                    //设置从右边进入
//                    slide.setSlideEdge(Gravity.RIGHT);
                    getWindow().setExitTransition(slide);  //退出一个Activity的效果
                    getWindow().setEnterTransition(slide);   //进入一个Activity的效果
                    break;
                case fade:
                    Fade fade = new Fade();
                    fade.setDuration(500);
                    fade.setMode(Visibility.MODE_IN);
                    getWindow().setExitTransition(fade);  //退出一个Activity的效果
                    getWindow().setEnterTransition(fade);   //进入一个Activity的效果
                    break;
            }
        }
    }

    protected enum AnimType {
        explode, slide, fade
    }

    //endregion

    //region 页面跳转

    /**
     * 传递数据对象到新启动的Activity
     *
     * @param target 要启动的Activity
     * @param key    键值
     * @param event  要传递的对象，必须Serializable化
     */
    public void goToActivity(Class<?> target, String key, Serializable event, boolean isFinish) {

        Intent intent = new Intent();
        intent.setClass(this, target);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, event);
        intent.putExtras(bundle);
        this.startActivity(intent);
        if (isFinish) {
            this.finish();
        }
    }

    public void goToActivity(Class<?> target, boolean isFinish) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        this.startActivity(intent);
        if (isFinish) {
            this.finish();
        }
    }


    public void goToActivityByAnim(Class<?> target, boolean isFinish) {

        // 启动普通转场动画 , 记得对方Activity要有动画设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
            Intent i2 = new Intent(this, target);
            startActivity(i2, oc2.toBundle());

        } else {
            startActivity(new Intent(this, target));
        }
        if (isFinish) {
            this.finish();
        }
    }

    /**
     * 接收 来自 源Activity 的数据对象
     *
     * @param key
     * @return
     */
    public Object getSerializDataByKey(String key) {

        try {
            Intent intent = this.getIntent();
            return intent.getSerializableExtra(key);
        } catch (NullPointerException e) {
            Log.e(TAG, "getSerializDataByKey: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "getSerializDataByKey: " + e.getMessage());
        }
        return null;
    }

    //endregion

    //region 数据处理

    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsLoadedData) {
            mIsLoadedData = true;
            onLazyLoadOnce();
        }
    }

    /**
     * 懒加载一次。如果只想在对用户可见时才加载数据，并且只加载一次数据，在子类中重写该方法
     */
    protected void onLazyLoadOnce() {
    }

    //endregion


    public void toast(String text) {
        Toast.makeText(BaseFragmentActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}