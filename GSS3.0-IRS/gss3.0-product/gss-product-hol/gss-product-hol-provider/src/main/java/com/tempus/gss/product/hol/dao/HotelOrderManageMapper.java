package com.tempus.gss.product.hol.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.response.HotelShowOrder;
import com.tempus.gss.product.hol.api.entity.vo.HotelOrderVo;

public interface HotelOrderManageMapper  extends AutoMapper<HotelShowOrder> {
	/**
	 * 根据条件查询订单列表
	 * @param hotelOrderVo
	 * @return
	 */
	List<HotelShowOrder> queryOrderList(Page<HotelShowOrder> page, HotelOrderVo hotelOrderVo);
}
