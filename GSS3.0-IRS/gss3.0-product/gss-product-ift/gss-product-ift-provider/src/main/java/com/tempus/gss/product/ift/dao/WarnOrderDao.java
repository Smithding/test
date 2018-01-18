package com.tempus.gss.product.ift.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.WarnOrder;
import com.tempus.gss.product.ift.api.entity.vo.WarnOrderVO;

/**
 *
 * WarnOrder 表数据库控制层接口
 *
 */
@Component
public interface WarnOrderDao extends BaseDao<WarnOrder, WarnOrderVO> {

	List<WarnOrder> listWarnOrder(Page<WarnOrder> page, WarnOrderVO entity);

	List<WarnOrder> findWarnOrders(WarnOrder warnOrder);

	//获取超过1天未处理的订单数量
	Long getOutTimeWarnOrders();

}