package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;

public class ContactInfo  implements Serializable {
	private String person;//联系人姓名（必填）

	private String cellphone;//联系人手机（必填）

	private String email;//联系人邮箱（可不填）

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

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}
}
