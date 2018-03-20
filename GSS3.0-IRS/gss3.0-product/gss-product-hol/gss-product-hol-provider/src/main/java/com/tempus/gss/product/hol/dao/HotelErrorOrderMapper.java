package com.tempus.gss.product.hol.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.response.HolErrorOrder;
import com.tempus.gss.product.hol.api.entity.vo.HotelOrderVo;

public interface HotelErrorOrderMapper extends AutoMapper<HolErrorOrder> {
	/**
	 * 根据条件查询订单列表
	 * @param hotelOrderVo
	 * @return
	 */
	List<HolErrorOrder> queryErrorOrderList(Page<HolErrorOrder> page, HotelOrderVo hotelOrderVo);
}
