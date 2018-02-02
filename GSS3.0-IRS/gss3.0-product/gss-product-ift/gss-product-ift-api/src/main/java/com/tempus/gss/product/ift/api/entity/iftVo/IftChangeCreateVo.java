/*===========================================
* Copyright (C) 2016 Tempus
* All rights reserved
*
*文 件 名：ChangeCreateVo.java
*版本信息： 1.0.0
*创建时间： 2016/9/10
*作 者： ning.fu
*
===========================================*/
package com.tempus.gss.product.ift.api.entity.iftVo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.List;

/**
 * 创建改签单请求.
 */
public class IftChangeCreateVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*销售单编号*/
	@JsonSerialize(using = LongSerializer.class)
	public Long saleOrderNo;
	/*需要改签的乘客编号集合*/
	public List<Long> oldPassengerNoList;
	/*需要改签的航班集合*/
	public List<Long> oldLegNoList;
	/*改签后的航班集合*/
	public List<IftLegVo> iftLegVoList;
	/*改签类型*/
	public String changeType;
	/** 改签原因*/
	public String changeReason;
	/** 联系人*/
	public String contactName;
	
	/** 联系人手机号*/
	public String contactPhone;
	
	public Long getSaleOrderNo() {

		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {

		this.saleOrderNo = saleOrderNo;
	}

	public List<Long> getOldPassengerNoList() {

		return oldPassengerNoList;
	}

	public void setOldPassengerNoList(List<Long> oldPassengerNoList) {

		this.oldPassengerNoList = oldPassengerNoList;
	}

	public List<Long> getOldLegNoList() {

		return oldLegNoList;
	}

	public void setOldLegNoList(List<Long> oldLegNoList) {

		this.oldLegNoList = oldLegNoList;
	}

	
	public String getChangeType() {

		return changeType;
	}

	public void setChangeType(String changeType) {

		this.changeType = changeType;
	}

	
	public String getChangeReason() {
	
		return changeReason;
	}

	
	public void setChangeReason(String changeReason) {
	
		this.changeReason = changeReason;
	}

	
	public String getContactName() {
	
		return contactName;
	}

	
	public void setContactName(String contactName) {
	
		this.contactName = contactName;
	}

	
	public String getContactPhone() {
	
		return contactPhone;
	}

	
	public void setContactPhone(String contactPhone) {
	
		this.contactPhone = contactPhone;
	}

	public List<IftLegVo> getIftLegVoList() {
		return iftLegVoList;
	}

	public void setIftLegVoList(List<IftLegVo> iftLegVoList) {
		this.iftLegVoList = iftLegVoList;
	}
}
