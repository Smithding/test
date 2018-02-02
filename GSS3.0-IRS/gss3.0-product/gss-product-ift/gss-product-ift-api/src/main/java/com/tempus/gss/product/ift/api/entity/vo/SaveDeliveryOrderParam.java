package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 保存配送订单参数
 * 
 * @Author <b>黄文</b> {@link wen.huang@tempus.cn}
 * @Date 2016年9月1日 下午4:41:30
 */

@SuppressWarnings("serial")
public class SaveDeliveryOrderParam implements Serializable{
	
	/** 订单来源 来自数据字典，如1主订单，2票证单，如果全部是主订单的话，可以取消该字段 */
	protected String orderSource;

	/** 订单ID */
	protected String orderId;

	/** 配送地址 */
	protected String deliveryAddr;

	/** 配送联系人 */
	protected String deliveryContact;

	/** 配送联系人电话 */
	protected String deliveryContactTel;

	/** 配送省 */
	protected String deliverProvince;

	/** 配送市 */
	protected String deliverCity;

	/** 要求配送时间 */
	protected String deliverExpectTime;

	/** 配送方式代码 取数据字典 */
	protected String deliverModeCode;
	
	/** 配送商品编号   取数据字典 */
	protected String deliverGoodID;

	/** 配送备注 */
	protected String deliveryRemark;

	/** 需要代收金额 */
	protected Double neadReceivedMoney;
	
	/** 创建人 */
	protected String creator;
	
	/**
	 * 配送内容
	 */
	protected List<Content> contentList;
	
	
	
	
	/**  
	 * 获取订单来源来自数据字典，如1主订单，2票证单，如果全部是主订单的话，可以取消该字段  
	 * @return orderSource 订单来源来自数据字典，如1主订单，2票证单，如果全部是主订单的话，可以取消该字段  
	 */
	public String getOrderSource() {
		return orderSource;
	}
	




	/**  
	 * 设置订单来源来自数据字典，如1主订单，2票证单，如果全部是主订单的话，可以取消该字段  
	 * @return orderSource 订单来源来自数据字典，如1主订单，2票证单，如果全部是主订单的话，可以取消该字段  
	 */
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	




	/**  
	 * 获取订单ID  
	 * @return orderId 订单ID  
	 */
	public String getOrderId() {
		return orderId;
	}
	




	/**  
	 * 设置订单ID  
	 * @return orderId 订单ID  
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	




	/**  
	 * 获取配送地址  
	 * @return deliveryAddr 配送地址  
	 */
	public String getDeliveryAddr() {
		return deliveryAddr;
	}
	




	/**  
	 * 设置配送地址  
	 * @return deliveryAddr 配送地址  
	 */
	public void setDeliveryAddr(String deliveryAddr) {
		this.deliveryAddr = deliveryAddr;
	}
	




	/**  
	 * 获取配送联系人  
	 * @return deliveryContact 配送联系人  
	 */
	public String getDeliveryContact() {
		return deliveryContact;
	}
	




	/**  
	 * 设置配送联系人  
	 * @return deliveryContact 配送联系人  
	 */
	public void setDeliveryContact(String deliveryContact) {
		this.deliveryContact = deliveryContact;
	}
	




	/**  
	 * 获取配送联系人电话  
	 * @return deliveryContactTel 配送联系人电话  
	 */
	public String getDeliveryContactTel() {
		return deliveryContactTel;
	}
	




	/**  
	 * 设置配送联系人电话  
	 * @return deliveryContactTel 配送联系人电话  
	 */
	public void setDeliveryContactTel(String deliveryContactTel) {
		this.deliveryContactTel = deliveryContactTel;
	}
	




	/**  
	 * 获取配送省  
	 * @return deliverProvince 配送省  
	 */
	public String getDeliverProvince() {
		return deliverProvince;
	}
	




	/**  
	 * 设置配送省  
	 * @return deliverProvince 配送省  
	 */
	public void setDeliverProvince(String deliverProvince) {
		this.deliverProvince = deliverProvince;
	}
	




	/**  
	 * 获取配送市  
	 * @return deliverCity 配送市  
	 */
	public String getDeliverCity() {
		return deliverCity;
	}
	




	/**  
	 * 设置配送市  
	 * @return deliverCity 配送市  
	 */
	public void setDeliverCity(String deliverCity) {
		this.deliverCity = deliverCity;
	}
	




