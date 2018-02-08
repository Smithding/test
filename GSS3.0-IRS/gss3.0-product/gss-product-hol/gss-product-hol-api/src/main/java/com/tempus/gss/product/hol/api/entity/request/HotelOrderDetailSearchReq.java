package com.tempus.gss.product.hol.api.entity.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店订单详情搜索入参
 * Created by luofengjie on 2017/3/24.
 */
public class HotelOrderDetailSearchReq extends BaseRequest {

	private static final long serialVersionUID = 1L;
	/**
     * 订单编号
     */
    @JSONField(name = "OrderNo")
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
