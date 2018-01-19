package com.tempus.gss.product.ift.api.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.AdjustOrder;
import com.tempus.gss.product.ift.api.entity.GssMain;
import com.tempus.gss.product.ift.api.entity.InallsaleRequest;
import com.tempus.gss.product.ift.api.entity.Passenger;
import com.tempus.gss.product.ift.api.entity.Pnr;
import com.tempus.gss.product.ift.api.entity.QueryByPnr;
import com.tempus.gss.product.ift.api.entity.SaleOrderDetail;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.product.ift.api.entity.vo.BlackOrderExtVo;
import com.tempus.gss.product.ift.api.entity.vo.DemandTeamVo;
import com.tempus.gss.product.ift.api.entity.vo.OrderCreateVo;
import com.tempus.gss.product.ift.api.entity.vo.OrderInformVo;
import com.tempus.gss.product.ift.api.entity.vo.OrderRefuseRequest;
import com.tempus.gss.product.ift.api.entity.vo.PassengerListVo;
import com.tempus.gss.product.ift.api.entity.vo.ReportVo;
import com.tempus.gss.product.ift.api.entity.vo.SaleOrderExtVo;
import com.tempus.gss.product.ift.api.entity.vo.SaleQueryOrderVo;
import com.tempus.gss.product.ift.api.entity.vo.WarnOrderRequest;
import com.tempus.gss.product.ift.api.entity.webservice.settt.ReturnSettInfo;
import com.tempus.gss.vo.Agent;

/**
 * 国际机票订单处理服务接口.
 * 包括销售单，采购单，支付，出票等
 */
public interface IOrderService {

	//<editor-fold desc="创建订单">

	/**
	 * 创建订单.
	 * 通过白屏查询、Pnr、需求单、手工方式创建订单.
	 *
	 * @param requestWithActor
	 * @return
	 */
	SaleOrderExt createOrder(RequestWithActor<OrderCreateVo> requestWithActor)throws Exception;
	//</editor-fold>

	//<editor-fold desc="订单查询、获取">

	/**
	 * 根据订单编号查询订单.
	 *
	 * @param orderNo
	 * @return
	 */
	SaleOrderExt getOrder(RequestWithActor<Long> orderNo);

	/**
	 * 查询订单.
	 * 为运营平台的订单管理提供服务.
	 *
	 * @param orderQueryRequest
	 * @return
	 */
//	public Page<SaleOrderExt> queryOrder(Page<SaleOrderExt> page,
//			RequestWithActor<SaleOrderExtVo> orderQueryRequest);
	
	/**
	 * 查询订单.
	 * 为运营平台订单管理提供服务.
	 *
	 * @param saleOrderQueryRequest
	 * @return 结果返回到销售平台，销售平台需要二次处理.
	 */
	public Page<SaleOrderExt> queryFromSale(Page<SaleOrderExt> page,
			RequestWithActor<SaleQueryOrderVo> saleOrderQueryRequest);


	/**
	 * 
	 * warnOrder:调整单提醒接口
	 *
	 * @param @param
	 *            requestWithActor
	 * @param @return
	 *            设定文件
	 * @return String DOM对象
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	public String warnOrder(RequestWithActor<WarnOrderRequest> requestWithActor);

	//</editor-fold>

	//<editor-fold desc="支付">

	/**
	 *根据业务情况接收出票消息，修改采购单状态为待出票
	 * @param saleOrderNo
	 * @return
	 */
	boolean updateBuyOrderStatus(RequestWithActor<Long> saleOrderNo);

	//</editor-fold>

	//<editor-fold desc="订单操作">

	/**
	 * 生成应收和应付的记录.
	 *
	 * @param passengerList
	 * @return
	 */
	boolean confirmPrice(RequestWithActor<List<Passenger>> passengerList);

	/**
	 * 取消订单.
	 *
	 * @param saleOrderNo，销售订单号
	 * @return
	 */
	boolean cancelOrder(RequestWithActor<Long> saleOrderNo);

	/**
	 * 修改采购价.
	 *
	 * @param passengerList，乘客价格.
	 * @return
	 */
//	boolean editBuyPrice(RequestWithActor<List<Passenger>> passengerList);

	/**
	 * 出单.
	 * @return
	 */
	boolean issuing(RequestWithActor<PassengerListVo> pgerListVo);
	/**
	 * 拒单.
	 *
	 * @param refuseRequest，拒单请求.
	 * @return
	 */
	boolean refuse(RequestWithActor<OrderRefuseRequest> refuseRequest);

