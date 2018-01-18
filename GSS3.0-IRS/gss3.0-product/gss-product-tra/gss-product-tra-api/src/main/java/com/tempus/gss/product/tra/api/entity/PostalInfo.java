package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;

public class PostalInfo  implements Serializable {
	private String person;//收件人姓名，（isPost为1时必填）

	private String cellphone;//收件人手机号

	private String province; //省份

	private String city;//城市

	private String district;//区域

	private String address;//邮寄地址

	private String zip;//邮编

	public void setPerson(String person) {
		this.person = person;
	}

	public String getPerson() {
		return this.person;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvince() {
		return this.province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return this.city;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getZip() {
		return this.zip;
	}
}
