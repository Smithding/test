package com.tempus.gss.product.hol.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.response.HotelOrder;
import com.tempus.gss.product.hol.api.entity.vo.HotelOrderVo;

/**
 *
 * HotelOrder 表数据库控制层接口
 *
 */
public interface HotelOrderMapper extends AutoMapper<HotelOrder> {

	/**
	 * 修改订单状态
	 */
	boolean updateStatus(HotelOrder hotelOrder);

	/**
	 * 根据订单编号获取酒店订单
	 * @param hotelOrderNo
	 * @return
	 */
	HotelOrder getOrderByNo(String hotelOrderNo);

	/**
	 * 根据条件查询订单列表
	 * @param hotelOrderVo
	 * @return
	 */
	List<HotelOrder> queryOrderList(Page<HotelOrder> page, HotelOrderVo hotelOrderVo);
	
	/**
	 * 根据条件查询分销商订单列表
	 * @param hotelOrderVo
	 * @return
	 */
	List<HotelOrder> queryCustomerOrderList(Page<HotelOrder> page, Map<String, Object> map);
	
	/**
	 * 根据订单状态查询订单列表
	 * @param orderStatus
	 * @return
	 */
	List<HotelOrder> queryOrderByOrderResultCode(String resultCode);


}