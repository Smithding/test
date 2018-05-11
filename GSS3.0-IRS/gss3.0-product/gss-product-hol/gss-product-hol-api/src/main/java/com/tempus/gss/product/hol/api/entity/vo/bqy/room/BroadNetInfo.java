package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class BroadNetInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="HasBroadnet")
	private String hasBroadnet;
	
	@JSONField(name="HasWirelessBroadnet")
    private String hasWirelessBroadnet;
	
	@JSONField(name="WirelessBroadnetRoom")
    private String wirelessBroadnetRoom;
	
	@JSONField(name="WirelessBroadnetFee")
    private String wirelessBroadnetFee;
	
	@JSONField(name="HasWiredBroadnet")
    private String hasWiredBroadnet;
	
	@JSONField(name="WiredBroadnetRoom")
    private String wiredBroadnetRoom;
	
	@JSONField(name="WiredBroadnetFee")
    private String wiredBroadnetFee;
	
	@JSONField(name="broadnetFeeDetail")
    private String BroadnetFeeDetail;

	public String getHasBroadnet() {
		return hasBroadnet;
	}

	public void setHasBroadnet(String hasBroadnet) {
		this.hasBroadnet = hasBroadnet;
	}

	public String getHasWirelessBroadnet() {
		return hasWirelessBroadnet;
	}

	public void setHasWirelessBroadnet(String hasWirelessBroadnet) {
		this.hasWirelessBroadnet = hasWirelessBroadnet;
	}

	public String getWirelessBroadnetRoom() {
		return wirelessBroadnetRoom;
	}

	public void setWirelessBroadnetRoom(String wirelessBroadnetRoom) {
		this.wirelessBroadnetRoom = wirelessBroadnetRoom;
	}

	public String getWirelessBroadnetFee() {
		return wirelessBroadnetFee;
	}

	public void setWirelessBroadnetFee(String wirelessBroadnetFee) {
		this.wirelessBroadnetFee = wirelessBroadnetFee;
	}

	public String getHasWiredBroadnet() {
		return hasWiredBroadnet;
	}

	public void setHasWiredBroadnet(String hasWiredBroadnet) {
		this.hasWiredBroadnet = hasWiredBroadnet;
	}

	public String getWiredBroadnetRoom() {
		return wiredBroadnetRoom;
	}

	public void setWiredBroadnetRoom(String wiredBroadnetRoom) {
		this.wiredBroadnetRoom = wiredBroadnetRoom;
	}

	public String getWiredBroadnetFee() {
		return wiredBroadnetFee;
	}

	public void setWiredBroadnetFee(String wiredBroadnetFee) {
		this.wiredBroadnetFee = wiredBroadnetFee;
	}

	public String getBroadnetFeeDetail() {
		return BroadnetFeeDetail;
	}

	public void setBroadnetFeeDetail(String broadnetFeeDetail) {
		BroadnetFeeDetail = broadnetFeeDetail;
	}
	
	
}
