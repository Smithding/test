package com.tempus.gss.product.ift.api.entity.policy;

import java.io.Serializable;
import java.util.List;

/**
 * 州的实体类
 * @author juan.yin
 *
 */
public class States implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 英文备注
	 */
	private String key;
	/**
	 * 中文备注
	 */
	private String label;
	/**
	 * 区的集合
	 */
	private List<Areas> data;
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
	public List<Areas> getData() {
		return data;
	}
	public void setData(List<Areas> data) {
		this.data = data;
	}
}
