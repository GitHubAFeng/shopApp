package com.xuri.sqfanli.ui.fragment;

import android.os.Bundle;
import android.util.Log;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.ui.base.BaseFragment;

public class Fragment2 extends BaseFragment {
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_two;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Log.d(TAG, "initView: ");
    }


}
