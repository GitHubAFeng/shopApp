package com.xuri.sqfanli.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.MessageVo;
import com.xuri.sqfanli.ui.base.BaseFragmentActivity;
import com.xuri.sqfanli.util.TaoBaoTools;
import com.xuri.sqfanli.view.LoadingDialog_logo_1;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * @author 王东
 * @Title: 爱淘宝
 */

public class A_aitaobao extends BaseFragmentActivity {
    private LinearLayout ll_back;
    private WebView mWebView;
    private TextView tv;
    private TextView tv_bottom;
    private int step = 0;

    @Override
    public int getLayoutRes() {
        return R.layout.shangpin_web;
    }

    public void initView() {
        String url = getIntent().getStringExtra("url").toString();

        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        tv = (TextView) findViewById(R.id.title_top_name);
        tv.setText("爱淘宝");
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);

        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        WebViewClient mWebViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                Log.e("wd", url);
                if (url.contains("tbopen:/")) {
                    return true;
                }
                if (url.startsWith("https://h5.m.taobao.com") || url.startsWith("https://detail.m.tmall.com")) {
                    String itemid = "";
                    if (url.contains("?id=")) {
                        itemid = url.substring(url.indexOf("?id=") + 4);
                        itemid = itemid.substring(0, itemid.indexOf("&"));
                    } else if (url.contains("?itemId=")) {
                        itemid = itemid.substring(itemid.indexOf("?itemId=") + 8);
                        if (itemid.contains("&")) {
                            itemid = itemid.substring(0, itemid.indexOf("&"));
                        }
                    }

                    Message msg = Message.obtain();
                    msg.what = 1;
                    handler.sendMessage(msg);

                    Log.e("wd", "走查询优惠券itemid=" + itemid);
                    RequestParams params = new RequestParams(Constant.host + "shop/appShopCoupon");
                    params.addParameter("shop.itemid", itemid);
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            MessageVo msg = new Gson().fromJson(result, MessageVo.class);
                            String coup = msg.getMessage();
                            Log.e("wd", "优惠券结果==" + coup);
                            if (coup != null && coup.length() > 0) {
                                TaoBaoTools.getIns(context).showPage(A_aitaobao.this, coup);
                            } else {
                                Toast.makeText(context, "该商品不支持余额淘哦~", Toast.LENGTH_SHORT).show();
                                //view.loadUrl(url);
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                        }

                        @Override
                        public void onFinished() {
                        }
                    });
                    return true;
                }
                if (url.startsWith("https://uland.taobao.com/coupon")) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    TaoBaoTools.getIns(context).showPage(A_aitaobao.this, url);
                    return true;
                }
                return false;

//                if(url.startsWith("https://h5.m.taobao.com") || url.startsWith("https://detail.m.tmall.com")) {
//                    if(url.contains(TaoBaoTools.TaoBaoKeId)) {
//                        tv_bottom.setVisibility(View.VISIBLE);
//                    }
//
//                    //说明不是先跳转优惠券，需要通过接口获取优惠券后跳转
//                    if(step==0) {
//                        String itemid = "";
//                        if(url.contains("?id=")) {
//                            itemid = url.substring(url.indexOf("?id=") + 4);
//                            itemid = itemid.substring(0, itemid.indexOf("&"));
//                        } else if (url.contains("?itemId=")) {
//                            itemid = itemid.substring(itemid.indexOf("?itemId=") + 8);
//                            if(itemid.contains("&")) {
//                                itemid = itemid.substring(0, itemid.indexOf("&"));
//                            }
//                        }
//
//                        Message msg = Message.obtain();
//                        msg.what = 1;
//                        handler.sendMessage(msg);
//
//                        Log.e("wd", "走查询优惠券itemid="+itemid);
//                        RequestParams params = new RequestParams(Constant.host + "shop/appShopCoupon");
//                        params.addParameter("shop.itemid", itemid);
//                        x.http().get(params, new Callback.CommonCallback<String>() {
//                            @Override
//                            public void onSuccess(String result) {
//                                MessageVo msg = new Gson().fromJson(result, MessageVo.class);
//                                String coup = msg.getMessage();
//                                Log.e("wd", "优惠券结果==" + coup);
//                                if(coup!=null && coup.length()>0) {
//                                    step++;
//                                    view.loadUrl(coup);
//                                } else {
//                                    step++;
//                                    view.loadUrl(url);
//                                }
//                            }
//
//                            @Override
//                            public void onError(Throwable ex, boolean isOnCallback) { }
//
//                            @Override
//                            public void onCancelled(CancelledException cex) { }
//
//                            @Override
//                            public void onFinished() { }
//                        });
//                        return true;
//                    }
//                } else {
//                    tv_bottom.setVisibility(View.GONE);
//                }
//                if(url.startsWith("https://uland.taobao.com/coupon")) {
//                    step=1;
//                }
//                if(url.startsWith("https://ai.m.taobao.com")) {
//                    step=0;
//                }
//                Log.e("wd", "步骤==" + step);
//                view.loadUrl(url);
//                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //只有在优惠券页面才需要抓html
//                if(url.contains("h5.m.taobao.com") || url.contains("detail.m.tmall.com")) {
//                    LoadingDialog_logo_1.close();
//                    mWebView.setVisibility(View.VISIBLE);
//                }
            }
        };
        mWebView.setWebViewClient(mWebViewClient);
        WebChromeClient mWebChromeClient = new WebChromeClient() {
        };
        mWebView.setWebChromeClient(mWebChromeClient);
        TaoBaoTools.getIns(context).show(url, this, mWebView, mWebViewClient, mWebChromeClient);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step = 0;
                tv_bottom.setVisibility(View.GONE);
                String url = mWebView.getUrl();
                if (!url.startsWith("https://ai.m.taobao.com")) {
                    mWebView.goBack();
                } else {
                    A_aitaobao.this.finish();
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    LoadingDialog_logo_1.show(A_aitaobao.this);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        LoadingDialog_logo_1.close();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            step = 0;
            tv_bottom.setVisibility(View.GONE);
            String url = mWebView.getUrl();
            if (!url.startsWith("https://ai.m.taobao.com")) {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
