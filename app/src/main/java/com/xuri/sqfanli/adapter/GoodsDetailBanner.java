package com.xuri.sqfanli.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xuri.sqfanli.util.PicassoHelper;

/**
 * Created by AFeng on 2018/5/14.
 */

public class GoodsDetailBanner extends PagerAdapter {

    private Context context;
    // 界面列表
    String imgUrls[];
    private int mChildCount = 0;

    public GoodsDetailBanner(Context context) {
        this.context = context;
    }

    public void setData(String imgUrls[]) {
        this.imgUrls = imgUrls;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(views.get(position));
    }

    @Override
    public int getCount() {
        if (imgUrls == null) {
            return 0;
        }
        return imgUrls.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView iv = new ImageView(context);
        try {
            PicassoHelper.showImageByPicassoFixXY(context, iv, imgUrls[position]);

            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);// 设置铺满

//            iv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.setClass(context, A_webView_x5.class);
//                    intent.putExtra("url", adv.getUrl());
//                    intent.putExtra("title", adv.getTitle());
//                    context.startActivity(intent);
//                    MobclickAgent.onEvent(context, "dianjilunbo");
//                }
//            });

            container.addView(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return iv;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
