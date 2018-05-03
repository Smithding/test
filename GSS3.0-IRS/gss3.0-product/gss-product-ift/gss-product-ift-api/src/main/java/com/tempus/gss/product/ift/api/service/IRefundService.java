package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.ift.api.entity.PassengerRefundPrice;
import com.tempus.gss.product.ift.api.entity.SaleChangeExt;
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

	/**
	 * 废退单审核
	 * 填入乘客退废税费
	 *
	 * @param passengerRefundList
	 * @return
	 */
	boolean verify(RequestWithActor<List<PassengerRefundPrice>> passengerRefundList);

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
	 * @return
	 */
	boolean refuse(RequestWithActor<Long> saleOrderNo);

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
}
