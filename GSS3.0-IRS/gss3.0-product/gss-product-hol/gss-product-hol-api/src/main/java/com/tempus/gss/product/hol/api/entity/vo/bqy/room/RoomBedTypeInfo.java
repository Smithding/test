package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class RoomBedTypeInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="HasKingBed")
	private String hasKingBed;
	
	@JSONField(name="KingBedWidth")
    private String kingBedWidth;
	
	@JSONField(name="HasTwinBed")
    private String hasTwinBed;
	
	@JSONField(name="TwinBedWidth")
    private String twinBedWidth;
	
	@JSONField(name="HasSingleBed")
    private String hasSingleBed;
	
	@JSONField(name="SingleBedWidth")
    private String singleBedWidth;

	public String getHasKingBed() {
		return hasKingBed;
	}

	public void setHasKingBed(String hasKingBed) {
		this.hasKingBed = hasKingBed;
	}

	public String getKingBedWidth() {
		return kingBedWidth;
	}

	public void setKingBedWidth(String kingBedWidth) {
		this.kingBedWidth = kingBedWidth;
	}

	public String getHasTwinBed() {
		return hasTwinBed;
	}

	public void setHasTwinBed(String hasTwinBed) {
		this.hasTwinBed = hasTwinBed;
	}

	public String getTwinBedWidth() {
		return twinBedWidth;
	}

	public void setTwinBedWidth(String twinBedWidth) {
		this.twinBedWidth = twinBedWidth;
	}

	public String getHasSingleBed() {
		return hasSingleBed;
	}

	public void setHasSingleBed(String hasSingleBed) {
		this.hasSingleBed = hasSingleBed;
	}

	public String getSingleBedWidth() {
		return singleBedWidth;
	}

	public void setSingleBedWidth(String singleBedWidth) {
		this.singleBedWidth = singleBedWidth;
	}
	
	
}
