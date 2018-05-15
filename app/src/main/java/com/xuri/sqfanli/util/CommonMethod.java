package com.xuri.sqfanli.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.event.Callback_string;

import org.xutils.image.ImageOptions;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * Created by AFeng on 2018/5/14.
 */

public class CommonMethod {


    public static ImageOptions itemImageOptions;

    public static ImageOptions getItemImageOptions() {
        if (itemImageOptions == null) {
            ImageOptions.Builder builder = new ImageOptions.Builder();
            builder.setSize(300, 300);
            itemImageOptions = builder.build();
        }

        return itemImageOptions;
    }

    public static void showImage_picasso(Context context, ImageView imageView, String url) {
        if (url == null || url.equals("") || context == null || imageView == null) {
            return;
        }
        Picasso.with(context).load(url).into(imageView);

    }

    public static void showImage_picasso(Context context, ImageView imageView, String url, final Callback_string callback) {
        if (url == null || url.equals("") || context == null || imageView == null) {
            return;
        }
        Picasso.with(context).load(url).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                callback.call("onSuccess");
            }

            @Override
            public void onError() {
                callback.call("onError");
            }
        });

    }

    public static void showImage_picasso_baochikuangao(Context context, final ImageView imageView, String url) {
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

    public static void showImage_picasso_withRaduis(Context context, ImageView imageView, String url, final int radius) {
        if (url == null || url.equals("") || context == null || imageView == null) {
            return;
        }

        Transformation transformation = new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {
                int width = source.getWidth();
                int height = source.getHeight();
                //画板
                Bitmap bitmap = Bitmap.createBitmap(width, height, source.getConfig());
                Paint paint = new Paint();
                Canvas canvas = new Canvas(bitmap);//创建同尺寸的画布
                paint.setAntiAlias(true);//画笔抗锯齿
                paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                //画圆角背景
                RectF rectF = new RectF(new Rect(0, 0, width, height));//赋值
                canvas.drawRoundRect(rectF, radius, radius, paint);//画圆角矩形
                //
                paint.setFilterBitmap(true);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(source, 0, 0, paint);
                source.recycle();//释放

                return bitmap;
            }

            @Override
            public String key() {
                return "round : radius = " + radius;
            }
        };

        Picasso.with(context).load(url).transform(transformation).into(imageView);

    }


    /**
     * 用于量取header中，综合、价格、销量，筛选栏距离顶部的Y轴距离
     *
     * @param headerView 顶部布局
     * @return 距离
     */
    public static int getDistanceY(View headerView) {
        LinearLayout shaixuanLayout = headerView.findViewById(R.id.shaixuan_root_layout);
        int[] location = new int[2];
        shaixuanLayout.getLocationOnScreen(location);
        int y = location[1];
        return y;
    }

    //显示商品标题和商城logo
    public static void xianshishangpinbiaotiheshangchenglogo(Context context, TextView tv, String shoptype, String title) {
        int lineHeight = tv.getLineHeight();
        ImageSpan imgSpan = null;
        if (shoptype != null && shoptype.equals("B")) {//天猫
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.xiaologo_tianmao);
            Bitmap b = Bitmap.createScaledBitmap(bitmap, lineHeight, lineHeight, true);
            imgSpan = new ImageSpan(context, b);
        } else {//淘宝
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.xiaologo_taobao);
            Bitmap b = Bitmap.createScaledBitmap(bitmap, lineHeight, lineHeight, true);
            imgSpan = new ImageSpan(context, b);
        }

        String kongge = " ";
        if (title.startsWith("【") == false) {
            kongge = "  ";
        }
        SpannableString spannableString = new SpannableString(kongge + title);
        spannableString.setSpan(imgSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannableString);
    }

    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imeiCode = null;

        try {
            imeiCode = manager.getDeviceId();
        } catch (Exception e) {
            imeiCode = getMac();
        }

        return imeiCode;
    }

    public static String getMac() {
        String str = "";
        String macSerial = "";

        try {
            Process e = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(e.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            while (null != str) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();
                    break;
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }
        return macSerial;
    }

    public static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    public static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];

        for (int readLength = reader.read(buffer); readLength >= 0; readLength = reader.read(buffer)) {
            builder.append(buffer, 0, readLength);
        }

        return builder.toString();
    }



}
