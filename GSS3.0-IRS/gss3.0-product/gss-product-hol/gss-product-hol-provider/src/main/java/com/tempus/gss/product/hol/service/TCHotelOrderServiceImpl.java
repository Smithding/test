package com.tempus.gss.product.hol.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.cps.service.ICustomerService;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.log.entity.LogRecord;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.mq.MqSender;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.GoodsBigType;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.service.IBuyOrderService;
import com.tempus.gss.order.service.ISaleOrderService;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.hol.api.entity.LogRecordHol;
import com.tempus.gss.product.hol.api.entity.request.tc.AssignDateHotelReq;
import com.tempus.gss.product.hol.api.entity.request.tc.CancelOrderBeforePayReq;
import com.tempus.gss.product.hol.api.entity.request.tc.CardSupportReq;
import com.tempus.gss.product.hol.api.entity.request.tc.IncrOrderChangeInfoReq;
import com.tempus.gss.product.hol.api.entity.request.tc.IsBookOrderReq;
import com.tempus.gss.product.hol.api.entity.request.tc.OrderCancelReq;
import com.tempus.gss.product.hol.api.entity.request.tc.OrderCreateReq;
import com.tempus.gss.product.hol.api.entity.request.tc.OrderDetailInfoReq;
import com.tempus.gss.product.hol.api.entity.request.tc.ResourceUseDateDetail;
import com.tempus.gss.product.hol.api.entity.request.tc.SpecialRequest;
import com.tempus.gss.product.hol.api.entity.request.tc.TcPushOrderInfo;
import com.tempus.gss.product.hol.api.entity.request.tc.TravlePassengerInfo;
import com.tempus.gss.product.hol.api.entity.response.HolErrorOrder;
import com.tempus.gss.product.hol.api.entity.response.HotelOrder;
import com.tempus.gss.product.hol.api.entity.response.StatusType;
import com.tempus.gss.product.hol.api.entity.response.tc.AssignDateHotel;
import com.tempus.gss.product.hol.api.entity.response.tc.BookableMessage;
import com.tempus.gss.product.hol.api.entity.response.tc.CancelOrderRes;
import com.tempus.gss.product.hol.api.entity.response.tc.CancelReasonModel;
import com.tempus.gss.product.hol.api.entity.response.tc.CancelTcOrderBeforePay;
import com.tempus.gss.product.hol.api.entity.response.tc.GuaranteeTypeIsBook;
import com.tempus.gss.product.hol.api.entity.response.tc.IncrOrderChangeInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.IncrementReason;
import com.tempus.gss.product.hol.api.entity.response.tc.IsBookOrder;
import com.tempus.gss.product.hol.api.entity.response.tc.OrderCreate;
import com.tempus.gss.product.hol.api.entity.response.tc.OrderIncrementInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.OrderInfoModel;
import com.tempus.gss.product.hol.api.entity.response.tc.OrderInfomationDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.OwnerOrderStatus;
import com.tempus.gss.product.hol.api.entity.response.tc.PassengerModel;
import com.tempus.gss.product.hol.api.entity.response.tc.PaymentWay;
import com.tempus.gss.product.hol.api.entity.response.tc.ProDetails;
import com.tempus.gss.product.hol.api.entity.response.tc.ProInfoDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.ProSaleInfoDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfos;
import com.tempus.gss.product.hol.api.entity.response.tc.ResourceModel;
import com.tempus.gss.product.hol.api.entity.response.tc.ResultTc;
import com.tempus.gss.product.hol.api.entity.response.tc.SupportCardInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.TcOrderStatus;
import com.tempus.gss.product.hol.api.entity.vo.HotelOrderVo;
import com.tempus.gss.product.hol.api.syn.ITCHotelInterService;
import com.tempus.gss.product.hol.api.syn.ITCHotelOrderService;
import com.tempus.gss.product.hol.api.syn.ITCHotelSupplierService;
import com.tempus.gss.product.hol.api.util.DateUtil;
import com.tempus.gss.product.hol.api.util.OrderStatusUtils;
import com.tempus.gss.product.hol.dao.HolSupplierMapper;
import com.tempus.gss.product.hol.dao.HotelErrorOrderMapper;
import com.tempus.gss.product.hol.dao.HotelOrderMapper;
import com.tempus.gss.security.ShiroUser;
import com.tempus.gss.sms.SMSUtil;
import com.tempus.gss.system.entity.SmsTemplateDetail;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.system.service.ISmsTemplateDetailService;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;
import com.tempus.gss.websocket.SocketDO;
import com.tempus.icenter.auto.entity.Result;

@Service
@EnableAutoConfiguration
public class TCHotelOrderServiceImpl implements ITCHotelOrderService{
	@Autowired
	HttpClientUtil httpClientUtil;
	@Autowired
	MongoTemplate mongoTemplate1;
	
	@Reference
    ISaleOrderService saleOrderService;

    @Reference
    IBuyOrderService buyOrderService;

    @Reference
    IMaxNoService maxNoService;

    @Autowired
    HotelOrderMapper hotelOrderMapper;
    
    @Autowired
    HotelErrorOrderMapper hotelErrorOrderMapper;
    
    @Autowired
    HolSupplierMapper supplierMapper;
    
    @Reference
    ISupplierService supplierService;
	
    @Reference
	ILogService iLogService;
    
	@Reference
	private ITCHotelSupplierService tCHotelSupplierService;
	
	@Reference
	private ITCHotelInterService tCHotelInterService;
	
	@Reference
	private ICustomerService customerService;
	
	@Reference
	private ISmsTemplateDetailService smsTemplateDetailService;
	
	@Autowired
	MqSender mqSender;
	
	@Autowired
	private SMSUtil smsUtil;
	
	@Value("${create.order.url}")
	private String CREATE_ORDER_URL;
	
	@Value("${order.detail.url}")
	private String ORDER_DETAIL_URL;
	
	@Value("${refund.url}")
	private String REFUND_URL;
	
	@Value("${card.info.url}")
	private String CARD_INFO_URL;
	
	@Value("${order.check.url}")
	private String CARD_SUPP_URL;
	
	@Value("${cancel.order.url}")
	private String CANCEL_ORDER_URL;
	
	@Value("${order.change.url}")
	private String ORDER_CHANGE_URL;
	
	@Value("${tc.contactNo}")
	private String CONTACT_NO;
	
