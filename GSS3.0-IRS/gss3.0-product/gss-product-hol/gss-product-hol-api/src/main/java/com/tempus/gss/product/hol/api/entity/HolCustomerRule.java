package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *  客户规则
 */
public class HolCustomerRule implements Serializable {
    private static final long serialVersionUID = -4488348428096140994L;

    /**
     * id 主键
     */
    private Long id;

    /**
     *  客户名称
     */
    private String customerName;

    /**
     *  登录名称
     */
    private String loginName;

    /**
     *  电话
     */
    private String telephone;

    /**
     *  支付类型
     */
    private String payType;

    /**
     *  更新时间
     */
    private String updateDate;

    /**
     *  修改人
     */
    private String modifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}
