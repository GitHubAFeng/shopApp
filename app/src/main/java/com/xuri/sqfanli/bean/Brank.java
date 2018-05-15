package com.xuri.sqfanli.bean;

import java.util.List;

/**
 * ClassName: Brank (品牌)
 * @Description: TODO
 * @author 王东
 * @date 2018年03月05日
 */
public class Brank {
	private String id;
	private String activityId;
	private String title;//
	private String subTitle;//
	private String logo;
	private List<Shop> bankShop;
	private List<Shop> tuiShop;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<Shop> getBankShop() {
		return bankShop;
	}

	public void setBankShop(List<Shop> bankShop) {
		this.bankShop = bankShop;
	}

	public List<Shop> getTuiShop() {
		return tuiShop;
	}

	public void setTuiShop(List<Shop> tuiShop) {
		this.tuiShop = tuiShop;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

}
