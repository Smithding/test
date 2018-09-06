package com.tempus.gss.product.hol.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;

import com.tempus.gss.order.entity.enums.GoodsBigType;
import com.tempus.gss.order.entity.vo.ActualInfoSearchVO;
import com.tempus.gss.order.entity.vo.CertificateCreateVO;
import com.tempus.gss.order.entity.vo.CreatePlanAmountVO;
import com.tempus.gss.product.hol.api.entity.SaleChangeExt;
import com.tempus.gss.product.hol.api.entity.WhiteListPhone;
import com.tempus.gss.product.hol.api.entity.response.tc.*;
import com.tempus.gss.product.hol.api.entity.vo.bqy.EnumOrderStatus;
import com.tempus.gss.product.hol.api.entity.vo.bqy.RoomInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.*;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.HotelOrderInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.OrderPayResult;
import com.tempus.gss.product.hol.dao.SaleChangeExtMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.AsyncResult;
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
import com.tempus.gss.order.entity.BusinessOrderInfo;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.service.IActualAmountRecorService;
import com.tempus.gss.order.service.IBuyOrderService;
import com.tempus.gss.order.service.ICertificateService;
import com.tempus.gss.order.service.IPlanAmountRecordService;
import com.tempus.gss.order.service.ISaleOrderService;
import com.tempus.gss.order.service.ITransationOrderService;
import com.tempus.gss.product.hol.api.entity.request.tc.CancelOrderBeforePayReq;
import com.tempus.gss.product.hol.api.entity.request.tc.OrderCreateReq;
import com.tempus.gss.product.hol.api.entity.response.HotelOrder;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.CreateOrderRespone;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.OrderCancelResult;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.BaseRoomInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.CancelLimitInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.RoomBedTypeInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.RoomInfoItem;
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

	private static final String BQY = "bqy";
	private static final String WAIT_DISPOSE = "1";
	private static final String ALREADY_DISPOSE = "2";


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
	private ICertificateService certificateService;
	
	@Reference
	private IActualAmountRecorService actualAmountRecorService;

	@Autowired
	private MongoTemplate mongoTemplate1;
	
	@Reference
	private ISmsTemplateDetailService smsTemplateDetailService;

	@Autowired
	private SaleChangeExtMapper saleChangeExtMapper;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Value("${tc.contactNo}")
	private String tempusMobile;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
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
            		Boolean orderCancelResult = bqyHotelInterService.cancelOrder(cancelParam);
            		if (orderCancelResult) {
            			des = "订单号"+hotelOrder.getHotelOrderNo() +",订单状态由"+ OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.CANCEL_OK.getValue();
            			//更新销售单和采购单状态
            		    updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.CANCEL_OK));
            		   //更新酒店订单状态
            		    hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_OK.getKey());
            		    hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_CANCEL.getKey());
            		    hotelOrder.setCancelTime(new Date());
            		    hotelOrderMapper.updateById(hotelOrder); 
            		    cancelOrderRes.setResult(true);
            		    cancelOrderRes.setMsg("订单取消成功!");
            		    cancelOrderRes.setLasestCancelTime(hotelOrder.getCancelPenalty());
            		}else {
            			cancelOrderRes.setResult(false);
            		    cancelOrderRes.setMsg("订单取消失败!");
            		    cancelOrderRes.setLasestCancelTime(hotelOrder.getCancelPenalty());
            		}
            		
            	}else if (TcOrderStatus.ALREADY_PAY.getKey().equals(orderStatus) || TcOrderStatus.WAIT_TC_CONFIRM.getKey().equals(orderStatus) || TcOrderStatus.ALREADY_TC_CONFIRM.getKey().equals(orderStatus)) {// 已支付 ||待BQY确认 || BQY已确认
            		String policyType = hotelOrder.getDbCancelRule();	//政策类型 cancelOrderRes
            		if("0".equals(policyType) || "8".equals(policyType)) {
            			cancelOrderRes.setResult(false);
                        cancelOrderRes.setMsg("该订单不可取消!");
            		}else if ("1".equals(policyType)) {	//免费取消, 任意退
						des = cancelOrder(agent, orderCancelBeforePayReq, cancelOrderRes, cancelParam, hotelOrder, des);
            		}else if ("2".equals(policyType) || "4".equals(policyType)) {	//限时取消 || 超时担保限时取消
            			String latestArriveTime = hotelOrder.getCancelPenalty();
            			String currentTime = sdf.format(new Date());
            			long compareDate = DateUtil.compareDateStr(latestArriveTime, currentTime);
            			if (compareDate > 0) {
            				cancelOrderRes.setResult(false);
                            cancelOrderRes.setMsg("已超过订单取消时间,该订单不可取消!");
                            cancelOrderRes.setLasestCancelTime(hotelOrder.getCancelPenalty());
            			}else {
            				des = cancelOrder(agent, orderCancelBeforePayReq, cancelOrderRes, cancelParam, hotelOrder, des);
            			}
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
		Boolean orderCancelResult = bqyHotelInterService.cancelOrder(cancelParam);
		if (orderCancelResult) {
			SaleChangeExt saleChangeExt = createSaleChangeExt(agent, hotelOrder, BQY);	// 创建退款单
			BigDecimal saleRefundCert = saleRefund(agent, hotelOrder); //退款
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
			    cancelOrderRes.setMsg("订单取消成功!");
			    cancelOrderRes.setLasestCancelTime(hotelOrder.getCancelPenalty());

				saleChangeExt.setStatus(ALREADY_DISPOSE);
			    saleChangeExtMapper.updateById(saleChangeExt);
			}else {
				throw new GSSException("取消酒店订单", "0107", "取消酒店订单,退款失败!");
			}
			
		}else {
			cancelOrderRes.setResult(false);
		    cancelOrderRes.setMsg("订单取消失败!");
		    cancelOrderRes.setLasestCancelTime(hotelOrder.getCancelPenalty());
		}

		return des;
	}

	/**
	 * 创建取消订单
	 * @param agent
	 * @param hotelOrder
	 * @param flag 区分是否为供应商推送
	 * @return
	 */
	public SaleChangeExt createSaleChangeExt(Agent agent, HotelOrder hotelOrder, String flag) {
		//创建取消订单记录
		SaleChangeExt cancelRecord = new SaleChangeExt();
		long saleChangeNo = IdWorker.getId();
		cancelRecord.setSaleChangeNo(saleChangeNo);
		cancelRecord.setOwner(hotelOrder.getOwner());
		cancelRecord.setSaleOrderNo(hotelOrder.getSaleOrderNo());
		cancelRecord.setModifyTime(new Date());
		cancelRecord.setCreateTime(new Date());
		if(BQY.equals(flag)) {
			cancelRecord.setCreator(BQY);
			cancelRecord.setModifier(BQY);
		}else {
			cancelRecord.setCreator(agent.getAccount());
			cancelRecord.setModifier(agent.getAccount());
		}
		cancelRecord.setChangeType(2);
		cancelRecord.setValid(1);
		cancelRecord.setStatus(WAIT_DISPOSE);
		saleChangeExtMapper.insertSelective(cancelRecord);
		return cancelRecord;
	}

	/**
	 * 退款
	 * @param agent
	 * @param hotelOrder
	 * @return
	 */
	private BigDecimal saleRefund(Agent agent, HotelOrder hotelOrder) {
		//退款操作
		ActualInfoSearchVO actualInfo = actualAmountRecorService.queryActualInfoByBusNo(agent, hotelOrder.getSaleOrderNo(), 2);
		CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
		certificateCreateVO.setPayNo(actualInfo.getPayNo());
		certificateCreateVO.setCustomerTypeNo(Long.valueOf(hotelOrder.getRequestText()));
		certificateCreateVO.setCustomerNo(hotelOrder.getCustomerNo());
		certificateCreateVO.setIncomeExpenseType(2);
		certificateCreateVO.setAmount(hotelOrder.getTotalPrice());
		List<BusinessOrderInfo> businessOrderInfoList = new ArrayList<>();
		BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();
		businessOrderInfo.setRecordNo(hotelOrder.getSaleOrderNo());
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
	public Boolean bqyPushOrderInfo(Agent agent, BQYPushOrderInfo bqyPushOrderInfo) throws GSSException{
		logger.info("推送更新订单状态:{}",JSON.toJSONString(bqyPushOrderInfo));
		if (StringUtil.isNotNullOrEmpty(bqyPushOrderInfo) && StringUtil.isNotNullOrEmpty(bqyPushOrderInfo.getOrderNumber())
				&& StringUtil.isNotNullOrEmpty(bqyPushOrderInfo.getOrderId())) {
			Integer orderType = bqyPushOrderInfo.getOrderType();

			if (null == orderType || 2 - orderType != 0) {
				logger.error("bqy推送订单类型不属于酒店订单!");
				throw new GSSException("更新状态信息异常", "0191", "推送订单类型不属于酒店订单!");
			}
			//订单号
			Long orderNumber = bqyPushOrderInfo.getOrderNumber();
			if (null == orderNumber) {
				logger.error("bqy推送酒店订单号为空!");
				throw new GSSException("更新状态信息异常", "0191", "推送酒店订单号为空!");
			}
			//子订单号
			//Long orderId = bqyPushOrderInfo.getOrderId();
			//HotelOrder hotelOrder = hotelOrderMapper.getOrderByNo(String.valueOf(orderNumber));
			HotelOrder queryHotelOrder = new HotelOrder();
			queryHotelOrder.setHotelOrderNo(String.valueOf(orderNumber));
			HotelOrder hotelOrder = hotelOrderMapper.selectOne(queryHotelOrder);
			if (null == hotelOrder) {
				logger.info("bqy订单推送,查询订单为空,没有该订单!");
				throw new GSSException("更新状态信息异常", "0200", "查询订单为空,没有该订单!");
			}
			LogRecord LogRecord=new LogRecord();
			LogRecord.setBizCode("HOL-Order");
			LogRecord.setTitle("酒店订单状态");
			LogRecord.setBizNo(hotelOrder.getHotelOrderNo());
			hotelOrder.setModifier("供应商");
			hotelOrder.setModifyTime(new Date());
			String des= "";
			String orderStatus = hotelOrder.getOrderStatus();
			//订单状态
	 		TcOrderStatus pushOrderStatus = bqyPushOrderInfo.getChangeStatus();
			if (pushOrderStatus.getKey().equals(TcOrderStatus.ALREADY_TC_CONFIRM.getKey())) {	//订单确认
				HotelOrderInfo hotelOrderInfo = getBQYHotelOrderInfo(Long.valueOf(orderNumber));
				if (3 - hotelOrderInfo.getUserStatus() == 0) {
					if(!orderStatus.equals(OwnerOrderStatus.ALREADY_CONRIRM.getKey())) {
						if (OwnerOrderStatus.PAY_OK.getKey().equals(orderStatus)) {
							//改变订单状态
							des = changeOrderStatus(agent,hotelOrder, OwnerOrderStatus.ALREADY_CONRIRM, TcOrderStatus.ALREADY_TC_CONFIRM);
							//更新数据库
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

						}else {
							logger.error("更新订单状态失败,订单状态非已支付!");
							throw new GSSException("更新酒店订单", "0106", "更新订单状态失败,订单状态非已支付!");
						}
					}
				}else{
					logger.error("更新订单状态失败,供应商非已确认状态!");
					throw new GSSException("更新酒店订单", "0106", "供应商非已确认状态!");
				}
			}else if (pushOrderStatus.getKey().equals(TcOrderStatus.CANCEL_ING.getKey())) {	//退订中
				HotelOrderInfo hotelOrderInfo = getBQYHotelOrderInfo(Long.valueOf(orderNumber));
				if (5 - hotelOrderInfo.getUserStatus() == 0) {
					if (!orderStatus.equals(OwnerOrderStatus.CANCEL_ONGOING.getKey())) {
						if (OwnerOrderStatus.PAY_OK.getKey().equals(orderStatus) || OwnerOrderStatus.ALREADY_CONRIRM.getKey().equals(orderStatus)) {
							des = changeOrderStatus(agent, hotelOrder, OwnerOrderStatus.CANCEL_ONGOING, TcOrderStatus.CANCEL_ING);
							hotelOrderMapper.updateById(hotelOrder);
						}else {
							logger.error("更新订单状态失败,订单状态非已支付或已确认!");
							throw new GSSException("更新酒店订单", "0108", "更新订单状态失败,订单状态非已支付、已确认!");
						}
					}
				} else {
					logger.error("更新订单状态失败,供应商非退订中状态!");
					throw new GSSException("更新酒店订单", "0106", "供应商非退订中状态!");
				}
			}else if (pushOrderStatus.getKey().equals(TcOrderStatus.CANCEL_FINISH.getKey())) {	//已退订
				HotelOrderInfo hotelOrderInfo = getBQYHotelOrderInfo(orderNumber);
				if (508 - hotelOrderInfo.getUserStatus() == 0) {
					if (!orderStatus.equals(OwnerOrderStatus.CANCEL_OK.getKey())) {
						if (OwnerOrderStatus.PAY_OK.getKey().equals(orderStatus) || OwnerOrderStatus.CANCEL_ONGOING.getKey().equals(orderStatus) || OwnerOrderStatus.ALREADY_CONRIRM.getKey().equals(orderStatus)) {
							try {
								SaleChangeExt saleChangeExt = createSaleChangeExt(agent, hotelOrder, BQY);//创建退款单
								BigDecimal saleRefund = saleRefund(agent, hotelOrder);
								if (null != saleRefund && saleRefund.compareTo(BigDecimal.ZERO) == 1) {	//退款成功
									logger.info("bqy酒店订单退款成功,取消订单号为:" + orderNumber);
									des = changeOrderStatus(agent, hotelOrder, OwnerOrderStatus.CANCEL_OK, TcOrderStatus.ALREADY_CANCEL);
									hotelOrder.setFactTotalPrice(hotelOrder.getFactTotalPrice().subtract(saleRefund));
									hotelOrderMapper.updateById(hotelOrder);

									saleChangeExt.setStatus(ALREADY_DISPOSE);
									saleChangeExtMapper.updateById(saleChangeExt);
								}
							} catch (GSSException e) {
								logger.info("bqy酒店订单取消异常:取消订单号为:" + orderNumber);
								//更新酒店订单状态
								hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_BAD.getKey());
								hotelOrder.setCancelTime(new Date());
								hotelOrderMapper.updateById(hotelOrder);
								return false;
							}
						}else if (OwnerOrderStatus.WAIT_PAY.getKey().equals(orderStatus)){
							des = changeOrderStatus(agent, hotelOrder, OwnerOrderStatus.CANCEL_OK, TcOrderStatus.ALREADY_CANCEL);
							hotelOrderMapper.updateById(hotelOrder);
						}else {
							logger.error("更新订单状态失败,订单状态非已支付、已确认、退订中!");
							throw new GSSException("更新酒店订单", "0107", "更新订单状态失败,与本地订单状态不符!");
						}
					}
				}else {
					logger.error("更新订单状态失败,供应商非已已退订状态!");
					throw new GSSException("更新酒店订单", "0106", "供应商非已退订状态!");
				}
			}else if (pushOrderStatus.getKey().equals(TcOrderStatus.ALREADY_CANCEL.getKey())) { //已取消(bqy用户下单未支付,超时自动取消)
				HotelOrderInfo hotelOrderInfo = getBQYHotelOrderInfo(Long.valueOf(orderNumber));
				if (9 - hotelOrderInfo.getUserStatus() == 0) {
					if (!OwnerOrderStatus.CANCEL_OK.getKey().equals(orderStatus)) {
						//改变订单状态
						des = changeOrderStatus(agent, hotelOrder, OwnerOrderStatus.CANCEL_OK, TcOrderStatus.ALREADY_CANCEL);
						//更新数据库
						hotelOrderMapper.updateById(hotelOrder);
					}
				} else {
					logger.error("更新订单状态失败,供应商非已取消状态!");
					throw new GSSException("更新酒店订单", "0106", "供应商非已取消状态!");
				}

			}
			/*LogRecord.setCreateTime(new Date());
			LogRecord.setDesc(des);
			LogRecord.setOptName(agent.getAccount());
			logService.insert(LogRecord);*/
			logger.info(des);
		}else {
			logger.error("bqy酒店订单状态更新异常!");
			throw new GSSException("更新状态信息异常", "0111", "bqy酒店订单更新状态异常!");
		}
		return true;
	}

	public HotelOrderInfo getBQYHotelOrderInfo(Long orderNumber) {
		OrderPayReq orderPayReq = new OrderPayReq();
		orderPayReq.setOrderNumber(orderNumber);
		return bqyHotelInterService.orderDetail(orderPayReq);
	}

	@Override
	public Future<OrderInfomationDetail> futureOrderDetailInfo(Agent agent, String hotelOrderNo) {
		HotelOrderInfo hotelOrderInfo = getBQYHotelOrderInfo(Long.valueOf(hotelOrderNo));
		if (hotelOrderInfo != null) {
			OrderInfomationDetail orderInfomationDetail = new OrderInfomationDetail();
			orderInfomationDetail.setIsSuccess(true);
			List<OrderInfoModel> orderInfoList = new ArrayList<>();
			OrderInfoModel orderInfo = new OrderInfoModel();
			orderInfo.setCusOrderId(hotelOrderInfo.getHotelOrderId().toString());
			orderInfo.setProductTitle(hotelOrderInfo.getHotelName());
			PassengerModel passengerModel = new PassengerModel();
			passengerModel.setName(hotelOrderInfo.getGuestName());
			orderInfo.setPassengers(Arrays.asList(passengerModel));//Arrays.asList(hotelOrderInfo.getGuestName())

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String checkInDate = simpleDateFormat.format(hotelOrderInfo.getCheckInDate());
			String checkOutDate = simpleDateFormat.format(hotelOrderInfo.getCheckOutDate());
			orderInfo.setStartTime(checkInDate);
			orderInfo.setEndTime(checkOutDate);

			ResourceModel resourceModel = new ResourceModel();
			resourceModel.setResourceId(hotelOrderInfo.getHotelId());
			resourceModel.setName(hotelOrderInfo.getHotelName());
			resourceModel.setProductName(hotelOrderInfo.getProductName());
			resourceModel.setType("0");
			resourceModel.setPriceFraction(hotelOrderInfo.getBookNumber());
			resourceModel.setRemark(hotelOrderInfo.getSpecial());
			orderInfo.setResources(Arrays.asList(resourceModel));

			//resourceModel.setArrivalTime(hotelOrderInfo.getArriveDate()); //到店时间
			orderInfo.setLasestCancelTime(format.format(hotelOrderInfo.getArriveDate())); //到店时间


			try {
				CountModel countModel = new CountModel();
				int daysBetween = com.tempus.gss.product.hol.api.util.DateUtil.daysBetween2(checkInDate, checkOutDate);
				countModel.setRoomCount(hotelOrderInfo.getBookNumber() * daysBetween);
				orderInfo.setCounts(countModel);
			} catch (ParseException e) {
				logger.error("计算两日期天数出错!");
				throw new GSSException("计算日期天数","0112", "计算两日期天数出错!");
			}
			orderInfo.setOrigin(hotelOrderInfo.getPayPrice());
			Integer orderStatus = hotelOrderInfo.getUserStatus(); //订单状态
			if (null != orderStatus) {
				orderInfo.setOrderStatus(String.valueOf(orderStatus));
                EnumOrderStatus enumOrderStatus = EnumOrderStatus.keyOf(orderStatus);   //订单枚举
                orderInfo.setOrderFlag(enumOrderStatus.getValue());
			}

			orderInfoList.add(orderInfo);
			orderInfomationDetail.setOrderInfos(orderInfoList);
			return new AsyncResult<>(orderInfomationDetail);
		}else {
			logger.info("bqy酒店订单查询为空!");
			throw new GSSException("bqy酒店订单查询为空", "0101", "bqy酒店订单查询为空!");
		}
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Boolean updateOrderStatus(Agent agent, String orderNo) {
    	boolean bool = false;
    	LogRecord logRecord = new LogRecord();
    	String des = "手动更新失败!";
    	//查询本地订单
		HotelOrder queryHotelOrder = new HotelOrder();
		queryHotelOrder.setHotelOrderNo(String.valueOf(orderNo));
		HotelOrder hotelOrder = hotelOrderMapper.selectOne(queryHotelOrder);
		if (StringUtil.isNullOrEmpty(hotelOrder)) {
			logger.info("手动更新状态信息异常: 查询本地订单为空,订单号为:" + hotelOrder);
			throw new GSSException("手动更新状态信息异常", "0191", "查询订单号为空,订单号为:" + hotelOrder);
		}
		//获取bqy订单详情
		OrderPayReq orderPayReq = new OrderPayReq();
		orderPayReq.setOrderNumber(Long.valueOf(orderNo));
		HotelOrderInfo bqyOrderInfo = bqyHotelInterService.orderDetail(orderPayReq);
		if (StringUtil.isNullOrEmpty(hotelOrder)) {
			logger.info("手动更新状态信息异常: 获取bqy订单为空,订单号为:" + hotelOrder);
			throw new GSSException("手动更新状态信息异常", "0192", "查询bqy订单为空,订单号为:" + hotelOrder);
		}
		TcOrderStatus bqyOrderStatus = TcOrderStatus.bqyKey(String.valueOf(bqyOrderInfo.getUserStatus()));
		/*TcOrderStatus tcOrderStatus = TcOrderStatus.bqyKey(hotelOrder.getTcOrderStatus());
		if (bqyOrderStatus.equals(tcOrderStatus)) {	//状态一致,无需更新
			logger.info("手动更新状态信息: 与供应商状态一致,无需更新!");
			return false;
		}*/

		if (TcOrderStatus.WAIT_PAY.getKey().equals(bqyOrderStatus.getKey())) { //bqy状态为待支付
			if (OwnerOrderStatus.PAY_OK.getKey().equals(hotelOrder.getOrderStatus())) { //本地为已支付,调用bqy订单支付接口
				OrderPayResult payResult = bqyHotelInterService.orderPay(orderPayReq);
				if (payResult.getReseult()) {
					des = "手动更新状态订单号"+orderNo +",通知bqy支付成功!";
					hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_PAY.getKey());
					hotelOrder.setResultCode(payResult.getPayPrice());	//与八千翼的支付价格
					hotelOrder.setModifier(agent.getAccount());
					hotelOrder.setModifyTime(new Date());
					hotelOrderMapper.updateById(hotelOrder);
					bool = true;
				}
			}
		}else if(TcOrderStatus.ALREADY_TC_CONFIRM.getKey().equals(bqyOrderStatus.getKey())) { //bqy已确认
			if (OwnerOrderStatus.PAY_OK.getKey().equals(hotelOrder.getOrderStatus())) {
				des = changeOrderStatus(agent, hotelOrder, OwnerOrderStatus.ALREADY_CONRIRM, TcOrderStatus.ALREADY_TC_CONFIRM);
				hotelOrderMapper.updateById(hotelOrder);
				bool = true;
			}
		}else if (TcOrderStatus.CANCEL_ING.getKey().equals(bqyOrderStatus.getKey())) { //bqy退订中
			if (OwnerOrderStatus.PAY_OK.getKey().equals(hotelOrder.getOrderStatus()) || OwnerOrderStatus.ALREADY_CONRIRM.getKey().equals(hotelOrder.getOrderStatus())) {
				des = changeOrderStatus(agent, hotelOrder, OwnerOrderStatus.CANCEL_ONGOING, TcOrderStatus.CANCEL_ING);
				hotelOrderMapper.updateById(hotelOrder);
				bool = true;
			}
		}else if (TcOrderStatus.CANCEL_FINISH.getKey().equals(bqyOrderStatus.getKey())) { //bqy已退订
			if (OwnerOrderStatus.PAY_OK.getKey().equals(hotelOrder.getOrderStatus()) || OwnerOrderStatus.CANCEL_ONGOING.getKey().equals(hotelOrder.getOrderStatus()) || OwnerOrderStatus.ALREADY_CONRIRM.getKey().equals(hotelOrder.getOrderStatus())) {
				try {
					BigDecimal saleRefund = saleRefund(AgentUtil.getAgent(), hotelOrder);
					if (null != saleRefund && saleRefund.compareTo(BigDecimal.ZERO) == 1) {	//退款成功
						logger.info("bqy酒店订单退款成功,取消订单号为:" + orderNo);
						des = changeOrderStatus(agent, hotelOrder, OwnerOrderStatus.CANCEL_OK, TcOrderStatus.ALREADY_CANCEL);
						hotelOrder.setFactTotalPrice(hotelOrder.getFactTotalPrice().subtract(saleRefund));
						hotelOrderMapper.updateById(hotelOrder);
						bool = true;
					}
				} catch (GSSException e) {
					des = "bqy酒店订单取消异常:取消订单号为:" + orderNo;
					//更新酒店订单状态
					hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_BAD.getKey());
					hotelOrder.setCancelTime(new Date());
					hotelOrderMapper.updateById(hotelOrder);
					logRecord.setCreateTime(new Date());
					logRecord.setDesc(des);
					logRecord.setOptName(agent.getAccount());
					logService.insert(logRecord);
					return false;
				}
			}else if (OwnerOrderStatus.WAIT_PAY.getKey().equals(hotelOrder.getOrderStatus())){	//待支付退订
				des = changeOrderStatus(agent, hotelOrder, OwnerOrderStatus.CANCEL_OK, TcOrderStatus.ALREADY_CANCEL);
				hotelOrderMapper.updateById(hotelOrder);
				bool = true;
			}
		}else if (TcOrderStatus.ALREADY_CANCEL.getKey().equals(bqyOrderStatus.getKey())) {  //已取消,用户超时自动取消
			if (!OwnerOrderStatus.CANCEL_OK.getKey().equals(hotelOrder.getOrderStatus())) {
				//改变订单状态
				des = changeOrderStatus(agent, hotelOrder, OwnerOrderStatus.CANCEL_OK, TcOrderStatus.ALREADY_CANCEL);
				//更新数据库
				hotelOrderMapper.updateById(hotelOrder);
				bool = true;
			}
		}
		return saveLog(agent, bool, logRecord, des);
	}

	public Boolean saveLog(Agent agent, boolean bool, LogRecord logRecord, String des) {
		logger.info(des);
		if (bool) {
			logRecord.setCreateTime(new Date());
			logRecord.setDesc(des);
			logRecord.setOptName(agent.getAccount());
			logService.insert(logRecord);
		}
		return bool;
	}

	/**
	 * 改变订单状态
	 * @param agent
	 * @param orderId
	 * @param hotelOrder
	 * @param orderStatus
	 * @param changeStatus
	 * @param tcOrderStatus
	 * @return
	 */
	public String changeOrderStatus(Agent agent, HotelOrder hotelOrder, OwnerOrderStatus changeStatus, TcOrderStatus tcOrderStatus) {
		String des = "订单号"+hotelOrder.getHotelOrderNo() +",订单状态由"+ OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ changeStatus.getValue();
		//更新销售单和采购单状态
		updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(changeStatus));
		hotelOrder.setOrderStatus(changeStatus.getKey());
		hotelOrder.setTcOrderStatus(tcOrderStatus.getKey());
		if (OwnerOrderStatus.CANCEL_OK.getKey().equals(changeStatus.getKey())) {
			hotelOrder.setCancelTime(new Date());
		}
		return des;
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

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public HotelOrder createOrder(Agent agent, CreateOrderReq orderReq, OrderCreateReq orderCreateReq, RoomInfo roomInfo) throws GSSException {
		if (tempusMobile.equals(orderCreateReq.getLinkManMobile())) {
			orderReq.setMobile(tempusMobile);
		}else {
			WhiteListPhone phone = mongoTemplate1.findOne(new Query(Criteria.where("_id").is(orderCreateReq.getLinkManMobile())), WhiteListPhone.class);
			if (null != phone) {
				orderReq.setMobile(orderCreateReq.getLinkManMobile());
			}else {
				orderReq.setMobile(tempusMobile);
			}
		}
		if (StringUtil.isNullOrEmpty(orderReq)) {
			logger.error("orderReq查询条件为空");
			throw new GSSException("创建订单信息", "0101", "orderReq查询条件为空");
		}
		if (StringUtil.isNullOrEmpty(agent)) {
			logger.error("agent对象为空");
			throw new GSSException("创建酒店订单", "0102", "agent对象为空");
		}
		if (StringUtil.isNullOrEmpty(orderReq.getHotelId())) {
			logger.error("酒店ID为空！");
			throw new GSSException("创建酒店订单", "0103", "酒店ID为空！");
		}
		if (StringUtil.isNullOrEmpty(orderReq.getHotelRoomId())) {
			logger.error("房型ID为空！");
			throw new GSSException("创建酒店订单", "0104", "房型ID为空！");
		}
		if (StringUtil.isNullOrEmpty(orderReq.getProductId())) {
			logger.error("房间ID为空！");
			throw new GSSException("创建酒店订单", "0109", "房间ID为空！");
		}
		if (StringUtils.isBlank(orderReq.getPassengers())) {
			logger.error("酒店入住人为空!");
			throw new GSSException("创建酒店订单", "0105", "酒店入住人为空!");
		}
		if (StringUtil.isNullOrEmpty(orderReq.getBookNumber())) {
			logger.error("预定房间数为空！");
			throw new GSSException("创建酒店订单", "0106", "预定房间数为空！");
		}
		/*if (StringUtil.isNullOrEmpty(orderReq.getUnitPrice())) {
			logger.error("酒店单价为空！");
			throw new GSSException("创建酒店订单", "0107", "酒店单价为空！");
		}*/
		if (StringUtil.isNullOrEmpty(orderReq.getCheckInTime())) {
			logger.error("入住时间为空！");
			throw new GSSException("创建酒店订单", "0107", "入住时间为空！");
		}
		if (StringUtil.isNullOrEmpty(orderReq.getCheckOutTime())) {
			logger.error("离店时间为空！");
			throw new GSSException("创建酒店订单", "0107", "离店时间为空！");
		}
		if (StringUtil.isNullOrEmpty(orderReq.getLateArrivalTime())) {
			logger.error("最迟到店时间(整点数)为空！");
			throw new GSSException("创建酒店订单", "0113", "最迟到店时间(只能是整点,默认18点)为空！");
		}
		if (StringUtil.isNullOrEmpty(orderReq.getHotelMobile())) {
			logger.error("酒店联系电话为空！");
			throw new GSSException("创建酒店订单", "0109", "酒店联系电话为空！");
		}
		if (StringUtil.isNullOrEmpty(orderReq.getCancelNotice())) {
			logger.error("取消政策为空！");
			throw new GSSException("创建酒店订单", "0110", "取消政策为空！");
		}
		if (StringUtil.isNullOrEmpty(orderReq.getOrderLink())) {
			logger.error("联系人姓名为空！");
			throw new GSSException("创建酒店订单", "0110", "联系人姓名为空！");
		}
		if (StringUtil.isNullOrEmpty(orderReq.getMobile())) {
			logger.error("联系人电话为空！");
			throw new GSSException("创建酒店订单", "0112", "联系人电话为空！");
		}
		/*
		 * if (StringUtil.isNullOrEmpty(orderReq.getPaymentSign())) {
		 * logger.error("支付模式为空！"); throw new GSSException("创建酒店订单", "0114",
		 * "支付模式为空！"); }
		 */
		if (StringUtil.isNullOrEmpty(orderReq.getBreakfast())) {
			logger.error("早餐为空！");
			throw new GSSException("创建酒店订单", "0114", "早餐为空！");
		}

		Date dateStartDate;
		Date departureDate;
		int daysBetween;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateStartDate = dateFormat.parse(orderReq.getCheckInTime());
			departureDate = dateFormat.parse(orderReq.getCheckOutTime());
			/*Calendar cal = Calendar.getInstance();
			cal.setTime(departDate);
			cal.add(Calendar.DAY_OF_MONTH, 0);
			departureDate = cal.getTime();*/
			daysBetween = com.tempus.gss.ops.util.DateUtil.daysBetween(orderReq.getCheckInTime(),
					orderReq.getCheckOutTime());
		} catch (ParseException e) {
			logger.error("入住日期/离店日期 格式错误,请重新输入！入住日期/离店日期的正确格式为(yyyy-MM-dd)", e);
			throw new GSSException("创建酒店订单", "0129",
					"入住日期/离店日期 格式错误,请重新输入！入住日期/离店日期的正确格式为(yyyy-MM-dd)" + e.getMessage());
		}

		HotelOrder hotelOrder = new HotelOrder();

		//房间单价
		BigDecimal unitPrice = roomInfo.getPrice();
		//房间原单价
		BigDecimal oldUnitPrice = roomInfo.getOldPrice();
		//房间挂牌价
		BigDecimal settleFee = roomInfo.getSettleFee();
		//供应商ID
		String supplierId = roomInfo.getSupplierId();
		//预定检查类型
		String ratePlanCategory = roomInfo.getRatePlanCategory();
		//政策类型
		String policyType = roomInfo.getPolicyType();

		// 每天的价格记录
		String eachNightPrice = null;
		// 实际每天价格
		BigDecimal oneDayPrice = unitPrice.multiply(new BigDecimal(orderReq.getBookNumber()));
		// 实际总价格
		BigDecimal newTotalPrice = oneDayPrice.multiply(new BigDecimal(daysBetween));
		//未减佣金每天的价格
		BigDecimal oldOneDayPrice = oldUnitPrice.multiply(new BigDecimal(orderReq.getBookNumber()));
		//未减佣金总价
		BigDecimal oldTotalPrice = oldOneDayPrice.multiply(new BigDecimal(daysBetween));

		for (int i = 0; i < daysBetween; i++) {
			if (eachNightPrice == null || "".equals(eachNightPrice)) {
				eachNightPrice = oneDayPrice.toString();
			} else {
				eachNightPrice = eachNightPrice + "," + oneDayPrice.toString();
			}
		}

		orderReq.setHotelRoomName(roomInfo.getRoomTypeName());
		orderReq.setProductName(roomInfo.getRoomName());

		// 产品名称
		hotelOrder.setSupPriceName(roomInfo.getRoomTypeName());
		hotelOrder.setProName(roomInfo.getRoomName());

		orderCreateReq.setCancelPenalty(roomInfo.getLastCancelTime());

		hotelOrder.setBedTypeName(roomInfo.getBedTypeName());

		if (unitPrice == null) {
			logger.error("酒店房间单价为空!");
			throw new GSSException("创建酒店订单", "0107", "酒店单价为空！");
		}
		//orderReq.setUnitPrice(unitPrice);

		hotelOrder.setEachNightPrice(eachNightPrice);

		Long businessSignNo = IdWorker.getId();
		BuyOrder buyOrder = orderCreateReq.getBuyOrder();
		if (buyOrder == null) {
			buyOrder = new BuyOrder();
		}
		if (StringUtil.isNotNullOrEmpty(orderCreateReq.getSupplierNo())) {
			Long supplierNo = Long.valueOf(orderCreateReq.getSupplierNo());
			hotelOrder.setSupplierNo(supplierNo);
			buyOrder.setSupplierNo(supplierNo);
			// 从客商系统查询供应商信息
			Supplier supplier = supplierService.getSupplierByNo(agent, supplierNo);
			if (StringUtil.isNotNullOrEmpty(supplier)) {
				buyOrder.setSupplierTypeNo(supplier.getCustomerTypeNo());
			} else {
				throw new GSSException("创建酒店订单", "0130", "根据编号查询供应商信息为空！");
			}
		} else {
			logger.error("供应商信息不存在！");
			throw new GSSException("创建酒店订单", "0130", "供应商信息不存在！");
		}

		/* 创建销售订单 */
		Long saleOrderNo = maxNoService.generateBizNo("HOL_SALE_ORDER_NO", 13);
		//获取交易单
		SaleOrder saleOrder = orderCreateReq.getSaleOrder();
		if (null == saleOrder) {
			saleOrder = new SaleOrder();
		}
		saleOrder.setSaleOrderNo(saleOrderNo);
		saleOrder.setOwner(agent.getOwner());
		saleOrder.setSourceChannelNo(agent.getDevice());
		saleOrder.setCustomerTypeNo(agent.getType());
		saleOrder.setCustomerNo(agent.getNum());
		saleOrder.setOrderingLoginName(agent.getAccount());
		saleOrder.setGoodsType(GoodsBigType.GROGSHOP.getKey());//
		saleOrder.setGoodsSubType(GoodsBigType.GROGSHOP.getKey());//
		saleOrder.setOrderingTime(new Date());// 下单时间
		saleOrder.setPayStatus(1);// 待支付
		saleOrder.setValid(1);// 有效
		saleOrder.setBusinessSignNo(businessSignNo);
		saleOrder.setBsignType(1);// 1销采 2换票 3废和退 4改签
		saleOrder.setTransationOrderNo(orderCreateReq.getSaleOrder().getTransationOrderNo());
		saleOrder.setOrderType(1);
		saleOrder.setOrderChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.ORDER_ONGOING));
		saleOrder.setGoodsName(orderReq.getHotelName());
		saleOrderService.create(agent, saleOrder);
		
		//创建应收单
		CreatePlanAmountVO amountVO = new CreatePlanAmountVO();
		amountVO.setRecordNo(saleOrderNo);
		amountVO.setBusinessType(2);
		amountVO.setGoodsType(4);
		amountVO.setIncomeExpenseType(1);
		amountVO.setPlanAmount(newTotalPrice);
		amountVO.setRecordMovingType(1);
		planAmountRecordService.create(agent, amountVO);

		/* 创建采购单 */
		Long buyOrderNo = maxNoService.generateBizNo("HOL_BUY_ORDER_NO", 14);
		buyOrder.setOwner(agent.getOwner());
		buyOrder.setBuyOrderNo(buyOrderNo);
		buyOrder.setSaleOrderNo(saleOrderNo);
		buyOrder.setGoodsType(GoodsBigType.GROGSHOP.getKey());
		buyOrder.setGoodsSubType(GoodsBigType.GROGSHOP.getKey());
		buyOrder.setGoodsName(orderReq.getHotelName());
		buyOrder.setBuyChannelNo("HOTEL");
		buyOrder.setBusinessSignNo(businessSignNo);
		buyOrder.setBuyChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.ORDER_ONGOING));
		buyOrder.setBsignType(1);// 1销采 2换票 3废和退 4改签
		buyOrderService.create(agent, buyOrder);

		// 入住旅客
		String passengers = orderReq.getPassengers();

		// 创建酒店订单
		hotelOrder.setOwner(agent.getOwner());
		hotelOrder.setCustomerNo(agent.getNum());
		//hotelOrder.setDbOrderType(orderCreateReq.getDbOrderType());
		hotelOrder.setDbOrderMoney(orderCreateReq.getDbOrderMoney());
		hotelOrder.setDbCancelRule(orderCreateReq.getDbCancelRule());
		hotelOrder.setCancelPenalty(orderCreateReq.getCancelPenalty());		//最迟取消时间
		hotelOrder.setEarlyArriveTime(orderCreateReq.getEarlyArriveTime());
		hotelOrder.setLatestArriveTime(orderCreateReq.getLatestArriveTime());
		hotelOrder.setSaleOrderNo(saleOrderNo);
		hotelOrder.setBuyOrderNo(buyOrderNo);
		hotelOrder.setHotelCode(String.valueOf(orderCreateReq.getResId()));
		hotelOrder.setHotelName(orderReq.getHotelName());
		hotelOrder.setOrderType(1);	
		hotelOrder.setContactName(orderCreateReq.getLinkManName());
		hotelOrder.setContactNumber(orderCreateReq.getLinkManMobile());
		hotelOrder.setArrivalDate(dateStartDate);
		hotelOrder.setDepartureDate(departureDate);
		hotelOrder.setFactTotalPrice(newTotalPrice);	//分销商支付总价
		hotelOrder.setTotalPrice(oldTotalPrice);		//未减佣金总价
		hotelOrder.setNightCount(daysBetween);
		hotelOrder.setTransationOrderNo(orderCreateReq.getSaleOrder().getTransationOrderNo());
		hotelOrder.setTotalRefund(orderCreateReq.getTotalRebateRateProfit());		//佣金
		hotelOrder.setFactTotalRefund(orderCreateReq.getTotalRebateRateProfit());	//实际佣金
		hotelOrder.setFactTotalRefund(new BigDecimal(0));
		hotelOrder.setGuestName(passengers);
		hotelOrder.setGuestMobile(orderReq.getMobile());
		hotelOrder.setDbCancelRule(policyType);			//取消政策类型
		hotelOrder.setSupPriceName(roomInfo.getRoomName()); //房间名称
		hotelOrder.setProName(roomInfo.getRoomTypeName()); //房型名称
		if (StringUtil.isNotNullOrEmpty(orderCreateReq.getOrderRemark())) {
			hotelOrder.setRemark(orderCreateReq.getOrderRemark());
		}
		hotelOrder.setLocker(0L);
		hotelOrder.setOrderStatus(OwnerOrderStatus.ORDER_ONGOING.getKey());
		hotelOrder.setCreator(agent.getAccount());
		hotelOrder.setCreateOrderTime(new Date());
		hotelOrder.setProductUniqueId(orderCreateReq.getProductUniqueId());
		hotelOrder.setBookCount(orderCreateReq.getBookCount());
		hotelOrder.setPaymentSign(orderCreateReq.getPaymentSign());
		hotelOrder.setArriveHotelTime(orderCreateReq.getArriveHotelTime());
		hotelOrder.setHotelAddress(orderReq.getCityName() + "市" + orderReq.getAddress());
		hotelOrder.setHotelPhone(orderCreateReq.getHotelPhone());
		//hotelOrder.setProName(orderCreateReq.getProName());
		hotelOrder.setProId(orderCreateReq.getProId());
		hotelOrder.setBreakfastCount(orderCreateReq.getBreakfastCount());
		hotelOrder.setRequestText(String.valueOf(saleOrder.getCustomerTypeNo()));	//存储CustomerType
		//特殊要求
		if (StringUtils.isNoneBlank(orderCreateReq.getOrderRemark())) {
			//hotelOrder.setRequestText(orderCreateReq.getOrderRemark());
			orderReq.setRemark(orderCreateReq.getOrderRemark());
			hotelOrder.setRemark(orderCreateReq.getOrderRemark());
		}
		hotelOrderMapper.insertSelective(hotelOrder);

		//前台传入的价格
		BigDecimal beforeTotalPrice = orderCreateReq.getBeforeTotalPrice();
		
		//验证价格是否一致
		//BigDecimal checkPrice = bookOrderResult.getCheckPrice();
		
		if (beforeTotalPrice.compareTo(newTotalPrice) != 0) {
			logger.error("传入的总价与最新的总价不一致");
			throw new GSSException("创建酒店订单失败", String.valueOf(saleOrderNo), "价格不一致");
		}
		
		if (StringUtils.isBlank(supplierId)) {
			logger.error("BQY酒店供应商ID为空!");
			throw new GSSException("创建酒店订单失败!", "0132", "酒店供应商ID为空!");
		}
		
		if (StringUtils.isBlank(ratePlanCategory)) {
			logger.error("预定检查类型为空!");
			throw new GSSException("创建酒店订单失败!", "0133", "酒店预定检查类型为空!");
		}
		orderReq.setSupplierId(supplierId);
		orderReq.setRatePlanCategory(ratePlanCategory);
		orderReq.setUnitPrice(settleFee);
		//可以不传
		orderReq.setChannelType(2);
		CreateOrderRespone createOrderRespone = bqyHotelInterService.createOrder(orderReq);
		// 判断条件返回0订单创建失败
		if (null != createOrderRespone) {
			Long orderNo = createOrderRespone.getOrderNumber();
			if (null == orderNo || orderNo <= 0){
				logger.info("bqy酒店订单创建失败,返回结订单号为空或小于等于0!");
				throw new GSSException("bqy酒店订单创建失败!", String.valueOf(saleOrderNo), "酒店订单创建失败,返回订单号为空!");
			}
			//订单创建成功更新订单表中数据
			hotelOrder.setHotelOrderNo(orderNo.toString());
			hotelOrder.setTcOrderStatus(TcOrderStatus.WAIT_PAY.getKey());
			hotelOrder.setOrderStatus(OwnerOrderStatus.WAIT_PAY.getKey());
			hotelOrder.setResultCode(createOrderRespone.getPayPrice().toString());
			hotelOrderMapper.updateById(hotelOrder);
			return hotelOrder;
		}else {
			logger.info("bqy酒店订单创建失败,返回结果为空!");
			throw new GSSException("bqy酒店订单创建失败!", String.valueOf(saleOrderNo), "酒店订单创建失败,返回结果为空!");
		}
	}
}
