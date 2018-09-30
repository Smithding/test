package com.tempus.gss.product.ift.dao;

import java.util.List;

import com.tempus.gss.product.ift.api.entity.AdjustOrder;
import org.apache.ibatis.annotations.Param;

public interface AdjustOrderDao {
	
	int insert(AdjustOrder record);

	List<AdjustOrder> getAdjustOrderByOrderId(@Param("orderid") String saleOrderNo);

	void updateByPrimaryKeySelective(AdjustOrder ado);
	
}