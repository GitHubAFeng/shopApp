package com.xuri.sqfanli.event;

/**
 * Created by AFeng on 2018/4/28.
 * 普通事件类
 */

public class MessageEvent {
    private String message;
    public MessageEvent(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
