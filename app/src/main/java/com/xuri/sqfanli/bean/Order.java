package com.xuri.sqfanli.bean;

public class Order {
    private Integer id;
    /**
     * @Fields orderStatus : TODO(来源：1淘宝，2天猫，3京东，4余额淘)
     */
    private String fromType;
    /**
     * @Fields orderStatus : TODO(订单状态：1进行中，2已存入，3无效)
     */
    private String orderStatus;
    /**
     * @Fields title : TODO(标题)
     */
    private String title;
    /**
     * @Fields ordImg : TODO(商品图片)
     */
    private String ordImg;
    /**
     * @Fields oderMoney : TODO(订单金额)
     */
    private String oderMoney;
    /**
     * @Fields cunMoney : TODO(已存入)
     */
    private String cunMoney;
    /**
     * @Fields orderTime : TODO(订单时间)
     */
    private String orderTime;
    /**
     * @Fields userId : TODO(用户ID)
     */
    private String userId;
    /**
     * @Fields orderId :TODO(订单ID)
     */
    private String orderId;
    /**
     * @Fields amount TODO(购买数量)
     */
    private String amount;
    /**
     * @Fields payTag TODO(判断是否付款)
     */
    private String payTag;
    /**
     * @Fields deposit : TODO(累计存款)
     */
    private String deposit;
    /**
     * @Fields cash : TODO(现金)
     */
    private String cash;
    /**
     * @Fields addition: TODO(存入加成)
     */
    private String addition;
    /**
     * @Fields nianlv : TODO(年利率)
     */
    private String nianlv;
    /**
     * @Fields state : TODO(翻牌状态，初始值为0，用户自己翻牌子或者好友帮忙点一次自增1，值为10表示已翻完)
     */
    private String state;
    /**
     * @Fields single : TODO(免单状态：1为免单成功，2为免单失败，3进行中)
     */
    private String single;
    /**
     * @Fields person : TODO(助力人数)
     */
    private Integer person;
    /**
     * @Fields needPerson : TODO(需要助力人数)
     */
    private Integer needPerson;
    /**
     * @Fields differ : TODO(还差助力人数)
     */
    private Integer differ;

    private String createTime;
    /**
     * @Fields overTime : TODO(免单结束时间)
     */
    private String overTime;
    /**
     * @Fields qrCode : TODO(免单分享的二维码)
     */
    private String qrCode;
    /**
     * @Fields qrCode : TODO(红包金额)
     */
    private String hongbaoMoney;
    /**
     * @Fields qrCode : TODO(红包存入状态  1已存入  2待存入  3已失效)
     */
    private String hongbaoState;
    /**
     * @Fields qrCode : TODO(红包领取状态  0表示未领取 1表示已领取)
     */
    private String getHongbao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromType() {
        return fromType == null ? "" : fromType.trim();
    }

    public void setFromType(String fromType) {
        this.fromType = fromType == null ? null : fromType.trim();
    }

    public String getTitle() {
        return title == null ? "" : title.trim();
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getOrdImg() {
        return ordImg == null ? "" : ordImg.trim();
    }

    public void setOrdImg(String ordImg) {
        this.ordImg = ordImg == null ? null : ordImg.trim();
    }

    public String getUserId() {
        return userId == null ? "" : userId.trim();
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getOrderStatus() {
        return orderStatus == null ? "" : orderStatus.trim();
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOderMoney() {
        return oderMoney == null ? "" : oderMoney.trim();
    }

    public void setOderMoney(String oderMoney) {
        this.oderMoney = oderMoney;
    }

    public String getCunMoney() {
        return cunMoney == null ? "" : cunMoney.trim();
    }

    public void setCunMoney(String cunMoney) {
        this.cunMoney = cunMoney;
    }

    public String getOrderTime() {
        return orderTime == null ? "" : orderTime.trim();
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderId() {
        return orderId == null ? "" : orderId.trim();
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount == null ? "" : amount.trim();
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayTag() {
        return payTag == null ? "" : payTag.trim();
    }

    public void setPayTag(String payTag) {
        this.payTag = payTag;
    }

    public String getDeposit() {
        return deposit == null ? "" : deposit.trim();
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getCash() {
        return cash == null ? "" : cash.trim();
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getAddition() {
        return addition == null ? "" : addition.trim();
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public String getNianlv() {
        return nianlv == null ? "" : nianlv.trim();
    }

    public void setNianlv(String nianlv) {
        this.nianlv = nianlv;
    }

    public String getState() {
        return state == null ? "" : state.trim();
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }

    public Integer getNeedPerson() {
        return needPerson;
    }

    public void setNeedPerson(Integer needPerson) {
        this.needPerson = needPerson;
    }

    public Integer getDiffer() {
        return differ;
    }

    public void setDiffer(Integer differ) {
        this.differ = differ;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getHongbaoMoney() {
        return hongbaoMoney;
    }

    public void setHongbaoMoney(String hongbaoMoney) {
        this.hongbaoMoney = hongbaoMoney;
    }

    public String getHongbaoState() {
        return hongbaoState;
    }

    public void setHongbaoState(String hongbaoState) {
        this.hongbaoState = hongbaoState;
    }

    public String getGetHongbao() {
        return getHongbao;
    }

    public void setGetHongbao(String getHongbao) {
        this.getHongbao = getHongbao;
    }
}