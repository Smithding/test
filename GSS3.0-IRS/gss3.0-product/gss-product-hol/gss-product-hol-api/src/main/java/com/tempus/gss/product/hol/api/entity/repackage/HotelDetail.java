package com.tempus.gss.product.hol.api.entity.repackage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 酒店详情信息实体（对原来千淘酒店信息实体重新封装）
 * Created by luofengjie on 2017/3/29.
 */
public class HotelDetail implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 酒店编号
     */
    private String hotelCode;
    /**
     * 酒店名称
     */
    private String hotelName;
    /**
     * 酒店地址
     */
    private String hotelAddress;
    /**
     * 酒店最低价格
     */
    private BigDecimal minPrice;
    /**
     * 主图片地址
     */
    private String pictureUrl;
    /**
     * 酒店星级
     */
    private String hotelLevel;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 电话
     */
    private String phoneNum;
    /**
     * 简短描述
     */
    private String shortDescription;
    /**
     * wifi
     */
    private String freeWIFI;
    /**
     * 会议室
     */
    private String boardRoom;
    /**
     * 餐厅
     */
    private String diningRoom;
    /**
     * 停车场
     */
    private String parking;
    /**
     * 健身房
     */
    private String gymnasium;
    /**
     * 游泳馆
     */
    private String swimmingPool;
    /**
     * 房型集合
     */
    private List<RoomInfo> roomInfoList;

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

    public List<RoomInfo> getRoomInfoList() {
        return roomInfoList;
    }

    public void setRoomInfoList(List<RoomInfo> roomInfoList) {
        this.roomInfoList = roomInfoList;
    }
}
