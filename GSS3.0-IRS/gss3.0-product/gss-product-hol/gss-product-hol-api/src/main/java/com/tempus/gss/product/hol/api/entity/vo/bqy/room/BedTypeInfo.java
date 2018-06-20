package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 床型
 */
public class BedTypeInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JSONField(name="BedType")
	private String bedType;
	
	@JSONField(name="BedName")
	private String bedName;
	
	@JSONField(name="BedCount")
	private String bedCount;			//总床数

	@JSONField(name="BedWidth")			
	private String bedWidth;			//床宽度

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getBedName() {
		return bedName;
	}

	public void setBedName(String bedName) {
		this.bedName = bedName;
	}

	public String getBedCount() {
		return bedCount;
	}

	public void setBedCount(String bedCount) {
		this.bedCount = bedCount;
	}

	public String getBedWidth() {
		return bedWidth;
	}

	public void setBedWidth(String bedWidth) {
		this.bedWidth = bedWidth;
	}
	
}
