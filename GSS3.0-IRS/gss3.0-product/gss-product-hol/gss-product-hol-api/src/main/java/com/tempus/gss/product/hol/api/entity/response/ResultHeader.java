package com.tempus.gss.product.hol.api.entity.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 千淘酒店信息实体
 * Created by luofengjie on 2017/3/24.
 */
public class ResultHeader implements Serializable {
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
     * 酒店名称
     */
    @JSONField(name = "HotelName")
    private String hotelName;
    /**
     * 酒店地址
     */
    @JSONField(name = "HotelAddress")
    private String hotelAddress;
    /**
     * 酒店最低价格
     */
    @JSONField(name = "MinPrice")
    private BigDecimal minPrice;
    /**
     * 主图片地址
     */
    @JSONField(name = "PictureUrl")
    private String pictureUrl;
    /**
     * 酒店价格信息
     */
    @JSONField(name = "ResultSupp")
    private ResultSupp resultSupp;
    /**
     * 酒店星级
     */
    @JSONField(name = "HotelLevel")
    private String hotelLevel;
    /**
     * 纬度
     */
    @JSONField(name = "Latitude")
    private String latitude;
    /**
     * 经度
     */
    @JSONField(name = "Longitude")
    private String longitude;
    /**
     * 电话
     */
    @JSONField(name = "PhoneNum")
    private String phoneNum;
    /**
     * 简短描述
     */
    @JSONField(name = "ShortDescription")
    private String shortDescription;
    /**
     * wifi
     */
    @JSONField(name = "FreeWIFI")
    private String freeWIFI;
    /**
     * 会议室
     */
    @JSONField(name = "BoardRoom")
    private String boardRoom;
    /**
     * 餐厅
     */
    @JSONField(name = "DiningRoom")
    private String diningRoom;
    /**
     * 停车场
     */
    @JSONField(name = "Parking")
    private String parking;
    /**
     * 健身房
     */
    @JSONField(name = "Gymnasium")
    private String gymnasium;
    /**
     * 游泳馆
     */
    @JSONField(name = "SwimmingPool")
    private String swimmingPool;

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public ResultSupp getResultSupp() {
        return resultSupp;
    }

    public void setResultSupp(ResultSupp resultSupp) {
        this.resultSupp = resultSupp;
    }

    public String getHotelLevel() {
        return hotelLevel;
    }

    public void setHotelLevel(String hotelLevel) {
        this.hotelLevel = hotelLevel;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFreeWIFI() {
        return freeWIFI;
    }

    public void setFreeWIFI(String freeWIFI) {
        this.freeWIFI = freeWIFI;
    }

    public String getBoardRoom() {
        return boardRoom;
    }

    public void setBoardRoom(String boardRoom) {
        this.boardRoom = boardRoom;
    }

    public String getDiningRoom() {
        return diningRoom;
    }

    public void setDiningRoom(String diningRoom) {
        this.diningRoom = diningRoom;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getGymnasium() {
        return gymnasium;
    }

    public void setGymnasium(String gymnasium) {
        this.gymnasium = gymnasium;
    }

    public String getSwimmingPool() {
        return swimmingPool;
    }

    public void setSwimmingPool(String swimmingPool) {
        this.swimmingPool = swimmingPool;
    }
}
