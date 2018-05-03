package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 商圈
 */
public class InfoShowlist implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JSONField(name="Infolist")
	private List<Information> infolist;		//商圈信息集合
	
	private String keyword;					//对应key

	public List<Information> getInfolist() {
		return infolist;
	}

	public void setInfolist(List<Information> infolist) {
		this.infolist = infolist;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
