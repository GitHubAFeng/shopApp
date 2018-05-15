package com.xuri.sqfanli.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jay&Vi on 2018/4/19.
 */

public class RecommendList implements Serializable {
    private String createTime;
    private String directoryId;
    private String id;
    private String menusId;
    private String name;
    private int rid;
    private List<ShopType> shopType;
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setDirectoryId(String directoryId) {
        this.directoryId = directoryId;
    }
    public String getDirectoryId() {
        return directoryId;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setMenusId(String menusId) {
        this.menusId = menusId;
    }
    public String getMenusId() {
        return menusId;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
    public int getRid() {
        return rid;
    }

    public void setShopType(List<ShopType> shopType) {
        this.shopType = shopType;
    }
    public List<ShopType> getShopType() {
        return shopType;
    }
}
