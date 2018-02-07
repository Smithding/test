package com.tempus.gss.product.ift.api.entity.iftVo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;

public class IftDemandPassenger implements Serializable {

	/**
	 * 乘客编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long passengerNo;

	/**
	 * 需求单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long demandNo;

	/**
	 * 乘客类型 1：ADT:2：CHD，3：INF
	 */
	private String passengerType;

	/**
	 * 姓 姓
	 */
	private String surname;

	/**
	 * 名 名
	 */
	private String name;

	/**
	 * 证件类型
	 */
	private String certType;

	/**
	 * 证件编号
	 */
	private String certNo;

	/**
	 * 国籍
	 */
	private String nationality;

	/**
	 * 性别
	 */
	private String gender;


	private static final long serialVersionUID = 1L;

	public Long getPassengerNo() {

		return passengerNo;
	}

	public void setPassengerNo(Long passengerNo) {

		this.passengerNo = passengerNo;
	}


	public Long getDemandNo() {

		return demandNo;
	}

	public void setDemandNo(Long demandNo) {

		this.demandNo = demandNo;
	}

	public String getPassengerType() {

		return passengerType;
	}

	public void setPassengerType(String passengerType) {

		this.passengerType = passengerType;
	}

	public String getSurname() {

		return surname;
	}

	public void setSurname(String surname) {

		this.surname = surname;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getCertType() {

		return certType;
	}

	public void setCertType(String certType) {

		this.certType = certType;
	}

	public String getCertNo() {

		return certNo;
	}

	public void setCertNo(String certNo) {

		this.certNo = certNo;
	}

	public String getNationality() {

		return nationality;
	}

	public void setNationality(String nationality) {

		this.nationality = nationality;
	}

	public String getGender() {

		return gender;
	}

	public void setGender(String gender) {

		this.gender = gender;
	}
}