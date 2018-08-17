/**
 * IOrderService.java
 * com.tempus.gss.product.ins.api.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2016年10月18日 		shuo.cheng
 *
 * Copyright (c) 2016, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ins.api.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.controller.Result;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ins.api.entity.ProfitControl;
import com.tempus.gss.product.ins.api.entity.ResultInsure;
import com.tempus.gss.product.ins.api.entity.SaleOrderDetail;
import com.tempus.gss.product.ins.api.entity.SaleOrderExt;
import com.tempus.gss.product.ins.api.entity.vo.OrderCancelVo;
import com.tempus.gss.product.ins.api.entity.vo.OrderCreateVo;
import com.tempus.gss.product.ins.api.entity.vo.SaleOrderDetailVo;
import com.tempus.gss.product.ins.api.entity.vo.SaleOrderExtVo;
import com.tempus.gss.vo.Agent;

/**
 * ClassName:IOrderService Function: 国际机票保险的订单服务
 *
 * @author shuo.cheng
 * @version
 * @since Ver 1.1
 * @Date 2016年10月18日 上午10:30:31
 *
 * @see
 * 
 */
public interface IOrderService {

	/**
	 * 
	 * createOrder:创建订单
	 *
	 * @param @param
	 *            createOrderVo
	 * @param @return
	 *            设定文件
	 * @return 销售单编号
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	Long createOrder(RequestWithActor<OrderCreateVo> requestWithActor) throws Exception;

	/**
	 *
	 * buyInsure:投保(根据销售单进行全部投保操作)
	 *
	 * @param @param
	 *            requestBean
	 * @param @return
	 *            设定文件
	 * @return boolean DOM对象
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	boolean buyInsure(RequestWithActor<Long> requestWithActor) throws Exception;

	/**
	 * 
	 * cancelSaleOrder:线上产品退保
	 *
	 * @param @param
	 *            cancel
	 * @param @return
	 *            设定文件
	 * @return PolicyVo DOM对象
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	Boolean cancelSaleOrder(RequestWithActor<OrderCancelVo> requestWithActor) throws Exception;

	/**
	 * 
	 * cancelSaleOrder:线上产品退保
	 *
	 * @param @param
	 *            cancel
	 * @param @return
	 *            设定文件
	 * @return PolicyVo DOM对象
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	ResultInsure cancelSaleOrder(RequestWithActor<OrderCancelVo> requestWithActor,int isRefund) throws Exception;
	/**
	 * 
	 * cancelSaleOrder:线上产品退保
	 *
	 * @param @param
	 *            cancel
	 * @param @return
	 *            设定文件
	 * @return PolicyVo DOM对象
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	ResultInsure cancelSaleOrderForB2b(RequestWithActor<OrderCancelVo> requestWithActor,int isRefund) throws Exception;
	/**
	 *
	 * cancelSaleOrder:线下产品退保
	 *
	 * @param @param
	 *            cancel
	 * @param @return
	 *            设定文件
	 * @return PolicyVo DOM对象
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	Boolean cancelSaleOrderOffline(RequestWithActor<OrderCancelVo> requestWithActor) throws Exception;
	/**
	 *
	 * cancelSaleOrder:线下产品退保
	 *
	 * @param @param
	 *            cancel
	 * @param @return
	 *            设定文件
	 * @return PolicyVo DOM对象
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	Boolean cancelSaleOrderOffline(RequestWithActor<OrderCancelVo> requestWithActor,int isRefund) throws Exception;
	/**
	 * 根据订单编号查询订单
	 * 
	 * @param requestWithActor
	 * @return
	 */
	SaleOrderExt querySaleOrder(RequestWithActor<Long> requestWithActor);
	/**
	 * 根据交易单号查询订单
	 * 
	 * @param requestWithActor
	 * @return TRANSACTION_ID
	 */
	 List<SaleOrderExt> querySaleOrderForTranSaction(RequestWithActor<Long> requestWithActor) ;
	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int deleteSaleOrder(RequestWithActor<Long> id) throws Exception;

