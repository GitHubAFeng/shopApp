package com.xuri.sqfanli.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutRes(), null);
            x.Ext.init(getActivity().getApplication());
            x.view().inject(this, view);
            initView();
        }
        //缓存的rootView需要判断是否已经被加过parent，
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            //如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            parent.removeView(view);
        }
        String class_ = this.getClass().getSimpleName();
        MobclickAgent.onEvent(context, class_);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        inflater = LayoutInflater.from(context);
        TAG = this.getClass().getSimpleName();
    }

    public abstract int getLayoutRes();

    public abstract void initView();

    public void toast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
