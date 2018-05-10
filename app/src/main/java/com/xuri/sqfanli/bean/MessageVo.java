package com.xuri.sqfanli.bean;

/**
 * @Title: MessageVo 
 * @Description: TODO 返回成功与否对象
 * @author 王东
 * @date 2016年11月17日
 */
public class MessageVo {
	//返回状态码
	private String code;
	//提示消息
	private String message;

	private User object;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public User getObject() {
		return object;
	}

	public void setObject(User object) {
		this.object = object;
	}
}