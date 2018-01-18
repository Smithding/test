package com.tempus.gss.product.ift.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.tempus.gss.product.ift.api.entity.GssMain;


public interface GssMainDao extends AutoMapper<GssMain> {
	/**
	 * 
	 * selectByOrderNo:根据订单号查询条件查询
	 *
	 * @param map
	 * @return
	 */
	GssMain selectByOrderNo(Long orderNo);
}