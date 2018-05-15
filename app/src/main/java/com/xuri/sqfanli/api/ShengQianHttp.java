package com.xuri.sqfanli.api;

import android.content.Context;
import android.util.Log;


import com.xuri.sqfanli.Constant;
import com.xuri.sqfanli.MyAPP;
import com.xuri.sqfanli.bean.User;
import com.xuri.sqfanli.util.EncryptUtil;

import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by bd on 2017/10/24.
 */

public class ShengQianHttp {


    /**
     * 切换支付宝
     *
     * @return
     */
    public static void appUpdateUser(Context context, String zhifubao, String name, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "user/appUpdateUser";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("user.imei", EncryptUtil.encrypt(User.getInstance(context).getImei()));
        params.addBodyParameter("user.alipay", zhifubao);
        params.addBodyParameter("user.uname", name);
        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }


    /**
     * 提现
     *
     * @return
     */
    public static void appDuihuan(Context context, String zhifubao, String name, String tiMoney, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "duihuan/appDuihuan";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("duihuan.userId", User.getInstance(context).getId() + "");
        params.addBodyParameter("duihuan.alipay", zhifubao);
        params.addBodyParameter("duihuan.uname", name);

        params.addBodyParameter("duihuan.tiMoney", tiMoney);
        long time = System.currentTimeMillis();
        try {
            params.addBodyParameter("sign", URLEncoder.encode(EncryptUtil.getMD5(Constant.TOKEN + time), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        params.addBodyParameter("time_stamp", time + "");

        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);
            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }


    /**
     * 关注微信公众号 绑定微信
     *
     * @return
     */
    public static void appBangWx(Context context, String secrCode, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "user/appBangWx";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("user.Id", User.getInstance(context).getId() + "");
        params.addBodyParameter("secrCode", secrCode);


        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);
            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }

    /**
     * 明细列表
     *
     * @return
     */
    public static void appEarnOrderList(Context context, String id, String earnType, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "earnOrder/appEarnOrderList";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("earnOrder.userId", User.getInstance(context).getId() + "");
        params.addBodyParameter("earnOrder.earnType", earnType);
        params.addBodyParameter("earnOrder.id", id);

        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);
            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }

