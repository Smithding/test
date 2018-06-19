package com.tempus.gss.product.hol.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.dps.order.service.SaleService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.log.entity.LogRecord;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.CreatePlanAmountVO;
import com.tempus.gss.order.entity.GoodsBigType;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.entity.TransationOrder;
import com.tempus.gss.order.service.IBuyOrderService;
import com.tempus.gss.order.service.IPlanAmountRecordService;
import com.tempus.gss.order.service.ISaleOrderService;
import com.tempus.gss.order.service.ITransationOrderService;
import com.tempus.gss.product.hol.api.entity.request.tc.OrderCreateReq;
import com.tempus.gss.product.hol.api.entity.response.HolErrorOrder;
import com.tempus.gss.product.hol.api.entity.response.HotelOrder;
import com.tempus.gss.product.hol.api.entity.response.tc.OwnerOrderStatus;
import com.tempus.gss.product.hol.api.entity.response.tc.TcOrderStatus;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.BookOrderParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.CreateOrderReq;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.BookOrderResponse;
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
import com.tempus.gss.system.service.IMaxNoService;
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

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public HotelOrder createOrder(Agent agent, CreateOrderReq orderReq, OrderCreateReq orderCreateReq) {
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
			dateStartDate = sdf.parse(orderReq.getCheckInTime());
			Date departDate = sdf.parse(orderReq.getCheckOutTime());
			Calendar cal = Calendar.getInstance();
			cal.setTime(departDate);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			departureDate = cal.getTime();
			daysBetween = com.tempus.gss.ops.util.DateUtil.daysBetween(orderReq.getCheckInTime(),
					orderReq.getCheckOutTime());
		} catch (ParseException e) {
			logger.error("入住日期/离店日期 格式错误,请重新输入！入住日期/离店日期的正确格式为(yyyy-MM-dd)", e);
			throw new GSSException("创建酒店订单", "0129",
					"入住日期/离店日期 格式错误,请重新输入！入住日期/离店日期的正确格式为(yyyy-MM-dd)" + e.getMessage());
		}

		HotelOrder hotelOrder = new HotelOrder();

		// 查询酒店房间价格
		QueryHotelParam query = new QueryHotelParam();
		query.setCheckInTime(orderReq.getCheckInTime());
		query.setCheckOutTime(orderReq.getCheckOutTime());
		query.setCityCode(String.valueOf(orderReq.getCityId()));
		query.setHotelId(orderReq.getHotelId());
		List<RoomPriceItem> roomPriceList = bqyHotelInterService.queryHotelRoomPrice(query);
		// 房型ID
		Long roomTypeId = orderReq.getHotelRoomId();
		//房间单价 (房间每天的价格相加)
		BigDecimal unitPrice = null;
		// 总价格
		BigDecimal newTotalPrice = new BigDecimal(0.0);
		// 每天的价格记录
		String eachNightPrice = null;
		//供应商ID
		String supplierId = null;
		//预定检查类型
		String ratePlanCategory = null;
		for (RoomPriceItem roomPriceItem : roomPriceList) {
			BaseRoomInfo baseRoomInfo = roomPriceItem.getBaseRoomInfo();
			if (roomTypeId.equals(Long.valueOf(baseRoomInfo.getRoomTypeID()))) {// 判断房型是否一致
				List<RoomInfoItem> roomInfoList = roomPriceItem.getRoomInfo();
				for (RoomInfoItem roomInfoItem : roomInfoList) {
					if (orderReq.getProductId().equals(roomInfoItem.getRoomID())) {// 判断房间ID是否一致
						orderReq.setHotelRoomName(baseRoomInfo.getRoomName());
						orderReq.setProductName(roomInfoItem.getRoomName());
						RoomPriceInfo roomPriceInfo = roomInfoItem.getRoomPriceInfo();
						//预定检查类型
						ratePlanCategory = roomPriceInfo.getRatePlanCategory();
						//供应商ID
						supplierId = roomInfoItem.getSupplierId();
						//平均价格
						unitPrice = new BigDecimal(roomPriceInfo.getAveragePrice().getAmount());
						List<RoomPriceDetail> priceDetailList = roomPriceInfo.getRoomPriceDetail();
						for (RoomPriceDetail roomPriceDetail : priceDetailList) {
							String oneDayPrice = roomPriceDetail.getPrice().getAmount();
							// 计算一间房的价格
							newTotalPrice = newTotalPrice.add(new BigDecimal(oneDayPrice));
							// 记录每天的价格
							if (eachNightPrice == null || "".equals(eachNightPrice)) {
								eachNightPrice = oneDayPrice;
							} else {
								eachNightPrice = eachNightPrice + "," + oneDayPrice;
							}
						}
						// 产品名称
						hotelOrder.setSupPriceName(roomInfoItem.getRoomName());

						// 床型
						RoomBedTypeInfo roomBedTypeInfo = roomInfoItem.getRoomBedTypeInfo();
						if ("T".equals(roomBedTypeInfo.getHasKingBed())) {
							hotelOrder.setBedTypeName("大床");
						} else if ("T".equals(roomBedTypeInfo.getHasTwinBed())) {
							hotelOrder.setBedTypeName("双床");
						} else if ("T".equals(roomBedTypeInfo.getHasSingleBed())) {
							hotelOrder.setBedTypeName("单人床");
						}
					}
				}
			}
		}
		if (unitPrice == null) {
			logger.error("酒店房间单价为空!");
			throw new GSSException("创建酒店订单", "0107", "酒店单价为空！");
		}
		orderReq.setUnitPrice(unitPrice);
		newTotalPrice = newTotalPrice.multiply(new BigDecimal(orderReq.getBookNumber()));
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
		String[] passengerArr = passengers.split(",");

		// 创建酒店订单
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
		hotelOrder.setHotelName(orderReq.getHotelName());
		hotelOrder.setOrderType(2);	//设置为2代表订单属于BQY (1.TC; 2.BQY)
		hotelOrder.setContactName(orderCreateReq.getLinkManName());
		hotelOrder.setContactNumber(orderCreateReq.getLinkManMobile());
		hotelOrder.setArrivalDate(dateStartDate);
		hotelOrder.setDepartureDate(departureDate);
		hotelOrder.setTotalPrice(newTotalPrice);
		hotelOrder.setNightCount(daysBetween);
		hotelOrder.setTransationOrderNo(orderCreateReq.getSaleOrder().getTransationOrderNo());
		hotelOrder.setTotalRefund(orderCreateReq.getTotalRebateRateProfit());
		hotelOrder.setFactTotalRefund(new BigDecimal(0));
		hotelOrder.setGuestName(passengers);
		hotelOrder.setGuestMobile(orderReq.getMobile());
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
		hotelOrder.setProName(orderCreateReq.getProName());
		hotelOrder.setProId(orderCreateReq.getProId());
		hotelOrder.setBreakfastCount(orderCreateReq.getBreakfastCount());
		hotelOrderMapper.insertSelective(hotelOrder);

		//试预订
		BookOrderParam bookOrderParam = new BookOrderParam();
		bookOrderParam.setGuestCount(passengerArr.length);
		bookOrderParam.setCheckInTime(orderReq.getCheckInTime());
		bookOrderParam.setCheckOutTime(orderReq.getCheckOutTime());
		bookOrderParam.setHotelId(orderReq.getHotelId());
		bookOrderParam.setLateArrivalTime(orderCreateReq.getArriveHotelTime());
		bookOrderParam.setQuantity(orderReq.getBookNumber());
		bookOrderParam.setRatePlanCode(orderReq.getProductId());

		BookOrderResponse bookOrderResult = bqyHotelInterService.isBookOrder(bookOrderParam);
		//TODO 暂时将此代码注释要打开
		/*if (!bookOrderResult.getCanBook()) {
			logger.error("酒店房间不可预订!");
			throw new GSSException("创建酒店订单失败", "0132", "酒店房间不可预订!");
		}
		// 验证房间数是否充足
		int availableQuantity = bookOrderResult.getAvailableQuantity(); // 剩余房间数
		if (availableQuantity < orderReq.getBookNumber()) {
			logger.error("酒店房间数量不足!");
			throw new GSSException("创建酒店订单失败", "0132", "酒店房间数量不足!");
		}*/
		
		//前台传入的价格
		BigDecimal beforeTotalPrice = orderCreateReq.getBeforeTotalPrice();
		
		//验证价格是否一致
		//BigDecimal checkPrice = bookOrderResult.getCheckPrice();
		
		if (beforeTotalPrice.compareTo(newTotalPrice) != 0) {
			logger.error("传入的总价与最新的总价不一致");
			throw new GSSException("创建酒店订单失败", "0132", "价格不一致");
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
		//可以不传
		orderReq.setChannelType(2);
		
		String orderNo = bqyHotelInterService.createOrder(orderReq);

		//TODO 判断条件要切换回来(返回0订单创建失败)
		//if (StringUtils.isNotBlank(orderNo) && !"0".equals(orderNo)) {
		if (StringUtils.isNotBlank(orderNo)) {
			int a = (int) (Math.random() * 100);
			orderNo = sdf.format(new Date());
			//订单创建成功更新订单表中数据
			hotelOrder.setHotelOrderNo(orderNo + a);
			//0=>下单失败，1=>下单成功，2=>下单成功，支付失败，3=>下单成功，支付成功
			hotelOrder.setResultCode("1");
			hotelOrder.setTcOrderStatus(TcOrderStatus.WAIT_PAY.getKey());
			hotelOrderMapper.updateById(hotelOrder);
			return hotelOrder;
		}
		return null;
	}
	
	@Override
//	public MssBaseResult<HotelOrder> hotelReserve(Agent agent, OrderCreateReq orderCreateReq) {
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
				HotelOrder hotelOrder = createOrder(agent, orderReq, orderCreateReq);//createHotelOrder(agent, createHotel);
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
					return orderCreateReq;
				}
				/*else{
					mbr.setStatus(0);
					mbr.setError("酒店预订，创建销售单返回false");
				}*/
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

}
