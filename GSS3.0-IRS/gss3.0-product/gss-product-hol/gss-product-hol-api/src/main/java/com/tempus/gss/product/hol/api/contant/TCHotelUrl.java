package com.tempus.gss.product.hol.api.contant;

/**
 * TC酒店连接地址
 * @author kai.yang
 *
 */
public class TCHotelUrl {
	
	/**
	 * 3.1 获取同程所有分销至合作方的酒店 id 以及酒店包含的房型 id
	 */
	public static final String ALL_LIST_URL="http://api.lvcang.cn/zizhuyou/standard/1.0/ResourceIndexInfoList?style=json";
	
	/**
	 * 3.2 获取同程某一时间段内发生对应类型变更的酒店&及房型 id 列表
	 */
	public static final String ASSIGN_LIST_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/GetIncrementResourceList?style=json";
	
	/**
	 * 3.3 获取同程单个酒店及其房型、图片的基本信息
	 */
	public static final String SINGLE_HOL_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/GetResourceDetailInfo?style=json";
	
	/**
	 * 3.4 获取同程某一时间段/某个月的酒店价格和库存信息
	 */
	public static final String PRICE_REPO_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/ResourceSaleInfoCalendar?style=json";
	
	/**
	 * 3.5 合作方推送订单信息至同程，同程创建单预付酒店订单，并返回同程订单号
	 */
	public static final String CREATE_ORDER_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/SingleOrderCreate?style=json";
	
	/**
	 * 3.6 获取同程与合作方的订单详细信息(可选)
	 */
	public static final String ORDER_DETAIL_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/OrderSearch?style=json";
	
	/**
	 * 3.7 根据同程订单号取消同程订单（支付前取消）（可选）
	 */
	public static final String CANCEL_ORDER_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/OrderCancel?style=json";
	
	/**
	 * 3.8 根据同程订单号和订单金额将同程订单变更为已支付状态(可选)
	 */
	public static final String ORDER_PAY_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/OrderPay?style=json";
	
	/**
	 * 3.9 根据同程订单号向同程发起对应订单的退款申请
	 */
	public static final String REFUND_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/OrderRefundApply?style=json";
	
	/**
	 * 3.10 根据同程订单号获取此订单同程退改处理结果(可选)
	 */
	public static final String RES_REFUND_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/OrderRefundApplyQuery?style=json";
	
	/**
	 * 3.11 获取同程订单取消原因 id
	 */
	public static final String RES_ORDER_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/GetCancelReason?style=json";
	
	/**
	 * 3.13 合作方调用此接口获取某一时间段内的发生变更的订单号及变更信息(可选)
	 */
	public static final String ORDER_CHANGE_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/GetOrderIncrementInfo?style=json";
	
	/**
	 * 3.14 合作方调用此接口获取支持的信用卡信息
	 */
	public static final String CARD_INFO_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/GetCardTypeList?style=json";
	
	/**
	 * 3.15 可订检查查询
	 */
	public static final String CARD_SUPP_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/BookableCheck?style=json";
	
	/**
	 * 3.16 合作方调用此接口获取订单日志信息
	 */
	public static final String ORDER_LOG_URL= "http://api.lvcang.cn/zizhuyou/standard/1.0/GetOrderLogInfo?style=json";
	
}
