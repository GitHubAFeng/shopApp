package com.xuri.sqfanli.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by AFeng on 2018/5/9.
 * 图片加载工具类
 */

public class PicassoHelper {

    public static void showImageRound(Context context, ImageView imageView, String url) {
        if (url == null || url.equals("") || context == null || imageView == null) {
            return;
        }
        Picasso.with(context).load(url).transform(new PicassoTransform(20)).into(imageView);

    }


    public static void showImageByPicasso(Context context, ImageView imageView, String url) {
        if (url == null || url.equals("") || context == null || imageView == null) {
            return;
        }
        Picasso.with(context).load(url).into(imageView);
    }

    //保持宽高
    public static void showImageByPicassoFixXY(Context context, final ImageView imageView, String url) {
        if (url == null || url.equals("") || context == null || imageView == null) {
            return;
        }

        Transformation transformation = new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {

                int targetWidth = imageView.getWidth();
                if (source.getWidth() == 0) {
                    return source;
                }
                //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                if (targetHeight != 0 && targetWidth != 0) {
                    Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                    if (result != source) {
                        // Same bitmap is returned if sizes are the same
                        source.recycle();
                    }
                    return result;
                } else {
                    return source;
                }

            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };

        Picasso.with(context)
                .load(url)
                .transform(transformation)
                .into(imageView);

    }



}
