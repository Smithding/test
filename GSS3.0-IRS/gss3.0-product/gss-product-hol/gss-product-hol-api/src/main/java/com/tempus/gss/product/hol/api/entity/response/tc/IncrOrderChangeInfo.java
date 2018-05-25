package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 获取某一时间段内的发生变更的订单号及变更信息
 * @author kai.yang
 *
 */
public class IncrOrderChangeInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 增量信息集合
	 */
	@JSONField(name = "OrderIncrementList")
	private List<OrderIncrementInfo> orderIncrementList;
	/**
	 * 最后的更新 id
	 */
	@JSONField(name = "EndIncrementId")
	private Long endIncrementId;

	public List<OrderIncrementInfo> getOrderIncrementList() {
		return orderIncrementList;
	}

	public void setOrderIncrementList(List<OrderIncrementInfo> orderIncrementList) {
		this.orderIncrementList = orderIncrementList;
	}

	public Long getEndIncrementId() {
		return endIncrementId;
	}

	public void setEndIncrementId(Long endIncrementId) {
		this.endIncrementId = endIncrementId;
	}
	
	
}
