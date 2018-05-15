package com.xuri.sqfanli.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.ResultType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcAddCartPage;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.bean.User;
import com.xuri.sqfanli.event.Callback_string;

import org.apache.commons.io.FileUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 何明洋
 * @Title: 淘宝相关的工具类
 * @Description:
 */

public class TaoBaoTools {
    public static String taobaoUrl = "https://taobao.com/";
    //public static String TaoBaoKeId = "mm_13835710_2883245_9952338";//淘宝客广告位的id
    public static String TaoBaoKeId = "mm_13835710_40934449_169286609";
    public static String cookie = "";

    public static TaoBaoTools ins;
    private static AlibcShowParams alibcShowParams;
    private static Map<String, String> exParams;
    private static Activity activity;

    private static AlibcTradeCallback callback = new AlibcTradeCallback() {
        @Override
        public void onTradeSuccess(TradeResult tradeResult) {
            //当addCartPage加购成功和其他page支付成功的时候会回调

            if (tradeResult.resultType.equals(ResultType.TYPECART)) {
                //加购物车成功
                Toast.makeText(activity, "加购物车成功", Toast.LENGTH_SHORT).show();
            } else if (tradeResult.resultType.equals(ResultType.TYPEPAY)) {
                System.err.println("---------------tradeResult.payResult.paySuccessOrders:" + tradeResult.payResult.paySuccessOrders);
                List orderList = tradeResult.payResult.paySuccessOrders;
                if (orderList != null && orderList.size() > 0) {
                    String ordStr = "";
                    for (int i = 0; i < orderList.size(); i++) {
                        ordStr += orderList.get(i);
                    }

                    String url = Constant.host + "order/appSubOrderId";
                    RequestParams params = new RequestParams(url);
                    params.addParameter("order.orderId", ordStr);
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
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
                    Constant.newOrder = true;
                }
            }
            System.err.println("---------------tradeResult.payResult.paySuccessOrders:" + tradeResult.payResult.paySuccessOrders);
            System.err.println("---------------tradeResult.payResult.payFailedOrders:" + tradeResult.payResult.payFailedOrders);


        }

        @Override
        public void onFailure(int errCode, String errMsg) {
            System.err.println("---------------onFailure:" + "电商SDK出错,错误码=" + errCode + " / 错误消息=" + errMsg);
            Toast.makeText(activity, "电商SDK出错,错误码=" + errCode + " / 错误消息=" + errMsg, Toast.LENGTH_SHORT).show();
        }
    };

    public static TaoBaoTools getIns(Context context) {
        ins = new TaoBaoTools();
        alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        exParams = new HashMap<String, String>();
        exParams.put(AlibcConstants.CONTEXT_PARAMS, "aadeddd");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        exParams.put("userId", String.valueOf(User.getInstance(context).getId()));
        return ins;
    }

    public static TaoBaoTools getIns(Context context, OpenType openType) {
        ins = new TaoBaoTools();
        alibcShowParams = new AlibcShowParams(openType, false);
        exParams = new HashMap<String, String>();
        exParams.put(AlibcConstants.CONTEXT_PARAMS, "aadeddd");
//            exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
//            exParams.put("userId", String.valueOf(User.getInstance(context).getId()));
        return ins;
    }


