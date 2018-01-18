package com.tempus.gss.product.hol.api.entity.vo;

import java.io.Serializable;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 酒店供应商
 *
 */
@TableName("HOL_SUPPLIER")
public class ContextVo implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	private String appStore;
	
	private String key;

	public String getAppStore() {
		return appStore;
	}

	public void setAppStore(String appStore) {
		this.appStore = appStore;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
