package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 入住人信息
 *
 * @author Administrator
 */
public class Guest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 入住人姓名
     */
    @JSONField(name = "TravelName")
    private String travelName;
    /**
     * 入住人电话
     */
    @JSONField(name = "Telephone")
    private String telephone;
    /**
     * 入住人证件编号
     */
    @JSONField(name = "CardNo")
    private String cardNo;
    /**
     * 入住人国籍
     */
    @JSONField(name = "Nationality")
    private String nationality;
    /**
     * 入住人证件类型
     */
    @JSONField(name = "CardType")
    private String cardType;
    /**
     * 入住人编号
     */
    @JSONField(name = "TravelID")
    private String travelID;
    /**
     * 所住房间编号，从1开始，顺序增加
     */
    @JSONField(name = "RoomNumber")
    private Integer roomNumber;

    public String getTravelName() {
        return travelName;
    }

    public void setTravelName(String travelName) {
        this.travelName = travelName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getTravelID() {
        return travelID;
    }

    public void setTravelID(String travelID) {
        this.travelID = travelID;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }


}
