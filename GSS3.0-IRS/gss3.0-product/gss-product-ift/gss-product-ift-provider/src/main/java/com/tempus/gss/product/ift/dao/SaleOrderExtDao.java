package com.tempus.gss.product.ift.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.product.ift.api.entity.vo.DemandTeamVo;
import com.tempus.gss.product.ift.api.entity.vo.QueryPnrAndTimeVo;
import com.tempus.gss.product.ift.api.entity.vo.ReportVo;
import com.tempus.gss.product.ift.api.entity.vo.SaleOrderExtVo;

import java.util.List;

import com.tempus.gss.product.ift.api.entity.vo.SaleQueryOrderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface SaleOrderExtDao extends BaseDao<SaleOrderExt, SaleOrderExtVo> {

	/**
	 * 待核价被locker锁定的订单数量
	 * @param locker
	 * @return
	 */
	int querySaleCountByLocker(@Param("locker") long locker);
	/**
	 * 待出票被locker锁定的订单数量
	 * @param locker
	 * @return
	 */
	int queryBuyCountByLocker(@Param("locker") long locker);
	/**
	 * 锁定、解锁
	 *
	 * @param record
	 * @return
	 */
	int updateLocker(SaleOrderExt record);
	
	/**
	 * 根据条件查询未核价订单
	 * @param page
	 * @param record
	 * @return
	 */
	List<SaleOrderExt> queryNoHandleList(Page<SaleOrderExt> page,SaleOrderExtVo record);

	/**
	 *为运营平台订单管理提供服务.(预定)
	 * 根据特殊查询条件查询订单
	 * @param page
	 * @param saleQueryOrderVo
	 * @return
	 */
	List<SaleOrderExt> queryFromSaleQueryOrderVo(Page<SaleOrderExt> page,SaleQueryOrderVo saleQueryOrderVo);

	/***
	 * 为订单分配提供
	 * @param saleQueryOrderVo
	 * @return
	 */
	//List<SaleOrderExt> queryFromSaleQueryOrderVo(SaleQueryOrderVo saleQueryOrderVo);
	List<SaleOrderExt> queryAssignOrder(SaleQueryOrderVo saleQueryOrderVo);
	/**
	 *查询需求单详情
	 * 根据特殊查询条件查询订单
	 * @param demandNo
	 * @return
	 */
	SaleOrderExt selectDemandOrder(Long demandNo);
	
	/**
	 * 根据票号查询
	 */
	List<ReportVo> selectByTicketNo(String ticketNo);
	
	SaleOrderExt queryByPnrNo(Long pnrNo);
	
	SaleOrderExt queryByPnrNoAndTime(QueryPnrAndTimeVo vo);

	SaleOrderExt selectDemandTeamOrder(DemandTeamVo demandTeamVo);

	/**
	 * 查询已出票，但是没有配送产品，并且在这个表(LS_DELIVERY_BATCH_OS)没有数据
	 * @param page
	 * @param saleQueryOrderVo
	 * @return
	 */
	List<SaleOrderExt> selectOutTicketOrder(Page<SaleOrderExt> page,SaleQueryOrderVo saleQueryOrderVo);
}