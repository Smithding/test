package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 订单查询时出游人信息集合
 * @author kai.yang
 *
 */
public class PassengerModel implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 姓名
	 */
	@JSONField(name = "Name")
	private String name;
	/**
	 * 证件类型：
	 * 身份证 1，
	 * 护照 2，
	 * 军官证 3，
	 * 士兵证 4，
	 * 港澳台通行证 5，
	 * 临时身份证 6，
	 * 户口本 7，
	 * 其他 8, 
	 * 警官证 9，
	 * 外国人居留证 12，
	 * 回乡证 15，
	 * 企业营业执照 16，
	 * 法人代码证 17，
	 * 台胞证 18
	 */
	@JSONField(name = "IdType")
	private String idType;
	
	/**
	 * 证件编号
	 */
	@JSONField(name = "IdNo")
	private String idNo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
}	
