package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.SaleChangeExt;
import com.tempus.gss.product.ift.api.entity.vo.SaleChangeExtVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SaleChangeExtDao extends BaseDao<SaleChangeExt, SaleChangeExtVo> {

	/*根据销售单编号查询销售拓展单*/
	List<SaleChangeExt> queryBySaleOrderNo(Long saleOrderNo);
	/**
	 * 锁定、解锁
	 *
	 * @param record
	 * @return
	 */
	int updateLocker(SaleChangeExt record);

	/**
	 * 根据销售单订单编号修改数据
	 * @param saleChangeExt
	 * @return
	 */
	int updateByOrderNo(SaleChangeExt saleChangeExt);
}