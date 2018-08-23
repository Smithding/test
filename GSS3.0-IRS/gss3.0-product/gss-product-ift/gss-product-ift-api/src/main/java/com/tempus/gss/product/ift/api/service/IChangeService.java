package com.tempus.gss.product.ift.api.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.SaleChange;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.BuyChangeExt;
import com.tempus.gss.product.ift.api.entity.PassengerChangePrice;
import com.tempus.gss.product.ift.api.entity.SaleChangeExt;
import com.tempus.gss.product.ift.api.entity.vo.*;
import com.tempus.gss.vo.Agent;

/**
 * 国际机票改签服务接口.
 */
public interface IChangeService {

	/**
	 * 创建改签单.
	 *
	 * @param requestWithActor
	 * @return
	 */
	Long createChange(RequestWithActor<ChangeCreateVo> requestWithActor);
	
	/**
	 * 创建改签单.
	 *
	 * @param requestWithActor
	 * @return
	 */
	SaleChangeExt apiCreateChange(RequestWithActor<ChangeCreateVo> requestWithActor);

	/**
	 * 改签单创建应收应付.
	 *
	 * @param passengerChangeList
	 * @return
	 */
	boolean verify(RequestWithActor<List<PassengerChangePrice>> passengerChangeList);

	/**
	 * 改签单打回退款创建应收应付
	 *
	 * @param passengerChangeList
	 * @return
	 */
	boolean refundVerify(RequestWithActor<List<PassengerChangePrice>> passengerChangeList);

	/**
	 * 创建销售退款单
	 * @param agent
	 * @param saleChangeNo
	 * @return
	 * @throws GSSException
	 */
	boolean saleRefund(Agent agent, Long saleChangeNo) throws GSSException;

	/**
	 * 创建采购退款单
	 * @param agent
	 * @param saleChangeNo
	 * @param saleOrderNo
	 * @return
	 * @throws GSSException
	 */
	boolean buyRefund(Agent agent, Long saleChangeNo,Long saleOrderNo) throws GSSException;

	/**
	 * 通过废退单编号获取销售废退单（包含saleChange）
	 * @param requestWithActor  saleChangeNo
	 * @return
	 */
	SaleChangeExt getSaleChangeExtByNo(RequestWithActor<Long> requestWithActor);

	/**
	 * 审核改签单.
	 * @param requestWithActor
	 * @return
	 */
	boolean changeAudit(RequestWithActor<ChangePriceRequest> requestWithActor);

	/**
	 * 审核改签采购打回单.
	 * @param requestWithActor
	 * @return
	 */
	boolean changeReAudit(RequestWithActor<ChangePriceRequest> requestWithActor);
	/**
	 * 改签处理.
	 *
	 * @param changeTicketRequest
	 * @return
	 */
	boolean changeHandle(RequestWithActor<ChangeTicketRequest> changeTicketRequest);

	/**
	 * 更新采购变更单状态
	 * @param buyChangeNo
	 * @return
	 */
	boolean updateBuyChangeStatus(RequestWithActor<Long> buyChangeNo);
	/**
	 * 改签出单确认.
	 *
	 * @param 
	 * @return
	 */
	boolean changeConfirm(RequestWithActor<ChangePriceRequest> requestWithActor);

	/**
	 * 取消改签.
	 *
	 * @param saleChangeNo
	 * @return
	 */
	boolean cancel(RequestWithActor<Long> saleChangeNo);

	/**
	 * 改签拒单.
	 *
	 * @param saleChangeNo
	 * @return
	 */
	boolean refuse(RequestWithActor<Long> saleChangeNo,String reason);

	/**
	 * 改签单查询.
	 *
	 * @param saleChangeQueryRequest
	 * @return
	 */
	Page<SaleChangeExt> querySaleChange(Page<SaleChangeExt> page,RequestWithActor<SaleChangeExtVo> saleChangeQueryRequest);
	/**
	 * 根据改签单编号查询详情.
	 *
	 * @param saleChangeNo
	 * @return
	 */
	SaleChangeExt getSaleChange(RequestWithActor<Long> saleChangeNo);

	/*
    * 通过销售单编号获取改签变更单 saleOrderNO
    * */
	List<SaleChangeExt> getSaleChangeExt(RequestWithActor<Long> saleOrderNo);
	
	
	/**
	 * 锁住订单
	 * @param saleChange
	 * @return
	 */
	boolean locker(RequestWithActor<SaleChangeExtVo> saleChange);


	/**
	 * 当客商编号不一致的时候创建采购单
	 *
	 * @param requestWithActor
	 * @return
	 */
	boolean createBuyOrder(RequestWithActor<ChangeCreateVo> requestWithActor);

	/**
	 * 订单改签通知
	 * @param orderInformVo
	 * @return
	 */
	int orderChangeInform(OrderInformVo orderInformVo);

	int updateByPrimarykey(SaleChangeExt salechangeExt);

	/**
	 * 获取改签审核信息
	 */
	BuyChangeExt getBuyChangeExtBySaleChangeNo(Long saleChangeNo);

	/**
	 * 拒回的销售变更单locker设置之前销售审核然后更新之前locker的数量
	 * @param requestWithActor
	 */
    void clearLockerAndUpdateOldLocker(RequestWithActor<Long> requestWithActor,String type);

	/**
	 * 采购改签分单任务
	 */
	void assignChange(RequestWithActor<Long> requestWithActor);

	public int updateLocker(SaleChangeExt changeExt);
	//更新销售备注
    void  updateBuyRemarkBySelectBuyChangeNo(BuyChangeExt buyChangeExt);
    //事务原因创建的服务
    int queryChangeCountBylocker(Long userId);

	 SaleChangeExt updateSaleChangeExt(RequestWithActor<ChangePriceRequest> requestWithActor, SaleChangeExt saleChangeExt);

	/**
	 * 根据原单号获取所有的改签单（不包括退废）
	 * @param agent
	 * @param saleOrderNo
	 * @return
	 */
    List<SaleChange> getSaleChangeBySONo(Agent agent, Long saleOrderNo);
}
