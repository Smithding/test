package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 创建订单时出游人信息集合
 * @author kai.yang
 *
 */
public class TravlePassengerInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 客人姓名
	 */
	@JSONField(name = "Name")
	private String name;
	/**
	 * 客人手机号
	 */
	@JSONField(name = "Mobile")
	private String mobile;
	/**
	 * 证件类型（-1=>未填写;0=>身份证;
	 * 1=>护照;2=>军官证;3=>士兵证;
	 * 4=>港澳通行证;5=>临时身份证;6=>户口本;
	 * 7=>其他;9=>警官证;12=>外国人居住证;
	 * 15=>回乡证;16=>企业执照证;
	 * 17=>法人代表证;18=>台胞证）
	 */
	@JSONField(name = "CertType")
	private Integer certType;
	/**
	 * 证件号
	 */
	@JSONField(name = "IdNo")
	private String idNo;
	/**
	 * 性别
	 */
	@JSONField(name = "Sex")
	private Integer sex;
	/**
	 * 生日
	 */
	@JSONField(name = "BirthDay")
	private String birthDay;
	
	/**
	 * 有些酒店在预订规则BookingRule中规定需要提供国籍；填写具体的国籍,如中国、日本、美国、 USA 等
	 */
	@JSONField(name = "Nationality")
	private String nationality;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getCertType() {
		return certType;
	}
	public void setCertType(Integer certType) {
		this.certType = certType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
}
