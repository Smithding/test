package com.tempus.gss.product.ift.api.entity.policy;

import java.io.Serializable;

/**
 * 城市三字码
 * @author juan.yin
 *
 */
public class IftCity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 英文备注
	 */
	private String key;
	/**
	 * 中文备注
	 */
	private String label;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
