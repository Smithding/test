package com.tempus.gss.product.ins.api.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;


public class InsApiProductVo extends InsApiBase implements Serializable {
	//保险名称
	public String name;
	//保险类型 1:航意险 2:航延险 3:航意/航延险
	public String insureType;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInsureType() {
		return insureType;
	}

	public void setInsureType(String insureType) {
		this.insureType = insureType;
	}


}