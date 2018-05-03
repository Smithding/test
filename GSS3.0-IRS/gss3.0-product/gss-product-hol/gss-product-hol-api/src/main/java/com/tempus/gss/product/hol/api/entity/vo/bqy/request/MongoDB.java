package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class MongoDB implements Serializable {

	private static final long serialVersionUID = 1L;

	@JSONField(name="Id")
	private Long id;
	@JSONField(name="City")
	private String city;
	@JSONField(name="Age")
	private String age;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
}
