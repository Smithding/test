package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * BQY酒店预订及订单创建所需参数
 */
public class RoomInfo implements Serializable{
    private static final long serialVersionUID = 1L;

    private String holMidId;        //酒店中间表ID

    private String checkInDate;     //入住时间

    private String checkOutDate;    //离店时间

    private Integer roomCount;      //房间数

    private String cancelPolicyInfo;//取消政策

    private String lastCancelTime;  //最迟取消时间

    private String policyType;      //取消政策

    private Integer person;         //入住人数

    private BigDecimal price;           //价格 (是否去除返佣的价格)

    private BigDecimal oldPrice;        //原价格 (未做任何处理的挂牌价)

    private BigDecimal settleFee;       //结算价 (与bqy的结算价格)

    private Long roomTypeId ;       //房型ID

    private String roomTypeName;    //房型名称

    private Long roomId;            //房间ID

    private String roomName;        //房间名称

    private String supplierId;      //供应商ID

    private String ratePlanCategory;//与检查类型

    private Integer breakfastCount; //早餐数量

    private String bedTypeName;     //床型

    public String getHolMidId() {
        return holMidId;
    }

    public void setHolMidId(String holMidId) {
        this.holMidId = holMidId;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public String getCancelPolicyInfo() {
        return cancelPolicyInfo;
    }

    public void setCancelPolicyInfo(String cancelPolicyInfo) {
        this.cancelPolicyInfo = cancelPolicyInfo;
    }

    public String getLastCancelTime() {
        return lastCancelTime;
    }

    public void setLastCancelTime(String lastCancelTime) {
        this.lastCancelTime = lastCancelTime;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getRatePlanCategory() {
        return ratePlanCategory;
    }

    public void setRatePlanCategory(String ratePlanCategory) {
        this.ratePlanCategory = ratePlanCategory;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Integer getBreakfastCount() {
        return breakfastCount;
    }

    public void setBreakfastCount(Integer breakfastCount) {
        this.breakfastCount = breakfastCount;
    }

    public String getBedTypeName() {
        return bedTypeName;
    }

    public void setBedTypeName(String bedTypeName) {
        this.bedTypeName = bedTypeName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    public BigDecimal getSettleFee() {
        return settleFee;
    }

    public void setSettleFee(BigDecimal settleFee) {
        this.settleFee = settleFee;
    }
}
