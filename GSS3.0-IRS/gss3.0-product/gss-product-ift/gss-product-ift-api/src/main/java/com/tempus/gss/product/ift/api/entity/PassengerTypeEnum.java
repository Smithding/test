package com.tempus.gss.product.ift.api.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2018/6/21.
 */
public enum PassengerTypeEnum {

    ADT("ADT", "ADT"),
    INF("INF", "INF"),
    CHD("CHD", "CHD"),
    CNN("CNN", "CHD"),
    MSTR("MSTR", "CHD"),
    MISS("MISS", "CHD");


    /** Key. */
    private String key;

    /** 名称. */
    private String value;

    /**
     * 构造方法
     * @param key
     * @param value
     */
    PassengerTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 根据KEY转换为对应枚举类别.
     * @param key
     * @return GoodsBigType
     */
    public static String getValue(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        for (PassengerTypeEnum item : PassengerTypeEnum.values()) {
            if (item.key.equals(key)) {
                return item.value;
            }
        }
        return key;
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
