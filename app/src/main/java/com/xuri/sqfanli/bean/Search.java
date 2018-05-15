package com.xuri.sqfanli.bean;

import java.io.Serializable;

/**
 * Created by Jay&Vi on 2018/4/23.
 * 搜索页面热搜区域Model
 */

public class Search implements Serializable {

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * ID
     */
    private int id;
    /**
     * 搜索关键字
     */
    private String keyword;
    /**
     * 排序
     */
    private int rid;
    /**
     * 热搜商品名称
     */
    private String search;
    /**
     * 是否标记（1表示标记为红色 0表示默认颜色）
     */
    private String sign;

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public String getKeyword() {
        return keyword;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
    public int getRid() {
        return rid;
    }

    public void setSearch(String search) {
        this.search = search;
    }
    public String getSearch() {
        return search;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getSign() {
        return sign;
    }
}
