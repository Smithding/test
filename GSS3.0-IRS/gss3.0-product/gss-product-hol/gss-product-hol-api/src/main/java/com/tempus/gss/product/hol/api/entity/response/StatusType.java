package com.tempus.gss.product.hol.api.entity.response;


import com.tempus.gss.order.entity.enums.GoodsBigType;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 同程推送订单状态值
 * 
 */
public enum StatusType implements Serializable {
	/**
	 * 订单确认可出游
	 */
    ORDER_CONFIRM("CanTravel", "订单确认可出游"),
    /**
     * 取消订单成功
     */
    CANCEL_ORDER_CONFIRM("Cancel", "取消订单成功"),
    /**
     * 可支付
     */
    PAY("Pay", "可支付"),
    /**
     * 订单核房
     */
    CHECK_RESIDE("CheckHotel", "订单核房"),
    /**
     * 补单
     */
    REORDER("AdditionalOrder", "补单"),
    /**
     * 退款成功
     */
    ALREADY_REFUND("Refund", "退款成功");

    private String key;
    private String value;

    private StatusType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static StatusType format(String key) {
        if (null == key) {
            return null;
        } else {
        	StatusType[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
            	StatusType item = var1[var3];
                if (key.equals(item.key)) {
                    return item;
                }
            }

            return null;
        }
    }

    public static HashMap<Object, GoodsBigType> toMap() {
        HashMap map = new HashMap();
        StatusType[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
        	StatusType item = var1[var3];
            map.put(Integer.valueOf(item.key), item);
        }

        return map;
    }

    public static Object[] toArray() {
    	StatusType[] values = values();
        Object[] array = new Object[values.length];

        for (int i = 0; i < values.length; ++i) {
            array[i] = new Object[]{Integer.valueOf(values[i].key), values[i].value};
        }
        return array;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