	/**  
	 * 获取要求配送时间  
	 * @return deliverExpectTime 要求配送时间  
	 */
	public String getDeliverExpectTime() {
		return deliverExpectTime;
	}
	




	/**  
	 * 设置要求配送时间  
	 * @return deliverExpectTime 要求配送时间  
	 */
	public void setDeliverExpectTime(String deliverExpectTime) {
		this.deliverExpectTime = deliverExpectTime;
	}
	




	/**  
	 * 获取配送方式代码取数据字典  
	 * @return deliverModeCode 配送方式代码取数据字典  
	 */
	public String getDeliverModeCode() {
		return deliverModeCode;
	}
	




	/**  
	 * 设置配送方式代码取数据字典  
	 * @return deliverModeCode 配送方式代码取数据字典  
	 */
	public void setDeliverModeCode(String deliverModeCode) {
		this.deliverModeCode = deliverModeCode;
	}
	




	/**  
	 * 获取配送商品编号取数据字典  
	 * @return deliverGoodID 配送商品编号取数据字典  
	 */
	public String getDeliverGoodID() {
		return deliverGoodID;
	}
	




	/**  
	 * 设置配送商品编号取数据字典  
	 * @return deliverGoodID 配送商品编号取数据字典  
	 */
	public void setDeliverGoodID(String deliverGoodID) {
		this.deliverGoodID = deliverGoodID;
	}
	




	/**  
	 * 获取配送备注  
	 * @return deliveryRemark 配送备注  
	 */
	public String getDeliveryRemark() {
		return deliveryRemark;
	}
	




	/**  
	 * 设置配送备注  
	 * @return deliveryRemark 配送备注  
	 */
	public void setDeliveryRemark(String deliveryRemark) {
		this.deliveryRemark = deliveryRemark;
	}
	




	/**  
	 * 获取需要代收金额  
	 * @return neadReceivedMoney 需要代收金额  
	 */
	public Double getNeadReceivedMoney() {
		return neadReceivedMoney;
	}
	




	/**  
	 * 设置需要代收金额  
	 * @return neadReceivedMoney 需要代收金额  
	 */
	public void setNeadReceivedMoney(Double neadReceivedMoney) {
		this.neadReceivedMoney = neadReceivedMoney;
	}
	




	/**  
	 * 获取创建人  
	 * @return creator 创建人  
	 */
	public String getCreator() {
		return creator;
	}
	




	/**  
	 * 设置创建人  
	 * @return creator 创建人  
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	




	/**  
	 * 获取配送内容  
	 * @return contentList 配送内容  
	 */
	public List<Content> getContentList() {
		return contentList;
	}
	




	/**  
	 * 设置配送内容  
	 * @return contentList 配送内容  
	 */
	public void setContentList(List<Content> contentList) {
		this.contentList = contentList;
	}
	




	public static class Content implements Serializable{
		/** 配送内容标识 来源数据字典：如1行程单 2保险 */
		protected String deliveryContextFlag;

		/** 内容标识 标识这个配送物品,如行程单编号，保单编号等，方便配送员配送的时候打包 */
		protected String itemMark;

		/**  
		 * 获取配送内容标识来源数据字典：如1行程单2保险  
		 * @return deliveryContextFlag 配送内容标识来源数据字典：如1行程单2保险  
		 */
		public String getDeliveryContextFlag() {
			return deliveryContextFlag;
		}
		

		/**  
		 * 设置配送内容标识来源数据字典：如1行程单2保险  
		 * @return deliveryContextFlag 配送内容标识来源数据字典：如1行程单2保险  
		 */
		public void setDeliveryContextFlag(String deliveryContextFlag) {
			this.deliveryContextFlag = deliveryContextFlag;
		}
		

		/**  
		 * 获取内容标识标识这个配送物品如行程单编号，保单编号等，方便配送员配送的时候打包  
		 * @return itemMark 内容标识标识这个配送物品如行程单编号，保单编号等，方便配送员配送的时候打包  
		 */
		public String getItemMark() {
			return itemMark;
		}
		

		/**  
		 * 设置内容标识标识这个配送物品如行程单编号，保单编号等，方便配送员配送的时候打包  
		 * @return itemMark 内容标识标识这个配送物品如行程单编号，保单编号等，方便配送员配送的时候打包  
		 */
		public void setItemMark(String itemMark) {
			this.itemMark = itemMark;
		}
		

		
		
		
	}

}