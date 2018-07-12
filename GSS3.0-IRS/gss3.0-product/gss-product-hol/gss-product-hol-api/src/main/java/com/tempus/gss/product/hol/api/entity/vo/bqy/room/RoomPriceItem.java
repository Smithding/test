package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.ibm.icu.math.BigDecimal;

/**
 * 房间价格和房间集合
 */
public class RoomPriceItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="BaseRoomInfo")
	private BaseRoomInfo baseRoomInfo;			//房型
	
	@JSONField(name="RoomInfo")
	private  List<RoomInfoItem> roomInfo;		//房间集合
	
	private Double minprice;				//最低价

	public BaseRoomInfo getBaseRoomInfo() {
		return baseRoomInfo;
	}

	public void setBaseRoomInfo(BaseRoomInfo baseRoomInfo) {
		this.baseRoomInfo = baseRoomInfo;
	}

	public List<RoomInfoItem> getRoomInfo() {
		return roomInfo;
	}

	public void setRoomInfo(List<RoomInfoItem> roomInfo) {
		this.roomInfo = roomInfo;
	}

	public Double getMinprice() {
		return minprice;
	}

	public void setMinprice(Double minprice) {
		this.minprice = minprice;
	}
	
}
