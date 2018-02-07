package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 酒店图片集合
 * @author kai.yang
 *
 */
public class ImgInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店 Id
	 */
	@JSONField(name = "ResId")
	private Long resId;
	/**
	 * 图片名称
	 */
	@JSONField(name = "ImageName")
	private String imageName;
	/**
	 * 图片路径
	 */
	@JSONField(name = "ImageUrl")
	private String imageUrl;
	/**
	 * 是否酒店默认图片（0 不是，1 是）
	 */
	@JSONField(name = "IsResDefault")
	private Integer isResDefault;
	/**
	 * 是否酒店房型默认图片（0 不是，1 是）
	 */
	@JSONField(name = "IsResProDefault")
	private Integer isResProDefault;
	/**
	 * 酒店房型 id（匹配 ProId 房型标示 id中"_"分隔的第一个 id，
	 * 例：ProId=123_4_5,则 123 即为 ResProId）
	 */
	@JSONField(name = "ResProId")
	private Integer resProId;
	/**
	 * 排 序 值 （ 倒 序 ， 酒 店 的 排 序 ：ResDefault > ResProDefault > Sort）
	 */
	@JSONField(name = "Sort")
	private Integer sort;
	/**
	 * 图片分类标签（0-默认值；1：外观；2：大厅；3：周边；
	 * 4：房型；5：浴室；6：餐厅；7：会议室；8：休闲；
	 * 9：公共区域；10：游泳池；11：健身房；
	 * 12：酒吧/休息室；13：客房设施；14：酒店设施；20：其他）
	 */
	@JSONField(name = "ImageLabel")
	private Byte imageLabel;
	/**
	 * 文字描述
	 */
	@JSONField(name = "Description")
	private String description;
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getIsResDefault() {
		return isResDefault;
	}
	public void setIsResDefault(Integer isResDefault) {
		this.isResDefault = isResDefault;
	}
	public Integer getIsResProDefault() {
		return isResProDefault;
	}
	public void setIsResProDefault(Integer isResProDefault) {
		this.isResProDefault = isResProDefault;
	}
	public Integer getResProId() {
		return resProId;
	}
	public void setResProId(Integer resProId) {
		this.resProId = resProId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Byte getImageLabel() {
		return imageLabel;
	}
	public void setImageLabel(Byte imageLabel) {
		this.imageLabel = imageLabel;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
