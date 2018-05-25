package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.util.DateUtil;
/**
 * 增量同程订单信息返回实体类
 * @author kai.yang
 *
 */
public class OrderIncrementInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 同程订单号
	 */
	@JSONField(name = "OrderId")
	private String orderId;
	/**
	 * 增量类型 0：OrderCreate下单
	 *  1：OrderFlagChange订单状态变化
	 */
	@JSONField(name = "IncrementType")
	private String incrementType;
	/**
	 * 变化时间
	 */
	@JSONField(name = "IncrementDate")
	private String incrementDate;
	/**
	 * 变化原因
	 * 0：OrderCancel 订单取消
	 * 1：InventoryConfirm 库存确认
	 * 2：OrderPay 订单支付
	 * 3：OrderConfirm 订单库存确认
	 * 4：OrderRefund 订单申请退款
	 * 5：OrderRefundFinish 订单确认退款
	 * 6：OrderCreate 订单创建
	 */
	@JSONField(name = "IncrementReason")
	private String incrementReason;
	/**
	 * 订单状态
	 * 0：WaitInventoryConfirm 待确认库存
	 * 1：WaitForPay 待支付
	 * 2：Cancel 已取消
	 * 3：AlreadyPay 已支付
	 * 5：NeedTcConfirm 待同程确认
	 * 10：TcAlreadyConfirm 同程已确认
	 * 15：PartialRefund 部分退款(有人出游)
	 * 20：AllRefund 申请全额退款
	 * 25：FinishRefund 全额退款结束
	 * 30：PartialRefundConfirmed部分退款结束
	 * 35：Finished 已结算
	 */
	@JSONField(name = "OrderFlag")
	private String orderFlag;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getIncrementType() {
		return incrementType;
	}
	public void setIncrementType(String incrementType) {
		this.incrementType = incrementType;
	}
	public String getIncrementDate() {
		return incrementDate;
	}
	public void setIncrementDate(String incrementDate) {
		this.incrementDate = incrementDate;
	}
	public String getIncrementReason() {
		return incrementReason;
	}
	public void setIncrementReason(String incrementReason) {
		this.incrementReason = incrementReason;
	}
	public String getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}
	
}
