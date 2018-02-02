package com.tempus.gss.product.hol.api.entity.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 千淘酒店信息中的房型信息实体
 * Created by luofengjie on 2017/3/24.
 */
public class QTRoomType implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 房型编号
     */
    @JSONField(name = "RoomCode")
    private String roomCode;
    /**
     * 房型名称
     */
    @JSONField(name = "RoomName")
    private String roomName;
    /**
     * 楼层
     */
    @JSONField(name = "Floor")
    private String floor;
    /**
     * 宽带
     */
    @JSONField(name = "Intent")
    private String intent;
    /**
     * 面积
     */
    @JSONField(name = "Area")
    private String area;
    /**
     * 床型
     */
    @JSONField(name = "BedType")
    private String bedType;
    /**
     * 描述
     */
    @JSONField(name = "Description")
    private String description;
    /**
     * 最大数量
     */
    @JSONField(name = "MaxCount")
    private Integer maxCount;

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
}
