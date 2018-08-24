package com.tempus.gss.product.ift.api.entity.policy;

import java.io.Serializable;
import java.util.List;

/**
 * 区的shit
 * @author juan.yin
 *
 */
public class Areas implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3372731325622929800L;
	/**
	 * 英文备注
	 */
	private String key;
	/**
	 * 中文备注
	 */
	private String label;
	/**
	 * 州的数据集合
	 */
	private List<IftContinent> data;
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
	public List<IftContinent> getData() {
		return data;
	}
	public void setData(List<IftContinent> data) {
		this.data = data;
	}
}
