package com.xuri.sqfanli.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jay&Vi on 2018/4/19.
 * 分类页面model
 */

public class SumShopType implements Serializable {

    private String code;
    private String message;
    private List<Categories> object;
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setObject(List<Categories> object) {
        this.object = object;
    }
    public List<Categories> getObject() {
        return object;
    }

}
