package com.tempus.gss.product.hol.api.entity.repackage;

import java.io.Serializable;
import java.util.List;

/**
 * 酒店房型信息
 * Created by luofengjie on 2017/3/29.
 */
public class RoomInfo implements Serializable{

    /**
     * 房型编号
     */
    private String roomCode;
    /**
     * 房型名称
     */
    private String roomName;
    /**
     * 楼层
     */
    private String floor;
    /**
     * 宽带
     */
    private String intent;
    /**
     * 面积
     */
    private String area;
    /**
     * 床型
     */
    private String bedType;
    /**
     * 描述
     */
    private String description;
    /**
     * 最大数量
     */
    private Integer maxCount;
    /**
     * 价格计划信息集合
     */
    private List<PricePlanInfo> pricePlanInfoList;

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public List<PricePlanInfo> getPricePlanInfoList() {
        return pricePlanInfoList;
    }

    public void setPricePlanInfoList(List<PricePlanInfo> pricePlanInfoList) {
        this.pricePlanInfoList = pricePlanInfoList;
    }
}
