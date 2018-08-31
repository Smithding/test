package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

/**
 * bqy时间范围枚举
 */
public enum EnumTimeType {

    /**
     * 十分钟
     */
    TEN_MINUTES("TenMinutes"),

    /**
     * 	二十分钟
     */
    TWENTY_MINUTES("TwentyMinutes"),

    /**
     * 	三十分钟
     */
    HALF_HOUR("Halfhour");

    private String key;

    EnumTimeType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
