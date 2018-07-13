package com.tempus.gss.product.hol.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.bbp.util.DateUtil;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.dps.order.service.SaleService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.log.entity.LogRecord;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.order.entity.ActualInfoSearchVO;
import com.tempus.gss.order.entity.BusinessOrderInfo;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.CertificateCreateVO;
import com.tempus.gss.order.entity.CreatePlanAmountVO;
import com.tempus.gss.order.entity.GoodsBigType;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.entity.TransationOrder;
import com.tempus.gss.order.service.IActualAmountRecorService;
import com.tempus.gss.order.service.IBuyOrderService;
import com.tempus.gss.order.service.ICertificateService;
import com.tempus.gss.order.service.IPlanAmountRecordService;
import com.tempus.gss.order.service.ISaleOrderService;
import com.tempus.gss.order.service.ITransationOrderService;
import com.tempus.gss.product.hol.api.entity.request.tc.CancelOrderBeforePayReq;
import com.tempus.gss.product.hol.api.entity.request.tc.OrderCreateReq;
import com.tempus.gss.product.hol.api.entity.request.tc.OrderDetailInfoReq;
import com.tempus.gss.product.hol.api.entity.response.HolErrorOrder;
import com.tempus.gss.product.hol.api.entity.response.HotelOrder;
import com.tempus.gss.product.hol.api.entity.response.tc.CancelOrderRes;
import com.tempus.gss.product.hol.api.entity.response.tc.OwnerOrderStatus;
import com.tempus.gss.product.hol.api.entity.response.tc.TcOrderStatus;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.BQYPushOrderInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.BookOrderParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.CreateOrderReq;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.OrderCancelParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.BookOrderResponse;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.OrderCancelResult;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.BaseRoomInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.RoomBedTypeInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.RoomInfoItem;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.RoomPriceDetail;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.RoomPriceInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.RoomPriceItem;
import com.tempus.gss.product.hol.api.service.IBQYHotelInterService;
import com.tempus.gss.product.hol.api.service.IBQYHotelOrderService;
import com.tempus.gss.product.hol.api.syn.ITCHotelOrderService;
import com.tempus.gss.product.hol.api.util.OrderStatusUtils;
import com.tempus.gss.product.hol.dao.HotelOrderMapper;
import com.tempus.gss.security.AgentUtil;
import com.tempus.gss.system.entity.SmsTemplateDetail;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.system.service.ISmsTemplateDetailService;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;

