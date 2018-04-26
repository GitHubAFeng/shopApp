package com.xuri.sqfanli.ui.base;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.xuri.sqfanli.util.SettingConfig;

import java.math.BigDecimal;

/**
 * @ClassName: User
 * @Description: TODO(用户管理)
 * @author: 王东
 * @date: 2017年10月19日 上午11:26:18
 */
public class User {
    private static User user = null;

    private Integer id;

    private String pid;

    private String openId;

    private String imei;

    private String userCode;
    /**
     * @Fields niname : TODO(昵称)
     */
    private String niName;
    /**
     * @Fields photo : TODO(头像)
     */
    private String photo;
    /**
     * @Fields phone : TODO(电话)
     */
    private String phone;
    /**
     * @Fields sex : TODO(性别,1男性，2女性，0未知)
     */
    private String sex;
    /**
     * @Fields alipay : TODO(支付宝账号)
     */
    private String alipay;
    /**
     * @Fields uname : TODO(真实姓名)
     */
    private String uname;
    /**
     * @Fields todMoney : TODO(今日收益)
     */
    private BigDecimal todMoney;
    /**
     * @Fields todMoney : TODO(今日收益，1上升,0下降)
     */
    private String todShengTag;
    /**
     * @Fields yuMoney : TODO(余额)
     */
    private BigDecimal yuMoney;
    /**
     * @Fields totMoney : TODO(累计收益)
     */
    private BigDecimal totMoney;
    /**
     * @Fields payMoney : TODO(花费总数)
     */
    private BigDecimal payMoney;
    /**
     * @Fields payNum : TODO(花钱笔数)
     */
    private Integer payNum;
    /**
     * @Fields totTiMoney : TODO(累计提现金额)
     */
    private String totTiMoney;
    /**
     * @Fields nianLv : TODO(年华率)
     */
    private BigDecimal nianLv;
    /**
     * @Fields bangWx : TODO(绑定微信账号，1已绑定，0未绑定)
     */
    private String bangWx;
    /**
     * @Fields qrCodeUrl : TODO(分享二维码)
     */
    private String qrCodeUrl;
    /**
     * @Fields popTi : TODO(提示框)
     */
    private String popTi;
    /**
     * @Fields fuPop : TODO(浮动框：1显示，0不显示)
     */
    private String fuPop;
    /**
     * @Fields fuPopImg : TODO(浮动框图片)
     */
    private String fuPopImg;
    /**
     * @Fields fuPopUrl : TODO(浮动框链接)
     */
    private String fuPopUrl;
    /**
     * @Fields huaHeadImg : TODO(花钱头部图片)
     */
    private String huaHeadImg;
    /**
     * @Fields huaHeadUrl : TODO(花钱头部链接)
     */
    private String huaHeadUrl;
    /**
     * @Fields huaMidImg : TODO(花钱中间图片)
     */
    private String huaMidImg;
    /**
     * @Fields huaMidUrl : TODO(花钱中间链接)
     */
    private String huaMidUrl;
    /**
     * @Fields taobaoImg : TODO(淘宝活动图片)
     */
    private String taobaoImg;
    /**
     * @Fields taobaoUrl : TODO(淘宝活动链接)
     */
    private String taobaoUrl;
    /**
     * @Fields tianmaoImg : TODO(天猫活动图片)
     */
    private String tianmaoImg;
    /**
     * @Fields tianmaoUrl : TODO(天猫活动链接)
     */
    private String tianmaoUrl;
    /**
     * @Fields quanNum : TODO(优惠券数量)
     */
    private String quanNum;
    /**
     * @Fields friPing : TODO(好友邀请)
     */
    private String friPing;
    /**
     * @Fields friPingTag : TODO(好友邀请,1升高，0降低)
     */
    private String friPingTag;
    /**
     * @Fields xiaoPing : TODO(消费能力)
     */
    private String xiaoPing;
    /**
     * @Fields xiaoPingTag : TODO(消费能力,1升高，0降低)
     */
    private String xiaoPingTag;
    /**
     * @Fields sharePing : TODO(分享能力)
     */
    private String sharePing;
    /**
     * @Fields sharePingTag : TODO(分享能力,1升高，0降低)
     */
    private String sharePingTag;
    /**
     * @Fields usePing : TODO(使用频率)
     */
    private String usePing;
    /**
     * @Fields usePingTag : TODO(使用频率,1升高，0降低)
     */
    private String usePingTag;
    /**
     * @Fields shopPing : TODO(购物行为)
     */
    private String shopPing;
    /**
     * @Fields shopPingTag : TODO(购物行为,1升高，0降低)
     */
    private String shopPingTag;
    /**
     * @Fields duiAdvImg : TODO(兑换界面广告图片)
     */
    private String duiAdvImg;
    /**
     * @Fields duiAdvUrl : TODO(兑换界面广告链接)
     */
    private String duiAdvUrl;
    /**
     * @Fields daiCunMoney : TODO(待存入金额)
     */
    private Float daiCunMoney;
    /**
     * @Fields hongbaoState : TODO(每日红包状态: 1表示可以领取  2表示已领取过  3表示没有可以领取的红包 4表示已拆未领取（此时红包金额不为空）)
     */
    private String hongbaoState;
    /**
     * @Fields hongbaoState : TODO(每日红包金额)
     */
    private String hongbaoMoney;

