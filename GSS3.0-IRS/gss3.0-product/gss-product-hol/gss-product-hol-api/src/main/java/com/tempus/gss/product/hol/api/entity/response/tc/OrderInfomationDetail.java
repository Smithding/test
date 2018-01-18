package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 获取同程与合作方的订单详细信息
 * @author kai.yang
 *
 */
public class OrderInfomationDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 是否查询成功
	 */
	@JSONField(name = "IsSuccess")
	private Boolean isSuccess;
	/**
	 * 错误信息；成功消息为空
	 */
	@JSONField(name = "Message")
	private String message;
	/**
	 * 当前页码
	 */
	@JSONField(name = "PageIndex")
	private Integer pageIndex;
	/**
	 * 每页条数
	 */
	@JSONField(name = "PageSize")
	private Integer pageSize;
	/**
	 * 查询总条数
	 */
	@JSONField(name = "TotalCount")
	private Integer totalCount;
	/**
	 * 订单信息
	 */
	@JSONField(name = "OrderInfos")
	private List<OrderInfoModel> orderInfos;
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<OrderInfoModel> getOrderInfos() {
		return orderInfos;
	}
	public void setOrderInfos(List<OrderInfoModel> orderInfos) {
		this.orderInfos = orderInfos;
	}
	
}
