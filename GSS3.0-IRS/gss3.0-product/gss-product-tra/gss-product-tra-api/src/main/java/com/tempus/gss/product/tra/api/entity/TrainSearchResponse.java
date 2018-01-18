package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;
import java.util.List;

public class TrainSearchResponse  implements Serializable {

	/**  
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）  
	 */
	private static final long serialVersionUID = 1L;
	
	private String queryKey;//下单接口使用的queryKey
	
	public String getQueryKey() {
		return queryKey;
	}
	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}
	private List<Train> data;
	
	public List<Train> getData() {
		return data;
	}
	public void setData(List<Train> data) {
		this.data = data;
	}
}
