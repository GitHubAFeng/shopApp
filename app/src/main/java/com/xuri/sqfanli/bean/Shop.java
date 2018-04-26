package com.xuri.sqfanli.bean;

import java.util.List;

public class Shop {
    private Integer cid;

    private String ctype;

    private String id;

    private String itemid;

    private String seller_id;
    /**
     * @Fields itemtitle : TODO(商品标题)
     */
    private String itemtitle;
    /**
     * @Fields itemshorttitle : TODO(商品子标题)
     */
    private String itemshorttitle;

    private String itemdesc;
    /**
     * @Fields itemprice : TODO(商品原价)
     */
    private String itemprice;
    /**
     * @Fields itemsale : TODO(月销)
     */
    private String itemsale;

    private String itemsale2;

    private String todaysale;
    /**
     * @Fields itempic : TODO(商品图片)
     */
    private String itempic;

    private String itempic_copy;

    private String itempic_type;

    private String fqcat;
    /**
     * @Fields itemendprice : TODO(到手价格)
     */
    private String itemendprice;

    private String shoptype;

    private String tktype;

    private String tkrates;

    private String ctrates;

    private String cuntao;

    private String tkmoney;

    private String tkurl;

    private String couponurl;
    /**
     * @Fields couponmoney : TODO(优惠券金额)
     */
    private String couponmoney;

    private String couponsurplus;

    private String couponreceive;

    private String couponreceive2;

    private String todaycouponreceive;

    private String couponnum;

    private String couponexplain;

    private String couponstarttime;

    private String couponendtime;
    /**
     * @Fields couponType : TODO(1，正常优惠券，2折扣)
     */
    private String couponType;

    private String start_time;

    private String end_time;

    private String starttime;

    private String isquality;

    private String report_status;

    private String is_brand;

    private String is_live;

    private String live_content;

    private String guide_article;

    private String videoid;

    private String activity_type;

    private String general_index;

    private String planlink;

    private String seller_name;

    private String seller_qq;

    private String userid;

    private String sellernick;

    private String online_users;

    private String original_img;

    private String original_article;
    //详情页链接
    private String taobaoPcDescUrl;
    //商品轮播图片
    private String images;
    /**
     * @Fields discount : TODO(几折)
     */
    private String discount;
    /**
     * @Fields collectionTag : TODO(1已收藏，0未收藏)
     */
    private String collectTag;
    /**
     * @Fields collectUserId : TODO(收藏的用户id)
     */
    private String collectUserId;
    /**
     * @Fields qcUrl : TODO(分享二维码)
     */
    private String qcUrl;
    /**
     * @Fields shopBang : TODO(是否是榜单商品,0不是，1是)
     */
    private String shopBang;
    /**
     * @Fields postFree : TODO(是否包邮，1包邮，0不包邮)
     */
    private String postFree;
    /**
     * @Fields person : TODO(免单商品需要的助力人数)
     */
    private Integer person;
    /**
     * @Fields conduct : TODO(判断用户是否有进行中的免单：1表示有免单，0表示没有)
     */
    private String conduct;

