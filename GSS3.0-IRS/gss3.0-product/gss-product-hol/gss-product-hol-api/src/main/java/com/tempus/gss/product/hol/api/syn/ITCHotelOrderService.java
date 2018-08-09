package com.tempus.gss.product.hol.api.syn;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.hol.api.entity.request.tc.CancelOrderBeforePayReq;
import com.tempus.gss.product.hol.api.entity.request.tc.CardSupportReq;
import com.tempus.gss.product.hol.api.entity.request.tc.IncrOrderChangeInfoReq;
import com.tempus.gss.product.hol.api.entity.request.tc.IsBookOrderReq;
import com.tempus.gss.product.hol.api.entity.request.tc.OrderCancelReq;
import com.tempus.gss.product.hol.api.entity.request.tc.OrderCreateReq;
import com.tempus.gss.product.hol.api.entity.request.tc.OrderDetailInfoReq;
import com.tempus.gss.product.hol.api.entity.request.tc.TcPushOrderInfo;
import com.tempus.gss.product.hol.api.entity.response.HolErrorOrder;
import com.tempus.gss.product.hol.api.entity.response.HotelOrder;
import com.tempus.gss.product.hol.api.entity.response.tc.CancelOrderRes;
import com.tempus.gss.product.hol.api.entity.response.tc.CancelReasonModel;
import com.tempus.gss.product.hol.api.entity.response.tc.IsBookOrder;
import com.tempus.gss.product.hol.api.entity.response.tc.OrderInfomationDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.SupportCardInfo;
import com.tempus.gss.product.hol.api.entity.vo.HotelOrderVo;
import com.tempus.gss.vo.Agent;

/**
 * 同程订单业务接口
 * @author kai.yang
 *
 */
public interface ITCHotelOrderService {
	

	/**
	 * 酒店创建订单列表
	 * @param orderCreateReq
	 * @return
	 */
	public HotelOrder createOrder(Agent agent, OrderCreateReq orderCreateReq) throws GSSException;
	
	/**
	 * 获取同程与合作方的订单详细信息
	 * @param orderDetailInfoReq 订单详情请求入参
	 * @return
	 */
	public OrderInfomationDetail orderDetailInfo(Agent agent, OrderDetailInfoReq orderDetailInfoReq) throws GSSException;
	
	Future<OrderInfomationDetail> futureorderDetailInfo(Agent agent, OrderDetailInfoReq orderDetailInfoReq) throws GSSException;
	/**
	 * 创建报错订单表
	 * @param agent
	 * @param hotelOrder
	 * @throws GSSException
	 */
	public void createErrorOrder(Agent agent, HolErrorOrder holErrorOrder) throws GSSException;
	
	/**
	 * 根据同程订单号向同程发起对应订单的退款申请
	 * @param orderId 客户订单号
	 * @return
	 */
	public String cancelTcOrder(Agent agent, OrderCancelReq orderCancelReq) throws GSSException;
	
	/**
	 * 获取支持的信用卡信息（ 现付必选）
	 * @param orderType 类型 1：代表国内酒店
	 * @return
	 */
	public SupportCardInfo supportCartInfo(Agent agent, CardSupportReq orderType) throws GSSException;
	
	/**
	 * 可订检查查询（ 现付必选）
	 * @param isBookOrderReq
	 * @return
	 */
	public IsBookOrder isBookOrder(Agent agent,IsBookOrderReq isBookOrderReq) throws GSSException;
	/**
	 * 根据条件查询酒店订单列表
	 * @param page
	 * @param pageRequest
	 * @return
	 */
	public Page<HotelOrder> queryOrderListWithPage(Page<HotelOrder> page, RequestWithActor<HotelOrderVo> pageRequest) throws GSSException;
	
	/**
	 * 根据条件查询酒店报错订单列表
	 * @param page
	 * @param pageRequest
	 * @return
	 */
	public Page<HolErrorOrder> queryErrorOrderListWithPage(Page<HolErrorOrder> page, RequestWithActor<HotelOrderVo> pageRequest) throws GSSException;
	
	/**
	 * 根据条件查询分销商订单列表
	 * @param hotelOrderVo
	 * @return
	 */
	Page<HotelOrder> queryCustomerOrderList(Page<HotelOrder> page, RequestWithActor<HotelOrderVo> pageRequest) throws GSSException;
	
	/**
	 * 根据销售单号查询酒店订单详情
	 * @param agent
	 * @param saleOrderNo
	 * @return
	 */
	public HotelOrder getHotelOrderDetail(Agent agent, Long saleOrderNo) throws GSSException;
	
	public Future<HotelOrder> getFutureOrderDetail(Agent agent, String hotelOrderNo) throws GSSException;
	
	
	/**
	 * 根据同程订单号查询酒店订单详情
	 * @param agent
	 * @param hotelOrderNo
	 * @return
	 * @throws GSSException
	 */
	public HotelOrder getRelatedHotelOrderDetail(Agent agent, Long rehotelOrderNo) throws GSSException;
	/**
	 * 支付前取消订单接口
	 * @param OrderCancelBeforePayReq
	 * @return
	 */
	public CancelOrderRes cancelOrderBeforePayReq(Agent agent, CancelOrderBeforePayReq orderCancelBeforePayReq) throws GSSException;
	/**
	 * 获取取消订单原因
	 * @return
	 */
	public List<CancelReasonModel> getCancelReasonModelList(Agent agent) throws GSSException;
	
	/**
	 * 获取某一时间段内的发生变更的订单号及变更信息
	 * @return
	 */
	public Long incrOrderChangeInfo(Agent agent, IncrOrderChangeInfoReq incrOrderChangeInfoReq) throws GSSException;
	
	/**
	 * 同程推送订单状态
	 * @param tcPushOrderInfo
	 */
	public Boolean tcPushOrderInfo(Agent agent, TcPushOrderInfo tcPushOrderInfo) throws GSSException;
	
	/**
	 * 添加特殊需求
	 * @param orderNo
	 * @param remark
	 * @return
	 */
	public Boolean findHotelOrderOne(String orderNo, String remark);
	
	
	/**
	 * 添加特殊需求
	 * @param SocketDO
	 * @param remark
	 * @return
	 */
	public void sendWebSocketByMQ(String msg);
	/**
	 * 根据订单号手动更新订单状态
	 * @param orderNo
	 * @return
	 */
	public Boolean updateOrderStatus(Agent agent, String orderNo);
	/**
	 * 手动更新控润
	 * @param agent
	 * @param orderNo
	 * @return
	 */
	public Boolean updateRefund(Agent agent, String orderNo, BigDecimal newRefund);
	
	/**
	 * 后台手动将修改后的订单推送给前台显示
	 * @param agent
	 * @param orderNo
	 * @return
	 */
	public Boolean pushToBeforeShow(Agent agent, String orderNo);
}
