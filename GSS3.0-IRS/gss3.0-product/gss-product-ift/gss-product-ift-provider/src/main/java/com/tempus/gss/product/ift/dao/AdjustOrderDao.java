package com.tempus.gss.product.ift.dao;

import java.util.List;

import com.tempus.gss.product.ift.api.entity.AdjustOrder;

public interface AdjustOrderDao {
	
	int insert(AdjustOrder record);

	List<AdjustOrder> getAdjustOrderByOrderId(String saleOrderNo);

	void updateByPrimaryKey(AdjustOrder ado);
	
}