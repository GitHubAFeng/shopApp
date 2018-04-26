package com.xuri.sqfanli.ui.base;

import java.util.Date;
import java.util.List;

/**
 * ClassName: ShopType
 * @Description: TODO(商品分类)
 * @author 孙健兴
 * @date 2018年3月1日
 */
public class ShopType {
	/**
	 * @Fields id : TODO(一级商品分类id)
	 */
	private String id;
	/**
	 * @Fields sex : TODO(性别:1为男版，2为女版)
	 */
	private String sex;
	/**
	 * @Fields typeSex : TODO(一级分类所属性别：1为男版专有分类，2为女版专有分类，3为男女版共有分类)
	 */
	private String typeSex;
	/**
	 * @Fields pid : TODO(所属一级商品分类id)
	 */
	private String pid;
	/**
	 * @Fields name : TODO(商品分类名称)
	 */
	private String name;
	/**
	 * @Fields img : TODO(二级分类才显示，商品图片)
	 */
	private String img;
	/**
	 * @Fields rid : TODO(排序)
	 */
	private String rid;
	/**
	 * @Fields list : TODO(二级分类)
	 */
	private List<ShopType> list;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTypeSex() {
		return typeSex;
	}

	public void setTypeSex(String typeSex) {
		this.typeSex = typeSex;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}



	public List<ShopType> getList() {
		return list;
	}

	public void setList(List<ShopType> list) {
		this.list = list;
	}

}
