package com.xuri.sqfanli.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jay&Vi on 2018/4/19.
 * 分类对象
 */

public class Categories implements Serializable {

    private String caiTag;
    private String cid;
    private String classify;
    private String createTime;
    private String id;
    private String img;
    private String keyword;
    private String name;
    private String number;
    private String pid;
    private String recommendId;
    private List<RecommendList> recommendList;
    private String recommendName;
    private int rid;
    private String sex;
    private List<ShopTypeImg> shopTypeImg;
    private String stick;
    private String toShopNum;
    private String typeSex;
    public void setCaiTag(String caiTag) {
        this.caiTag = caiTag;
    }
    public String getCaiTag() {
        return caiTag;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
    public String getCid() {
        return cid;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }
    public String getClassify() {
        return classify;
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

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public String getKeyword() {
        return keyword;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public String getNumber() {
        return number;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
    public String getPid() {
        return pid;
    }

    public void setRecommendId(String recommendId) {
        this.recommendId = recommendId;
    }
    public String getRecommendId() {
        return recommendId;
    }

    public void setRecommendList(List<RecommendList> recommendList) {
        this.recommendList = recommendList;
    }
    public List<RecommendList> getRecommendList() {
        return recommendList;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }
    public String getRecommendName() {
        return recommendName;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
    public int getRid() {
        return rid;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSex() {
        return sex;
    }

    public void setShopTypeImg(List<ShopTypeImg> shopTypeImg) {
        this.shopTypeImg = shopTypeImg;
    }
    public List<ShopTypeImg> getShopTypeImg() {
        return shopTypeImg;
    }

    public void setStick(String stick) {
        this.stick = stick;
    }
    public String getStick() {
        return stick;
    }

    public void setToShopNum(String toShopNum) {
        this.toShopNum = toShopNum;
    }
    public String getToShopNum() {
        return toShopNum;
    }

    public void setTypeSex(String typeSex) {
        this.typeSex = typeSex;
    }
    public String getTypeSex() {
        return typeSex;
    }

}
