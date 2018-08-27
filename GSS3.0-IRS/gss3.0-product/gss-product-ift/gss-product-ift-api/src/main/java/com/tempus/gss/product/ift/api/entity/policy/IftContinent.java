package com.tempus.gss.product.ift.api.entity.policy;

import java.io.Serializable;
import java.util.List;

/**
 * 所有的国家
 * @author juan.yin
 *
 */
public class IftContinent implements Serializable{
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
	 * 省份的数据集合（境外或者是全球。对应是城市三字码，如果是国内，对应是省份）
	 */
	private List<Capital> data;
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
	public List<Capital> getData() {
		return data;
	}
	public void setData(List<Capital> data) {
		this.data = data;
	}
}
