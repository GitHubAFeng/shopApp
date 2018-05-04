package com.xuri.sqfanli.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

public abstract class BaseFragment extends Fragment {
    protected String TAG;
    public Context context;
    public View view;
    public LayoutInflater inflater;
    protected boolean mIsLoadedData = false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        context = getActivity();
        inflater = LayoutInflater.from(context);
        TAG = this.getClass().getSimpleName();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutRes(), null);
            x.Ext.init(getActivity().getApplication());
            x.view().inject(this, view);
            initView(savedInstanceState);
        } else {
            //缓存的rootView需要判断是否已经被加过parent，
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                //如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
                parent.removeView(view);
            }
        }

        String class_ = this.getClass().getSimpleName();
        MobclickAgent.onEvent(context, class_);

        return view;
    }


    public abstract int getLayoutRes();

    public abstract void initView(Bundle savedInstanceState);

    //region 数据处理

    /**
     * 实现懒加载，可见时才加载数据
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed()) {
            handleOnVisibilityChangedToUser(isVisibleToUser);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            handleOnVisibilityChangedToUser(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            handleOnVisibilityChangedToUser(false);
        }
    }

    /**
     * 处理对用户是否可见
     *
     * @param isVisibleToUser
     */
    private void handleOnVisibilityChangedToUser(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            // 对用户可见
            if (!mIsLoadedData) {
                mIsLoadedData = true;
                onLazyLoadOnce();
            }
            onVisibleToUser();
        } else {
            // 对用户不可见
            onInvisibleToUser();
        }
    }

    /**
     * 懒加载一次。如果只想在对用户可见时才加载数据，并且只加载一次数据，在子类中重写该方法
     */
    protected void onLazyLoadOnce() {

    }

    /**
     * 对用户可见时触发该方法。如果只想在对用户可见时才加载数据，在子类中重写该方法
     */
    protected void onVisibleToUser() {

    }

    /**
     * 对用户不可见时触发该方法
     */
    protected void onInvisibleToUser() {

    }

    //endregion

    //region 页面跳转

    public void goToActivity(Class<?> target, boolean isFinish) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), target);
        this.startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
    }

    public void goToActivityByAnim(Class<?> target, boolean isFinish) {

        // 启动普通转场动画 , 记得对方Activity要有动画设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
            Intent i2 = new Intent(getActivity(), target);
            startActivity(i2, oc2.toBundle());

        } else {
            startActivity(new Intent(getActivity(), target));
        }
        if (isFinish) {
            getActivity().finish();
        }
    }

    //endregion


    public void toast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