@Service
class BQYHotelOrderServiceImpl implements IBQYHotelOrderService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IBQYHotelInterService bqyHotelInterService;

	@Reference
	private ISupplierService supplierService;

	@Reference
	private IMaxNoService maxNoService;

	@Reference
	private ISaleOrderService saleOrderService;

	@Reference
	private IBuyOrderService buyOrderService;

	@Autowired
	private HotelOrderMapper hotelOrderMapper;
	
	@Reference
	private SaleService saleService;
	
	@Reference
	private ILogService logService;
	
	@Reference
	private ITransationOrderService transationOrderService;
	
	@Reference
	private IPlanAmountRecordService planAmountRecordService;
	
	@Reference
	private ITCHotelOrderService tCHotelOrderService;
	
	@Reference
	private ICertificateService certificateService;
	
	@Reference
	private IActualAmountRecorService actualAmountRecorService;
	
	@Reference
	private ISmsTemplateDetailService smsTemplateDetailService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public OrderCreateReq hotelReserve(Agent agent, CreateOrderReq orderReq, OrderCreateReq orderCreateReq) {
		logger.info("==============开始酒店预订==================");
		logger.info("入参createHotel："+JsonUtil.toJson(orderCreateReq));
		//获取交易单
		Long transactionId = IdWorker.getId();
		TransationOrder tr = setTransationOrderValue(agent,orderCreateReq.getLinkManName(),orderCreateReq.getLinkManMobile(),transactionId);//(agent, createHotel.getContactName(), createHotel.getContactNumber(), transactionId);
		
		//MssBaseResult<HotelOrder> mbr = new MssBaseResult<HotelOrder>();
		try {
			//生成交易单
			TransationOrder transationOrder = transationOrderService.create(agent, tr);
			
			if(StringUtil.isNotNullOrEmpty(transationOrder)){
				//交易单生成完成后创建酒店订单
				orderCreateReq.getSaleOrder().setTransationOrderNo(transactionId);
				HotelOrder hotelOrder = bqyHotelInterService.createLocalOrder(agent, orderReq, orderCreateReq);//createHotelOrder(agent, createHotel);
				if(StringUtil.isNotNullOrEmpty(hotelOrder)){
					LogRecord logRecord=new LogRecord();
					logRecord.setAppCode("GSS");
					logRecord.setBizCode("HOL-Order");
					logRecord.setTitle("酒店订单添加备注");
					String orderNo = hotelOrder.getHotelOrderNo();
					logRecord.setBizNo(orderNo);		
					logRecord.setRequestIp(agent.getIp());
					logRecord.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
					String des = "订单号"+hotelOrder.getSaleOrderNo()+"由用户："+agent.getAccount()+"第一次下单添加备注："+orderCreateReq.getOrderRemark();
					logRecord.setDesc(des);
					logRecord.setOptLoginName(agent.getAccount());
					logRecord.setOptName(agent.getAccount()); 
					logService.insert(logRecord);
					//mbr.setStatus(1);
					//mbr.setResult(hotelOrder);
					orderCreateReq.setProName(hotelOrder.getProName());
					return orderCreateReq;
				}
			}
		} catch (GSSException e) {
			HolErrorOrder hotelOrder = new HolErrorOrder();
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			hotelOrder.setSaleOrderNo(Long.valueOf(e.getCode()));
			hotelOrder.setHotelCode(orderCreateReq.getResId().toString());
			hotelOrder.setHotelName(e.getModule());
			hotelOrder.setProName(orderCreateReq.getProName());
			hotelOrder.setProductUniqueId(orderCreateReq.getProductUniqueId());
			hotelOrder.setGuestName(orderReq.getPassengers());
			String startDate = orderReq.getCheckInTime();
			String endDate = orderReq.getCheckOutTime();
			try {
				Date dateStartDate = simple.parse(startDate);
				Date departDate = simple.parse(endDate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(departDate);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				Date departureDate= cal.getTime();
				hotelOrder.setArrivalDate(dateStartDate);
	            hotelOrder.setDepartureDate(departureDate);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			hotelOrder.setOwner(agent.getOwner());
			hotelOrder.setCreator(agent.getAccount());
            hotelOrder.setCreateOrderTime(new Date());
            hotelOrder.setContactName(orderCreateReq.getLinkManName());
            hotelOrder.setContactNumber(orderCreateReq.getLinkManMobile());
            hotelOrder.setOrderStatus(OwnerOrderStatus.BOOK_FAIL.getKey());
            hotelOrder.setResultCode("0");
            hotelOrder.setMsg(e.getMessage());
            tCHotelOrderService.createErrorOrder(agent, hotelOrder);
			logger.error("酒店预订有误{}",e);
			/*mbr.setStatus(0);
			mbr.setError(e.getMessage());*/
		}
		logger.info("==============结束酒店预订==================");
		return null;
	}
	
	private TransationOrder setTransationOrderValue(Agent agent,String contacts,String mobile,Long transactionId){
		TransationOrder tr = new TransationOrder();
		tr.setContacts(contacts);
		tr.setCreateTime(new Date());
		tr.setCustomerNo(agent.getNum());
		tr.setCustomerTypeNo(agent.getType());
		tr.setMobile(mobile);
		tr.setOrderingLoginName(agent.getAccount());
		tr.setOwner(agent.getOwner());
		tr.setSourceChannelNo(agent.getDevice());
		tr.setPayStatus(1);
		tr.setTransationOrderNo(transactionId);
		tr.setValid(1);
		logger.info("tr:"+JsonUtil.toJson(tr));
		return tr;
	}

	@Override
	public CancelOrderRes cancelOrder(Agent agent, CancelOrderBeforePayReq orderCancelBeforePayReq) {
		logger.info("取消酒店订单开始,入参:" + JSONObject.toJSONString(orderCancelBeforePayReq) + ";remark=" + orderCancelBeforePayReq.getOtherReason());
		if (StringUtil.isNullOrEmpty(orderCancelBeforePayReq)) {
            logger.error("取消订单入参为空");
            throw new GSSException("取消酒店订单", "0101", "取消订单入参为空");
        }
        if (StringUtil.isNullOrEmpty(agent)) {
            logger.error("agent对象为空");
            throw new GSSException("取消酒店订单", "0102", "agent对象为空");
        }
        CancelOrderRes cancelOrderRes = new CancelOrderRes();
        OrderCancelParam cancelParam = new OrderCancelParam();
        HotelOrder hotelOrder = new HotelOrder();
        hotelOrder.setHotelOrderNo(orderCancelBeforePayReq.getOrderId());
        hotelOrder = hotelOrderMapper.selectOne(hotelOrder);
        if (StringUtil.isNullOrEmpty(hotelOrder)) {
            logger.error("订单信息不存在");
            throw new GSSException("取消酒店订单", "0103", "订单信息不存在");
        } else {
        	LogRecord logRecord=new LogRecord();
        	String des = "";
        	try {
        		String orderStatus = hotelOrder.getTcOrderStatus();
            	if (TcOrderStatus.WAIT_PAY.getKey().equals(orderStatus)) {	//待支付
            		cancelParam.setOrderNumber(Long.valueOf(orderCancelBeforePayReq.getOrderId()));
            		OrderCancelResult orderCancelResult = bqyHotelInterService.cancelOrder(cancelParam);
            		if (orderCancelResult.getResult()) {
            			des = "订单号"+hotelOrder.getHotelOrderNo() +",订单状态由"+ OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.CANCEL_OK.getValue();
            			//更新销售单和采购单状态
            		    updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.CANCEL_OK));
            		   //更新酒店订单状态
            		    hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_OK.getKey());
            		    hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_CANCEL.getKey());
            		    hotelOrder.setCancelTime(new Date());
            		    hotelOrderMapper.updateById(hotelOrder); 
            		    cancelOrderRes.setResult(true);
            		    cancelOrderRes.setMsg(orderCancelResult.getMessage());
            		    cancelOrderRes.setLasestCancelTime(hotelOrder.getLatestArriveTime());
            		}else {
            			cancelOrderRes.setResult(false);
            		    cancelOrderRes.setMsg(orderCancelResult.getMessage());
            		    cancelOrderRes.setLasestCancelTime(hotelOrder.getLatestArriveTime());
            		}
            		
            	}else if (TcOrderStatus.WAIT_TC_CONFIRM.getKey().equals(orderStatus) || TcOrderStatus.ALREADY_TC_CONFIRM.getKey().equals(orderStatus)) {//待BQY确认 || BQY已确认
            		String policyType = hotelOrder.getDbOrderType();	//政策类型 cancelOrderRes
            		if("0".equals(policyType) || "8".equals(policyType)) {
            			cancelOrderRes.setResult(false);
                        cancelOrderRes.setMsg("该订单不可取消!");
                        
            		}else if ("1".equals(policyType)) {	//免费取消, 任意退
            			des = cancelOrder(agent, orderCancelBeforePayReq, cancelOrderRes, cancelParam, hotelOrder, des);
                		
            		}else if ("2".equals(policyType)) {	//限时取消
            			String latestArriveTime = hotelOrder.getLatestArriveTime();
            			String currentTime = sdf.format(new Date());
            			long compareDate = DateUtil.compareDateStr(latestArriveTime, currentTime);
            			if (compareDate > 0) {
            				cancelOrderRes.setResult(false);
                            cancelOrderRes.setMsg("已超过订单取消时间,该订单不可取消!");
                            cancelOrderRes.setLasestCancelTime(hotelOrder.getLatestArriveTime());
            			}else {
            				des = cancelOrder(agent, orderCancelBeforePayReq, cancelOrderRes, cancelParam, hotelOrder, des);
            			}
            			
            		}else if ("4".equals(policyType)) {	//超时担保限时取消
            			//TODO 取消酒店订单,超时担保限时取消
            		}
            		
            	}
			} catch (Exception e) {
				logger.error("取消酒店订单请求出错"+e);
                throw new GSSException("取消酒店订单出错", "0106", "取消酒店订单出错!");
			}
        	logRecord.setBizCode("HOL-Order");
    		logRecord.setTitle("申请取消订单");
    		logRecord.setBizNo(hotelOrder.getHotelOrderNo());
    		logRecord.setCreateTime(new Date());
    		logRecord.setDesc(des);
    		if(StringUtils.isNotEmpty(agent.getAccount())){
    			logRecord.setOptName(agent.getAccount());
    		}
    		logService.insert(logRecord);
        }
        return cancelOrderRes;
	}

	private String cancelOrder(Agent agent, CancelOrderBeforePayReq orderCancelBeforePayReq,
			CancelOrderRes cancelOrderRes, OrderCancelParam cancelParam, HotelOrder hotelOrder, String des) {
		cancelParam.setOrderNumber(Long.valueOf(orderCancelBeforePayReq.getOrderId()));
		OrderCancelResult orderCancelResult = bqyHotelInterService.cancelOrder(cancelParam);
		if (orderCancelResult.getResult()) {
			BigDecimal saleRefundCert = saleRefund(agent, hotelOrder);
			if (null != saleRefundCert && saleRefundCert.compareTo(BigDecimal.ZERO) == 1) {	//退款成功
				des = "订单号"+hotelOrder.getHotelOrderNo() +",订单状态由"+ OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.CANCEL_OK.getValue();
				//更新销售单和采购单状态
			    updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.CANCEL_OK));
			   //更新酒店订单状态
			    hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_OK.getKey());
			    hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_CANCEL.getKey());
			    hotelOrder.setCancelTime(new Date());
			    hotelOrder.setFactTotalPrice(hotelOrder.getFactTotalPrice().subtract(saleRefundCert));
			    hotelOrderMapper.updateById(hotelOrder); 
			    cancelOrderRes.setResult(true);
			    cancelOrderRes.setMsg(orderCancelResult.getMessage());
			    cancelOrderRes.setLasestCancelTime(hotelOrder.getLatestArriveTime());
			}else {
				throw new GSSException("取消酒店订单", "0107", "取消酒店订单,退款失败!");
			}
			
		}else {
			cancelOrderRes.setResult(false);
		    cancelOrderRes.setMsg(orderCancelResult.getMessage());
		    cancelOrderRes.setLasestCancelTime(hotelOrder.getLatestArriveTime());
		}
		return des;
	}

	private BigDecimal saleRefund(Agent agent, HotelOrder hotelOrder) {
		ActualInfoSearchVO actualInfo = actualAmountRecorService.queryActualInfoByBusNo(agent, Long.valueOf(hotelOrder.getRequestCode()), 2);
		CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
		certificateCreateVO.setPayNo(actualInfo.getPayNo());
		certificateCreateVO.setCustomerTypeNo(Long.valueOf(hotelOrder.getRequestText()));
		certificateCreateVO.setCustomerNo(hotelOrder.getCustomerNo());
		certificateCreateVO.setIncomeExpenseType(2);
		certificateCreateVO.setAmount(hotelOrder.getTotalPrice());
		List<BusinessOrderInfo> businessOrderInfoList = new ArrayList<>();
		BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();
		businessOrderInfo.setRecordNo(Long.valueOf(hotelOrder.getRequestCode()));
		businessOrderInfo.setBusinessType(Long.valueOf(hotelOrder.getRequestText()).intValue());
		businessOrderInfoList.add(businessOrderInfo);
		certificateCreateVO.setOrderInfoList(businessOrderInfoList);
		BigDecimal saleRefundCert = certificateService.saleRefundCert(agent, certificateCreateVO);
		return saleRefundCert;
	}

	
	/**
     * 更新销售单和采购单状态
     *
     * @param saleOrderNo
     * @param buyOrderNo
     */
    private void updateSaleAndBuyOrderStatus(Agent agent, Long saleOrderNo, Long buyOrderNo, Integer status) throws GSSException{
        saleOrderService.updateStatus(agent, saleOrderNo, status);
        buyOrderService.updateStatus(agent, buyOrderNo, status);
    }
    
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public Boolean bqyPushOrderInfo(Agent agent, BQYPushOrderInfo bqyPushOrderInfo) {
		logger.info("推送更新订单状态:{}",JSON.toJSONString(bqyPushOrderInfo));
		if (StringUtil.isNotNullOrEmpty(bqyPushOrderInfo) && StringUtil.isNotNullOrEmpty(bqyPushOrderInfo.getOrdernumber()) 
				&& StringUtil.isNotNullOrEmpty(bqyPushOrderInfo.getOrderId())) {
			//订单号
			Long orderNumber = bqyPushOrderInfo.getOrdernumber();
			if (null == orderNumber) {
				logger.error("推送酒店订单号为空!");
				throw new GSSException("更新状态信息异常", "0191", "推送酒店订单号为空!");
			}
			//子订单号
			Long orderId = bqyPushOrderInfo.getOrderId();
			HotelOrder hotelOrder = hotelOrderMapper.getOrderByNo(String.valueOf(orderNumber));
			LogRecord LogRecord=new LogRecord();
			LogRecord.setBizCode("HOL-Order");
			LogRecord.setTitle("酒店订单状态");
			LogRecord.setBizNo(hotelOrder.getHotelOrderNo());
			hotelOrder.setModifier("供应商");
			hotelOrder.setModifyTime(new Date());
			String des= "";
			String orderStatus = hotelOrder.getOrderStatus();
			//订单状态
	 		TcOrderStatus pushOrderStatus = bqyPushOrderInfo.getOrderStatus();
			if (pushOrderStatus.getKey().equals(TcOrderStatus.ALREADY_TC_CONFIRM.getKey())) {	//订单确认
				if(!orderStatus.equals(OwnerOrderStatus.ALREADY_CONRIRM.getKey())) {
					if (OwnerOrderStatus.PAY_OK.equals(orderStatus)) {
						des = "订单号"+orderId +",订单状态由"+ OwnerOrderStatus.keyOf(orderStatus).getValue()+"变成:"+ OwnerOrderStatus.ALREADY_CONRIRM.getValue();
						//更新销售单和采购单状态
						updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.ALREADY_CONRIRM));
						hotelOrder.setOrderStatus(OwnerOrderStatus.ALREADY_CONRIRM.getKey());
						hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_TC_CONFIRM.getKey());
						hotelOrderMapper.updateById(hotelOrder);
				    	
						SmsTemplateDetail smsTemplateDetail = new SmsTemplateDetail();
						smsTemplateDetail.setDictCode("HOTEL_GUARANTEE");
				    	List<SmsTemplateDetail> stds = smsTemplateDetailService.query(smsTemplateDetail);
				    	String messageReplace = messageReplace(stds.get(0).getContent(), hotelOrder);
				    	LogRecord logRecord=new LogRecord();
						logRecord.setAppCode("GSS");
						logRecord.setBizCode("HOL-Order");
						logRecord.setBizNo(hotelOrder.getHotelOrderNo());		
						//logRecord.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
						logRecord.setDesc(messageReplace);
						logRecord.setCreateTime(new Date());
						logRecord.setOptName("腾邦国际");
						logService.insert(logRecord); 
						
						//Agent ag = new Agent(8755, 301L, hotelOrder.getCustomerNo());
						//mssReserveService.interHotelStatus(ag, hotelOrder.getSaleOrderNo(), OwnerOrderStatus.ALREADY_CONRIRM.getKey(), "酒店订单"+hotelOrder.getSaleOrderNo()+"下单确认成功");
					}else {
						logger.error("更新订单状态失败,订单状态非已支付!");
						throw new GSSException("更新酒店订单", "0106", "更新订单状态失败,订单状态非已支付!");
					}
					
				}
				
			}else if (pushOrderStatus.getKey().equals(TcOrderStatus.CANCEL_ING.getKey())) {		//退订中
				if (!orderStatus.equals(OwnerOrderStatus.CANCEL_ONGOING.getKey())) {
					if (OwnerOrderStatus.PAY_OK.equals(orderStatus) || OwnerOrderStatus.ALREADY_CONRIRM.equals(orderStatus)) {
						des = "订单号"+orderId +",订单状态由"+ OwnerOrderStatus.keyOf(orderStatus).getValue()+"变成:"+ OwnerOrderStatus.CANCEL_ONGOING.getValue();
						//更新销售单和采购单状态
						updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.CANCEL_ONGOING));
						hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_ONGOING.getKey());
						hotelOrder.setTcOrderStatus(TcOrderStatus.CANCEL_ING.getKey());
						hotelOrderMapper.updateById(hotelOrder);
					}else {
						logger.error("更新订单状态失败,订单状态非已支付或已确认!");
						throw new GSSException("更新酒店订单", "0108", "更新订单状态失败,订单状态非已支付、已确认!");
					}
				}
				
			}else if (pushOrderStatus.getKey().equals(TcOrderStatus.CANCEL_FINISH.getKey())) {	//已退订
				if (!orderStatus.equals(OwnerOrderStatus.CANCEL_OK.getKey())) {
					des = "订单号"+orderId +",订单状态由"+ OwnerOrderStatus.keyOf(orderStatus).getValue()+"变成:"+ OwnerOrderStatus.CANCEL_OK.getValue();
					if (OwnerOrderStatus.PAY_OK.equals(orderStatus) || OwnerOrderStatus.CANCEL_ONGOING.equals(orderStatus) || OwnerOrderStatus.ALREADY_CONRIRM.equals(orderStatus)) {
						try {
							BigDecimal saleRefund = saleRefund(AgentUtil.getAgent(), hotelOrder);
							if (null != saleRefund && saleRefund.compareTo(BigDecimal.ZERO) == 1) {	//退款成功
								des = "订单号"+hotelOrder.getHotelOrderNo() +",订单状态由"+ OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.CANCEL_OK.getValue();
								//更新销售单和采购单状态
							    updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.CANCEL_OK));
							   //更新酒店订单状态
							    hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_OK.getKey());
							    hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_CANCEL.getKey());
							    hotelOrder.setCancelTime(new Date());
							    hotelOrder.setFactTotalPrice(hotelOrder.getFactTotalPrice().subtract(saleRefund));
							    hotelOrderMapper.updateById(hotelOrder);
							}
						} catch (GSSException e) {
							//更新酒店订单状态
						    hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_BAD.getKey());
						    hotelOrder.setCancelTime(new Date());
						    hotelOrderMapper.updateById(hotelOrder);
						    return false;
						}
					}else {
						logger.error("更新订单状态失败,订单状态非已支付、已确认、退订中!");
						throw new GSSException("更新酒店订单", "0107", "更新订单状态失败,与本地订单状态不符!");
					}
				}

			}else if (pushOrderStatus.getKey().equals(TcOrderStatus.ALREADY_CANCEL.getKey())) { //已取消
				if (!orderStatus.equals(OwnerOrderStatus.CANCEL_OK.getKey())) {
					des = "订单号"+orderId +",订单状态由"+ OwnerOrderStatus.keyOf(orderStatus).getValue()+"变成:"+ OwnerOrderStatus.CANCEL_OK.getValue();
					//更新销售单和采购单状态
					updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.CANCEL_OK));
					hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_OK.getKey());
					hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_CANCEL.getKey());
					hotelOrderMapper.updateById(hotelOrder);
				}
			}
			
		}else {
			logger.error("bqy酒店订单状态更新异常!");
			throw new GSSException("更新状态信息异常", "0111", "bqy酒店订单更新状态异常!");
		}
		return true;
	}
	
	public String messageReplace(String message, HotelOrder hotelOrder){
		SimpleDateFormat simpleorder = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simple = new SimpleDateFormat("yyyy/MM/dd");
		String replace1 = message.replace("{$OrderNo}", hotelOrder.getSaleOrderNo().toString());
		String replace2 = replace1.replace("{$CreateDate}", simpleorder.format(hotelOrder.getCreateOrderTime()));
		String replace3 = replace2.replace("{$HotelName}", hotelOrder.getHotelName());
		String replace4 = replace3.replace("{$Address}", hotelOrder.getHotelAddress());
		String replace5 = replace4.replace("{$Phone}", hotelOrder.getHotelPhone());
		String replace6 = replace5.replace("{$RoomNum}", hotelOrder.getBookCount().toString());
		String replace7 = replace6.replace("{$RoomNight}", hotelOrder.getNightCount().toString());
		String replace8 = replace7.replace("{$RoomType}", hotelOrder.getProName());
		String replace9 = replace8.replace("{$DayInfo}", simple.format(hotelOrder.getArrivalDate()).substring(5, 10)+"-"+simple.format(hotelOrder.getDepartureDate()).substring(5, 10)+" 总价¥"+hotelOrder.getTotalPrice());
		String replace10 = replace9.replace("{$GuestsName}", hotelOrder.getGuestName());
		String replace = replace10.replace("{$LateTime}", hotelOrder.getArriveHotelTime().substring(0, 13));
		return replace;
	}
}
