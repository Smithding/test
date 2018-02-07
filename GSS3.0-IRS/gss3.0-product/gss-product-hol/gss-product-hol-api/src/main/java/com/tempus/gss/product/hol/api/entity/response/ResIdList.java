package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 *用来做测试，成功插入的酒店ID
 * @author kai.yang
 *
 */
public class ResIdList implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 酒店 Id
	 */
	@JSONField(name = "ResId")
	private Long resId;




	public Long getResId() {
		return resId;
	}


	public void setResId(Long resId) {
		this.resId = resId;
	}


	

	
	
	

}
