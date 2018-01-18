package com.tempus.gss.product.hol.api.entity.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店订单支付入参
 * Created by luofengjie on 2017/3/24.
 */
public class HotelOrderPayReq extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 千淘酒店订单编号
     */
    @JSONField(name = "OrderNo")
    private String orderNo;
    /**
     * 操作人编号
     */
    @JSONField(name = "UserID")
    private String userID;
    /**
     * 备注
     */
    @JSONField(name = "Remark")
    private String remark;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
