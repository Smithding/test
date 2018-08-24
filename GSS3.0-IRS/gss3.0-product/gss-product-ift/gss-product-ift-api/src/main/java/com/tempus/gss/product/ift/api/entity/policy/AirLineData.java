package com.tempus.gss.product.ift.api.entity.policy;

import java.io.Serializable;
import java.util.List;

/**
 * 政策城市控件全球  州   区   国家 省 城市
 *         境内  国家  省   城市
 *         境外 州   区   国家  城市
 * @author juan.yin
 *
 */
public class AirLineData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6018969299007747384L;
	/**
	 * 境内CN0，境外CN1，全球到全球CN2
	 */
	private String key;
	/**
	 * 中文备注
	 */
	private String label;
	/**
	 * 州的数据集合
	 */
	private List<States> data;
	public List<States> getData() {
		return data;
	}
	public void setData(List<States> data) {
		this.data = data;
	}
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
