package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.BuyOrderExt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BuyOrderExtDao extends BaseDao<BuyOrderExt, Object> {

	/**
	 * 通过销售单编号获取采购单
	 * @param saleOrderNo
	 * @return
	 */
	List<BuyOrderExt> selectBuyOrderBySaleOrderNo(Long saleOrderNo);

	int updateByOrderNo(BuyOrderExt buyOrderExt);

	int updateByChangeOrderNo(BuyOrderExt buyOrderExt);
}