package com.tempus.gss.product.hol.api.entity.response;

import com.ibm.icu.math.BigDecimal;

import java.io.Serializable;

;

public class BdOrderLog implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */

    private String orderNumber;

    /**
     *
     */
    private String action;

    /**
     *
     */
    private String actionTime;

    /**
     *
     */
    private String userId;

    /**
     *
     */
    private String remark;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