    private String mianTag;
    private String wxAppID;
    private String wxAppSecret;

    public static User getInstance(Context context) {
        if (user == null) {
            user = new User();
            String id = SettingConfig.getInstance(context).getStringPreference("id", "0");
            if(id==null || id.length()==0) {
                id = "0";
            }
            user.setId(Integer.parseInt(id));
            user.setPid(SettingConfig.getInstance(context).getStringPreference("pid", ""));
            user.setOpenId(SettingConfig.getInstance(context).getStringPreference("openId", ""));
            user.setImei(SettingConfig.getInstance(context).getStringPreference("imei", ""));
            user.setUserCode(SettingConfig.getInstance(context).getStringPreference("userCode", ""));
            user.setNiName(SettingConfig.getInstance(context).getStringPreference("niName", ""));
            user.setPhoto(SettingConfig.getInstance(context).getStringPreference("photo", ""));
            user.setPhone(SettingConfig.getInstance(context).getStringPreference("phone", ""));
            user.setAlipay(SettingConfig.getInstance(context).getStringPreference("alipay", ""));
            user.setUname(SettingConfig.getInstance(context).getStringPreference("uname", ""));
            user.setSex(SettingConfig.getInstance(context).getStringPreference("sex", ""));
            user.setTodMoney(new BigDecimal(SettingConfig.getInstance(context).getStringPreference("todMoney", "0")));
            user.setYuMoney(new BigDecimal(SettingConfig.getInstance(context).getStringPreference("yuMoney", "0")));
            user.setTotMoney(new BigDecimal(SettingConfig.getInstance(context).getStringPreference("totMoney", "0")));
            user.setPayMoney(new BigDecimal(SettingConfig.getInstance(context).getStringPreference("payMoney", "0")));
            user.setPayNum((int) (SettingConfig.getInstance(context).getStringPreference("payNum", 0)));
            user.setTotTiMoney(SettingConfig.getInstance(context).getStringPreference("totTiMoney", "0"));
            user.setNianLv(new BigDecimal(SettingConfig.getInstance(context).getStringPreference("nianLv", "0")));
            user.setBangWx(SettingConfig.getInstance(context).getStringPreference("bangWx", ""));
            user.setQrCodeUrl(SettingConfig.getInstance(context).getStringPreference("qrCodeUrl", ""));
            user.setHuaHeadImg(SettingConfig.getInstance(context).getStringPreference("huaHeadImg", ""));
            user.setHuaHeadUrl(SettingConfig.getInstance(context).getStringPreference("huaHeadUrl", ""));
            user.setHuaMidImg(SettingConfig.getInstance(context).getStringPreference("huaMidImg", ""));
            user.setHuaMidUrl(SettingConfig.getInstance(context).getStringPreference("huaMidUrl", ""));
            user.setPopTi(SettingConfig.getInstance(context).getStringPreference("popTi", "0"));
            user.setFuPop(SettingConfig.getInstance(context).getStringPreference("fuPop", "0"));
            user.setFuPopImg(SettingConfig.getInstance(context).getStringPreference("fuPopImg", ""));
            user.setFuPopUrl(SettingConfig.getInstance(context).getStringPreference("fuPopUrl", ""));
            user.setTaobaoImg(SettingConfig.getInstance(context).getStringPreference("taobaoImg", ""));
            user.setTaobaoUrl(SettingConfig.getInstance(context).getStringPreference("taobaoUrl", ""));
            user.setTianmaoImg(SettingConfig.getInstance(context).getStringPreference("tianmaoImg", ""));
            user.setTianmaoUrl(SettingConfig.getInstance(context).getStringPreference("tianmaoUrl", ""));
            user.setQuanNum(SettingConfig.getInstance(context).getStringPreference("quannum", ""));
            user.setTodShengTag(SettingConfig.getInstance(context).getStringPreference("todShengTag", ""));
            user.setFriPing(SettingConfig.getInstance(context).getStringPreference("friPing", ""));
            user.setFriPingTag(SettingConfig.getInstance(context).getStringPreference("friPingTag", ""));
            user.setXiaoPing(SettingConfig.getInstance(context).getStringPreference("xiaoPing", ""));
            user.setXiaoPingTag(SettingConfig.getInstance(context).getStringPreference("xiaoPingTag", ""));
            user.setSharePing(SettingConfig.getInstance(context).getStringPreference("sharePing", ""));
            user.setSharePingTag(SettingConfig.getInstance(context).getStringPreference("sharePingTag", ""));
            user.setUsePing(SettingConfig.getInstance(context).getStringPreference("usePing", ""));
            user.setUsePingTag(SettingConfig.getInstance(context).getStringPreference("usePingTag", ""));
            user.setShopPing(SettingConfig.getInstance(context).getStringPreference("shopPing", ""));
            user.setShopPingTag(SettingConfig.getInstance(context).getStringPreference("shopPingTag", ""));
            user.setDuiAdvImg(SettingConfig.getInstance(context).getStringPreference("duiAdvImg", ""));
            user.setDuiAdvUrl(SettingConfig.getInstance(context).getStringPreference("duiAdvUrl", ""));
            user.setMianTag(SettingConfig.getInstance(context).getStringPreference("mianTag", ""));
            user.setDaiCunMoney(SettingConfig.getInstance(context).getStringPreference("daiCunMoney", 0));
            user.setHongbaoState(SettingConfig.getInstance(context).getStringPreference("hongbaoState", ""));
            user.setHongbaoMoney(SettingConfig.getInstance(context).getStringPreference("hongbaoMoney", ""));

        }
        return user;
    }