    private List<ShopImg> shopImgList;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype == null ? null : ctype.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid == null ? null : itemid.trim();
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id == null ? null : seller_id.trim();
    }

    public String getItemtitle() {
        return itemtitle;
    }

    public void setItemtitle(String itemtitle) {
        this.itemtitle = itemtitle == null ? null : itemtitle.trim();
    }

    public String getItemshorttitle() {
        return itemshorttitle;
    }

    public void setItemshorttitle(String itemshorttitle) {
        this.itemshorttitle = itemshorttitle == null ? null : itemshorttitle.trim();
    }

    public String getItemdesc() {
        return itemdesc;
    }

    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc == null ? null : itemdesc.trim();
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice == null ? null : itemprice.trim();
    }

    public String getItemsale() {
        return itemsale;
    }

    public void setItemsale(String itemsale) {
        this.itemsale = itemsale == null ? null : itemsale.trim();
    }

    public String getItemsale2() {
        return itemsale2;
    }

    public void setItemsale2(String itemsale2) {
        this.itemsale2 = itemsale2 == null ? null : itemsale2.trim();
    }

    public String getTodaysale() {
        return todaysale;
    }

    public void setTodaysale(String todaysale) {
        this.todaysale = todaysale == null ? null : todaysale.trim();
    }

    public String getItempic() {
        return itempic;
    }

    public void setItempic(String itempic) {
        this.itempic = itempic == null ? null : itempic.trim();
    }

    public String getItempic_copy() {
        return itempic_copy;
    }

    public void setItempic_copy(String itempic_copy) {
        this.itempic_copy = itempic_copy == null ? null : itempic_copy.trim();
    }

    public String getItempic_type() {
        return itempic_type;
    }

    public void setItempic_type(String itempic_type) {
        this.itempic_type = itempic_type == null ? null : itempic_type.trim();
    }

    public String getFqcat() {
        return fqcat;
    }

    public void setFqcat(String fqcat) {
        this.fqcat = fqcat == null ? null : fqcat.trim();
    }

    public String getItemendprice() {
        return itemendprice;
    }

    public void setItemendprice(String itemendprice) {
        this.itemendprice = itemendprice == null ? null : itemendprice.trim();
    }

    public String getShoptype() {
        return shoptype;
    }

    public void setShoptype(String shoptype) {
        this.shoptype = shoptype == null ? null : shoptype.trim();
    }

    public String getTktype() {
        return tktype;
    }

    public void setTktype(String tktype) {
        this.tktype = tktype == null ? null : tktype.trim();
    }

    public String getTkrates() {
        return tkrates;
    }

    public void setTkrates(String tkrates) {
        this.tkrates = tkrates == null ? null : tkrates.trim();
    }

    public String getCtrates() {
        return ctrates;
    }

    public void setCtrates(String ctrates) {
        this.ctrates = ctrates == null ? null : ctrates.trim();
    }

    public String getCuntao() {
        return cuntao;
    }

    public void setCuntao(String cuntao) {
        this.cuntao = cuntao == null ? null : cuntao.trim();
    }

    public String getTkmoney() {
        return tkmoney;
    }

    public void setTkmoney(String tkmoney) {
        this.tkmoney = tkmoney == null ? null : tkmoney.trim();
    }

    public String getTkurl() {
        return tkurl;
    }

    public void setTkurl(String tkurl) {
        this.tkurl = tkurl == null ? null : tkurl.trim();
    }

    public String getCouponurl() {
        return couponurl;
    }

    public void setCouponurl(String couponurl) {
        this.couponurl = couponurl == null ? null : couponurl.trim();
    }

    public String getCouponmoney() {
        return couponmoney;
    }

    public void setCouponmoney(String couponmoney) {
        this.couponmoney = couponmoney == null ? null : couponmoney.trim();
    }

    public String getCouponsurplus() {
        return couponsurplus;
    }

    public void setCouponsurplus(String couponsurplus) {
        this.couponsurplus = couponsurplus == null ? null : couponsurplus.trim();
    }

    public String getCouponreceive() {
        return couponreceive;
    }

    public void setCouponreceive(String couponreceive) {
        this.couponreceive = couponreceive == null ? null : couponreceive.trim();
    }

    public String getCouponreceive2() {
        return couponreceive2;
    }

    public void setCouponreceive2(String couponreceive2) {
        this.couponreceive2 = couponreceive2 == null ? null : couponreceive2.trim();
    }

    public String getTodaycouponreceive() {
        return todaycouponreceive;
    }

    public void setTodaycouponreceive(String todaycouponreceive) {
        this.todaycouponreceive = todaycouponreceive == null ? null : todaycouponreceive.trim();
    }

    public String getCouponnum() {
        return couponnum;
    }

    public void setCouponnum(String couponnum) {
        this.couponnum = couponnum == null ? null : couponnum.trim();
    }

    public String getCouponexplain() {
        return couponexplain;
    }

    public void setCouponexplain(String couponexplain) {
        this.couponexplain = couponexplain == null ? null : couponexplain.trim();
    }

    public String getCouponstarttime() {
        return couponstarttime;
    }

    public void setCouponstarttime(String couponstarttime) {
        this.couponstarttime = couponstarttime == null ? null : couponstarttime.trim();
    }

    public String getCouponendtime() {
        return couponendtime;
    }

    public void setCouponendtime(String couponendtime) {
        this.couponendtime = couponendtime == null ? null : couponendtime.trim();
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time == null ? null : start_time.trim();
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time == null ? null : end_time.trim();
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime == null ? null : starttime.trim();
    }

    public String getIsquality() {
        return isquality;
    }

    public void setIsquality(String isquality) {
        this.isquality = isquality == null ? null : isquality.trim();
    }

    public String getReport_status() {
        return report_status;
    }

    public void setReport_status(String report_status) {
        this.report_status = report_status == null ? null : report_status.trim();
    }

    public String getIs_brand() {
        return is_brand;
    }

    public void setIs_brand(String is_brand) {
        this.is_brand = is_brand == null ? null : is_brand.trim();
    }

    public String getIs_live() {
        return is_live;
    }

    public void setIs_live(String is_live) {
        this.is_live = is_live == null ? null : is_live.trim();
    }

    public String getLive_content() {
        return live_content;
    }

    public void setLive_content(String live_content) {
        this.live_content = live_content == null ? null : live_content.trim();
    }

    public String getGuide_article() {
        return guide_article;
    }

    public void setGuide_article(String guide_article) {
        this.guide_article = guide_article == null ? null : guide_article.trim();
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid == null ? null : videoid.trim();
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type == null ? null : activity_type.trim();
    }

    public String getGeneral_index() {
        return general_index;
    }

    public void setGeneral_index(String general_index) {
        this.general_index = general_index == null ? null : general_index.trim();
    }

    public String getPlanlink() {
        return planlink;
    }

    public void setPlanlink(String planlink) {
        this.planlink = planlink == null ? null : planlink.trim();
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name == null ? null : seller_name.trim();
    }

    public String getSeller_qq() {
        return seller_qq;
    }

    public void setSeller_qq(String seller_qq) {
        this.seller_qq = seller_qq == null ? null : seller_qq.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getSellernick() {
        return sellernick;
    }

    public void setSellernick(String sellernick) {
        this.sellernick = sellernick == null ? null : sellernick.trim();
    }

    public String getOnline_users() {
        return online_users;
    }

    public void setOnline_users(String online_users) {
        this.online_users = online_users == null ? null : online_users.trim();
    }

    public String getOriginal_img() {
        return original_img;
    }

    public void setOriginal_img(String original_img) {
        this.original_img = original_img == null ? null : original_img.trim();
    }

    public String getOriginal_article() {
        return original_article;
    }

    public void setOriginal_article(String original_article) {
        this.original_article = original_article == null ? null : original_article.trim();
    }

    public String getTaobaoPcDescUrl() {
        return taobaoPcDescUrl;
    }

    public void setTaobaoPcDescUrl(String taobaoPcDescUrl) {
        this.taobaoPcDescUrl = taobaoPcDescUrl == null ? null : taobaoPcDescUrl.trim();
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public List<ShopImg> getShopImgList() {
        return shopImgList;
    }

    public void setShopImgList(List<ShopImg> shopImgList) {
        this.shopImgList = shopImgList;
    }

    public String getCollectTag() {
        return collectTag;
    }

    public void setCollectTag(String collectTag) {
        this.collectTag = collectTag;
    }

    public String getCollectUserId() {
        return collectUserId;
    }

    public void setCollectUserId(String collectUserId) {
        this.collectUserId = collectUserId;
    }

    public String getQcUrl() {
        return qcUrl;
    }

    public void setQcUrl(String qcUrl) {
        this.qcUrl = qcUrl;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }

    public String getConduct() {
        return conduct;
    }

    public void setConduct(String conduct) {
        this.conduct = conduct;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getShopBang() {
        return shopBang;
    }

    public void setShopBang(String shopBang) {
        this.shopBang = shopBang;
    }

    public String getPostFree() {
        return postFree;
    }

    public void setPostFree(String postFree) {
        this.postFree = postFree;
    }
}