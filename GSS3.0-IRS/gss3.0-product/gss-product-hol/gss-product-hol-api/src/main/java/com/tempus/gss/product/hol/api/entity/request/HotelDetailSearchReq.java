package com.tempus.gss.product.hol.api.entity.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.vo.Agent;

/**
 * 酒店详情搜索入参
 * Created by luofengjie on 2017/3/24.
 */
public class HotelDetailSearchReq extends BaseRequest {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 酒店编号
     */
    @JSONField(name = "HotelCode")
    private String hotelCode;
    /**
     * 入住日期(yyyy-MM-dd)
     */
    @JSONField(name = "CheckinDate")
    private String checkinDate;
    /**
     * 离店日期(yyyy-MM-dd)
     */
    @JSONField(name = "CheckoutDate")
    private String checkoutDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