    public static String getString(String url, String myCookie) {
        StringBuffer sb = new StringBuffer();
        BufferedReader buffer = null;
        URL url_ = null;
        String line = null;
        try {
            // 创建一个URL对象
            url_ = new URL(url);
            // 根据URL对象创建一个Http连接
            HttpURLConnection urlConn = (HttpURLConnection) url_
                    .openConnection();
            urlConn.setRequestProperty("Cookie", myCookie);
            urlConn.setDoInput(true);
            // 使用IO读取下载的文件数据
            buffer = new BufferedReader(new InputStreamReader(
                    urlConn.getInputStream(), "utf-8"));
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (buffer == null) {
                    return "";
                } else {
                    buffer.close();
                }

            } catch (IOException e) {
            }
        }
        return sb.toString();
    }

    /**
     * 显示商品详情页
     */
    public void showDetail(final Activity activity, String shangpinid) {
        ins.activity = activity;
        AlibcBasePage alibcBasePage;
        alibcBasePage = new AlibcDetailPage(shangpinid);
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams(TaoBaoKeId, "", "");
        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, callback);

    }

    public void showMyCartsPage(Activity activity) {
        ins.activity = activity;
        AlibcBasePage myCartsPage = new AlibcMyCartsPage();
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams(TaoBaoKeId, "", "");
        AlibcTrade.show(activity, myCartsPage, alibcShowParams, alibcTaokeParams, exParams, callback);
    }

    /**
     * 2添加到购物车
     */
    public void addToCart(final Activity activity, String shangpinid) {
        ins.activity = activity;
        AlibcBasePage alibcBasePage;
        alibcBasePage = new AlibcAddCartPage(shangpinid);
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams("mm_25756367_12146445_60696410", "mm_25756367_12146445_60696410", null);
        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, callback);
    }

    public void showPage(final Activity activity, String url) {
        ins.activity = activity;
        //实例化URL打开page
        AlibcBasePage page = new AlibcPage(url);
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams(TaoBaoKeId, "", "");
        AlibcTrade.show(activity, page, alibcShowParams, alibcTaokeParams, exParams, callback);

    }

    //我的订单
    public void getCookie(Activity activity, final Callback_string callback) {
        final boolean[] yijinghuidiao = {false};
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (yijinghuidiao[0] == false) {
                    yijinghuidiao[0] = true;
                    System.err.println("-------------超时");
                    callback.call("");
                }
            }
        }, 15000);

        //实例化我的订单打开page
        //@param status   默认跳转页面；填写：0：全部；1：待付款；2：待发货；3：待收货；4：待评价
        // @param allOrder false 进行订单分域（只展示通过当前app下单的订单），true 显示所有订单
        final AlibcMyOrdersPage page = new AlibcMyOrdersPage(0, false);
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.H5, false);


        final WebView webView = new WebView(activity);
        WebChromeClient webChromeClient = new WebChromeClient();
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                System.err.println("----------------onPageFinished");

                CookieManager cookieManager = CookieManager.getInstance();
                String CookieStr = cookieManager.getCookie(taobaoUrl);
                System.err.println("----------------cookie:" + CookieStr);
                yijinghuidiao[0] = true;
                callback.call(CookieStr);
                cookie = CookieStr;
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                yijinghuidiao[0] = true;
                callback.equals("");
                System.err.println("-------------同步订单错误：" + error.toString());
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                yijinghuidiao[0] = true;
                callback.equals("");
                System.err.println("-------------同步订单错误：" + errorResponse.toString());
                super.onReceivedHttpError(view, request, errorResponse);
            }
        };

        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams(TaoBaoKeId, "", "");
        int result = AlibcTrade.show(activity, webView, webViewClient, webChromeClient, page, alibcShowParams, alibcTaokeParams, exParams,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                        System.err.println("--------------onTradeSuccess:" + tradeResult.toString());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                        System.err.println("--------------onFailure:" + msg);
                    }
                });

    }

    //我的订单
    public String getCookie() {


        CookieManager cookieManager = CookieManager.getInstance();
        String cookieaaa = cookieManager.getCookie("https://taobao.com");
        return cookieaaa;
    }

    //我的订单
    public void getCookie2(Activity activity, final Callback_string callback) {


        CookieManager cookieManager = CookieManager.getInstance();
        final String cookieaaa = cookieManager.getCookie("https://taobao.com");
        System.err.println("-------------cookieaaa:" + cookieaaa);

        final AlibcMyOrdersPage page = new AlibcMyOrdersPage(0, true);
//        String url = page.genOpenUrl();


        final String urlPath = page.genOpenUrl();
        System.err.println("----------------" + urlPath);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String aa = getString("https://buyertrade.taobao.com/trade/itemlist/list_bought_items.htm", cookieaaa);
                    FileUtils.writeStringToFile(new File("sdcard/tbcookie.txt"), aa);
                    System.err.println("------result:" + aa);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


//        RequestParams params = new RequestParams(url);
//        params.addHeader("Cookie", cookieaaa);
//        params.setUseCookie(true);
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                System.err.println("-------------haha:" + result);
//                try {
//                    FileUtils.writeStringToFile(new File("sdcard/tbcookie.txt"), result);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                System.err.println("------------haha onError:" + ex.getMessage());
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });


