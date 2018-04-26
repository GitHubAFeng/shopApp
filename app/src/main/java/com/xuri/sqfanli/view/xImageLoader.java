package com.xuri.sqfanli.view;

import android.content.Context;
import android.widget.ImageView;
import com.youth.banner.loader.ImageLoader;
import org.xutils.x;

/**
 * Created by Administrator on 2018/4/24.
 */

public class xImageLoader extends com.youth.banner.loader.ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        x.image().bind(imageView,(String) path);
    }

}