	@Value("${tc.guestNo}")
	private String GUEST_NO;
	
	
	protected final transient Logger logger = LogManager.getLogger(TCHotelOrderServiceImpl.class);
	SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public HotelOrder createOrder(Agent agent,OrderCreateReq orderCreateReq)throws GSSException{
		if (StringUtil.isNullOrEmpty(orderCreateReq)) {
			logger.error("orderCreateReq查询条件为空");
            throw new GSSException("创建订单信息", "0101", "orderCreateReq查询条件为空");
        }
		if (StringUtil.isNullOrEmpty(agent)) {
            logger.error("agent对象为空");
            throw new GSSException("创建酒店订单", "0102", "agent对象为空");
        } 
        if (StringUtil.isNullOrEmpty(orderCreateReq.getResId())) {
            logger.error("酒店ID为空！");
            throw new GSSException("创建酒店订单", "0103", "酒店ID为空！");
        }
        if (StringUtil.isNullOrEmpty(orderCreateReq.getProId())) {
            logger.error("房型ID为空！");
            throw new GSSException("创建酒店订单", "0104", "房型ID为空！");
        }
        if (StringUtil.isNullOrEmpty(orderCreateReq.getOrderAdults()) && StringUtil.isNullOrEmpty(orderCreateReq.getOrderChilds())) {
            logger.error("成人数或儿童数同时为空！");
            throw new GSSException("创建酒店订单", "0105", "成人数或儿童数同时为空！");
        }
        if (StringUtil.isNullOrEmpty(orderCreateReq.getBookCount())) {
            logger.error("预定份数为空！");
            throw new GSSException("创建酒店订单", "0106", "预定份数为空！");
        }
        if (StringUtil.isNullOrEmpty(orderCreateReq.getOrderUseDateDetails())) {
            logger.error("酒店使用明细为空！");
            throw new GSSException("创建酒店订单", "0107", "酒店使用明细为空！");
        }else{
        	if(StringUtil.isNullOrEmpty(orderCreateReq.getOrderUseDateDetails().get(0).getUseDate()) || StringUtil.isNullOrEmpty(orderCreateReq.getOrderUseDateDetails().get(0).getCheckPrice())){
        		logger.error("酒店使用明细日期或者价钱为空！");
                throw new GSSException("创建酒店订单", "0108", "酒店使用明细日期或者价钱为空(至少一晚)！");
        	}
        }
        if(StringUtil.isNotNullOrEmpty(orderCreateReq.getOrderPassengerDetails())){
        	if (StringUtil.isNullOrEmpty(orderCreateReq.getOrderPassengerDetails().get(0).getName()) || StringUtil.isNullOrEmpty(orderCreateReq.getOrderPassengerDetails().get(0).getMobile())) {
                logger.error("出游人信息姓名或手机号为空！");
                throw new GSSException("创建酒店订单", "0108", "出游人信息姓名或手机号为空(第一姓名或第一手机号为空)！");
            }
        }else{
        	logger.error("出游人信息集合为空！");
            throw new GSSException("创建酒店订单", "0108", "出游人信息集合为空！");
        }
        if (StringUtil.isNullOrEmpty(orderCreateReq.getProductUniqueId())) {
            logger.error("销售策略 Id为空！");
            throw new GSSException("创建酒店订单", "0109", "销售策略 Id为空！");
        }
        if (StringUtil.isNullOrEmpty(orderCreateReq.getLinkManName())) {
            logger.error("联系人姓名为空！");
            throw new GSSException("创建酒店订单", "0110", "联系人姓名为空！");
        }
        if (StringUtil.isNullOrEmpty(orderCreateReq.getLinkManSex())) {
            logger.error("联系人性别为空！");
            throw new GSSException("创建酒店订单", "0111", "联系人性别为空！");
        }
        if (StringUtil.isNullOrEmpty(orderCreateReq.getLinkManMobile())) {
            logger.error("联系人手机号为空！");
            throw new GSSException("创建酒店订单", "0112", "联系人手机号为空！");
        }
        if (StringUtil.isNullOrEmpty(orderCreateReq.getArrivalTime())) {
            logger.error("最迟到店时间(整点数)为空！");
            throw new GSSException("创建酒店订单", "0113", "最迟到店时间(只能是整点,默认18点)为空！");
        }
        if (StringUtil.isNullOrEmpty(orderCreateReq.getPaymentSign())) {
            logger.error("支付模式为空！");
            throw new GSSException("创建酒店订单", "0114", "支付模式为空！");
        }
        if (StringUtil.isNullOrEmpty(orderCreateReq.getBreakfastCount())) {
            logger.error("早餐为空！");
            throw new GSSException("创建酒店订单", "0115", "早餐为空！");
        }
       
        Long saleOrderNo = maxNoService.generateBizNo("HOL_SALE_ORDER_NO", 13);
        HotelOrder hotelOrder = new HotelOrder();
        try{
		List<ResourceUseDateDetail> resourceUseDateDetail = orderCreateReq.getOrderUseDateDetails();
		String startDate = resourceUseDateDetail.get(0).getUseDate();
		String endDate = resourceUseDateDetail.get((resourceUseDateDetail.size()-1)).getUseDate();
		String hotelName="";
		String cityName = "";
		BigDecimal totalPrice = new BigDecimal(Double.toString(0));
		String eachNightPrice= null;
		Date dateStartDate;
		Date departureDate;
			for(ResourceUseDateDetail li : resourceUseDateDetail){
				totalPrice = totalPrice.add(li.getCheckPrice());
				if(eachNightPrice == null || "".equals(eachNightPrice)){
					eachNightPrice = li.getCheckPrice().toString();
	            }else {
	                if (li.getCheckPrice().toString() != null && !"".equals(li.getCheckPrice().toString())) {
	                	eachNightPrice = eachNightPrice + "," + li.getCheckPrice().toString();
	                }
	            }
			}
			totalPrice = totalPrice.multiply(new BigDecimal(orderCreateReq.getBookCount()));
			hotelOrder.setEachNightPrice(eachNightPrice);
			ResProBaseInfos resInfomation =mongoTemplate1.findOne(new Query(Criteria.where("_id").is(orderCreateReq.getResId())),ResProBaseInfos.class);
			if(resInfomation != null){
				hotelName = StringEscapeUtils.unescapeHtml(orderCreateReq.getResName().trim());
				cityName =  orderCreateReq.getCityName();
				for(ResProBaseInfo detail : resInfomation.getResProBaseInfos()){
						if(detail.getProductUniqueId().equals(orderCreateReq.getProductUniqueId())){
							if(StringUtils.isNotEmpty(detail.getSupPriceName())){
								hotelOrder.setSupPriceName(detail.getSupPriceName());
							}
							if(StringUtils.isNotEmpty(detail.getBedTypeName())){
								hotelOrder.setBedTypeName(detail.getBedTypeName());
							}
						}
					
				}
				
			}

			dateStartDate = simple.parse(startDate);
			Date departDate = simple.parse(endDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(departDate);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			departureDate= cal.getTime();
			ResultTc<OrderCreate> orderCreateBase = null;
    	
    	
    		Long businessSignNo = IdWorker.getId();
    		BuyOrder buyOrder = orderCreateReq.getBuyOrder();
            if (buyOrder == null) {
                buyOrder = new BuyOrder();
            }
            if (StringUtil.isNotNullOrEmpty(orderCreateReq.getSupplierNo())) {
            	Long supplierNo = Long.valueOf(orderCreateReq.getSupplierNo());
            	hotelOrder.setSupplierNo(supplierNo);
            	buyOrder.setSupplierNo(supplierNo);
                //从客商系统查询供应商信息
                Supplier supplier = supplierService.getSupplierByNo(agent, supplierNo);
                if(StringUtil.isNotNullOrEmpty(supplier)){
                	 buyOrder.setSupplierTypeNo(supplier.getCustomerTypeNo());
                }else{
                	throw new GSSException("创建酒店订单", "0130", "根据编号查询供应商信息为空！");
                }
            } else {
                logger.error("供应商信息不存在！");
                throw new GSSException("创建酒店订单", "0130", "供应商信息不存在！");
            }
            
			AssignDateHotelReq assignDateHotelReq=new AssignDateHotelReq();
			assignDateHotelReq.setResId(orderCreateReq.getResId());
			assignDateHotelReq.setNeedSpecialPolicy(1);
			assignDateHotelReq.setSourceFrom("-1");
			assignDateHotelReq.setProId(orderCreateReq.getProId());
			assignDateHotelReq.setProductUniqueId(orderCreateReq.getProductUniqueId());
			assignDateHotelReq.setStartTime(startDate);
			assignDateHotelReq.setEndTime(endDate);
			
			AssignDateHotel assignDateHotel = tCHotelInterService.queryAssignDateHotel(assignDateHotelReq);
			Integer newTotalPrice=0;
			List<BigDecimal> newCheckPrice = new ArrayList<BigDecimal>();
			if(assignDateHotel != null){
				List<ProInfoDetail> proInfoDetailList= assignDateHotel.getProInfoDetailList();
				if(proInfoDetailList != null && proInfoDetailList.get(0).getProSaleInfoDetails() != null){
					for(Map.Entry<String, ProSaleInfoDetail> entry : proInfoDetailList.get(0).getProSaleInfoDetails().entrySet()){
						newCheckPrice.add(new BigDecimal(Double.toString(entry.getValue().getDistributionSalePrice())));
						newTotalPrice += entry.getValue().getDistributionSalePrice();
					}
				}
				newTotalPrice = newTotalPrice * orderCreateReq.getBookCount();
			}
			
				/*创建销售订单*/
	            SaleOrder saleOrder = orderCreateReq.getSaleOrder();
	            saleOrder.setSaleOrderNo(saleOrderNo);
	            saleOrder.setOwner(agent.getOwner());
	            saleOrder.setSourceChannelNo(saleOrder.getSourceChannelNo());
	            saleOrder.setCustomerTypeNo(saleOrder.getCustomerTypeNo() == null ? 0 : saleOrder.getCustomerTypeNo());
	            saleOrder.setCustomerNo(saleOrder.getCustomerNo() == null ? 0 : saleOrder.getCustomerNo());
	            saleOrder.setOrderingLoginName(agent.getAccount());
	            saleOrder.setGoodsType(GoodsBigType.GROGSHOP.getKey());//
	            saleOrder.setGoodsSubType(GoodsBigType.GROGSHOP.getKey());//
	            saleOrder.setOrderingTime(new Date());//下单时间
	            saleOrder.setPayStatus(1);//待支付
	            saleOrder.setValid(1);//有效
	            saleOrder.setBusinessSignNo(businessSignNo);
	            saleOrder.setBsignType(1);//1销采 2换票 3废和退 4改签
	            saleOrder.setTransationOrderNo(orderCreateReq.getSaleOrder().getTransationOrderNo());
	            saleOrder.setOrderType(1);
	            saleOrder.setOrderChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.ORDER_ONGOING));
	            saleOrder.setGoodsName(hotelName);
	            saleOrderService.create(agent, saleOrder);

	            /*创建采购单*/
				//Long buyOrderNo = IdWorker.getId();
			    Long buyOrderNo = maxNoService.generateBizNo("HOL_BUY_ORDER_NO", 14);
	            buyOrder.setOwner(agent.getOwner());
	            buyOrder.setBuyOrderNo(buyOrderNo);
	            buyOrder.setSaleOrderNo(saleOrderNo);
	            buyOrder.setGoodsType(GoodsBigType.GROGSHOP.getKey());
	            buyOrder.setGoodsSubType(GoodsBigType.GROGSHOP.getKey());
	            buyOrder.setGoodsName(hotelName);
	            buyOrder.setBuyChannelNo("HOTEL");
	            buyOrder.setBusinessSignNo(businessSignNo);
	            buyOrder.setBuyChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.ORDER_ONGOING));
	            buyOrder.setBsignType(1);//1销采 2换票 3废和退 4改签
	            buyOrderService.create(agent, buyOrder);
				
				//创建酒店订单
				hotelOrder.setOwner(agent.getOwner());
				hotelOrder.setCustomerNo(agent.getNum());
				hotelOrder.setDbOrderType(orderCreateReq.getDbOrderType());
				hotelOrder.setDbOrderMoney(orderCreateReq.getDbOrderMoney());
				hotelOrder.setDbCancelRule(orderCreateReq.getDbCancelRule());
				hotelOrder.setCancelPenalty(orderCreateReq.getCancelPenalty());
				hotelOrder.setEarlyArriveTime(orderCreateReq.getEarlyArriveTime());
				hotelOrder.setLatestArriveTime(orderCreateReq.getLatestArriveTime());
	            hotelOrder.setSaleOrderNo(saleOrderNo);
	            hotelOrder.setBuyOrderNo(buyOrderNo);
	            hotelOrder.setHotelCode(String.valueOf(orderCreateReq.getResId()));
	            hotelOrder.setHotelName(hotelName);
	            hotelOrder.setOrderType(1);
	            hotelOrder.setOrderType(orderCreateReq.getOrderType());
	            hotelOrder.setContactName(orderCreateReq.getLinkManName());
	            hotelOrder.setContactNumber(orderCreateReq.getLinkManMobile());
				hotelOrder.setArrivalDate(dateStartDate);
	            hotelOrder.setDepartureDate(departureDate);
	            hotelOrder.setTotalPrice(totalPrice);
	            hotelOrder.setNightCount(resourceUseDateDetail.size());
	            hotelOrder.setTransationOrderNo(orderCreateReq.getSaleOrder().getTransationOrderNo());
	            hotelOrder.setTotalRefund(orderCreateReq.getTotalRebateRateProfit());
	            hotelOrder.setFactTotalRefund(new BigDecimal(0));
	            String guestName = null;
	            String requestCode = null;
	            String requestText = null;
	            String requestName = null;
	            for (TravlePassengerInfo guest : orderCreateReq.getOrderPassengerDetails()) {
	            	guest.setNationality("中国");
	                if (guestName == null || "".equals(guestName)) {
	                    guestName = guest.getName();
	                } else {
	                    if (guest.getName() != null && !"".equals(guest.getName())) {
	                        guestName = guestName + "," + guest.getName();
	                    }
	                }
	            }
	           /* if(orderCreateReq.getSpecialRequestOption() != null){
	            	for(SpecialRequest request : orderCreateReq.getSpecialRequestOption()){
		            	if (requestCode == null || "".equals(requestCode)) {
		            		requestCode = request.getRequestCode();
		                } else {
		                    if (request.getRequestCode() != null && !"".equals(request.getRequestCode())) {
		                    	requestCode = requestCode + "," + request.getRequestCode();
		                    }
		                }
		            	if (requestText == null || "".equals(requestText)) {
		            		requestText = request.getRequestText();
		                } else {
		                    if (request.getRequestText() != null && !"".equals(request.getRequestText())) {
		                    	requestText = requestText + "," + request.getRequestText();
		                    }
		                }
		            	if (requestName == null || "".equals(requestName)) {
		            		requestName = request.getRequestName();
		                } else {
		                    if (request.getRequestName() != null && !"".equals(request.getRequestName())) {
		                    	requestName = requestName + "," + request.getRequestName();
		                    }
		                }
		            }
	            	hotelOrder.setRequestCode(requestCode);
		            hotelOrder.setRequestText(requestText);
		            hotelOrder.setRequestName(requestName);
	            }*/
	            hotelOrder.setGuestName(guestName);
	            hotelOrder.setGuestMobile(orderCreateReq.getOrderPassengerDetails().get(0).getMobile());
	            if(StringUtil.isNotNullOrEmpty(orderCreateReq.getOrderRemark())){
	            	 hotelOrder.setRemark(orderCreateReq.getOrderRemark());
	            }
	            hotelOrder.setLocker(0L);
	            hotelOrder.setOrderStatus(OwnerOrderStatus.ORDER_ONGOING.getKey());
	            hotelOrder.setCreator(agent.getAccount());
	            hotelOrder.setCreateOrderTime(new Date());
	            hotelOrder.setProductUniqueId(orderCreateReq.getProductUniqueId());
	            hotelOrder.setBookCount(orderCreateReq.getBookCount());
	            hotelOrder.setPaymentSign(orderCreateReq.getPaymentSign());
	            if(StringUtil.isNotNullOrEmpty(orderCreateReq.getBankId())){
	            	hotelOrder.setBankId(orderCreateReq.getBankId().longValue());
	            }
	            if(StringUtil.isNotNullOrEmpty(orderCreateReq.getBankName())){
	            	hotelOrder.setBankName(orderCreateReq.getBankName());
	            }
	            if(StringUtil.isNotNullOrEmpty(orderCreateReq.getCardNumber())){
	            	hotelOrder.setCardNumber(orderCreateReq.getCardNumber());
	            }
	            if(StringUtil.isNotNullOrEmpty(orderCreateReq.getCardUserName())){
	            	hotelOrder.setCardUserName(orderCreateReq.getCardUserName());
	            }
	            if(StringUtil.isNotNullOrEmpty(orderCreateReq.getCardValueCode())){
	            	hotelOrder.setCardValueCode(orderCreateReq.getCardValueCode());
	            }
	            if(StringUtil.isNotNullOrEmpty(orderCreateReq.getCardValueDate())){
	            	hotelOrder.setCardValueDate(orderCreateReq.getCardValueDate());
	            }
	            if(StringUtil.isNotNullOrEmpty(orderCreateReq.getCredentialName())){
	            	hotelOrder.setCredentialName(orderCreateReq.getCredentialName());
	            }
	            if(StringUtil.isNotNullOrEmpty(orderCreateReq.getCredentialNumber())){
	            	hotelOrder.setCredentialNumber(orderCreateReq.getCredentialNumber());
	            	orderCreateReq.setIsPay(true);
	            }
	            if(StringUtil.isNotNullOrEmpty(orderCreateReq.getMobile())){
	            	hotelOrder.setCardMobile(orderCreateReq.getMobile());
	            }
	            hotelOrder.setArriveHotelTime(orderCreateReq.getArriveHotelTime());
	            hotelOrder.setHotelAddress(cityName+orderCreateReq.getHotelAddress());
	            hotelOrder.setHotelPhone(orderCreateReq.getHotelPhone());
	            hotelOrder.setProName(orderCreateReq.getProName());
	            hotelOrder.setProId(orderCreateReq.getProId());
	            hotelOrder.setBreakfastCount(orderCreateReq.getBreakfastCount());
	            hotelOrderMapper.insertSelective(hotelOrder);
	            BigDecimal beforeTotalPrice = orderCreateReq.getBeforeTotalPrice();
        		if(beforeTotalPrice.compareTo(new BigDecimal(newTotalPrice)) != 0){
        			logger.error("传入的总价与最新的总价不一致");
                    throw new GSSException("创建酒店订单失败", "0132", "价格不一致");
        		}
				if(totalPrice.compareTo(new BigDecimal(newTotalPrice)) == 0){
					orderCreateReq.setOutSideOrderId("newordertc_"+buyOrderNo);
					orderCreateReq.setLinkManMobile(CONTACT_NO);
					for (TravlePassengerInfo guest : orderCreateReq.getOrderPassengerDetails()) {
						guest.setMobile(GUEST_NO);
					}
					logger.info("创建酒店订单开始,入参:" + JSONObject.toJSONString(orderCreateReq));
					String reqJson= JSONObject.toJSONString(orderCreateReq);
					String resultJson= httpClientUtil.doTCJsonPost(CREATE_ORDER_URL, reqJson);
					logger.info("下单请求酒店返回:" + resultJson);
					if(StringUtils.isNotEmpty(resultJson)){
						orderCreateBase= JsonUtil.toBean(resultJson, new TypeReference<ResultTc<OrderCreate>>(){});
						
						if(StringUtil.isNotNullOrEmpty(orderCreateBase)){
							if(orderCreateBase.getRet_code().equals("200")){
								hotelOrder.setHotelOrderNo(orderCreateBase.getResult().getOrderId());
								//hotelOrder.setMsg(orderCreateBase.getResult().getMsg().trim());
								//hotelOrder.setIsAffirm(orderCreateBase.getResult().getIsAffirm());
								//hotelOrder.setResultCode(orderCreateBase.getResult().getResultCode());
								hotelOrder.setTcOrderStatus(TcOrderStatus.WAIT_TC_CONFIRM.getKey());
								hotelOrderMapper.updateById(hotelOrder);
							}else{
								throw new GSSException("创建酒店失败", "0133", "创建酒店请求失败:" + orderCreateBase.getErr_msg());
							}
						}else{
							logger.error("酒店数据解析失败");
	                        throw new GSSException("实体类酒店失败", "0134", "实体类酒店失败");
						}
					}else{
						logger.error("创建酒店请求返回空值");
	                    throw new GSSException("创建酒店失败", "0135", "创建酒店请求返回空值");
					}
				}else{
					logger.error("传入的价格与最新的价格不一致");
                    throw new GSSException("创建酒店订单失败", "0132", "价格不一致");
				}
				
		}
    	catch(Exception e){
    		e.printStackTrace();
    		logger.error("创建酒店订单请求出错",e);
            throw new GSSException(hotelOrder.getHotelName(), String.valueOf(saleOrderNo), e.getMessage());
    	}
    	/*finally {
			if(resultCode.equals("0")) {
				HolErrorOrder holErrorOrder=new HolErrorOrder();
				holErrorOrder.setResultCode("resultCode");
	    		BeanUtils.copyProperties(hotelOrder, holErrorOrder);
	    		holErrorOrder.setMsg(errMsg);
	    		hotelErrorOrderMapper.insertSelective(holErrorOrder);
			}
		}*/
    	logger.info("创建酒店订单结束=====");
		return hotelOrder;
	}
	
	public List<SpecialRequest> formatSpecialReq(List<SpecialRequest> req){
		List<SpecialRequest> specList = new ArrayList<SpecialRequest>();
		for(SpecialRequest spec : req){
			if(StringUtils.isNotEmpty(spec.getRequestCode()) && StringUtils.isNotEmpty(spec.getRequestName()) && StringUtils.isNotEmpty(spec.getRequestText())){
				specList.add(spec);
			}
		}
		return specList;
	}

	@Override
	public OrderInfomationDetail orderDetailInfo(Agent agent, OrderDetailInfoReq orderDetailInfoReq) throws GSSException{
		logger.info("请求订单详情入参:" + JsonUtil.toJson(orderDetailInfoReq));
		if (StringUtil.isNullOrEmpty(agent)) {
			logger.error("agent对象为空");
            throw new GSSException("获取订单详情", "0102", "agent对象为空");
        }
		if (StringUtil.isNullOrEmpty(orderDetailInfoReq)) {
			logger.error("orderDetailInfoReq查询条件为空");
            throw new GSSException("获取订单详情", "0101", "orderDetailInfoReq查询条件为空");
        }
		ResultTc<OrderInfomationDetail> base = null;
		try {
			String jsonReq= JSONObject.toJSONString(orderDetailInfoReq);
			String resultJson = httpClientUtil.doTCJsonPost(ORDER_DETAIL_URL, jsonReq);
			logger.info("订单详情请求酒店返回:" + resultJson);
			if(StringUtil.isNotNullOrEmpty(resultJson)){
				//OrderInfomationDetailBase base = JsonUtil.toBean(resultJson, OrderInfomationDetailBase.class);
				base= JsonUtil.toBean(resultJson, new TypeReference<ResultTc<OrderInfomationDetail>>(){});
				if(StringUtil.isNotNullOrEmpty(base) && base.getRet_code().equals("200")){
					return base.getResult();
				}else{
					throw new GSSException("获取订单详情", "0101", base.getErr_msg());
				}
			}else{
				throw new GSSException("获取订单详情", "0101", "返回订单详情为空");
			}
		} catch (Exception e) {
			logger.error("获取订单详情",e);
			throw new GSSException("获取订单详情", "0101",  base.getErr_msg());
		}
	}
	
	@Override
	public String cancelTcOrder(Agent agent, OrderCancelReq orderCancelReq) throws GSSException{
		if (StringUtil.isNullOrEmpty(agent)) {
			logger.error("agent对象为空");
            throw new GSSException("取消订单信息", "0102", "agent对象为空");
        }
		if (StringUtil.isNullOrEmpty(orderCancelReq)) {
			logger.error("orderCancelReq查询条件为空");
            throw new GSSException("取消订单信息", "0101", "orderCancelReq查询条件为空");
        }
		
		String jsonReq= JSONObject.toJSONString(orderCancelReq);
		String resultJson = httpClientUtil.doTCJsonPost(REFUND_URL, jsonReq);
		
		return resultJson;
	}

	@Override
	public SupportCardInfo supportCartInfo(Agent agent,CardSupportReq orderType) throws GSSException{
		if (StringUtil.isNullOrEmpty(agent)) {
			logger.error("agent对象为空");
            throw new GSSException("获取支持的信用卡信息", "0102", "agent对象为空");
        }
		if (StringUtil.isNullOrEmpty(orderType)) {
			logger.error("orderType查询条件为空");
            throw new GSSException("获取支持的信用卡信息", "0101", "orderType查询条件为空");
        }
		String jsonReq= JSONObject.toJSONString(orderType);
		String resultJson= httpClientUtil.doTCJsonPost(CARD_INFO_URL, jsonReq);
		logger.info("查询可以信用卡请求酒店返回:" + resultJson);
		ResultTc<SupportCardInfo> supportCardInfoBase= JsonUtil.toBean(resultJson, new TypeReference<ResultTc<SupportCardInfo>>(){});
		
		if(StringUtil.isNotNullOrEmpty(supportCardInfoBase)){
			return supportCardInfoBase.getResult();
		}
		return null;
	}

	@Override
	public IsBookOrder isBookOrder(Agent agent,IsBookOrderReq isBookOrderReq) throws GSSException{
		if (StringUtil.isNullOrEmpty(agent)) {
			logger.error("agent对象为空");
            throw new GSSException("订单是否可定信息", "0102", "agent对象为空");
        }
		if (StringUtil.isNullOrEmpty(isBookOrderReq)) {
			logger.error("isBookOrderReq查询条件为空");
            throw new GSSException("订单是否可定信息", "0101", "isBookOrderReq查询条件为空");
        }
		ResultTc<IsBookOrder> isBookOrderBase = null;
		IsBookOrder resBase =null;
		try {
			//IsBookOrder isBookOrderCheck=new IsBookOrder();
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar da = Calendar.getInstance();
			if(StringUtils.isEmpty(isBookOrderReq.getEarliestArrivalTime())) {
				String today = simple.format(da.getTime());
				if(today.compareTo(isBookOrderReq.getComeTime().substring(0, 10)) < 0) {
					da.setTime(simple.parse(isBookOrderReq.getComeTime()));
					da.set(Calendar.HOUR, 6);
					da.set(Calendar.MINUTE, 0);
					da.set(Calendar.SECOND, 0);
					isBookOrderReq.setEarliestArrivalTime(sim.format(da.getTime()));
				}else {
					da.add(Calendar.HOUR,1);
					da.set(Calendar.MINUTE, 0);
					da.set(Calendar.SECOND, 0);
					isBookOrderReq.setEarliestArrivalTime(sim.format(da.getTime()));
				}
			}
			if(sim.format(new Date()).compareTo(isBookOrderReq.getComeTime()) > 0) {
				da.add(Calendar.HOUR,2);
				da.set(Calendar.MINUTE, 0);
				da.set(Calendar.SECOND, 0);
				isBookOrderReq.setComeTime(sim.format(da.getTime()));
			}
			if(StringUtils.isEmpty(isBookOrderReq.getLatestArrivalTime())) {
				if(isBookOrderReq.getComeTime().equals(isBookOrderReq.getEarliestArrivalTime())) {
					da.setTime(simple.parse(isBookOrderReq.getComeTime()));
					da.set(Calendar.HOUR,23);
					da.set(Calendar.MINUTE, 0);
					da.set(Calendar.SECOND, 0);
					isBookOrderReq.setLatestArrivalTime(sim.format(da.getTime()));
				}else {
					isBookOrderReq.setLatestArrivalTime(isBookOrderReq.getComeTime());
				}
			}
			logger.info("酒店订单进行可定检查,入参:" + JSONObject.toJSONString(isBookOrderReq));
			//System.out.println("EarliestArrivalTime: "+isBookOrderReq.getEarliestArrivalTime());
			//System.out.println("LatestArrivalTime: "+isBookOrderReq.getLatestArrivalTime());
			//System.out.println("ComeTime: "+isBookOrderReq.getComeTime());
			
			String reqJson = JSONObject.toJSONString(isBookOrderReq);
			String resultJson= httpClientUtil.doTCJsonPost(CARD_SUPP_URL, reqJson);
			logger.info("可定检查请求返回:" + resultJson);
			if(StringUtil.isNotNullOrEmpty(resultJson)){
				isBookOrderBase= JsonUtil.toBean(resultJson, new TypeReference<ResultTc<IsBookOrder>>(){});
				if(StringUtil.isNotNullOrEmpty(isBookOrderBase)){
					resBase = isBookOrderBase.getResult();
					if(StringUtil.isNotNullOrEmpty(resBase)) {
						if(StringUtil.isNotNullOrEmpty(resBase.getCanBooking())) {
							if(resBase.getCanBooking().equals(1)){
								BookableMessage jobj=JSON.parseObject(resBase.getBookableMessage(), BookableMessage.class);  
								resBase.setBookableMessageTarget(jobj);
								if(resBase.getBookableMessageTarget() != null ){
									/*String cancelPenaltyStart = resBase.getBookableMessageTarget().getCtripGuaranteeInfo().getCancelPenaltyStart();
									System.out.println("aaa: "+cancelPenaltyStart);*/
									/*if(resBase.getBookableMessageTarget().getLimitLatestArrivalTime() != null){
										//Date inputComeDate = sim.parse(isBookOrderReq.getComeTime());
										String inputComeDate = isBookOrderReq.getComeTime();
										String returnDate = resBase.getBookableMessageTarget().getLimitLatestArrivalTime();
										if(inputComeDate.compareTo(returnDate) > 0 || inputComeDate.equals(returnDate)){
											resBase.setLastestArriveTimeConfirm(true);
										}else{
											resBase.setLastestArriveTimeConfirm(false);
											isBookOrderCheck =  resBase;
											return isBookOrderCheck;
										}
									}*/
									/*if(resBase.getBookableMessageTarget().getExcessRooms() < isBookOrderReq.getBookingCount()){
										isBookOrderCheck.setCanBooking(0);
										isBookOrderCheck.setSelfMessage("可选房间数量不足, 请重新选择");
										return isBookOrderCheck;
									}*/
									String bookCode = "0";
									//BigDecimal totalPrice = new BigDecimal(Double.toString(0));
									if(resBase.getBookableMessageTarget().getCtripGuaranteeInfo() != null){
										if(StringUtil.isNotNullOrEmpty(resBase.getBookableMessageTarget().getCtripGuaranteeInfo().getCgCode())){
											String[] strs = resBase.getBookableMessageTarget().getCtripGuaranteeInfo().getCgCode();
											if(strs != null) {
												if(strs.length ==1 ) {
													bookCode = strs[0];
												}else if(strs.length >=2){
													if(Arrays.asList(strs).contains(GuaranteeTypeIsBook.FOUR.getKey())){
														bookCode = GuaranteeTypeIsBook.FOUR.getKey();
													}else if(Arrays.asList(strs).contains(GuaranteeTypeIsBook.TWO.getKey())){
														bookCode = GuaranteeTypeIsBook.TWO.getKey();
													}else if(Arrays.asList(strs).contains(GuaranteeTypeIsBook.ONE.getKey())){
														bookCode = GuaranteeTypeIsBook.ONE.getKey();
													}else if(Arrays.asList(strs).contains(GuaranteeTypeIsBook.THREE.getKey())){
														bookCode = GuaranteeTypeIsBook.THREE.getKey();
													}else if(Arrays.asList(strs).contains(GuaranteeTypeIsBook.FIVE.getKey())){
														bookCode = GuaranteeTypeIsBook.FIVE.getKey();
													}
												}
											}
											
											/*if(resBase.getBookableMessageTarget().getCtripGuaranteeInfo().getCgCode()[0].equals(GuaranteeTypeIsBook.THREE.getKey())){
												ProInfoDetail proInfoDetail = mongoTemplate1.findOne(new Query(Criteria.where("_id").is(isBookOrderReq.getProductUniqueId())),ProInfoDetail.class);
												int comres = 0;
												for (Map.Entry<String, ProSaleInfoDetail> entry : proInfoDetail.getProSaleInfoDetails().entrySet()) {
				 	    	        				int begincompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(isBookOrderReq.getComeDate());
					 	    	        				if(begincompare == 0){
					 	    	        					comres = 1;
					 	    	        					String substring = isBookOrderReq.getComeTime().substring(11,16);
					 	    	        						
					 	    	        					int compareTo = (entry.getValue().getGuaranteeHoldTime().replace(":0:",":00:").substring(0,5)).compareTo(substring);
					 	    	        					if(compareTo > 0){
					 	    	        						newstr[0] = "5";
					 	    	        						resBase.getBookableMessageTarget().getCtripGuaranteeInfo().setCgCode(newstr);
					 	    	        					}
					 	    	        				}
					 	    	        				if(comres == 1){
					 	    	        					break;
					 	    	        				}
				 	            					}
											}*/
											/*int daysBetween = 0;
											try {
												daysBetween = DateUtil.daysBetween(isBookOrderReq.getComeDate(), isBookOrderReq.getLeaveDate());
											} catch (ParseException e) {
												e.printStackTrace();
											}
											if(daysBetween > 1){
												if(resBase.getBookableMessageTarget().getCtripGuaranteeInfo().getCgCode()[0].equals(GuaranteeTypeIsBook.TWO.getKey())){
													BigDecimal[] big = resBase.getBookableMessageTarget().getCtripGuaranteeInfo().getAmountPercent();
													if(big != null && big.length >= 2){
														for(BigDecimal b : big){
															totalPrice = totalPrice.add(b);
														}
														big[0] = totalPrice;
														resBase.getBookableMessageTarget().getCtripGuaranteeInfo().setAmountPercent(big);
													}
												}
											}*/
										}
										resBase.setBookCode(bookCode);
									}
								}
							}
						}
					}
					 
				}else{
		            throw new GSSException("订单是否可定信息", "0101", "解析数据异常");
				}
				
				/*else{
					Integer aa = 0;
					isBookOrderCheck.setCanBooking(aa);
					isBookOrderCheck.setSelfMessage("可选房间数量不足, 请重新选择");
					return isBookOrderCheck;
				}*/
				//sortPayList(resBase);
				
			}else{
	            throw new GSSException("订单是否可定信息", "0101", "查询请求返回空值");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("订单可定检查请求异常"+e);
			 throw new GSSException("订单是否可定信息", "0101", e.getMessage());
		}
		return resBase;
		
	}

	/*private void sortPayList(IsBookOrder isBookOrderCheck) {
		
		if(isBookOrderCheck!=null)
		{
			List<PaymentWay>  pls = isBookOrderCheck.getPaymentWayList();
			logger.info("进行排序银行列表。。..."+(pls==null));
			logger.info("进行排序银行列表。。"+pls.size());
			for (PaymentWay paymentWay : pls) {
				paymentWay.setOrderBy(paymentWay.getCreditCardBankId());
				if(paymentWay.getPaymentWayName().contains("农业"))
				{
					paymentWay.setOrderBy("0");
				}
			}
			Collections.sort(isBookOrderCheck.getPaymentWayList(), new PaymentWayComparator());
		}
		
	}*/

	@Override
	public Page<HotelOrder> queryOrderListWithPage(Page<HotelOrder> page, RequestWithActor<HotelOrderVo> pageRequest) throws GSSException{
		logger.info("根据条件查询酒店订单列表开始,入参:" + JSONObject.toJSONString(pageRequest));
		try {
			Agent agent = pageRequest.getAgent();
	        if (StringUtil.isNullOrEmpty(agent)) {
	            logger.error("当前操作用户不能为空");
	            throw new GSSException("查询酒店订单列表", "1010", "当前操作用户不能为空");
	        }
	        //如果是运营平台账号，可查看全部订单，否则只能查看当前账号自己创建的订单
	        if (StringUtil.isNotNullOrEmpty(agent.getType()) && agent.getType() != 0L) { //不是运营平台账号
	            if (StringUtil.isNullOrEmpty(pageRequest.getEntity())) {
	                HotelOrderVo hotelOrderVo = new HotelOrderVo();
	                hotelOrderVo.setOwner(pageRequest.getAgent().getOwner());
	                hotelOrderVo.setCreator(pageRequest.getAgent().getAccount());
	                pageRequest.setEntity(hotelOrderVo);
	            } else {
	                pageRequest.getEntity().setOwner(pageRequest.getAgent().getOwner());
	                pageRequest.getEntity().setCreator(pageRequest.getAgent().getAccount());
	                pageRequest.getEntity().setCustomerNo(pageRequest.getAgent().getNum());
	            }
	        }
	        List<HotelOrder> hotelOrderList = hotelOrderMapper.queryOrderList(page, pageRequest.getEntity());
	        /**
	         * 根据saleOrderNo通过API接口去其他子系统去获取数据
	         * 需要根据list的长度去执行获取数据的次数,此操作可能会存在性能问题
	         */
	        /*for (HotelOrder hotelOrder : hotelOrderList) {
	            SaleOrder saleOrder = saleOrderService.getSOrderByNo(pageRequest.getAgent(), hotelOrder.getSaleOrderNo());
	            hotelOrder.setSaleOrder(saleOrder);
	        }*/
	        page.setRecords(hotelOrderList);
		}catch(Exception ex)
		{
			logger.error("查询订单列表异常：", ex);
		}
        
        return page;
	}
	
	@Override
	public Page<HolErrorOrder> queryErrorOrderListWithPage(Page<HolErrorOrder> page, RequestWithActor<HotelOrderVo> pageRequest) throws GSSException{
		logger.info("根据条件查询酒店报错订单列表开始,入参:" + JSONObject.toJSONString(pageRequest));
		try {
			Agent agent = pageRequest.getAgent();
	        if (StringUtil.isNullOrEmpty(agent)) {
	            logger.error("当前操作用户不能为空");
	            throw new GSSException("查询酒店订单列表", "1010", "当前操作用户不能为空");
	        }
	        //如果是运营平台账号，可查看全部订单，否则只能查看当前账号自己创建的订单
	        if (StringUtil.isNotNullOrEmpty(agent.getType()) && agent.getType() != 0L) { //不是运营平台账号
	            if (StringUtil.isNullOrEmpty(pageRequest.getEntity())) {
	                HotelOrderVo hotelOrderVo = new HotelOrderVo();
	                hotelOrderVo.setOwner(pageRequest.getAgent().getOwner());
	                pageRequest.setEntity(hotelOrderVo);
	            } else {
	                pageRequest.getEntity().setOwner(pageRequest.getAgent().getOwner());
	            }
	        }
	        List<HolErrorOrder> hotelOrderList = hotelErrorOrderMapper.queryErrorOrderList(page, pageRequest.getEntity());
	        /**
	         * 根据saleOrderNo通过API接口去其他子系统去获取数据
	         * 需要根据list的长度去执行获取数据的次数,此操作可能会存在性能问题
	         */
	        /*for (HotelOrder hotelOrder : hotelOrderList) {
	            SaleOrder saleOrder = saleOrderService.getSOrderByNo(pageRequest.getAgent(), hotelOrder.getSaleOrderNo());
	            hotelOrder.setSaleOrder(saleOrder);
	        }*/
	        page.setRecords(hotelOrderList);
		}catch(Exception ex)
		{
			logger.error("查询订单列表异常：", ex);
		}
        
        return page;
	}

	@Override
	public HotelOrder getHotelOrderDetail(Agent agent, Long saleOrderNo) throws GSSException{
		 logger.info("根据销售单号查询酒店订单详情开始,入参:saleOrderNo=" + saleOrderNo);
	        if (StringUtil.isNullOrEmpty(saleOrderNo)) {
	            logger.error("销售单号为空");
	            throw new GSSException("根据销售单号查询酒店订单详情", "0101", "销售单号为空");
	        }
	        if (StringUtil.isNullOrEmpty(agent)) {
	            logger.error("agent对象为空");
	            throw new GSSException("根据销售单号查询酒店订单详情", "0102", "agent对象为空");
	        }
	        HotelOrder hotelOrder = new HotelOrder();
	        try {
	        	hotelOrder.setSaleOrderNo(saleOrderNo);
		        hotelOrder = hotelOrderMapper.selectOne(hotelOrder);
		        //hotelOrder = hotelOrderMapper.getOrderBySaleOrderNo(saleOrderNo);
		        /*if (StringUtil.isNotNullOrEmpty(hotelOrder.getHotelOrderNo())) {
		        	OrderDetailInfoReq orderDetailInfoReq=new OrderDetailInfoReq();
		            String hotelOrderNo = hotelOrder.getHotelOrderNo();
		            OrderInfoModel info=null;
	            	orderDetailInfoReq.setOrderId(hotelOrderNo);
	            	OrderInfomationDetail orderInfomationDetail = orderDetailInfo(agent, orderDetailInfoReq);
	            	if(orderInfomationDetail != null){
	            		if(orderInfomationDetail.getIsSuccess() == true){
	            			if(orderInfomationDetail.getOrderInfos().size()>0)
	            			{
	                			info = orderInfomationDetail.getOrderInfos().get(0);
	                			
	                			for(ResourceModel resourmodel : info.getResources()){
	                				if(StringUtils.isNotEmpty(resourmodel.getSupplierConfirmNumber())){
	                					hotelOrder.setSupplierNumber(resourmodel.getSupplierConfirmNumber());
	                					break;
	                				}
	                			}
	                			if(info.getOrderStatus().equals(TcOrderStatus.CONFIRM_TO_ROOM.getKey())){
	                				if(hotelOrder.getOrderStatus().equals(OwnerOrderStatus.ORDER_ONGOING.getKey()) || hotelOrder.getOrderStatus().equals(OwnerOrderStatus.ALREADY_CONRIRM.getKey())){
	                					if(new Date().before(hotelOrder.getDepartureDate())){
	                						hotelOrder.setOrderStatus(OwnerOrderStatus.RESIDE_ONGOING.getKey());
	                    					hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
	                    					try {
	    	                					String factName = "";
	    	    								hotelOrder.setFactArriveTime(simple.parse(info.getStartTime()));
	    	    								for(PassengerModel pm : info.getPassengers()){
	    	    									if (factName == null || "".equals(factName)) {
	    	    										factName = pm.getName();
	    	    					                } else {
	    	    					                    if (pm.getName() != null && !"".equals(pm.getName())) {
	    	    					                    	factName = factName + "," + pm.getName();
	    	    					                    }
	    	    					                }
	    	    								}
	    	    								hotelOrder.setFactGuestName(factName);
	    	    							} catch (ParseException e) {
	    	    								e.printStackTrace();
	    	    							}
	                    					long startTime6 = System.currentTimeMillis(); 
	                    					hotelOrderMapper.updateById(hotelOrder);
	                    			        long endTime6 = System.currentTimeMillis();
	                    			        System.out.println("updateById运行时间：" + (endTime6 - startTime6) + "ms");
	                    					
	                					}
	                				}
	                				
	                			}
	            			}
	            		}
	            	}
		        }*/ 
	               /* try {
	                	BeanUtils.copyProperties(tcOrderInfosDetail, info);
	                	BeanUtils.copyProperties(tcOrderInfosDetail, hotelOrder);
	                } catch (Exception e) {
	                    logger.error("对象属性复制错误");
	                }*/
	               // hotelOrderDetail.setSaleOrderNo(hotelOrder.getSaleOrderNo());
	               // hotelOrderDetail.setCreateTime(hotelOrder.getCreateTime());
	            	
	                //设置销售单信息
	                /*SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, hotelOrder.getSaleOrderNo());
	                hotelOrder.setSaleOrder(saleOrder);*/
	                
	                /*List<String> guestNameList= Arrays.asList(hotelOrder.getGuestName().split(","));
	                if(StringUtil.isNotNullOrEmpty(hotelOrder.getFactGuestName())){
	                	List<String> factGuestNameList = Arrays.asList(hotelOrder.getFactGuestName().split(","));
	                	hotelOrder.setFactGuestNameList(factGuestNameList);
	                }
	                hotelOrder.setGuestNameList(guestNameList);*/
		        logger.info("根据销售单号查询酒店订单详情结果:" + JSONObject.toJSONString(hotelOrder));
		        
			} catch (Exception e) {
				logger.error("查询订单详情异常：", e);
			}
	        return hotelOrder;
	}
	
	@Override
	public HotelOrder getRelatedHotelOrderDetail(Agent agent, Long rehotelOrderNo) throws GSSException {
		logger.info("根据供应商订单号查询酒店订单详情开始:" + rehotelOrderNo);
		if(StringUtil.isNullOrEmpty(rehotelOrderNo)){
			throw new GSSException("根据供应商订单号查询酒店订单详情", "0101", "供应商订单号为空");
		}
		HotelOrder hotelOrder = new HotelOrder();
		try {
			hotelOrder = hotelOrderMapper.getOrderByNo(String.valueOf(rehotelOrderNo));
	        /*if (StringUtil.isNotNullOrEmpty(hotelOrder.getHotelOrderNo())) {
	        	OrderDetailInfoReq orderDetailInfoReq=new OrderDetailInfoReq();
	            String hotelOrderNo = hotelOrder.getHotelOrderNo();
	            OrderInfoModel info=null;
            	orderDetailInfoReq.setOrderId(hotelOrderNo);
            	OrderInfomationDetail orderInfomationDetail = orderDetailInfo(agent, orderDetailInfoReq);
            	if(orderInfomationDetail != null){
            		if(orderInfomationDetail.getIsSuccess() == true){
            			if(orderInfomationDetail.getOrderInfos().size()>0)
            			{
                			info = orderInfomationDetail.getOrderInfos().get(0);
                			for(ResourceModel resourmodel : info.getResources()){
                				if(StringUtils.isNotEmpty(resourmodel.getSupplierConfirmNumber())){
                					hotelOrder.setSupplierNumber(resourmodel.getSupplierConfirmNumber());
                					break;
                				}
                			}
                			if(info.getOrderStatus().equals(TcOrderStatus.CONFIRM_TO_ROOM.getKey())){
                				if(hotelOrder.getOrderStatus().equals(OwnerOrderStatus.ORDER_ONGOING.getKey()) || hotelOrder.getOrderStatus().equals(OwnerOrderStatus.ALREADY_CONRIRM.getKey())){
                					if(new Date().before(hotelOrder.getDepartureDate())){
                						hotelOrder.setOrderStatus(OwnerOrderStatus.RESIDE_ONGOING.getKey());
                    					hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
                    					hotelOrderMapper.updateById(hotelOrder);
                					}
                				}
                				
                			}
            			}

            		}
            	}
	        }*/ 
                /*List<String> guestNameList= Arrays.asList(hotelOrder.getGuestName().split(","));
                if(StringUtil.isNotNullOrEmpty(hotelOrder.getFactGuestName())){
                	List<String> factGuestNameList = Arrays.asList(hotelOrder.getFactGuestName().split(","));
                	hotelOrder.setFactGuestNameList(factGuestNameList);
                }
                hotelOrder.setGuestNameList(guestNameList);*/
	        
			logger.info("根据供应商订单号查询酒店订单详情结果:" + JSONObject.toJSONString(hotelOrder));
		} catch (Exception e) {
			logger.error("查询订单详情异常：", e);
		}
		
		return hotelOrder;
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
	public List<CancelReasonModel> getCancelReasonModelList(Agent agent) {
		if (StringUtil.isNullOrEmpty(agent)) {
            logger.error("agent对象为空");
            throw new GSSException("取消酒店订单", "0102", "agent对象为空");
        }
		List<CancelReasonModel> res = mongoTemplate1.find(new Query(Criteria.where("_id").ne("").ne(null)),CancelReasonModel.class);
		return res;
	}
	
	
	@Override
	public CancelOrderRes cancelOrderBeforePayReq(Agent agent, CancelOrderBeforePayReq orderCancelBeforePayReq) throws GSSException{
		logger.info("取消酒店订单开始,入参:" + JSONObject.toJSONString(orderCancelBeforePayReq) + ";remark=" + orderCancelBeforePayReq.getOtherReason());
		if (StringUtil.isNullOrEmpty(orderCancelBeforePayReq)) {
            logger.error("取消订单入参为空");
            throw new GSSException("取消酒店订单", "0101", "取消订单入参为空");
        }
        if (StringUtil.isNullOrEmpty(agent)) {
            logger.error("agent对象为空");
            throw new GSSException("取消酒店订单", "0102", "agent对象为空");
        }
        CancelOrderRes cancelOrderRes=new CancelOrderRes();
        HotelOrder hotelOrder = new HotelOrder();
        hotelOrder.setHotelOrderNo(orderCancelBeforePayReq.getOrderId());
        hotelOrder = hotelOrderMapper.selectOne(hotelOrder);
        
        if (StringUtil.isNullOrEmpty(hotelOrder)) {
            logger.error("订单信息不存在");
            throw new GSSException("取消酒店订单", "0103", "订单信息不存在");
        } else {
        	LogRecord LogRecord=new LogRecord();
        	String des = "";
            	try{
            		if(hotelOrder.getTcOrderStatus().equals(TcOrderStatus.WAIT_TC_CONFIRM.getKey())){
            			String reqJson = JSONObject.toJSONString(orderCancelBeforePayReq);
                        String result = httpClientUtil.doTCJsonPost(CANCEL_ORDER_URL, reqJson);
                        logger.info("订单取消请求酒店返回:" + result);
                        if (result != null) {
                         	ResultTc<CancelTcOrderBeforePay> cancelTcOrderBeforePayBase= JsonUtil.toBean(result, new TypeReference<ResultTc<CancelTcOrderBeforePay>>(){});
                         	if(cancelTcOrderBeforePayBase != null && cancelTcOrderBeforePayBase.getResult() != null){
                         		if(cancelTcOrderBeforePayBase.getResult().getIsSuccessed() == true){
                         			cancelOrderRes.setResult(true);
                        			cancelOrderRes.setMsg(cancelTcOrderBeforePayBase.getResult().getMsg());
                        			des = "订单号"+hotelOrder.getHotelOrderNo() +",订单状态由"+ OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.CANCEL_OK.getValue();
                         			//更新销售单和采购单状态
                                     updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.CANCEL_OK));
                                    //更新酒店订单状态
                                     hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_OK.getKey());
                                     hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_CANCEL.getKey());
                                     hotelOrder.setCancelTime(new Date());
                                     hotelOrderMapper.updateById(hotelOrder); 
                                     return cancelOrderRes;
                         		}else{
                         			cancelOrderRes.setResult(false);
                        			cancelOrderRes.setMsg(cancelTcOrderBeforePayBase.getResult().getMsg());
                        			return cancelOrderRes;
                         		}
                         	}
                         }else {
                             logger.error("取消酒店订单请求返回空值");
                             throw new GSSException("取消酒店订单请求", "0105", "取消酒店订单请求返回空值");
                         }
            		}else if(hotelOrder.getTcOrderStatus().equals(TcOrderStatus.ALREADY_TC_CONFIRM.getKey())){
            			SimpleDateFormat sim = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                		SimpleDateFormat sim1 = new SimpleDateFormat("MM/dd/yyyy");
                		OrderDetailInfoReq orderDetailInfoReq=new OrderDetailInfoReq();
        	            String hotelOrderNo = hotelOrder.getHotelOrderNo();
        	            OrderInfoModel info=null;
                    	orderDetailInfoReq.setOrderId(hotelOrderNo);
                    	OrderInfomationDetail orderInfomationDetail = orderDetailInfo(agent, orderDetailInfoReq);
                    	if(orderInfomationDetail.getIsSuccess() == true){
                    		info = orderInfomationDetail.getOrderInfos().get(0);
                			if(info.getLasestCancelTime().compareTo("01/01/1900 00:00:00") == 0 || info.getLasestCancelTime().compareTo("01/01/1900") == 0){
                    			cancelOrderRes.setResult(false);
                    			cancelOrderRes.setMsg("当前订单不能取消");
                    			return cancelOrderRes;
                    		}
                    		if(info.getLasestCancelTime().compareTo(sim.format(new Date())) >= 0 || info.getLasestCancelTime().compareTo(sim1.format(new Date())) >= 0){
                    			String reqJson = JSONObject.toJSONString(orderCancelBeforePayReq);
                                String result = httpClientUtil.doTCJsonPost(CANCEL_ORDER_URL, reqJson);
                                 if (result != null) {
                                 	ResultTc<CancelTcOrderBeforePay> cancelTcOrderBeforePayBase= JsonUtil.toBean(result, new TypeReference<ResultTc<CancelTcOrderBeforePay>>(){});
                                 	if(cancelTcOrderBeforePayBase != null && cancelTcOrderBeforePayBase.getResult() != null){
                                 		if(cancelTcOrderBeforePayBase.getResult().getIsSuccessed() == true){
                                 			cancelOrderRes.setResult(true);
                                			cancelOrderRes.setMsg("取消中, 请等待");
                                			des = "订单号"+hotelOrder.getHotelOrderNo() +",订单状态由"+ OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.CANCEL_ONGOING.getValue();
                                 			//更新销售单和采购单状态
                                             updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.CANCEL_ONGOING));
                                            //更新酒店订单状态
                                             hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_ONGOING.getKey());
                                             hotelOrder.setTcOrderStatus(TcOrderStatus.WAIT_TC_CONFIRM.getKey());
                                             hotelOrder.setCancelTime(new Date());
                                             hotelOrderMapper.updateById(hotelOrder); 
                                 		}else{
                                 			logger.error("取消酒店订单请求失败");
                                             throw new GSSException("取消酒店订单请求失败", "0104", "取消酒店订单请求失败:" + cancelTcOrderBeforePayBase.getResult().getMsg());
                                 		}
                                 	}
                                 }else {
                                     logger.error("取消酒店订单请求返回空值");
                                     throw new GSSException("取消酒店订单请求", "0105", "取消酒店订单请求返回空值");
                                 }
                    		}else{
                    			cancelOrderRes.setResult(false);
                    			cancelOrderRes.setMsg("取消时间已过, 不能取消");
                    			return cancelOrderRes;
                    		}
                    	}
            		}
           	}catch (Exception e) {
                   logger.error("取消酒店订单请求出错"+e);
                   throw new GSSException("取消酒店订单请求出错", "0106", "取消酒店订单请求出错");
               }
                LogRecord.setBizCode("HOL-Order");
        		LogRecord.setTitle("申请取消订单");
        		LogRecord.setBizNo(hotelOrder.getHotelOrderNo());
        		LogRecord.setCreateTime(new Date());
				LogRecord.setDesc(des);
				if(StringUtils.isNotEmpty(agent.getAccount())){
					LogRecord.setOptName(agent.getAccount());
				}
				iLogService.insert(LogRecord);
        }
        return cancelOrderRes;
	}
	
	@Override
	public Long incrOrderChangeInfo(Agent agent, IncrOrderChangeInfoReq incrOrderChangeInfoReq) throws GSSException{
		logger.info("酒店订单状态增量更新开始,入参:" + JSONObject.toJSONString(incrOrderChangeInfoReq));
		//List<HotelOrder> res = hotelOrderMapper.queryOrderByOrderResultCode("1");
		String reqJson = JSONObject.toJSONString(incrOrderChangeInfoReq);
		String result= httpClientUtil.doTCJsonPost(ORDER_CHANGE_URL, reqJson);
		logger.info("酒店订单状态增量更新: "+result);
		Long endIncrementId = 1L;
		//HotelOrder hotelOrder = new HotelOrder();
		try {
			if(StringUtil.isNotNullOrEmpty(result)){
					ResultTc<IncrOrderChangeInfo> incr= JsonUtil.toBean(result, new TypeReference<ResultTc<IncrOrderChangeInfo>>(){});
					if(StringUtil.isNotNullOrEmpty(incr) && StringUtil.isNotNullOrEmpty(incr.getResult())){
						List<OrderIncrementInfo> IncrOrderChangeInfoList = incr.getResult().getOrderIncrementList();
						endIncrementId = incr.getResult().getEndIncrementId();
						if(StringUtil.isNotNullOrEmpty(IncrOrderChangeInfoList)){
							for(OrderIncrementInfo oii : IncrOrderChangeInfoList){
								HotelOrder hotelOrder = hotelOrderMapper.getOrderByNo(oii.getOrderId());
								LogRecord LogRecord=new LogRecord();
								String des= "";
								String orderId = hotelOrder.getHotelOrderNo();
								LogRecord.setBizCode("HOL-Order");
								LogRecord.setTitle("酒店订单状态");
								LogRecord.setBizNo(hotelOrder.getHotelOrderNo());
								hotelOrder.setModifier("供应商");
								hotelOrder.setModifyTime(new Date());
						        if(StringUtil.isNotNullOrEmpty(orderId)) {
						        	if(oii.getOrderFlag().equals(TcOrderStatus.ALREADY_TC_CONFIRM.getKey())) {
						        		if(hotelOrder.getTcOrderStatus().equals(TcOrderStatus.WAIT_TC_CONFIRM.getKey()) || hotelOrder.getTcOrderStatus().equals(TcOrderStatus.WAIT_PAY.getKey())) {
						        			des = "订单号"+orderId +",订单状态由"+ OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.ALREADY_CONRIRM.getValue();
											//更新销售单和采购单状态
											updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.ALREADY_CONRIRM));
											//更新mysql酒店订单状态
											hotelOrder.setOrderStatus(OwnerOrderStatus.ALREADY_CONRIRM.getKey());
											hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_TC_CONFIRM.getKey());
											OrderDetailInfoReq orderDetailInfoReq =new OrderDetailInfoReq();
											orderDetailInfoReq.setOrderId(oii.getOrderId());
											OrderInfomationDetail orderInfomationDetail = orderDetailInfo(agent, orderDetailInfoReq);
											if(StringUtil.isNullOrEmpty(orderInfomationDetail.getOrderInfos())){
												throw new GSSException("更新状态信息异常", "0110", "获取订单详情列表为空");
											}
											OrderInfoModel orderInfoModel = orderInfomationDetail.getOrderInfos().get(0);
											//Date stringToSimpleDate = DateUtil.stringToSimpleDate(orderInfoModel.getLasestCancelTime());
											//hotelOrder.setCancelPenalty(stringToSimpleDate);
											if(StringUtil.isNotNullOrEmpty(orderInfoModel.getResources())){
												for(ResourceModel resource : orderInfoModel.getResources()){
													if(StringUtils.isNotEmpty(resource.getSupplierConfirmNumber())){
														hotelOrder.setSupplierNumber(resource.getSupplierConfirmNumber());
													}
												}
											}
											hotelOrderMapper.updateById(hotelOrder);
											//确认下单成功，发送短信
											/*if(StringUtil.isNullOrEmpty(hotelOrder.getBankId()) && StringUtil.isNullOrEmpty(hotelOrder.getCardUserName())){
												SmsTemplateDetail smsTemplateDetail = new SmsTemplateDetail();
										    	smsTemplateDetail.setDictCode("HOTEL_NO_GUARANTEE");
										    	List<SmsTemplateDetail> stds = smsTemplateDetailService.query(smsTemplateDetail);
										    	System.out.println("短信内容: "+JsonUtil.toJson(stds));
										    	String messageReplace = messageReplace(stds.get(0).getContent(), hotelOrder);
										    	System.out.println("变化后的内容: "+messageReplace);
										    	//smsUtil.sendMsgForUpdateBill(agent, hotelOrder.getContactNumber(), messageReplace);
											}else{*/
												SmsTemplateDetail smsTemplateDetail = new SmsTemplateDetail();
												smsTemplateDetail.setDictCode("HOTEL_GUARANTEE");
										    	List<SmsTemplateDetail> stds = smsTemplateDetailService.query(smsTemplateDetail);
										    	String messageReplace = messageReplace(stds.get(0).getContent(), hotelOrder);
										    	
										    	LogRecord logRecord=new LogRecord();
												logRecord.setAppCode("GSS");
												logRecord.setBizCode("HOL-Order");
												logRecord.setBizNo(hotelOrder.getHotelOrderNo());		
												logRecord.setDesc(messageReplace);
												logRecord.setCreateTime(new Date());
												logRecord.setOptName("腾邦国际");
												iLogService.insert(logRecord);
						        		}
						        	}else if(oii.getOrderFlag().equals(TcOrderStatus.ALREADY_CANCEL.getKey())) {
						        		des = "订单号"+orderId +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.CANCEL_OK.getValue();
										//更新销售单和采购单状态
										updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.CANCEL_OK));
										//更新mysql酒店订单状态
										hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_OK.getKey());
										hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_CANCEL.getKey());
										hotelOrderMapper.updateById(hotelOrder);
						        	}else if(oii.getOrderFlag().equals(TcOrderStatus.CONFIRM_TO_ROOM.getKey())) {
						        		OrderDetailInfoReq orderDetailInfoReq =new OrderDetailInfoReq();
										orderDetailInfoReq.setOrderId(oii.getOrderId());
										OrderInfomationDetail orderInfomationDetail = orderDetailInfo(agent, orderDetailInfoReq);
										if(StringUtil.isNullOrEmpty(orderInfomationDetail.getOrderInfos())){
											throw new GSSException("更新状态信息异常", "0110", "获取订单详情列表为空");
										}
										OrderInfoModel orderInfoModel = orderInfomationDetail.getOrderInfos().get(0);
											int startTime = simple.format(hotelOrder.getArrivalDate()).compareTo(orderInfoModel.getStartTime());
											int endTime   = simple.format(hotelOrder.getDepartureDate()).compareTo(orderInfoModel.getEndTime());
											if(endTime > 0){
												des = "订单号"+orderId +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.BEFORE_RESIDE.getValue();
												//更新销售单和采购单状态
												updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.BEFORE_RESIDE));
												//更新mysql酒店订单状态
												hotelOrder.setOrderStatus(OwnerOrderStatus.BEFORE_RESIDE.getKey());
												hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
											}else if(endTime < 0){
												des = "订单号"+orderId +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.AFTER_RESIDE.getValue();
												//更新销售单和采购单状态
												updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.AFTER_RESIDE));
												//更新mysql酒店订单状态
												hotelOrder.setOrderStatus(OwnerOrderStatus.AFTER_RESIDE.getKey());
												hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
											}else if(startTime == 0 && endTime == 0){
												int rooms = orderInfoModel.getCounts().getRoomCount().compareTo(hotelOrder.getNightCount()*hotelOrder.getBookCount());
												if(rooms < 0){
													des = "订单号"+orderId +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.BEFORE_RESIDE.getValue();
													//更新销售单和采购单状态
													updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.BEFORE_RESIDE));
													//更新mysql酒店订单状态
													hotelOrder.setOrderStatus(OwnerOrderStatus.BEFORE_RESIDE.getKey());
													hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
												}
												if(rooms == 0){
													des = "订单号"+orderId +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.RESIDE_OK.getValue();
													//更新销售单和采购单状态
													updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.RESIDE_OK));
													//更新mysql酒店订单状态
													hotelOrder.setOrderStatus(OwnerOrderStatus.RESIDE_OK.getKey());
													hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
												}
											}
											String factName = null;
											for(PassengerModel pm : orderInfoModel.getPassengers()){
												if (factName == null || "".equals(factName)) {
													factName = pm.getName();
								                } else {
								                    if (pm.getName() != null && !"".equals(pm.getName())) {
								                    	factName = factName + "," + pm.getName();
								                    }
								                }
											}
											hotelOrder.setFactGuestName(factName);
											hotelOrder.setFactArriveTime(simple.parse(orderInfoModel.getStartTime()));
											hotelOrder.setFactLeaveTime(simple.parse(orderInfoModel.getEndTime()));
											Integer factNight = 0;
											try{
												factNight = DateUtil.daysBetween(orderInfoModel.getStartTime(), orderInfoModel.getEndTime());
									        }catch(ParseException e){
									        	e.printStackTrace();
									        	throw new GSSException("更新状态信息异常", "0192", "根据消费日期计算天数异常");
									        }
											hotelOrder.setFactNightCount(factNight);
											Integer roomCount = orderInfoModel.getCounts().getRoomCount();
											
											
											BigDecimal hoteelDivide = hotelOrder.getTotalPrice().divide(new BigDecimal(hotelOrder.getNightCount()*hotelOrder.getBookCount()), 2, BigDecimal.ROUND_HALF_UP);
											BigDecimal tcInfoDivide = (orderInfoModel.getOrigin()).divide(new BigDecimal(roomCount),2, BigDecimal.ROUND_HALF_UP);
											int compareToPrice = hoteelDivide.compareTo(tcInfoDivide);
											if(compareToPrice == 0){
												BigDecimal multiply = orderInfoModel.getOrigin().multiply(hotelOrder.getTotalRefund().divide(hotelOrder.getTotalPrice(), 2, BigDecimal.ROUND_HALF_UP));
												hotelOrder.setFactTotalRefund(multiply);
											}
											hotelOrder.setFactTotalPrice(orderInfoModel.getOrigin());
											Integer factProCo = 0;
											if(StringUtil.isNotNullOrEmpty(orderInfoModel.getResources())){
												factProCo = orderInfoModel.getResources().get(0).getPriceFraction();
												if(StringUtils.isNotEmpty(orderInfoModel.getResources().get(0).getSupplierConfirmNumber())){
													hotelOrder.setSupplierNumber(orderInfoModel.getResources().get(0).getSupplierConfirmNumber());
												}
												for(ResourceModel resource : orderInfoModel.getResources()){
													if(resource.getPriceFraction().compareTo(factProCo) > 0){
														factProCo = resource.getPriceFraction();
													}
												}
												hotelOrder.setFactProCount(factProCo);
											}
											hotelOrderMapper.updateById(hotelOrder);
											
						        	}else if(oii.getOrderFlag().equals(TcOrderStatus.CONFIRM_NOT_TO_ROOM.getKey())) {
						        		des = "订单号"+orderId +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.NO_RESIDE.getValue();
										//更新销售单和采购单状态
										updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.NO_RESIDE));
										//更新mysql酒店订单状态
										hotelOrder.setFactTotalRefund(new BigDecimal(0));
										hotelOrder.setOrderStatus(OwnerOrderStatus.NO_RESIDE.getKey());
										hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_NOT_TO_ROOM.getKey());
										hotelOrderMapper.updateById(hotelOrder);
										
										
						        	}else if(oii.getOrderFlag().equals(TcOrderStatus.ORDER_CHANGED.getKey())) {
						        		OrderDetailInfoReq orderDetailInfoReq =new OrderDetailInfoReq();
										orderDetailInfoReq.setOrderId(oii.getOrderId());
										OrderInfomationDetail orderInfomationDetail = orderDetailInfo(agent, orderDetailInfoReq);
										if(StringUtil.isNullOrEmpty(orderInfomationDetail.getOrderInfos())){
											throw new GSSException("更新状态信息异常", "0110", "获取订单详情列表为空");
										}
										OrderInfoModel orderInfoModel = orderInfomationDetail.getOrderInfos().get(0);
										
										des = "订单号"+orderId +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.ORDER_ONGOING.getValue();
										//更新销售单和采购单状态
										updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.ORDER_ONGOING));
										//更新mysql酒店订单状态
										hotelOrder.setTcOrderStatus(orderInfoModel.getOrderStatus());
										hotelOrder.setOrderStatus(OwnerOrderStatus.ORDER_ONGOING.getKey());
										
										Integer priceFraction = 0;
										if(StringUtil.isNotNullOrEmpty(orderInfoModel.getResources())){
											ResourceModel resourceModel = orderInfoModel.getResources().get(0);
											
											priceFraction = resourceModel.getPriceFraction();
											/*String newRemark = resourceModel.getRemark();
											hotelOrder.setRemark(newRemark);*/
											String productUniqueId = resourceModel.getProductUniqueId();
											hotelOrder.setProductUniqueId(productUniqueId);
											if(StringUtils.isNotEmpty(resourceModel.getSupplierConfirmNumber())){
												hotelOrder.setSupplierNumber(resourceModel.getSupplierConfirmNumber());
											}
											hotelOrder.setProName(resourceModel.getProductName());
											if(StringUtil.isNotNullOrEmpty(resourceModel.getArrivalTime())){
												String atime = "";
												if(resourceModel.getArrivalTime().compareTo(24) <= 0){
													atime = orderInfoModel.getStartTime()+" "+resourceModel.getArrivalTime()+":00:00";
												}else{
													 Calendar da = Calendar.getInstance(); 
													 da.setTime(simple.parse(orderInfoModel.getStartTime()));
													 da.add(Calendar.DAY_OF_MONTH, 1);
													 String arriveTomorow = simple.format(da.getTime());
													 atime = arriveTomorow+" "+(resourceModel.getArrivalTime().intValue() - 24)+":00:00";
												}
												hotelOrder.setArriveHotelTime(atime);
											}
											if(orderInfoModel.getResources().size() > 1){
												for(ResourceModel resource : orderInfoModel.getResources()){
													if(resource.getPriceFraction().compareTo(priceFraction) > 0){
														priceFraction = resource.getPriceFraction();
													}
												}
											}
											hotelOrder.setBookCount(priceFraction);
										}
										
										Date arrivalDate = simple.parse(orderInfoModel.getStartTime());
										Date departureDate = simple.parse(orderInfoModel.getEndTime());
										String guestName = "";
										List<PassengerModel> passengers = orderInfoModel.getPassengers();
										for(PassengerModel guest : passengers){
											if (guestName == null || "".equals(guestName)) {
							                    guestName = guest.getName();
							                } else {
							                    if (guest.getName() != null && !"".equals(guest.getName())) {
							                        guestName = guestName + "," + guest.getName();
							                    }
							                }
										}
										hotelOrder.setGuestName(guestName);
										hotelOrder.setArrivalDate(arrivalDate);
										hotelOrder.setDepartureDate(departureDate);
										Integer factNight = 0;
										try{
											factNight = DateUtil.daysBetween(orderInfoModel.getStartTime(), orderInfoModel.getEndTime());
								        }catch(ParseException e){
								        	e.printStackTrace();
								        	throw new GSSException("更新状态信息异常", "0192", "根据消费日期计算天数异常");
								        }
										//Integer roomCount = orderInfoModel.getCounts().getRoomCount();
										
										hotelOrder.setTotalPrice(orderInfoModel.getOrigin());
										BigDecimal multiply = orderInfoModel.getOrigin().multiply(hotelOrder.getTotalRefund().divide(hotelOrder.getTotalPrice(), 2, BigDecimal.ROUND_HALF_UP));
										hotelOrder.setTotalRefund(multiply);
										hotelOrder.setNightCount(factNight);
										String eachNightPrice = null;
										String breakfastNum = null;
										ProInfoDetail proInfoDetail=null;
										//ProInfoDetail proInfoDetail=tCHotelSupplierService.queryListByProductUniqueId(hotelOrder.getProductUniqueId(), ProInfoDetail.class);
										 AssignDateHotel assignDateHotel = tCHotelSupplierService.queryDetailById(Long.valueOf(hotelOrder.getHotelCode()), AssignDateHotel.class);
										if(StringUtil.isNotNullOrEmpty(assignDateHotel) && StringUtil.isNotNullOrEmpty(assignDateHotel.getProInfoDetailList())) {
											for(ProInfoDetail pinfo : assignDateHotel.getProInfoDetailList()) {
												if(pinfo.getProductUniqueId().equals(hotelOrder.getProductUniqueId())) {
													proInfoDetail = pinfo;
													break;
												}
											}
										}
										 if(StringUtil.isNotNullOrEmpty(proInfoDetail)){
											for (Map.Entry<String, ProSaleInfoDetail> entry : proInfoDetail.getProSaleInfoDetails().entrySet()) {
			        	        				int begincompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(orderInfoModel.getStartTime());
			        	        				int endCompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(orderInfoModel.getEndTime());
			        	        				if(begincompare >= 0 && endCompare < 0){
			    	        						Integer price= entry.getValue().getDistributionSalePrice();
			    	        						Integer breakNum = entry.getValue().getBreakfastNum();
			    	        						if(StringUtil.isNotNullOrEmpty(price)){
			    	        							if(eachNightPrice == null || "".equals(eachNightPrice)){
			        	        							eachNightPrice = price.toString();
			        	        			            }else {
			        	        			                eachNightPrice = eachNightPrice + "," + price.toString();
			        	        			            }
			    	        						}
			    	        						if(StringUtil.isNotNullOrEmpty(breakNum)){
			    	        							if(breakfastNum == null || "".equals(breakfastNum)){
			        	        							breakfastNum = breakNum.toString();
			        	        			            }else {
			        	        			                breakfastNum = breakfastNum + "," + breakNum.toString();
			        	        			            }
			    	        						}
			                					}
			                				}
											hotelOrder.setEachNightPrice(eachNightPrice);
											hotelOrder.setBreakfastCount(breakfastNum);
										}else{
											logger.error("补单订单推送，拉取每日价格异常");
										}
										hotelOrder.setModifier("供应商");
										hotelOrder.setModifyTime(new Date());
										hotelOrderMapper.updateById(hotelOrder);
						        	}
						        	LogRecord.setCreateTime(new Date());
									LogRecord.setDesc(des);
									LogRecord.setOptName("供应商");
									iLogService.insert(LogRecord);
						        }
							}
						}
					}
			}else{
				logger.error("酒店增量订单状态返回空值");
	            throw new GSSException("增量获取酒店订单状态", "0111", "增量获取酒店订单状态空值");
	        } 
		} catch (Exception e) {
			logger.error("解析JSON数据异常"+e);
            throw new GSSException("解析JSON数据异常", "1111", "解析JSON数据异常"+e);
		}
		return endIncrementId;
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
	public Boolean tcPushOrderInfo(Agent agent, TcPushOrderInfo tcPushOrderInfo) throws GSSException{
		logger.info("推送更新订单状态:{}",JSON.toJSONString(tcPushOrderInfo));
		try {
			if (StringUtil.isNotNullOrEmpty(tcPushOrderInfo) && StringUtil.isNotNullOrEmpty(tcPushOrderInfo.getOrderId())) {
				HotelOrder hotelOrder = hotelOrderMapper.getOrderByNo(tcPushOrderInfo.getOrderId());
				LogRecord LogRecord=new LogRecord();
				String des= "";
				Integer flag = 0;
				String orderId = hotelOrder.getHotelOrderNo();
				if(StringUtil.isNotNullOrEmpty(orderId)){
					LogRecord.setBizCode("HOL-Order");
					LogRecord.setTitle("酒店订单状态");
					LogRecord.setBizNo(hotelOrder.getHotelOrderNo());
					hotelOrder.setModifier("供应商");
					hotelOrder.setModifyTime(new Date());
					if(tcPushOrderInfo.getOperateType().equals(StatusType.ORDER_CONFIRM.getKey())){
						if(!hotelOrder.getOrderStatus().equals(OwnerOrderStatus.ALREADY_CONRIRM.getKey())) {
							flag =1;
							des = "订单号"+orderId +",订单状态由"+ OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.ALREADY_CONRIRM.getValue();
							//更新销售单和采购单状态
							updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.ALREADY_CONRIRM));
							//更新mysql酒店订单状态
							hotelOrder.setOrderStatus(OwnerOrderStatus.ALREADY_CONRIRM.getKey());
							hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_TC_CONFIRM.getKey());
							OrderDetailInfoReq orderDetailInfoReq =new OrderDetailInfoReq();
							orderDetailInfoReq.setOrderId(tcPushOrderInfo.getOrderId());
							OrderInfomationDetail orderInfomationDetail = orderDetailInfo(agent, orderDetailInfoReq);
							if(StringUtil.isNullOrEmpty(orderInfomationDetail.getOrderInfos())){
								throw new GSSException("更新状态信息异常", "0110", "获取订单详情列表为空");
							}
							OrderInfoModel orderInfoModel = orderInfomationDetail.getOrderInfos().get(0);
							//Date stringToSimpleDate = DateUtil.stringToSimpleDate(orderInfoModel.getLasestCancelTime());
							//hotelOrder.setCancelPenalty(stringToSimpleDate);
							if(StringUtil.isNotNullOrEmpty(orderInfoModel.getResources())){
								for(ResourceModel resource : orderInfoModel.getResources()){
									if(StringUtils.isNotEmpty(resource.getSupplierConfirmNumber())){
										hotelOrder.setSupplierNumber(resource.getSupplierConfirmNumber());
									}
								}
							}
							hotelOrderMapper.updateById(hotelOrder);
							//确认下单成功，发送短信
							/*if(StringUtil.isNullOrEmpty(hotelOrder.getBankId()) && StringUtil.isNullOrEmpty(hotelOrder.getCardUserName())){
								SmsTemplateDetail smsTemplateDetail = new SmsTemplateDetail();
						    	smsTemplateDetail.setDictCode("HOTEL_NO_GUARANTEE");
						    	List<SmsTemplateDetail> stds = smsTemplateDetailService.query(smsTemplateDetail);
						    	System.out.println("短信内容: "+JsonUtil.toJson(stds));
						    	String messageReplace = messageReplace(stds.get(0).getContent(), hotelOrder);
						    	System.out.println("变化后的内容: "+messageReplace);
						    	//smsUtil.sendMsgForUpdateBill(agent, hotelOrder.getContactNumber(), messageReplace);
							}else{*/
								SmsTemplateDetail smsTemplateDetail = new SmsTemplateDetail();
								smsTemplateDetail.setDictCode("HOTEL_GUARANTEE");
						    	List<SmsTemplateDetail> stds = smsTemplateDetailService.query(smsTemplateDetail);
						    	//System.out.println("总内容: "+JsonUtil.toJson(stds));
						    	String messageReplace = messageReplace(stds.get(0).getContent(), hotelOrder);
						    	//System.out.println("短信内容1: "+stds.get(0).getContent());
						    	//System.out.println("变化后的内容: "+messageReplace);
						    	
						    	LogRecord logRecord=new LogRecord();
								logRecord.setAppCode("GSS");
								logRecord.setBizCode("HOL-Order");
								logRecord.setBizNo(hotelOrder.getHotelOrderNo());		
								//logRecord.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
								logRecord.setDesc(messageReplace);
								logRecord.setCreateTime(new Date());
								logRecord.setOptName("腾邦国际");
								iLogService.insert(logRecord); 
						    	
						    	//smsUtil.sendMsgForUpdateBill(agent, hotelOrder.getContactNumber(), messageReplace);
							//}
						}
					}else if(tcPushOrderInfo.getOperateType().equals(StatusType.CANCEL_ORDER_CONFIRM.getKey())){
						if(!hotelOrder.getOrderStatus().equals(OwnerOrderStatus.CANCEL_OK.getKey())) {
							flag =1;
							des = "订单号"+orderId +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.CANCEL_OK.getValue();
							//更新销售单和采购单状态
							updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.CANCEL_OK));
							//更新mysql酒店订单状态
							hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_OK.getKey());
							hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_CANCEL.getKey());
							hotelOrderMapper.updateById(hotelOrder);
						}
					}else if(tcPushOrderInfo.getOperateType().equals(StatusType.CHECK_RESIDE.getKey())){
						if( (!hotelOrder.getOrderStatus().equals(OwnerOrderStatus.NO_RESIDE.getKey())) || (!hotelOrder.getOrderStatus().equals(OwnerOrderStatus.BEFORE_RESIDE.getKey()))
							|| 	(!hotelOrder.getOrderStatus().equals(OwnerOrderStatus.AFTER_RESIDE.getKey())) || (!hotelOrder.getOrderStatus().equals(OwnerOrderStatus.RESIDE_OK.getKey()))) {
							flag =1;
							OrderDetailInfoReq orderDetailInfoReq =new OrderDetailInfoReq();
							orderDetailInfoReq.setOrderId(tcPushOrderInfo.getOrderId());
							OrderInfomationDetail orderInfomationDetail = orderDetailInfo(agent, orderDetailInfoReq);
							if(StringUtil.isNullOrEmpty(orderInfomationDetail.getOrderInfos())){
								throw new GSSException("更新状态信息异常", "0110", "获取订单详情列表为空");
							}
							OrderInfoModel orderInfoModel = orderInfomationDetail.getOrderInfos().get(0);
							if(orderInfoModel.getOrderStatus().equals(TcOrderStatus.CONFIRM_NOT_TO_ROOM.getKey())){
								des = "订单号"+orderId +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.NO_RESIDE.getValue();
								//更新销售单和采购单状态
								updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.NO_RESIDE));
								//更新mysql酒店订单状态
								hotelOrder.setFactTotalRefund(new BigDecimal(0));
								hotelOrder.setOrderStatus(OwnerOrderStatus.NO_RESIDE.getKey());
								hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_NOT_TO_ROOM.getKey());
								hotelOrderMapper.updateById(hotelOrder);
							}else if(orderInfoModel.getOrderStatus().equals(TcOrderStatus.CONFIRM_TO_ROOM.getKey())){
								int startTime = simple.format(hotelOrder.getArrivalDate()).compareTo(orderInfoModel.getStartTime());
								int endTime   = simple.format(hotelOrder.getDepartureDate()).compareTo(orderInfoModel.getEndTime());
								if(endTime > 0){
									des = "订单号"+orderId +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.BEFORE_RESIDE.getValue();
									//更新销售单和采购单状态
									updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.BEFORE_RESIDE));
									//更新mysql酒店订单状态
									hotelOrder.setOrderStatus(OwnerOrderStatus.BEFORE_RESIDE.getKey());
									hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
								}else if(endTime < 0){
									des = "订单号"+orderId +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.AFTER_RESIDE.getValue();
									//更新销售单和采购单状态
									updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.AFTER_RESIDE));
									//更新mysql酒店订单状态
									hotelOrder.setOrderStatus(OwnerOrderStatus.AFTER_RESIDE.getKey());
									hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
								}else if(startTime == 0 && endTime == 0){
									int rooms = orderInfoModel.getCounts().getRoomCount().compareTo(hotelOrder.getNightCount()*hotelOrder.getBookCount());
									if(rooms < 0){
										des = "订单号"+orderId +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.BEFORE_RESIDE.getValue();
										//更新销售单和采购单状态
										updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.BEFORE_RESIDE));
										//更新mysql酒店订单状态
										hotelOrder.setOrderStatus(OwnerOrderStatus.BEFORE_RESIDE.getKey());
										hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
									}
									if(rooms == 0){
										des = "订单号"+orderId +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.RESIDE_OK.getValue();
										//更新销售单和采购单状态
										updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.RESIDE_OK));
										//更新mysql酒店订单状态
										hotelOrder.setOrderStatus(OwnerOrderStatus.RESIDE_OK.getKey());
										hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
									}
								}
								String factName = null;
								for(PassengerModel pm : orderInfoModel.getPassengers()){
									if (factName == null || "".equals(factName)) {
										factName = pm.getName();
					                } else {
					                    if (pm.getName() != null && !"".equals(pm.getName())) {
					                    	factName = factName + "," + pm.getName();
					                    }
					                }
								}
								hotelOrder.setFactGuestName(factName);
								hotelOrder.setFactArriveTime(simple.parse(orderInfoModel.getStartTime()));
								hotelOrder.setFactLeaveTime(simple.parse(orderInfoModel.getEndTime()));
								Integer factNight = 0;
								try{
									factNight = DateUtil.daysBetween(orderInfoModel.getStartTime(), orderInfoModel.getEndTime());
						        }catch(ParseException e){
						        	e.printStackTrace();
						        	throw new GSSException("更新状态信息异常", "0192", "根据消费日期计算天数异常");
						        }
								hotelOrder.setFactNightCount(factNight);
								Integer roomCount = orderInfoModel.getCounts().getRoomCount();
								
								
								BigDecimal hoteelDivide = hotelOrder.getTotalPrice().divide(new BigDecimal(hotelOrder.getNightCount()*hotelOrder.getBookCount()), 2, BigDecimal.ROUND_HALF_UP);
								BigDecimal tcInfoDivide = (orderInfoModel.getOrigin()).divide(new BigDecimal(roomCount),2, BigDecimal.ROUND_HALF_UP);
								int compareToPrice = hoteelDivide.compareTo(tcInfoDivide);
								if(compareToPrice == 0){
									BigDecimal multiply = orderInfoModel.getOrigin().multiply(hotelOrder.getTotalRefund().divide(hotelOrder.getTotalPrice(), 2, BigDecimal.ROUND_HALF_UP));
									hotelOrder.setFactTotalRefund(multiply);
								}
								hotelOrder.setFactTotalPrice(orderInfoModel.getOrigin());
								Integer factProCo = 0;
								if(StringUtil.isNotNullOrEmpty(orderInfoModel.getResources())){
									factProCo = orderInfoModel.getResources().get(0).getPriceFraction();
									if(StringUtils.isNotEmpty(orderInfoModel.getResources().get(0).getSupplierConfirmNumber())){
										hotelOrder.setSupplierNumber(orderInfoModel.getResources().get(0).getSupplierConfirmNumber());
									}
									for(ResourceModel resource : orderInfoModel.getResources()){
										if(resource.getPriceFraction().compareTo(factProCo) > 0){
											factProCo = resource.getPriceFraction();
										}
									}
									hotelOrder.setFactProCount(factProCo);
								}
								hotelOrderMapper.updateById(hotelOrder);
							}
						}
						
					}else if(tcPushOrderInfo.getOperateType().equals(StatusType.REORDER.getKey())){
						HotelOrder newhotelOrder = hotelOrderMapper.getOrderByNo(tcPushOrderInfo.getNewOrderId());
						if(StringUtil.isNullOrEmpty(newhotelOrder)) {
							flag =1;
							if(StringUtils.isNotEmpty(tcPushOrderInfo.getNewOrderId())){
								HotelOrder newHotelOrder = new HotelOrder();
								LogRecord oldLogRecord=new LogRecord();
								oldLogRecord.setBizCode("HOL-Order");
								oldLogRecord.setTitle("酒店订单状态");
								oldLogRecord.setBizNo(tcPushOrderInfo.getOrderId());
								try {
				                	BeanUtils.copyProperties(hotelOrder, newHotelOrder);
				                } catch (Exception e) {
				                    logger.error("对象属性复制错误");
				                }
								
								newHotelOrder.setRelateOrderNo(tcPushOrderInfo.getOrderId());
								des = "补单信息, 老订单号: "+tcPushOrderInfo.getOrderId()+", 新订单号: "+tcPushOrderInfo.getNewOrderId();
								LogRecord.setBizNo(tcPushOrderInfo.getNewOrderId());
								String oldSupplierNo = "";
								if(StringUtils.isNotEmpty(hotelOrder.getSupplierNumber())){
									oldSupplierNo = hotelOrder.getSupplierNumber();
								}
								String newSupplierNo = "";
								newHotelOrder.setHotelOrderNo(tcPushOrderInfo.getNewOrderId());
								//locker为1, 表示是补单订单，此状态后台可以看到但是前台看不到
								newHotelOrder.setLocker(1L);
								newHotelOrder.setLockTime(new Date());
								OrderDetailInfoReq orderDetailInfoReq =new OrderDetailInfoReq();
								orderDetailInfoReq.setOrderId(tcPushOrderInfo.getNewOrderId());
								OrderInfomationDetail orderInfomationDetail = orderDetailInfo(agent, orderDetailInfoReq);
								if(StringUtil.isNullOrEmpty(orderInfomationDetail.getOrderInfos())){
									throw new GSSException("更新状态信息异常", "0110", "获取订单详情列表为空");
								}
								OrderInfoModel orderInfoModel = orderInfomationDetail.getOrderInfos().get(0);
								Integer priceFraction = 0;
								if(StringUtil.isNotNullOrEmpty(orderInfoModel.getResources())){
									ResourceModel resourceModel = orderInfoModel.getResources().get(0);
									priceFraction = resourceModel.getPriceFraction();
									String newRemark = resourceModel.getRemark();
									newHotelOrder.setRemark(newRemark);
									String productUniqueId = resourceModel.getProductUniqueId();
									newHotelOrder.setProductUniqueId(productUniqueId);
									if(StringUtils.isNotEmpty(resourceModel.getSupplierConfirmNumber())){
										newSupplierNo = resourceModel.getSupplierConfirmNumber();
										newHotelOrder.setSupplierNumber(newSupplierNo);
									}
									newHotelOrder.setProName(resourceModel.getProductName());
									if(StringUtil.isNotNullOrEmpty(resourceModel.getArrivalTime())){
										String atime = "";
										if(resourceModel.getArrivalTime().compareTo(24) <= 0){
											atime = orderInfoModel.getStartTime()+" "+resourceModel.getArrivalTime()+":00:00";
										}else{
											 Calendar da = Calendar.getInstance(); 
											 da.setTime(simple.parse(orderInfoModel.getStartTime()));
											 da.add(Calendar.DAY_OF_MONTH, 1);
											 String arriveTomorow = simple.format(da.getTime());
											 atime = arriveTomorow+" "+(resourceModel.getArrivalTime().intValue() - 24)+":00:00";
										}
										newHotelOrder.setArriveHotelTime(atime);
									}
									if(orderInfoModel.getResources().size() > 1){
										for(ResourceModel resource : orderInfoModel.getResources()){
											/*if (newRemark == null || "".equals(newRemark)) {
												newRemark = resource.getRemark().trim();
							                } else {
							                    if (resource.getRemark() != null && !"".equals(resource.getRemark())) {
							                    	newRemark = newRemark + "," + resource.getRemark().trim();
							                    }
							                }*/
											if(resource.getPriceFraction().compareTo(priceFraction) > 0){
												priceFraction = resource.getPriceFraction();
											}
										}
									}
									newHotelOrder.setBookCount(priceFraction);
								}
								
								Date arrivalDate = simple.parse(orderInfoModel.getStartTime());
								Date departureDate = simple.parse(orderInfoModel.getEndTime());
								String guestName = "";
								List<PassengerModel> passengers = orderInfoModel.getPassengers();
								for(PassengerModel guest : passengers){
									if (guestName == null || "".equals(guestName)) {
					                    guestName = guest.getName();
					                } else {
					                    if (guest.getName() != null && !"".equals(guest.getName())) {
					                        guestName = guestName + "," + guest.getName();
					                    }
					                }
								}
								newHotelOrder.setGuestName(guestName);
								newHotelOrder.setArrivalDate(arrivalDate);
								newHotelOrder.setDepartureDate(departureDate);
								Integer factNight = 0;
								try{
									factNight = DateUtil.daysBetween(orderInfoModel.getStartTime(), orderInfoModel.getEndTime());
						        }catch(ParseException e){
						        	e.printStackTrace();
						        	throw new GSSException("更新状态信息异常", "0192", "根据消费日期计算天数异常");
						        }
								//Integer roomCount = orderInfoModel.getCounts().getRoomCount();
								
								newHotelOrder.setTotalPrice(orderInfoModel.getOrigin());
								BigDecimal multiply = orderInfoModel.getOrigin().multiply(hotelOrder.getTotalRefund().divide(hotelOrder.getTotalPrice(), 2, BigDecimal.ROUND_HALF_UP));
								newHotelOrder.setTotalRefund(multiply);
								newHotelOrder.setNightCount(factNight);
								String eachNightPrice = null;
								String breakfastNum = null;
								//ProInfoDetail proInfoDetail=tCHotelSupplierService.queryListByProductUniqueId(newHotelOrder.getProductUniqueId(), ProInfoDetail.class);
								ProInfoDetail proInfoDetail=null;
								//ProInfoDetail proInfoDetail=tCHotelSupplierService.queryListByProductUniqueId(hotelOrder.getProductUniqueId(), ProInfoDetail.class);
								 AssignDateHotel assignDateHotel = tCHotelSupplierService.queryDetailById(Long.valueOf(hotelOrder.getHotelCode()), AssignDateHotel.class);
								if(StringUtil.isNotNullOrEmpty(assignDateHotel) && StringUtil.isNotNullOrEmpty(assignDateHotel.getProInfoDetailList())) {
									for(ProInfoDetail pinfo : assignDateHotel.getProInfoDetailList()) {
										if(pinfo.getProductUniqueId().equals(hotelOrder.getProductUniqueId())) {
											proInfoDetail = pinfo;
											break;
										}
									}
								}
								if(StringUtil.isNotNullOrEmpty(proInfoDetail)){
									for (Map.Entry<String, ProSaleInfoDetail> entry : proInfoDetail.getProSaleInfoDetails().entrySet()) {
	        	        				int begincompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(orderInfoModel.getStartTime());
	        	        				int endCompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(orderInfoModel.getEndTime());
	        	        				if(begincompare >= 0 && endCompare < 0){
	    	        						Integer price= entry.getValue().getDistributionSalePrice();
	    	        						Integer breakNum = entry.getValue().getBreakfastNum();
	    	        						if(StringUtil.isNotNullOrEmpty(price)){
	    	        							if(eachNightPrice == null || "".equals(eachNightPrice)){
	        	        							eachNightPrice = price.toString();
	        	        			            }else {
	        	        			                eachNightPrice = eachNightPrice + "," + price.toString();
	        	        			            }
	    	        						}
	    	        						if(StringUtil.isNotNullOrEmpty(breakNum)){
	    	        							if(breakfastNum == null || "".equals(breakfastNum)){
	        	        							breakfastNum = breakNum.toString();
	        	        			            }else {
	        	        			                breakfastNum = breakfastNum + "," + breakNum.toString();
	        	        			            }
	    	        						}
	                					}
	                				}
									newHotelOrder.setEachNightPrice(eachNightPrice);
									newHotelOrder.setBreakfastCount(breakfastNum);
									
								}else{
									logger.error("补单订单推送，拉取每日价格异常");
								}
								
								if(StringUtils.isNotEmpty(hotelOrder.getSupplierNumber())){
									String desAdd = ", 老携程订单号: "+oldSupplierNo+", 新携程订单号: "+newSupplierNo;
									des = des + desAdd;
								}
								newHotelOrder.setModifier("供应商");
								newHotelOrder.setModifyTime(new Date());
								
								Long businessSignNo = IdWorker.getId();
					    		Long saleOrderNo = maxNoService.generateBizNo("HOL_SALE_ORDER_NO", 13);
					    		
					            /*if (StringUtil.isNotNullOrEmpty(newHotelOrder.getSupplierNo())) {
					            	Long supplierNo = newHotelOrder.getSupplierNo();
					            	buyOrder.setSupplierNo(supplierNo);
					                //从客商系统查询供应商信息
					                Supplier supplier = supplierService.getSupplierByNo(agent, supplierNo);
					                if(StringUtil.isNotNullOrEmpty(supplier)){
					                	 buyOrder.setSupplierTypeNo(supplier.getCustomerTypeNo());
					                }else{
					                	throw new GSSException("创建酒店订单", "0130", "根据编号查询供应商信息为空！");
					                }
					            } else {
					                logger.error("供应商信息不存在！");
					                throw new GSSException("创建酒店订单", "0130", "供应商信息不存在！");
					            }*/
								/*创建销售订单*/
					    		SaleOrder sOrder = saleOrderService.getSOrderByNo(agent, hotelOrder.getSaleOrderNo());
					            SaleOrder saleOrder = new SaleOrder();
					            saleOrder.setSaleOrderNo(saleOrderNo);
					            saleOrder.setOwner(sOrder.getOwner());
					            saleOrder.setSourceChannelNo(sOrder.getSourceChannelNo());
					            saleOrder.setCustomerTypeNo(sOrder.getCustomerTypeNo());
					            saleOrder.setCustomerNo(sOrder.getCustomerNo());
					            saleOrder.setOrderingLoginName(sOrder.getOrderingLoginName());
					            saleOrder.setGoodsType(GoodsBigType.GROGSHOP.getKey());//
					            saleOrder.setGoodsSubType(GoodsBigType.GROGSHOP.getKey());//
					            saleOrder.setOrderingTime(hotelOrder.getCreateOrderTime());//下单时间
					            saleOrder.setModifyTime(new Date());
					            saleOrder.setPayStatus(1);//待支付
					            saleOrder.setValid(1);//有效
					            saleOrder.setBusinessSignNo(businessSignNo);
					            saleOrder.setBsignType(1);//1销采 2换票 3废和退 4改签
					            saleOrder.setTransationOrderNo(newHotelOrder.getTransationOrderNo());
					            saleOrder.setOrderType(1);
					            saleOrder.setGoodsName(newHotelOrder.getHotelName());

					            /*创建采购单*/
					            BuyOrder buyOrder = new BuyOrder();
					    		buyOrder.setSupplierNo(newHotelOrder.getSupplierNo());
							    Long buyOrderNo = maxNoService.generateBizNo("HOL_BUY_ORDER_NO", 14);
					            buyOrder.setOwner(agent.getOwner());
					            buyOrder.setBuyOrderNo(buyOrderNo);
					            buyOrder.setSaleOrderNo(saleOrderNo);
					            buyOrder.setGoodsType(GoodsBigType.GROGSHOP.getKey());
					            buyOrder.setGoodsSubType(GoodsBigType.GROGSHOP.getKey());
					            buyOrder.setGoodsName(newHotelOrder.getHotelName());
					            buyOrder.setBuyChannelNo("HOTEL");
					            buyOrder.setBusinessSignNo(businessSignNo);
					            buyOrder.setBsignType(1);//1销采 2换票 3废和退 4改签
					            
					            newHotelOrder.setSaleOrderNo(saleOrderNo);
					            newHotelOrder.setBuyOrderNo(buyOrderNo);
					            
								if(orderInfoModel.getOrderStatus().equals(TcOrderStatus.WAIT_TC_CONFIRM.getKey())){
									newHotelOrder.setOrderStatus(OwnerOrderStatus.ORDER_ONGOING.getKey());
									newHotelOrder.setTcOrderStatus(TcOrderStatus.WAIT_TC_CONFIRM.getKey());
									saleOrder.setOrderChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.ORDER_ONGOING));
									buyOrder.setBuyChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.ORDER_ONGOING));
									saleOrderService.create(agent, saleOrder);
									 buyOrderService.create(agent, buyOrder);
									 hotelOrderMapper.insertSelective(newHotelOrder);
								}else if(orderInfoModel.getOrderStatus().equals(TcOrderStatus.ALREADY_TC_CONFIRM.getKey())){
									newHotelOrder.setOrderStatus(OwnerOrderStatus.ALREADY_CONRIRM.getKey());
									newHotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_TC_CONFIRM.getKey());
									saleOrder.setOrderChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.ALREADY_CONRIRM));
									buyOrder.setBuyChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.ALREADY_CONRIRM));
									saleOrderService.create(agent, saleOrder);
									 buyOrderService.create(agent, buyOrder);
									 hotelOrderMapper.insertSelective(newHotelOrder);
								}else if(orderInfoModel.getOrderStatus().equals(TcOrderStatus.ALREADY_CANCEL.getKey())){
									newHotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_OK.getKey());
									newHotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_CANCEL.getKey());
									saleOrder.setOrderChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.CANCEL_OK));
									buyOrder.setBuyChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.CANCEL_OK));
									saleOrderService.create(agent, saleOrder);
									 buyOrderService.create(agent, buyOrder);
									 hotelOrderMapper.insertSelective(newHotelOrder);
								}else if(orderInfoModel.getOrderStatus().equals(TcOrderStatus.CONFIRM_NOT_TO_ROOM.getKey())){
									newHotelOrder.setFactTotalRefund(new BigDecimal(0));
									newHotelOrder.setOrderStatus(OwnerOrderStatus.NO_RESIDE.getKey());
									newHotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_NOT_TO_ROOM.getKey());
									saleOrder.setOrderChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.NO_RESIDE));
									buyOrder.setBuyChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.NO_RESIDE));
									saleOrderService.create(agent, saleOrder);
									 buyOrderService.create(agent, buyOrder);
									 hotelOrderMapper.insertSelective(newHotelOrder);
								}
								else if(orderInfoModel.getOrderStatus().equals(TcOrderStatus.CONFIRM_TO_ROOM.getKey())  || orderInfoModel.getOrderStatus().equals(TcOrderStatus.ORDER_FINISH.getKey())){
									int startTime = simple.format(newHotelOrder.getArrivalDate()).compareTo(orderInfoModel.getStartTime());
									int endTime   = simple.format(newHotelOrder.getDepartureDate()).compareTo(orderInfoModel.getEndTime());
									if(endTime > 0){
										saleOrder.setOrderChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.BEFORE_RESIDE));
										buyOrder.setBuyChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.BEFORE_RESIDE));
										//更新mysql酒店订单状态
										newHotelOrder.setOrderStatus(OwnerOrderStatus.BEFORE_RESIDE.getKey());
										newHotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
									}else if(endTime < 0){
										saleOrder.setOrderChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.AFTER_RESIDE));
										buyOrder.setBuyChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.AFTER_RESIDE));
										//更新mysql酒店订单状态
										newHotelOrder.setOrderStatus(OwnerOrderStatus.AFTER_RESIDE.getKey());
										newHotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
									}else if(startTime == 0 && endTime == 0){
										int rooms = orderInfoModel.getCounts().getRoomCount().compareTo(newHotelOrder.getNightCount()*newHotelOrder.getBookCount());
										if(rooms < 0){
											saleOrder.setOrderChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.BEFORE_RESIDE));
											buyOrder.setBuyChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.BEFORE_RESIDE));
											//更新mysql酒店订单状态
											newHotelOrder.setOrderStatus(OwnerOrderStatus.BEFORE_RESIDE.getKey());
											newHotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
										}
										if(rooms == 0){
											saleOrder.setOrderChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.RESIDE_OK));
											buyOrder.setBuyChildStatus(OrderStatusUtils.getStatus(OwnerOrderStatus.RESIDE_OK));
											//更新mysql酒店订单状态
											newHotelOrder.setOrderStatus(OwnerOrderStatus.RESIDE_OK.getKey());
											newHotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
										}
									}
									String factName = null;
									for(PassengerModel pm : orderInfoModel.getPassengers()){
										if (factName == null || "".equals(factName)) {
											factName = pm.getName();
						                } else {
						                    if (pm.getName() != null && !"".equals(pm.getName())) {
						                    	factName = factName + "," + pm.getName();
						                    }
						                }
									}
									newHotelOrder.setFactGuestName(factName);
									newHotelOrder.setFactArriveTime(simple.parse(orderInfoModel.getStartTime()));
									newHotelOrder.setFactLeaveTime(simple.parse(orderInfoModel.getEndTime()));
									
									newHotelOrder.setFactNightCount(factNight);
									newHotelOrder.setFactProCount(priceFraction);
									
									/*BigDecimal hoteelDivide = hotelOrder.getTotalPrice().divide(new BigDecimal(hotelOrder.getNightCount()*hotelOrder.getBookCount()), 2, BigDecimal.ROUND_HALF_UP);
									BigDecimal tcInfoDivide = (orderInfoModel.getOrigin()).divide(new BigDecimal(roomCount),2, BigDecimal.ROUND_HALF_UP);
									int compareToPrice = hoteelDivide.compareTo(tcInfoDivide);*/
									//if(compareToPrice == 0){
										BigDecimal factmultiply = orderInfoModel.getOrigin().multiply(hotelOrder.getTotalRefund().divide(hotelOrder.getTotalPrice(), 2, BigDecimal.ROUND_HALF_UP));
										newHotelOrder.setFactTotalRefund(factmultiply);
									//}
									newHotelOrder.setFactTotalPrice(orderInfoModel.getOrigin());
									
									saleOrderService.create(agent, saleOrder);
									buyOrderService.create(agent, buyOrder);
									hotelOrderMapper.insertSelective(newHotelOrder);
								}
								hotelOrder.setRelateOrderNo(tcPushOrderInfo.getNewOrderId());
								hotelOrderMapper.updateById(hotelOrder);
								oldLogRecord.setCreateTime(new Date());
								oldLogRecord.setDesc(des);
								oldLogRecord.setOptName("供应商");
								iLogService.insert(oldLogRecord);
							}else{
								throw new GSSException("更新状态信息异常", "0191", "新订单号为空");
							}
						}
					}
					if(flag.equals(1)) {
						LogRecord.setCreateTime(new Date());
						LogRecord.setDesc(des);
						LogRecord.setOptName("供应商");
						iLogService.insert(LogRecord);
					}
				}else{
					throw new GSSException("更新状态信息异常", "0191", "查询订单号为空");
				}
			}
		} catch (Exception e) {			
			logger.error("更新订单状态异常：",e);
			throw new GSSException("更新状态信息异常", "0111", "更新状态信息异常"+e.getMessage());
		}
		return true;
	}

	@Override
	public Page<HotelOrder> queryCustomerOrderList(Page<HotelOrder> page, RequestWithActor<HotelOrderVo> pageRequest) throws GSSException{
		logger.info("根据条件查询酒店订单列表开始,入参:" + JSONObject.toJSONString(pageRequest));
        Agent agent = pageRequest.getAgent();
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isNullOrEmpty(agent)) {
            logger.error("当前操作用户不能为空");
            throw new GSSException("查询酒店订单列表", "1010", "当前操作用户不能为空");
        }
        if(StringUtil.isNullOrEmpty(pageRequest.getEntity())){
        	logger.error("查询对象不能为空");
            throw new GSSException("查询酒店订单列表", "1110", "入参查询对象不能为空");
        }
        if(pageRequest.getEntity().getSubCustomer()){
        	List<Customer> customerList = customerService.getSubCustomerByCno(agent, pageRequest.getEntity().getCustomerNo());
        	List<Long> list = new ArrayList<Long>();
        	if(StringUtil.isNotNullOrEmpty(customerList)){
        		for(Customer cu : customerList){
            		list.add(cu.getCustomerNo());
            	}
        		map.put("list", list);
        		pageRequest.getEntity().setCustomerNo(null);
        	}
        	/*List<Long> list = new ArrayList<Long>();
        	list.add(401611190940500199L);
        	map.put("list", list);*/
        }
        //如果是运营平台账号，可查看全部订单，否则只能查看当前账号自己创建的订单
        if (StringUtil.isNotNullOrEmpty(agent.getType()) && agent.getType() != 0L) { //不是运营平台账号
            if (StringUtil.isNullOrEmpty(pageRequest.getEntity())) {
                HotelOrderVo hotelOrderVo = new HotelOrderVo();
                hotelOrderVo.setOwner(pageRequest.getAgent().getOwner());
               // hotelOrderVo.setCreator(pageRequest.getAgent().getAccount());
                pageRequest.setEntity(hotelOrderVo);
            } else {
                try {
                	pageRequest.getEntity().setOwner(pageRequest.getAgent().getOwner());
                   // pageRequest.getEntity().setCreator(pageRequest.getAgent().getAccount());
                    Field[] field= pageRequest.getEntity().getClass().getDeclaredFields();
    				for(int i=0; i< field.length;i++){
    					String name= field[i].getName();
    					String upName = name.substring(0,1).toUpperCase()+name.substring(1);
    					//Object type = field[i].getGenericType();
    						Method m = pageRequest.getEntity().getClass().getMethod("get"+upName);
    						Object value =  m.invoke(pageRequest.getEntity());
    						 if(value != null){
    							 map.put(name, value);
    						 }
    				}
				} catch (Exception e) {
					logger.error("查询报错"+e);
				}
            }
        }
        List<HotelOrder> hotelOrderList = hotelOrderMapper.queryCustomerOrderList(page, map);
        page.setRecords(hotelOrderList);
        return page;
	}

	@Override
	public Boolean findHotelOrderOne(String orderNo, String remark) {
			try {
				HotelOrder hotelOrder = hotelOrderMapper.getOrderByNo(orderNo);
				
				if(StringUtil.isNotNullOrEmpty(hotelOrder)){
					hotelOrder.setRemark(remark);
				}else{
					throw new GSSException("查询酒店订单列表", "1110", "查不到此订单");
				}
				hotelOrderMapper.updateById(hotelOrder);
			} catch (Exception e) {
				logger.error("插入备注出错"+e);
				throw new GSSException("查询酒店订单列表", "1110", "插入备注出错");
			}
			return true;
	}

	@Override
	public void sendWebSocketByMQ(String msg) {
		SocketDO sd = JsonUtil.toBean(msg, SocketDO.class);
		mqSender.send("gss-websocket-exchange", "hotelremark", sd);
		logger.info("发送websocket消息到mq:"+msg);
		
	}

	@Override
	public Boolean updateOrderStatus(Agent agent, String orderNo) {
		logger.info("手动更新订单状态, 入参: "+orderNo);
		HotelOrder hotelOrder = hotelOrderMapper.getOrderByNo(orderNo);
		LogRecord LogRecord=new LogRecord();
		String des= "手动同步没有更新";
		Boolean flag = false;
		try {
			if(StringUtil.isNotNullOrEmpty(hotelOrder)){
				LogRecord.setBizCode("HOL-Order");
				LogRecord.setTitle("手动更新酒店订单状态");
				LogRecord.setBizNo(hotelOrder.getHotelOrderNo());
				OrderDetailInfoReq orderDetailInfoReq =new OrderDetailInfoReq();
				orderDetailInfoReq.setOrderId(orderNo);
				OrderInfomationDetail orderInfomationDetail = orderDetailInfo(agent, orderDetailInfoReq);
				if(StringUtil.isNullOrEmpty(orderInfomationDetail.getOrderInfos())){
					throw new GSSException("更新状态信息异常", "0110", "获取订单详情为空");
				}
				if(StringUtil.isNotNullOrEmpty(orderInfomationDetail.getOrderInfos())){
					OrderInfoModel orderInfoModel = orderInfomationDetail.getOrderInfos().get(0);
					
					if(orderInfoModel.getOrderStatus().equals(TcOrderStatus.ALREADY_TC_CONFIRM.getKey())){
						if(hotelOrder.getOrderStatus().equals(OwnerOrderStatus.ORDER_ONGOING.getKey())){
							des = "订单号"+orderNo +",订单状态由"+ OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.ALREADY_CONRIRM.getValue();
							//更新销售单和采购单状态
							updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.ALREADY_CONRIRM));
							//更新mysql酒店订单状态
							hotelOrder.setOrderStatus(OwnerOrderStatus.ALREADY_CONRIRM.getKey());
							hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_TC_CONFIRM.getKey());
							flag = true;
						}
					}
					
					if(orderInfoModel.getOrderStatus().equals(TcOrderStatus.CONFIRM_NOT_TO_ROOM.getKey())){
						if(hotelOrder.getOrderStatus().equals(OwnerOrderStatus.ORDER_ONGOING.getKey()) || hotelOrder.getOrderStatus().equals(OwnerOrderStatus.ALREADY_CONRIRM.getKey()) || hotelOrder.getOrderStatus().equals(OwnerOrderStatus.BOOK_OK.getKey())){
							des = "订单号"+orderNo +",订单状态由"+ OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.NO_RESIDE.getValue();
							//更新销售单和采购单状态
							updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.NO_RESIDE));
							//更新mysql酒店订单状态
							hotelOrder.setFactTotalRefund(new BigDecimal(0));
							hotelOrder.setOrderStatus(OwnerOrderStatus.NO_RESIDE.getKey());
							hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_NOT_TO_ROOM.getKey());
							flag = true;
						}
					}
					
					if(orderInfoModel.getOrderStatus().equals(TcOrderStatus.CONFIRM_TO_ROOM.getKey())){
						Integer factNight = 0;
						try{
							factNight = DateUtil.daysBetween(orderInfoModel.getStartTime(), orderInfoModel.getEndTime());
				        }catch(ParseException e){
				        	e.printStackTrace();
				        	throw new GSSException("更新状态信息异常", "0192", "根据消费日期计算天数异常");
				        }
						Integer roomCount = orderInfoModel.getCounts().getRoomCount();
						/*Integer days = orderInfoModel.getCounts().getDays();
						Integer proCount = roomCount/days;
						Integer factProCount = roomCount/factNight;*/
						/*if(hotelOrder.getOrderStatus().equals(OwnerOrderStatus.ORDER_ONGOING.getKey()) 
								|| hotelOrder.getOrderStatus().equals(OwnerOrderStatus.ALREADY_CONRIRM.getKey()) 
								|| hotelOrder.getOrderStatus().equals(OwnerOrderStatus.BOOK_OK.getKey())
								|| (!(new Date().before(hotelOrder.getDepartureDate())) &&  hotelOrder.getOrderStatus().equals(OwnerOrderStatus.RESIDE_ONGOING.getKey()))
								|| (!proCount.equals(factProCount))
						){*/
							int startTime = simple.format(hotelOrder.getArrivalDate()).compareTo(orderInfoModel.getStartTime());
							int endTime   = simple.format(hotelOrder.getDepartureDate()).compareTo(orderInfoModel.getEndTime());
							if(endTime > 0){
								des = "订单号"+orderNo +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.BEFORE_RESIDE.getValue();
								//更新销售单和采购单状态
								updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.BEFORE_RESIDE));
								//更新mysql酒店订单状态
								hotelOrder.setOrderStatus(OwnerOrderStatus.BEFORE_RESIDE.getKey());
								hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
							}else if(endTime < 0){
								des = "订单号"+orderNo +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.AFTER_RESIDE.getValue();
								//更新销售单和采购单状态
								updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.AFTER_RESIDE));
								//更新mysql酒店订单状态
								hotelOrder.setOrderStatus(OwnerOrderStatus.AFTER_RESIDE.getKey());
								hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
							}else if(startTime == 0 && endTime == 0){
								int rooms = orderInfoModel.getCounts().getRoomCount().compareTo(hotelOrder.getNightCount()*hotelOrder.getBookCount());
								if(rooms < 0){
									des = "订单号"+orderNo +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.BEFORE_RESIDE.getValue();
									//更新销售单和采购单状态
									updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.BEFORE_RESIDE));
									//更新mysql酒店订单状态
									hotelOrder.setOrderStatus(OwnerOrderStatus.BEFORE_RESIDE.getKey());
									hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
								}else{
									des = "订单号"+orderNo +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.RESIDE_OK.getValue();
									//更新销售单和采购单状态
									updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.RESIDE_OK));
									//更新mysql酒店订单状态
									hotelOrder.setOrderStatus(OwnerOrderStatus.RESIDE_OK.getKey());
									hotelOrder.setTcOrderStatus(TcOrderStatus.CONFIRM_TO_ROOM.getKey());
								}
							}
							String factName = null;
							for(PassengerModel pm : orderInfoModel.getPassengers()){
								if (factName == null || "".equals(factName)) {
									factName = pm.getName();
				                } else {
				                    if (pm.getName() != null && !"".equals(pm.getName())) {
				                    	factName = factName + "," + pm.getName();
				                    }
				                }
							}
							hotelOrder.setFactGuestName(factName);
							hotelOrder.setFactArriveTime(simple.parse(orderInfoModel.getStartTime()));
							hotelOrder.setFactLeaveTime(simple.parse(orderInfoModel.getEndTime()));
							hotelOrder.setFactNightCount(factNight);
							
							BigDecimal hoteelDivide = hotelOrder.getTotalPrice().divide(new BigDecimal(hotelOrder.getNightCount()*hotelOrder.getBookCount()), 2, BigDecimal.ROUND_HALF_UP);
							BigDecimal tcInfoDivide = (orderInfoModel.getOrigin()).divide(new BigDecimal(roomCount),2, BigDecimal.ROUND_HALF_UP);
							int compareToPrice = hoteelDivide.compareTo(tcInfoDivide);
							if(compareToPrice == 0){
								BigDecimal multiply = orderInfoModel.getOrigin().multiply(hotelOrder.getTotalRefund().divide(hotelOrder.getTotalPrice(), 2, BigDecimal.ROUND_HALF_UP));
								hotelOrder.setFactTotalRefund(multiply);
							}
							
							hotelOrder.setFactTotalPrice(orderInfoModel.getOrigin());
							Integer priceFraction = 0;
							if(StringUtil.isNotNullOrEmpty(orderInfoModel.getResources())){
								priceFraction = orderInfoModel.getResources().get(0).getPriceFraction();
								String longValue = orderInfoModel.getResources().get(0).getProductUniqueId();
								hotelOrder.setProductUniqueId(longValue);
								for(ResourceModel resource : orderInfoModel.getResources()){
									if(StringUtils.isNotEmpty(resource.getSupplierConfirmNumber())){
										hotelOrder.setSupplierNumber(resource.getSupplierConfirmNumber());
									}
									if(resource.getPriceFraction().compareTo(priceFraction) > 0){
										priceFraction = resource.getPriceFraction();
									}
								}
								hotelOrder.setFactProCount(priceFraction);
							}
							//当关联订单号为空说明这个订单不为补单订单,更新预定时的房间数和晚数为最新没有影响
							if(StringUtils.isEmpty(hotelOrder.getRelateOrderNo())){
								hotelOrder.setBookCount(priceFraction);
								hotelOrder.setNightCount(factNight);
							}
							if(StringUtil.isNullOrEmpty(hotelOrder.getEachNightPrice()) || StringUtil.isNullOrEmpty(hotelOrder.getBreakfastCount())){
								String eachNightPrice = null;
								String breakfastNum = null;
								//ProInfoDetail proInfoDetail=tCHotelSupplierService.queryListByProductUniqueId(hotelOrder.getProductUniqueId(), ProInfoDetail.class);
								ProInfoDetail proInfoDetail=null;
								//ProInfoDetail proInfoDetail=tCHotelSupplierService.queryListByProductUniqueId(hotelOrder.getProductUniqueId(), ProInfoDetail.class);
								 AssignDateHotel assignDateHotel = tCHotelSupplierService.queryDetailById(Long.valueOf(hotelOrder.getHotelCode()), AssignDateHotel.class);
								if(StringUtil.isNotNullOrEmpty(assignDateHotel) && StringUtil.isNotNullOrEmpty(assignDateHotel.getProInfoDetailList())) {
									for(ProInfoDetail pinfo : assignDateHotel.getProInfoDetailList()) {
										if(pinfo.getProductUniqueId().equals(hotelOrder.getProductUniqueId())) {
											proInfoDetail = pinfo;
											break;
										}
									}
								}
								if(StringUtil.isNotNullOrEmpty(proInfoDetail)){
									for (Map.Entry<String, ProSaleInfoDetail> entry : proInfoDetail.getProSaleInfoDetails().entrySet()) {
	        	        				int begincompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(orderInfoModel.getStartTime());
	        	        				int endCompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(orderInfoModel.getEndTime());
	        	        				if(begincompare >= 0 && endCompare < 0){
	    	        						Integer price= entry.getValue().getDistributionSalePrice();
	    	        						Integer breakNum = entry.getValue().getBreakfastNum();
	    	        						if(StringUtil.isNotNullOrEmpty(price)){
	    	        							if(eachNightPrice == null || "".equals(eachNightPrice)){
	        	        							eachNightPrice = price.toString();
	        	        			            }else {
	        	        			                eachNightPrice = eachNightPrice + "," + price.toString();
	        	        			            }
	    	        						}
	    	        						if(StringUtil.isNotNullOrEmpty(breakNum)){
	    	        							if(breakfastNum == null || "".equals(breakfastNum)){
	        	        							breakfastNum = breakNum.toString();
	        	        			            }else {
	        	        			                breakfastNum = breakfastNum + "," + breakNum.toString();
	        	        			            }
	    	        						}
	                					}
	                				}
									hotelOrder.setEachNightPrice(eachNightPrice);
									hotelOrder.setBreakfastCount(breakfastNum);
								}else{
									logger.error("补单订单推送，拉取每日价格异常");
								}
							}
							flag = true;
						//}
					}
					
					if(orderInfoModel.getOrderStatus().equals(TcOrderStatus.ALREADY_CANCEL.getKey())){
						if(hotelOrder.getOrderStatus().equals(OwnerOrderStatus.ORDER_ONGOING.getKey()) || hotelOrder.getOrderStatus().equals(OwnerOrderStatus.CANCEL_ONGOING.getKey()) || hotelOrder.getOrderStatus().equals(OwnerOrderStatus.ALREADY_CONRIRM.getKey()) || hotelOrder.getOrderStatus().equals(OwnerOrderStatus.BOOK_OK.getKey())){
							des = "订单号"+orderNo +",订单状态由"+OwnerOrderStatus.keyOf(hotelOrder.getOrderStatus()).getValue()+"变成:"+ OwnerOrderStatus.CANCEL_OK.getValue();
							//更新销售单和采购单状态
							updateSaleAndBuyOrderStatus(agent, hotelOrder.getSaleOrderNo(), hotelOrder.getBuyOrderNo(), OrderStatusUtils.getStatus(OwnerOrderStatus.CANCEL_OK));
							//更新mysql酒店订单状态
							hotelOrder.setOrderStatus(OwnerOrderStatus.CANCEL_OK.getKey());
							hotelOrder.setTcOrderStatus(TcOrderStatus.ALREADY_CANCEL.getKey());
							flag = true;
						}
					}
					hotelOrder.setModifier(agent.getAccount());
					hotelOrder.setModifyTime(new Date());
					hotelOrderMapper.updateById(hotelOrder);
					
					LogRecord.setCreateTime(new Date());
					LogRecord.setDesc(des);
					LogRecord.setOptName(agent.getAccount());
					iLogService.insert(LogRecord);
				}
			}else{
				throw new GSSException("手动更新状态信息异常", "0191", "查询订单号为空");
			}
		} catch (Exception e) {
			logger.error("手动更新状态信息异常"+e);
			throw new GSSException("手动更新状态信息异常", "0191", e.getMessage());
		}
		return flag;
	}

	@Override
	public Boolean updateRefund(Agent agent, String orderNo, BigDecimal newRefund) {
		logger.info("手动实际返佣, 入参: "+orderNo);
		HotelOrder hotelOrder = hotelOrderMapper.getOrderByNo(orderNo);
		LogRecord LogRecord=new LogRecord();
		String des= "";
		Boolean flag = false;
		try {
			if(StringUtil.isNotNullOrEmpty(hotelOrder)){
				hotelOrder.setFactTotalRefund(newRefund);
				hotelOrder.setModifier(agent.getAccount());
				hotelOrder.setModifyTime(new Date());
				hotelOrderMapper.updateById(hotelOrder);
				
				des = "订单号"+orderNo +",实际返佣由: "+hotelOrder.getFactTotalRefund().toString()+"变成: "+ newRefund.toString()+", 修改人: "+agent.getAccount();
				LogRecord.setBizCode("HOL-Order");
				LogRecord.setTitle("手动修改实际返佣");
				LogRecord.setBizNo(hotelOrder.getHotelOrderNo());
				LogRecord.setCreateTime(new Date());
				LogRecord.setDesc(des);
				LogRecord.setOptName(agent.getAccount());
				iLogService.insert(LogRecord);
				
				flag = true;
			}else{
				throw new GSSException("手动修改实际返佣", "0191", "查询该订单失败");
			}
		} catch (Exception e) {
			logger.error("手动修改实际返佣异常"+e);
			throw new GSSException("手动修改实际返佣", "0191", "查询该订单失败"+e.getMessage());
		}
		return flag;
	}

	@Override
	public Boolean pushToBeforeShow(Agent agent, String orderNo) {
		logger.info("手动推送修改订单前台展示, 入参: "+orderNo);
		Boolean flag = false;
		LogRecord LogRecord=new LogRecord();
		String des= "";
		try {
			HotelOrder hotelOrder = hotelOrderMapper.getOrderByNo(orderNo);
			if(StringUtil.isNotNullOrEmpty(hotelOrder)){
				hotelOrder.setLocker(0L);
				hotelOrder.setLockTime(new Date());
				hotelOrderMapper.updateById(hotelOrder);
				
				des = "订单号"+orderNo +", 由 "+agent.getAccount()+" 推送到前台展示";
				LogRecord.setBizCode("HOL-Order");
				LogRecord.setTitle("手动推送订单到前台展示");
				LogRecord.setBizNo(hotelOrder.getHotelOrderNo());
				LogRecord.setCreateTime(new Date());
				LogRecord.setDesc(des);
				LogRecord.setOptName(agent.getAccount());
				iLogService.insert(LogRecord);
				
				flag = true;
			}else{
				throw new GSSException("手动推送修改订单前台展示", "0291", "手动推送修改订单前台展示异常, 该订单不存在");
			}
			
		} catch (Exception e) {
			logger.error("手动推送修改订单前台展示异常"+e);
			throw new GSSException("手动推送修改订单前台展示", "0291", "手动推送修改订单前台展示异常"+e.getMessage());
		}
		return flag;
	}

	@Override
	public void createErrorOrder(Agent agent, HolErrorOrder holErrorOrder) throws GSSException {
		hotelErrorOrderMapper.insertSelective(holErrorOrder);
	}


	
}