	/**
	 * 创建销售退款单
	 * @param agent
	 * @param saleOrderNo
	 * @return
	 * @throws GSSException
	 */
	boolean saleRefund(Agent agent, Long saleOrderNo) throws GSSException;
	/**
	 * 拒单重提.
	 *
	 * @param saleOrderNo,销售订单号
	 * @return
	 */
//	boolean resubmit(RequestWithActor<Long> saleOrderNo);

	/**
	 * 回贴票号.
	 *
	 * @param ticketRequest
	 * @return
	 */
//	boolean writeTicketNo(RequestWithActor<TicketRequest> ticketRequest);

	/**
	 * 锁单、解锁.
	 *
	 * @param saleOrderNo
	 * @return
	 */
	boolean lock(RequestWithActor<Long> saleOrderNo);
	
	/**
	 * 修改
	 * @param saleOrderExt
	 * @return
	 */
	int updateSaleOrderExt(RequestWithActor<SaleOrderExt> saleOrderExt);
	/**
	 * 订单核价
	 * @param saleOrderExt
	 * @return
	 */
	int auditOrder(RequestWithActor<SaleOrderExt> saleOrderExt, Long supplierNo,String airLine,String ticketType);
	

	/**
	 * 根据需求单查询订单
	 */
	SaleOrderExt getDemandNo(RequestWithActor<Long> demandNo);
	
	
	/**
	 * 根据团队需求单查询订单
	 */
	SaleOrderExt getTeamDemandNo(RequestWithActor<DemandTeamVo> demandTeamVo);
	
	
	
	boolean verify(RequestWithActor<List<Passenger>> passengerList);

	/**
	 * 根据票号获取订单报表
	 */
	List<ReportVo> getReportByTicketNo(Agent agent,String ticketNo);
	
	/**
	 * 根据PNR获取销售单采购单信息
	 */
	List<QueryByPnr> queryByPnr(Agent agent,String pnr);
	
	/**
	 * 根据PNR查询
	 * pnr 
	 * date 天数
	 */
	boolean queryByPnr(Agent agent,String pnr,int date);
	
	/**
	 * 黑屏预定
	 */
	Long blankBookTicketing(RequestWithActor<OrderCreateVo> requestWithActor,BlackOrderExtVo blackOrderExtVo);
	
	/**
	 * 创建PNR.
	 * @return
	 */
	boolean createPnr(Agent agent,Pnr pnr,SaleOrderExt saleOrderExt);

	/**订单分配*/
	void assign();
	
	/**
	 * 销售单新增婴儿乘机人
	 * @param agent 终端信息
	 * @param saleNo 原销售单号
	 * @param remark 备注
	 * @return long 销售单编号
	 */
	long addInfPassengers(Long saleNo,RequestWithActor<OrderCreateVo> requestWithActor,String remark);

	/**
	 * 出票通知
	 * @param orderInformVo
	 * @return
	 */
	int outTicketInform(OrderInformVo orderInformVo);
	
	/**
	 * 
	 * uploadUbpInfo:调用结算订单接口解挂订单
	 *
	 * @param orederNo
	 * @return
	 */
	ReturnSettInfo uploadUbpInfo(Agent agent,Long supplierNo,SaleOrderExtVo saleOrderExtVo);
	
	/**
	 * uploadUbpInfo:根据订单号查询结算数据，用于封装解挂数据
	 * @param orederNo
	 * @return
	 */
	GssMain selectBatchIds(Long orederNo);

	/**解挂成功修改采购单
	 * @return
	 */
	void updateBuy(SaleOrderExtVo saleOrderExtVo,Long supplierNo,Agent agent, Long needPayId, Integer incomeExpenseType, Integer recordMovingType, Double planAmount, 
			List<SaleOrderDetail> saleOrderDetailList,String movingReason) throws Exception;
	/**
	 * 记录挂起记录
	 * @return
	 */
	int addAdjustOrder(AdjustOrder adjustOrder,InallsaleRequest inallsaleRequest,SaleOrderExt saleOrderExt);

	/**
	 * 更新订单状态为拒单
	 * @param agent
	 * @param saleOrderNo
	 */
	void updateStatusAsRefuse(Agent agent,Long saleOrderNo);

	/**
	 * 查询已出票，但是没有配送产品，并且在这个表(LS_DELIVERY_BATCH_OS)没有数据
	 * @param saleOrderQueryRequest
	 * @return 结果返回到销售平台，销售平台需要二次处理.
	 */
	Page<SaleOrderExt> selectOutTicketOrder(Page<SaleOrderExt> page,RequestWithActor<SaleQueryOrderVo> saleOrderQueryRequest);

	void sendWebSocketInfoByMq(String msg);
}
