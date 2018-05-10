package com.xuri.sqfanli.view;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoader;

import org.xutils.x;

/**
 * Created by AFeng on 2018/4/24.
 * 轮播图片加载器
 */

public class xImageLoader extends com.youth.banner.loader.ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        String url = (String) path;
        if (url == null || url.equals("") || context == null || imageView == null) {
            return;
        }
//        x.image().bind(imageView,url);
        Picasso.with(context).load(url).into(imageView);
    }

}
