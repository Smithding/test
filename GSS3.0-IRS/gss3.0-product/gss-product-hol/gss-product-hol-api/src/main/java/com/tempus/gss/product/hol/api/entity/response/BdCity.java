package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 城市
 *
 * @author Administrator
 */
public class BdCity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 城市编号
     */
    @JSONField(name = "CityCode")
    private String cityCode;

    /**
     * 城市名称
     */
    @JSONField(name = "CityName")
    private String cityName;

    /**
     * 拼音
     */
    @JSONField(name = "PinyinName")
    private String pinyinName;

    /**
     * 是否热点城市
     */
    @JSONField(name = "IsHot")
    private Integer isHot;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPinyinName() {
        return pinyinName;
    }

    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }
}
