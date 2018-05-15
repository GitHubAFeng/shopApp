package com.xuri.sqfanli.bean;

import java.io.Serializable;

/**
 * Created by Jay&Vi on 2018/4/19.
 */

public class ShopTypeImg implements Serializable{

    private String cid;
    private String createTime;
    private String id;
    private String img;
    private String imgType;
    private String imgUrl;
    private String keyword;
    private String rid;
    private String shopNum;
    private String shopTypeId;
    private String itemId;
    private String title;

    public void setCid(String cid) {
        this.cid = cid;
    }
    public String getCid() {
        return cid;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getImg() {
        return img;
    }

    public String getImgType() {
        return imgType;
    }
    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public String getKeyword() {
        return keyword;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
    public String getRid() {
        return rid;
    }

    public void setShopNum(String shopNum) {
        this.shopNum = shopNum;
    }
    public String getShopNum() {
        return shopNum;
    }

    public void setShopTypeId(String shopTypeId) {
        this.shopTypeId = shopTypeId;
    }
    public String getShopTypeId() {
        return shopTypeId;
    }

    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
