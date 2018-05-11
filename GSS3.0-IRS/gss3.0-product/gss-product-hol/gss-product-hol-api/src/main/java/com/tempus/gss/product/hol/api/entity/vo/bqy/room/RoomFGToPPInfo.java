package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class RoomFGToPPInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@JSONField(name="CanFGToPP")
	private boolean canFGToPP;
	
	@JSONField(name="IsFGToPP")
    private boolean isFGToPP;

	public boolean isCanFGToPP() {
		return canFGToPP;
	}

	public void setCanFGToPP(boolean canFGToPP) {
		this.canFGToPP = canFGToPP;
	}

	public boolean isFGToPP() {
		return isFGToPP;
	}

	public void setFGToPP(boolean isFGToPP) {
		this.isFGToPP = isFGToPP;
	}
}
