package com.xuri.sqfanli.bean;

/**
 * @ClassName: MainAdv
 * @Description: TODO(首页轮播广告)
 * @author: 王东
 * @date: 2017年10月18日 上午11:12:54
 */
public class MainAdv {
	private Integer id;
	/**
	 * @Fields advType : TODO(广告类型，1打开网页，2下载APP,3跳转商品列表,4跳转淘宝链接)
	*/
	private String advType;
	/**
	 * @Fields title : TODO(标题)
	*/
	private String title;
	/**
	 * @Fields advPackName : TODO(下载app时，需要填写app的包名)
	*/
	private String advPackName;
	/**
	 * @Fields advImg : TODO(广告图片)
	*/
	private String advImg;
	/**
	 * @Fields url : TODO(链接)
	*/
	private String url;
	/**
	 * @Fields rid : TODO(排序)
	*/
	private String rid;
	/**
	 * @Fields createTime : TODO(创建时间)
	*/
	private String createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdvType() {
		return advType;
	}

	public void setAdvType(String advType) {
		this.advType = advType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAdvPackName() {
		return advPackName;
	}

	public void setAdvPackName(String advPackName) {
		this.advPackName = advPackName;
	}

	public String getAdvImg() {
		return advImg;
	}

	public void setAdvImg(String advImg) {
		this.advImg = advImg;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