    public String getWxAppID() {
        return wxAppID;
    }

    public void setWxAppID(String wxAppID) {
        this.wxAppID = wxAppID;
    }

    public String getWxAppSecret() {
        return wxAppSecret;
    }

    public void setWxAppSecret(String wxAppSecret) {
        this.wxAppSecret = wxAppSecret;
    }

    //登录
    public static void userLogin(Context context, User userLog) {
        SettingConfig.getInstance(context).setStringPreference("id", String.valueOf(userLog.getId()));
        SettingConfig.getInstance(context).setStringPreference("pid", userLog.getPid());
        SettingConfig.getInstance(context).setStringPreference("openId", userLog.getOpenId());
        SettingConfig.getInstance(context).setStringPreference("imei", userLog.getImei());
        SettingConfig.getInstance(context).setStringPreference("userCode", userLog.getUserCode());
        SettingConfig.getInstance(context).setStringPreference("niName", userLog.getNiName());
        SettingConfig.getInstance(context).setStringPreference("photo", userLog.getPhoto());
        SettingConfig.getInstance(context).setStringPreference("phone", userLog.getPhone());
        SettingConfig.getInstance(context).setStringPreference("alipay", userLog.getAlipay());
        SettingConfig.getInstance(context).setStringPreference("uname", userLog.getUname());
        SettingConfig.getInstance(context).setStringPreference("sex", userLog.getSex());
        SettingConfig.getInstance(context).setStringPreference("todMoney", userLog.getTodMoney().toString());
        SettingConfig.getInstance(context).setStringPreference("yuMoney", userLog.getYuMoney().toString());
        SettingConfig.getInstance(context).setStringPreference("totMoney", userLog.getTotMoney().toString());
        SettingConfig.getInstance(context).setStringPreference("payMoney", userLog.getPayMoney().toString());
        SettingConfig.getInstance(context).setStringPreference("payNum", userLog.getPayNum());
        SettingConfig.getInstance(context).setStringPreference("totTiMoney", userLog.getTotTiMoney());
        SettingConfig.getInstance(context).setStringPreference("nianLv", userLog.getNianLv().toString());
        SettingConfig.getInstance(context).setStringPreference("bangWx", userLog.getBangWx().toString());
        SettingConfig.getInstance(context).setStringPreference("qrCodeUrl", userLog.getQrCodeUrl().toString());
        SettingConfig.getInstance(context).setStringPreference("huaHeadImg", userLog.getHuaHeadImg());
        SettingConfig.getInstance(context).setStringPreference("huaHeadUrl", userLog.getHuaHeadUrl());
        SettingConfig.getInstance(context).setStringPreference("huaMidImg", userLog.getHuaMidImg());
        SettingConfig.getInstance(context).setStringPreference("huaMidUrl", userLog.getHuaMidUrl());
        SettingConfig.getInstance(context).setStringPreference("popTi", userLog.getPopTi());
        SettingConfig.getInstance(context).setStringPreference("fuPop", userLog.getFuPop());
        SettingConfig.getInstance(context).setStringPreference("fuPopImg", userLog.getFuPopImg());
        SettingConfig.getInstance(context).setStringPreference("fuPopUrl", userLog.getFuPopUrl());
        SettingConfig.getInstance(context).setStringPreference("taobaoImg", userLog.getTaobaoImg());
        SettingConfig.getInstance(context).setStringPreference("taobaoUrl", userLog.getTaobaoUrl());
        SettingConfig.getInstance(context).setStringPreference("tianmaoImg", userLog.getTianmaoImg());
        SettingConfig.getInstance(context).setStringPreference("tianmaoUrl", userLog.getTianmaoUrl());
        SettingConfig.getInstance(context).setStringPreference("quannum", userLog.getQuanNum());
        SettingConfig.getInstance(context).setStringPreference("todShengTag", userLog.getTodShengTag());
        SettingConfig.getInstance(context).setStringPreference("friPing", userLog.getFriPing());
        SettingConfig.getInstance(context).setStringPreference("friPingTag", userLog.getFriPingTag());
        SettingConfig.getInstance(context).setStringPreference("xiaoPing", userLog.getXiaoPing());
        SettingConfig.getInstance(context).setStringPreference("xiaoPingTag", userLog.getXiaoPingTag());
        SettingConfig.getInstance(context).setStringPreference("sharePing", userLog.getSharePing());
        SettingConfig.getInstance(context).setStringPreference("sharePingTag", userLog.getSharePingTag());
        SettingConfig.getInstance(context).setStringPreference("usePing", userLog.getUsePing());
        SettingConfig.getInstance(context).setStringPreference("usePingTag", userLog.getUsePingTag());
        SettingConfig.getInstance(context).setStringPreference("shopPing", userLog.getShopPing());
        SettingConfig.getInstance(context).setStringPreference("shopPingTag", userLog.getShopPingTag());
        SettingConfig.getInstance(context).setStringPreference("duiAdvImg", userLog.getDuiAdvImg());
        SettingConfig.getInstance(context).setStringPreference("duiAdvUrl", userLog.getDuiAdvUrl());
        SettingConfig.getInstance(context).setStringPreference("mianTag", userLog.getMianTag());
        SettingConfig.getInstance(context).setStringPreference("daiCunMoney", userLog.getDaiCunMoney());
        SettingConfig.getInstance(context).setStringPreference("hongbaoState", userLog.getHongbaoState());
        SettingConfig.getInstance(context).setStringPreference("hongbaoMoney", userLog.getHongbaoMoney());
        user = null;
    }
    //退出登录
    public static void userOutLogin(Context context) {
        SettingConfig.getInstance(context).setStringPreference("id", "0");

        user = null;

        Intent intent = new Intent();
//        intent.setClass(context, LoginActivity.class);  //登陆
        context.startActivity(intent);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getNiName() {
        return niName;
    }

    public void setNiName(String niName) {
        this.niName = niName == null ? null : niName.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay == null ? null : alipay.trim();
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public BigDecimal getTodMoney() {
        return todMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setTodMoney(BigDecimal todMoney) {
        this.todMoney = todMoney;
    }

    public BigDecimal getYuMoney() {
        return yuMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setYuMoney(BigDecimal yuMoney) {
        this.yuMoney = yuMoney;
    }

    public BigDecimal getTotMoney() {
        return totMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setTotMoney(BigDecimal totMoney) {
        this.totMoney = totMoney;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getNianLv() {
        return nianLv;
    }

    public void setNianLv(BigDecimal nianLv) {
        this.nianLv = nianLv;
    }

    public Integer getPayNum() {
        return payNum;
    }

    public void setPayNum(Integer payNum) {
        this.payNum = payNum;
    }

    public String getHuaHeadImg() {
        return huaHeadImg;
    }

    public void setHuaHeadImg(String huaHeadImg) {
        this.huaHeadImg = huaHeadImg;
    }

    public String getHuaHeadUrl() {
        return huaHeadUrl;
    }

    public void setHuaHeadUrl(String huaHeadUrl) {
        this.huaHeadUrl = huaHeadUrl;
    }

    public String getHuaMidImg() {
        return huaMidImg;
    }

    public void setHuaMidImg(String huaMidImg) {
        this.huaMidImg = huaMidImg;
    }

    public String getHuaMidUrl() {
        return huaMidUrl;
    }

    public void setHuaMidUrl(String huaMidUrl) {
        this.huaMidUrl = huaMidUrl;
    }

    public String getTotTiMoney() {
        return totTiMoney;
    }

    public void setTotTiMoney(String totTiMoney) {
        this.totTiMoney = totTiMoney;
    }

    public String getPopTi() {
        return popTi;
    }

    public void setPopTi(String popTi) {
        this.popTi = popTi;
    }

    public String getTaobaoImg() {
        return taobaoImg;
    }

    public void setTaobaoImg(String taobaoImg) {
        this.taobaoImg = taobaoImg;
    }

    public String getTaobaoUrl() {
        return taobaoUrl;
    }

    public void setTaobaoUrl(String taobaoUrl) {
        this.taobaoUrl = taobaoUrl;
    }

    public String getTianmaoImg() {
        return tianmaoImg;
    }

    public void setTianmaoImg(String tianmaoImg) {
        this.tianmaoImg = tianmaoImg;
    }

    public String getTianmaoUrl() {
        return tianmaoUrl;
    }

    public void setTianmaoUrl(String tianmaoUrl) {
        this.tianmaoUrl = tianmaoUrl;
    }

    public String getBangWx() {
        return bangWx;
    }

    public void setBangWx(String bangWx) {
        this.bangWx = bangWx;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getFuPop() {
        return fuPop;
    }

    public void setFuPop(String fuPop) {
        this.fuPop = fuPop;
    }

    public String getFuPopImg() {
        return fuPopImg;
    }

    public void setFuPopImg(String fuPopImg) {
        this.fuPopImg = fuPopImg;
    }

    public String getFuPopUrl() {
        return fuPopUrl;
    }

    public void setFuPopUrl(String fuPopUrl) {
        this.fuPopUrl = fuPopUrl;
    }

    public String getQuanNum() {
        return quanNum;
    }

    public void setQuanNum(String quanNum) {
        this.quanNum = quanNum;
    }

    public String getFriPing() {
        return friPing;
    }

    public void setFriPing(String friPing) {
        this.friPing = friPing;
    }

    public String getFriPingTag() {
        return friPingTag;
    }

    public void setFriPingTag(String friPingTag) {
        this.friPingTag = friPingTag;
    }

    public String getXiaoPing() {
        return xiaoPing;
    }

    public void setXiaoPing(String xiaoPing) {
        this.xiaoPing = xiaoPing;
    }

    public String getXiaoPingTag() {
        return xiaoPingTag;
    }

    public void setXiaoPingTag(String xiaoPingTag) {
        this.xiaoPingTag = xiaoPingTag;
    }

    public String getSharePing() {
        return sharePing;
    }

    public void setSharePing(String sharePing) {
        this.sharePing = sharePing;
    }

    public String getSharePingTag() {
        return sharePingTag;
    }

    public void setSharePingTag(String sharePingTag) {
        this.sharePingTag = sharePingTag;
    }

    public String getUsePing() {
        return usePing;
    }

    public void setUsePing(String usePing) {
        this.usePing = usePing;
    }

    public String getUsePingTag() {
        return usePingTag;
    }

    public void setUsePingTag(String usePingTag) {
        this.usePingTag = usePingTag;
    }

    public String getShopPing() {
        return shopPing;
    }

    public void setShopPing(String shopPing) {
        this.shopPing = shopPing;
    }

    public String getShopPingTag() {
        return shopPingTag;
    }

    public void setShopPingTag(String shopPingTag) {
        this.shopPingTag = shopPingTag;
    }

    public String getTodShengTag() {
        return todShengTag;
    }

    public void setTodShengTag(String todShengTag) {
        this.todShengTag = todShengTag;
    }

    public String getDuiAdvImg() {
        return duiAdvImg;
    }

    public void setDuiAdvImg(String duiAdvImg) {
        this.duiAdvImg = duiAdvImg;
    }

    public String getDuiAdvUrl() {
        return duiAdvUrl;
    }

    public void setDuiAdvUrl(String duiAdvUrl) {
        this.duiAdvUrl = duiAdvUrl;
    }

    public String getMianTag() {
        return mianTag;
    }

    public void setMianTag(String mianTag) {
        this.mianTag = mianTag;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Float getDaiCunMoney() {
        return daiCunMoney;
    }

    public void setDaiCunMoney(Float daiCunMoney) {
        this.daiCunMoney = daiCunMoney;
    }

    public String getHongbaoState() {
        return hongbaoState;
    }

    public void setHongbaoState(String hongbaoState) {
        this.hongbaoState = hongbaoState;
    }

    public String getHongbaoMoney() {
        return hongbaoMoney;
    }

    public void setHongbaoMoney(String hongbaoMoney) {
        this.hongbaoMoney = hongbaoMoney;
    }
}