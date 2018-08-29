package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.SaleChange;
import com.tempus.gss.product.ift.api.entity.BuyChangeExt;
import com.tempus.gss.product.ift.api.entity.PassengerRefundPrice;
import com.tempus.gss.product.ift.api.entity.SaleChangeExt;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.product.ift.api.entity.vo.*;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.vo.Agent;

import java.util.List;

/**
 * 国际机票废退服务接口.
 */
public interface IRefundService {

	/**
	 * 创建废退单.
	 *
	 * @param requestWithActor 根据销售单，创建销售废退单、采购废退单
	 * @return
	 */
	boolean createRefund(RequestWithActor<RefundCreateVo> requestWithActor);
	
	/**
	 * 
	 * @param requestWithActor
	 * @return
	 */
	SaleChangeExt createRefundExt(RequestWithActor<RefundCreateVo> requestWithActor);

	/**
	 * 废退单查询.
	 * @param queryRequest
	 * @return
	 */
  /*  PageResult<RefundQueryResponse> query(RequestWithActor<Long> queryRequest);*/

	/**
	 * 获取废退单.
	 *
	 * @param
	 * @return
	 */
	/*RefundGetResponse get(RefundGetRequest getRequest);*/

    /*
    * 通过销售单编号获取废退变更单 saleOrderNO
    * */
	List<SaleChangeExt> getSaleChangeExt(RequestWithActor<Long> saleOrderNO);

	/**
	 * 通过废退单编号获取销售废退单（包含saleChange）
	 * @param requestWithActor  saleChangeNo
	 * @return
	 */
    SaleChangeExt getSaleChangeExtByNo(RequestWithActor<Long> requestWithActor);

	/**
	 * 获得采购变更单
	 * @param saleChangeNo
	 * @return
	 */
	BuyChangeExt getBuyChangeExtBySaleChangeNo(Long saleChangeNo);

	/*
	*
	* 锁住销售废退单
	* */
	boolean lockRefund(RequestWithActor<Long> requestWithActor);

	/*
	*
	* 解锁销售废退单
	* */
	boolean unLock(RequestWithActor<Long> requestWithActor);

	public int updateLocker(SaleChangeExt saleChangeExt);

	/**
	 * 废退单审核
	 * 填入乘客退废税费
	 *
	 * @param passengerRefundList
	 * @return
	 */
	//boolean verify(RequestWithActor<List<PassengerRefundPrice>> passengerRefundList);

	/**
	 * 废退单审核创建爱采购应收
	 * @param requestWithActor
	 * @return
	 */
	boolean buyVerify(RequestWithActor<Long> requestWithActor);

	/**
	 * 销售废退单审核 创建销售应收
	 * @param passengerRefundList
	 * @return
	 */
	boolean saleVerify(RequestWithActor<List<PassengerRefundPrice>> passengerRefundList);

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
	 * @param requestWithActor
	 * @param saleOrderNo
	 * @return
	 * @throws GSSException
	 */
	boolean buyRefund(RequestWithActor<AirLineRefundRequest> requestWithActor,Long saleOrderNo) throws GSSException;

	/**
	 * 废票处理
	 *
	 * @param saleOrderNo
	 * @return
	 */
	boolean invalidHandle(RequestWithActor<Long> saleOrderNo);

	/**
	 * 退票处理
	 *
	 * @param saleOrderNo
	 * @return
	 */
	boolean refundHandle(RequestWithActor<Long> saleOrderNo);

	/**
	 * 取消废退.
	 *
	 * @param saleChangeNo
	 * @return
	 */
	boolean cancel(RequestWithActor<Long> saleChangeNo);

	/**
	 * 拒绝废退.
	 *
	 * @param saleOrderNo
	 * @param reason
	 * @return
	 */
	boolean refuse(RequestWithActor<Long> saleOrderNo, String reason);

	/**
	 * 通过条件VO查询废退改签单
	 * @param requestWithActor
	 * @return
	 */
	Page<SaleChangeVo> querySaleChangeExtByVo(Page page,RequestWithActor<SaleChangeExtVo> requestWithActor) throws Exception;

	/**
	 * 通过条件VO查询废退改签单
	 * @param requestWithActor
	 * @return
	 */
	Page<SaleChangeExt> queryListByVo(Page<SaleChangeExt> page,RequestWithActor<SaleChangeExtVo> requestWithActor) throws Exception;

	/**
	 * 修改
	 */
	
	boolean updateSaleChangeExt(RequestWithActor<SaleChangeExt> saleOrderChangeExt);

	/**
	 * 订单退费通知
	 * @param orderInformVo
	 * @return
	 */
	int orderRefundInform(OrderInformVo orderInformVo);

	/**
	 * 申请杂费单时创建的销售变更单
	 * @return
	 * @param saleOrderExt
	 */
	public SaleChange createAjustSaleOrder(RequestWithActor<SaleOrderExt> saleOrderExt, Integer IncomeExpenseType);

	/**
	 * 采购废票分单
	 */
	void assignBuyWaste(RequestWithActor<Long> requestWithActor);

	/**
	 * 采购退票分单
	 */
	void assignBuyRefund(RequestWithActor<Long> requestWithActor);

	/**
	 * 提交航司后 采购打回重审
	 * @param requestWithActor
	 */
	void shoppingRefuse(RequestWithActor<Long> requestWithActor);

	/**
	 * 提交航司后 采购审核通过
	 * @param requestWithActor
	 */
	void shoppingAuditPass(RequestWithActor<AirLineRefundRequest> requestWithActor);
	/**
	 *废票退票 销售退款
	 * @param requestWithActor
	 */
	void saleReturnMoney(RequestWithActor<PassengerRefundPriceVo> requestWithActor);

	/**
	 * 废票退票 采购退款
	 * @param requestWithActor
	 */
	void buyReturnMoney(RequestWithActor<AirLineRefundRequest> requestWithActor);

	void updateBuyExtRefund(RequestWithActor<Long> requestWithActor);

	//事务原因创建的服务
	int queryBuyRefundAndDelCountBylocker(Long userId);

	//废票销售审核
	void auditWasteTicket(RequestWithActor<WasteAuditPramaVo> request) throws GSSException;
	//退票销售审核
    void auditRefundTicket(RequestWithActor<WasteAuditPramaVo> request) throws GSSException;
}
