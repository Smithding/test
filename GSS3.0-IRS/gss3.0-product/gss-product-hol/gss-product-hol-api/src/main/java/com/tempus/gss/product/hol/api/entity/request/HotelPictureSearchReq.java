package com.tempus.gss.product.hol.api.entity.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店图片搜索入参
 * Created by luofengjie on 2017/3/24.
 */
public class HotelPictureSearchReq extends BaseRequest {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 酒店编号
     */
    @JSONField(name = "HotelCode")
    private String hotelCode;

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }
}
