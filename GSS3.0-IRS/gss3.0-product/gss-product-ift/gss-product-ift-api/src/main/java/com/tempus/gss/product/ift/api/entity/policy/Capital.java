package com.tempus.gss.product.ift.api.entity.policy;

import java.io.Serializable;
import java.util.List;
/**
 * 所有的省份
 * @author juan.yin
 *
 */
public class Capital implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2202908495296734372L;
	/**
	 * 英文备注
	 */
	private String key;
	/**
	 * 中文备注
	 */
	private String label;
	/**
	 * 城市三字码的集合
	 */
	private List<IftCity> data;
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
	public List<IftCity> getData() {
		return data;
	}
	public void setData(List<IftCity> data) {
		this.data = data;
	}
}