//        //------------------------
//        final boolean[] yijinghuidiao = {false};
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (yijinghuidiao[0] == false) {
//                    yijinghuidiao[0] = true;
//                    System.err.println("-------------超时");
//                    callback.call("");
//                }
//            }
//        }, 15000);
//
//        //实例化我的订单打开page
//        final AlibcMyOrdersPage page = new AlibcMyOrdersPage(0, true);
//        //@param status   默认跳转页面；填写：0：全部；1：待付款；2：待发货；3：待收货；4：待评价
//        // @param allOrder false 进行订单分域（只展示通过当前app下单的订单），true 显示所有订单
//        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.H5, false);
//
//
//        final WebView webView = new WebView(activity);
//        WebChromeClient webChromeClient = new WebChromeClient();
//        WebViewClient webViewClient = new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return false;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                System.err.println("----------------onPageFinished");
//
//                CookieManager cookieManager = CookieManager.getInstance();
//                String CookieStr = cookieManager.getCookie(taobaoUrl);
//                System.err.println("----------------cookie:" + CookieStr);
//                yijinghuidiao[0] = true;
//                callback.call(CookieStr);
//                cookie = CookieStr;
//                super.onPageFinished(view, url);
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                yijinghuidiao[0] = true;
//                callback.equals("");
//                System.err.println("-------------同步订单错误：" + error.toString());
//                super.onReceivedError(view, request, error);
//            }
//
//            @Override
//            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
//                yijinghuidiao[0] = true;
//                callback.equals("");
//                System.err.println("-------------同步订单错误：" + errorResponse.toString());
//                super.onReceivedHttpError(view, request, errorResponse);
//            }
//        };
//
//        int result = AlibcTrade.show(activity, webView, webViewClient, webChromeClient, page, alibcShowParams, null, exParams,
//                new AlibcTradeCallback() {
//                    @Override
//                    public void onTradeSuccess(TradeResult tradeResult) {
//                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
//                        System.err.println("--------------onTradeSuccess:" + tradeResult.toString());
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
//                        System.err.println("--------------onFailure:" + msg);
//                    }
//                });
//
//        System.err.println("-----------------------show result:" + result);
    }

    public void login(final Activity activity) {

        final AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.showLogin(activity, new AlibcLoginCallback() {

            @Override
            public void onSuccess() {
                Toast.makeText(activity, "登录成功 ",
                        Toast.LENGTH_LONG).show();
                //获取淘宝用户信息
                System.err.println("----------------------------" + "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());

            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(activity, "登录失败 ",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void logout(final Activity activity) {

        AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.logout(activity, new LogoutCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(activity, "退出登录成功",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(activity, "退出登录失败 " + code + msg,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void gouwuche(Activity act, WebView wb, WebViewClient mWebViewClient, WebChromeClient mWebChromeClient) {
        AlibcBasePage alibcBasePage = new AlibcMyCartsPage();
        alibcShowParams = new AlibcShowParams(OpenType.H5, false);
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams(TaoBaoKeId, "", "");
        AlibcTrade.show(act, wb, mWebViewClient, mWebChromeClient, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
            @Override
            public void onFailure(int i, String s) {

            }

            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
                Log.e("wd", tradeResult.resultType.toString());
                if (tradeResult.resultType.toString().equals("TYPEPAY")) {
                    try {
                        List orderList = tradeResult.payResult.paySuccessOrders;
                        if (orderList != null && orderList.size() > 0) {
                            String ordStr = "";
                            for (int i = 0; i < orderList.size(); i++) {
                                ordStr += orderList.get(i);
                            }

                            String url = Constant.host + "order/appSubOrderId";
                            RequestParams params = new RequestParams(url);
                            params.addParameter("order.orderId", ordStr + "__car");
                            x.http().get(params, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
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
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Constant.newOrder = true;
                }
            }
        });
    }

    public void show(String couponUrl, Activity act, WebView wb, WebViewClient mWebViewClient, WebChromeClient mWebChromeClient) {
        AlibcPage tradePage = new AlibcPage(couponUrl);
        alibcShowParams = new AlibcShowParams(OpenType.H5, false);

        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams(TaoBaoKeId, "", "");
        System.out.println("-----------" + exParams);
        AlibcTrade.show(act, wb, mWebViewClient, mWebChromeClient, tradePage, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {

            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                Log.e("wd", tradeResult.resultType.toString());
                if (tradeResult.resultType.toString().equals("TYPEPAY")) {
                    try {
                        List orderList = tradeResult.payResult.paySuccessOrders;
                        if (orderList != null && orderList.size() > 0) {
                            String ordStr = "";
                            for (int i = 0; i < orderList.size(); i++) {
                                ordStr += orderList.get(i);
                            }

                            String url = Constant.host + "order/appSubOrderId";
                            RequestParams params = new RequestParams(url);
                            params.addParameter("order.orderId", ordStr);
                            x.http().get(params, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
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
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Constant.newOrder = true;
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
            }
        });
    }

    public void showOrder(Activity act, WebView wb, WebViewClient mWebViewClient, WebChromeClient mWebChromeClient) {
        AlibcMyOrdersPage page = new AlibcMyOrdersPage(0, false);
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.H5, false);
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams(TaoBaoKeId, "", "");
        int result = AlibcTrade.show(act, wb, mWebViewClient, mWebChromeClient, page, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
                System.err.println("--------------onTradeSuccess:" + tradeResult.toString());
            }

            @Override
            public void onFailure(int code, String msg) {
                System.err.println("--------------onFailure:" + msg);
            }
        });
    }

    public void showOrder(Activity act) {
        AlibcContext.initData();
        AlibcMyOrdersPage page = new AlibcMyOrdersPage(0, false);
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.H5, false);
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams(TaoBaoKeId, "", "");
        AlibcTrade.show(act, page, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {

            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
            }

            @Override
            public void onFailure(int code, String msg) {
                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
            }
        });
    }
}
