package com.tempus.gss.product.hol.api.entity.vo.bqy;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public enum EnumOrderStatus implements Serializable {

    WAIT_PAY(1, "待支付"),

    WAIT_CONFIRM(2, "待确认"),

    ALREADY_CONRIRM(3, "已确认"),

    CANCEL_ONGOING(5, "退订中"),

    ALREADY_ONGOING(508, "已退订"),

    /**
     * 用户超时自动取消
     */
    CANCEL(9, "已取消");

    private Integer key;

    private String value;

    private EnumOrderStatus(Integer key,String value) {
        this.key=key;
        this.value=value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public static EnumOrderStatus keyOf(Integer index) {
        for (EnumOrderStatus key : values()) {
            if (key.getKey().equals(index)){
                return key;
            }
        }
        return null;
    }
}
