package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取某一时间段酒店信息
 * @author kai.yang
 *
 */
public class AssignDateHotel implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店 Id
	 */
	@JSONField(name = "ResId")
	private Long resId;
	/**
	 * 房型详情信息集合
	 */
	@JSONField(name = "ProInfoDetailList")
	private List<ProInfoDetail> proInfoDetailList;
	
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public List<ProInfoDetail> getProInfoDetailList() {
		return proInfoDetailList;
	}
	public void setProInfoDetailList(List<ProInfoDetail> proInfoDetailList) {
		this.proInfoDetailList = proInfoDetailList;
	}
}