    /**
     * 获奖列表
     *
     * @return
     */
    public static void appRedBot(Context context, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "redPack/appRedBot";
        RequestParams params = new RequestParams(url);

        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);
            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }

    /**
     * 摇红包
     *
     * @return
     */
    public static void appYaoYaoRed(Context context, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "redPack/appYaoYaoRed";
        RequestParams params = new RequestParams(url);

        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);
            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }

    /**
     * 领取利率
     *
     * @return
     */
    public static void appLingPack(Context context, String liv, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "redPack/appLingPack";
        RequestParams params = new RequestParams(url);

        params.addBodyParameter("userId", User.getInstance(context).getId() + "");
        params.addBodyParameter("lilv", liv);
        long time = System.currentTimeMillis();
        params.addBodyParameter("time_stamp", String.valueOf(time));
        try {
            params.addBodyParameter("sign", URLEncoder.encode(EncryptUtil.getMD5(Constant.TOKEN + time), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);
            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }

    /**
     * 切换支付宝
     *
     * @return
     */
    public static void ZaoQiTiaoZhanAppUpdateUser(Context context, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "challenge/appUserData";
        RequestParams params = new RequestParams(url);
        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }

    /**
     * 翻牌子
     *
     * @return
     */
    public static void appAward(Context context, String id, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "order/appAward";
        RequestParams params = new RequestParams(url);

        params.addBodyParameter("order.id", id);


        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }

    /**
     * 提交奖励
     *
     * @return
     */
    public static void appPrize(Context context, String id, String beanid, String pname, String ptype, String prizeValue, String firendName, String friendAvatar, String userid, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "order/appPrize";
        RequestParams params = new RequestParams(url);

        params.addBodyParameter("award.orderid", id);

        params.addBodyParameter("award.id", beanid);


        params.addBodyParameter("award.prizeName", pname);
        params.addBodyParameter("award.prize_type", ptype);
        params.addBodyParameter("award.friendName", firendName);
        params.addBodyParameter("award.friendAvatar", friendAvatar);
        params.addBodyParameter("award.userid", userid);

        params.addBodyParameter("award.prizeValue", prizeValue);


        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }


    /**
     * 查看好友翻牌子
     *
     * @return
     */
    public static void appGetAward(Context context, String id, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "order/appGetAward";
        RequestParams params = new RequestParams(url);

        params.addBodyParameter("award.orderid", id);


        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }


    /**
     * 获取转盘抽奖中奖信息
     *
     * @return
     */
    public static void appGetUserDraw(Context context, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "draw/appGetUserDraw";
        RequestParams params = new RequestParams(url);

        params.addBodyParameter("dial_activity.bazaar", MyAPP.getAppMetaData(context, "UMENG_CHANNEL"));


        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }

    /**
     * 抽奖
     *
     * @return
     */
    public static void appSetUserDraw(Context context, String id, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "draw/appSetUserDraw";
        RequestParams params = new RequestParams(url);

        params.addBodyParameter("dial_activity.bazaar", MyAPP.getAppMetaData(context, "UMENG_CHANNEL"));

        params.addBodyParameter("user.id", id);


        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }


    /**
     * 提交抽奖
     *
     * @return
     */
    public static void appSaveUserDraw(Context context, String type, String id, String name, String qq, String phone, String address, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "draw/appSaveUserDraw";
        RequestParams params = new RequestParams(url);

        params.addBodyParameter("draw.userid", id);
        params.addBodyParameter("draw.userName", name);
        params.addBodyParameter("draw.phone", phone);
        params.addBodyParameter("draw.QQ", qq);
        params.addBodyParameter("draw.site", address);
        params.addBodyParameter("draw.type", type);


        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }

    /**
     * 获取品牌list
     *
     * @return
     */
    public static void appBrankList(Context context, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "shop/appBrankList";
        RequestParams params = new RequestParams(url);
        params.addParameter("brank.sex", User.getInstance(context).getSex());
        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }

    /**
     * 获取品牌的商品
     *
     * @return
     */
    public static void appBrankShopList(Context context, String id, String activityId, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "shop/appBrankShopList";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("brank.id", id);
        params.addBodyParameter("brank.activityId", activityId);

        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }

    /**
     * 切换男女生
     *
     * @return
     */
    public static void appUpdateUserInf(Context context, String sex, final MyHttp.CallBack mCallBack) {
        String url = Constant.host + "user/appUpdateUserInf";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("user.id", User.getInstance(context).getId() + "");
        params.addBodyParameter("user.sex", sex);

        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }


    /**
     * 添加购物车链接
     *
     * @return
     */
    public static void appShopCoupon(Context context, String str, final MyHttp.CallBack mCallBack) {

        String url = Constant.host + "shop/appShopCoupon";
        RequestParams params = new RequestParams(url);

        params.addBodyParameter("shop.itemid", str);
        Log.e("wd", "购物车优惠券itemid===" + str);

        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }


    /**
     * 签到初始化
     *
     * @return
     */
    public static void appNewSingIn(Context context, String str, final MyHttp.CallBack mCallBack) {

        String url = Constant.host + "user/appNewSingIn";
        RequestParams params = new RequestParams(url);

        params.addBodyParameter("singIn.userId", str);


        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }


    /**
     * 签到
     *
     * @return
     */
    public static void appSingIn(Context context, String str, final MyHttp.CallBack mCallBack) {

        String url = Constant.host + "user/appSingIn";
        RequestParams params = new RequestParams(url);

        params.addBodyParameter("singIn.userId", str);


        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }


    /**
     * 签到
     *
     * @return
     */
    public static void appSingInShare(Context context, String str, final MyHttp.CallBack mCallBack) {

        String url = Constant.host + "user/appSingInShare";
        RequestParams params = new RequestParams(url);

        params.addBodyParameter("singIn.userId", str);


        MyHttp.http(params, new MyHttp.CallBack() {
            @Override
            public void onCall(String str) {
                mCallBack.onCall(str);

            }

            @Override
            public void onError(String str) {
                mCallBack.onError(str);
            }

            @Override
            public void onFinished() {
                mCallBack.onFinished();
            }
        });
    }
}