	/**
	 * 通过条件查询销售单集合
	 * @param pageRequest
	 * @return
	 */
	public Page<SaleOrderExt> querySaleOrderList(Page<SaleOrderExt> page,RequestWithActor<SaleOrderExtVo> pageRequest);
	/**
	 * 通过交易单号查询保险详情（多个销售单）
	 * @param pageRequest
	 * @return
	 */
	public List<SaleOrderExt> querySaleOrderListIntransactionNo(RequestWithActor<SaleOrderExtVo> pageRequest) throws Exception;
	/**
	 * 通过交易单号查询保险订单 b2b保险订单支付使用
	 * @param pageRequest
	 * @return
	 */
	public List<SaleOrderExt> queryInsuranceSaleOrderForTranSaction(RequestWithActor<Long> requestWithActor) throws Exception;
	/**
	 * 通过条件查询报表数据
	 * @param pageRequest
	 * @return
	 */
	public Page<SaleOrderExt> queryReportOrderList(Page<SaleOrderExt> page,RequestWithActor<SaleOrderExtVo> pageRequest);
	/**
	 * 
	 * selectByInsuranceNo:根据产品编号查询销售价
	 * 
	 * @param @param
	 *            insuranceNo
	 * @param @return
	 *            设定文件
	 * @return BigDecimal DOM对象
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	List<ProfitControl> selectByInsuranceNo(Long insuranceNo);

	/**
	 * 
	 * selectDetailBySaleOrderNo:根据订单号查询被保人列表
	 *
	 * @param  @param requestWithActor
	 * @param  @return    设定文件
	 * @return Page<SaleOrderDetail>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	Page<SaleOrderDetail> selectDetailBySaleOrderNo(Page<SaleOrderDetail> page,
			RequestWithActor<SaleOrderDetailVo> requestWithActor);

	/**
	 * 
	 * selectDetailListBySaleOrderNo:根据销售单号查询被保人列表(不分页)
	 *
	 * @param  @param requestWithActor
	 * @param  @return    设定文件
	 * @return List<SaleOrderDetail>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	List<SaleOrderDetail> selectDetailListBySaleOrderNo(RequestWithActor<Long> requestWithActor);

	/**
	 * 
	 * updateStatus4Offline:为线下保险产品更新订单状态
	 *
	 * @param  @param agent
	 * @param  @param saleOrderNo
	 * @param  @param orderChildStatus
	 * @param  @return    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	void updateStatus4Offline(Agent agent, Long saleOrderNo, Integer orderChildStatus) throws Exception;
	/**
	 * 更新保单号
	 * @param requestWithActor
	 * @return
	 */
	int updateStatusDetail(RequestWithActor<SaleOrderDetail> requestWithActor) throws Exception;

	/**
	 * 
	 * cancelSaleOrderExt:取消订单
	 *
	 * @param      设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	boolean cancelSaleOrderExt(RequestWithActor<Long> saleOrderNo);
	/**
	 * 
	 * updateStatus4Offline:为个人线下保险产品更新订单状态
	 *
	 * @param  @param agent
	 * @param  @param saleOrderNo
	 * @param  @param orderChildStatus
	 * @param  @return    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	void updateStatus4OfflineForPerson(Agent agent, Long saleOrderNo, Integer orderChildStatus,Long insuredNo) throws Exception;
	/**
	 * 
	 * refundForPersonDetail:为个人线下退款
	 *
	 * @param  @param saleOrderExt
	 * @param  @param saleOrderDetail
	 * @param  @param agent
	 * @param  @return    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 * 	 */
	boolean refundForPersonDetail(SaleOrderExt saleOrderExt,SaleOrderDetail saleOrderDetail,Agent agent) throws Exception;
	/**
	 * 
	 * refundForPersonDetail:为B2B个人退款
	 *
	 * @param  @param saleOrderExt
	 * @param  @param saleOrderDetail
	 * @param  @param agent
	 * @param  @return    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 * 	 */
	boolean refundForB2BPersonDetail(SaleOrderExt saleOrderExt,SaleOrderDetail saleOrderDetail,Agent agent) throws Exception;
	/**
	 * 
	 * refundForPersonDetail:为B2B个人取消退款申请
	 *
	 * @param  @param saleOrderExt
	 * @param  @param saleOrderDetail
	 * @param  @param agent
	 * @param  @return    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 * 	 */
	boolean cacelForB2BPersonDetail(SaleOrderExt saleOrderExt,SaleOrderDetail saleOrderDetail,Agent agent) throws Exception;
	/**
	 * 
	 * refundForPersonDetail:为个人线上重新投保
	 *
	 * @param  @param saleOrderExt
	 * @param  @param saleOrderDetail
	 * @param  @param agent
	 * @param  @return    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 * 	 */
	ResultInsure buyInsureForPerson(RequestWithActor<Long> requestWithActor,Long insuredNo) throws Exception;
	/**
	 * cancelInsuranceOrder:取消保险订单
	 * @param requestWithActor
	 * @return
	 * @throws Exception
	 */
	boolean cancelInsuranceOrder(RequestWithActor<Long> requestWithActor) throws Exception;

}

