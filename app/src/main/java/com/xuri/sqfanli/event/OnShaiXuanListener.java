package com.xuri.sqfanli.event;

import com.xuri.sqfanli.bean.ShaiXuanBean;

/**
 * Created by AFeng on 2018/5/14.
 */

public interface OnShaiXuanListener {
    void call(String eventType, ShaiXuanBean data);
}
