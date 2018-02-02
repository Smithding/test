package com.tempus.gss.product.ift.api.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

public class Leg implements Serializable, Comparable<Leg> {

	/**
	 * 航程编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long legNo;

	/**
	 * Id
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long id;

	/**
	 * 数据归属单位
	 */
	private Integer owner;

	/**
	 * 销售单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	/**
	 * 航司
	 */
	private String airline;

	/**
	 * 航班号
	 */
	private String flightNo;

	/**
	 * 起点机场-出发地
	 */
	private String depAirport;

	/**
	 * 经停机场
	 */
	private String stopAirport;

	/**
	 * 停留时间 48:30,表示停留48小时30分钟.
	 */
	private String stopTime;

	/**
	 * 终点机场-目的地
	 */
	private String arrAirport;

	/**
	 * 起飞时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date depTime;

	/**
	 * 到达时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date arrTime;

	/**
	 * 父航段序号. 表示该航程属于第几个航段，便于还原用户选择的多段。
	 * 一个航段包含多个航程时，
	 * <p>
	 * 比如用户的总航程是SZX-SIN。
	 * 选择的航线是：SZX-KUL-SIN。
	 * 那么这里就是两条记录，SZX-KUL的section是0，KUL-SIN的section是1.
	 */
	private Integer parentSection;

	/**
	 * 0:原始航段 1：改签过的航段
	 */
	private Integer childSection;

	/**
	 * 行程类型 1.去程  2.返程
	 */
	private Integer goBack;

	/**
	 * 机型
	 */
	private String airportModel;

	/**
	 * 航站楼
	 */
	private String terminal;

	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Byte valid;

	/**
	 * 启用状态 1：启用，2：禁用
	 */
	private String status;

	/**
	 * 操作人 默认为：sys
	 */
	private String modifier;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 修改时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;

	/**
	 * 舱位信息
	 */
	private String cabin;

	/**
	 * 起点城市名称
	 */
	@Transient
	private String depCityName;

	/**
	 * 终点城市名称
	 */
	@Transient
	private String arrCityName;

	private String fsi;


	private static final long serialVersionUID = 1L;

	public Long getLegNo() {

		return legNo;
	}

	public void setLegNo(Long legNo) {

		this.legNo = legNo;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public Integer getOwner() {

		return owner;
	}

	public void setOwner(Integer owner) {

		this.owner = owner;
	}

	public Long getSaleOrderNo() {

		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {

		this.saleOrderNo = saleOrderNo;
	}

	public String getAirline() {

		return airline;
	}

	public void setAirline(String airline) {

		this.airline = airline;
	}

	public String getFlightNo() {

		return flightNo;
	}

	public void setFlightNo(String flightNo) {

		this.flightNo = flightNo;
	}

	public String getDepAirport() {

		return depAirport;
	}

	public void setDepAirport(String depAirport) {

		this.depAirport = depAirport;
	}

	public String getStopAirport() {

		return stopAirport;
	}

	public void setStopAirport(String stopAirport) {

		this.stopAirport = stopAirport;
	}

	public String getStopTime() {

		return stopTime;
	}

	public void setStopTime(String stopTime) {

		this.stopTime = stopTime;
	}

	public String getArrAirport() {

		return arrAirport;
	}

	public void setArrAirport(String arrAirport) {

		this.arrAirport = arrAirport;
	}

	public Date getDepTime() {

		return depTime;
	}

	public void setDepTime(Date depTime) {

		this.depTime = depTime;
	}

	public Date getArrTime() {

		return arrTime;
	}

	public void setArrTime(Date arrTime) {

		this.arrTime = arrTime;
	}

	public Integer getParentSection() {

		return parentSection;
	}

	public void setParentSection(Integer parentSection) {

		this.parentSection = parentSection;
	}

	public Integer getChildSection() {

		return childSection;
	}

	public void setChildSection(Integer childSection) {

		this.childSection = childSection;
	}

	public Integer getGoBack() {

		return goBack;
	}

	public void setGoBack(Integer goBack) {

		this.goBack = goBack;
	}

	public String getAirportModel() {

		return airportModel;
	}

	public void setAirportModel(String airportModel) {

		this.airportModel = airportModel;
	}

	public String getTerminal() {

		return terminal;
	}

	public void setTerminal(String terminal) {

		this.terminal = terminal;
	}

	public Byte getValid() {

		return valid;
	}

	public void setValid(Byte valid) {

		this.valid = valid;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public String getModifier() {

		return modifier;
	}

	public void setModifier(String modifier) {

		this.modifier = modifier;
	}

	public String getCreator() {

		return creator;
	}

	public void setCreator(String creator) {

		this.creator = creator;
	}

	public Date getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public Date getModifyTime() {

		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {

		this.modifyTime = modifyTime;
	}

	public String getCabin() {

		return cabin;
	}

	public void setCabin(String cabin) {

		this.cabin = cabin;
	}

	public String getDepCityName() {
		return depCityName;
	}

	public void setDepCityName(String depCityName) {
		this.depCityName = depCityName;
	}

	public String getArrCityName() {
		return arrCityName;
	}

	public void setArrCityName(String arrCityName) {
		this.arrCityName = arrCityName;
	}

	@Override
	public int compareTo(Leg o) {
		Integer p1 = o.getParentSection();
		Integer p2 = this.getParentSection();
		return p2<p1?-1:1;
	}

	public String getFsi() {
		return fsi;
	}

	public void setFsi(String fsi) {
		this.fsi = fsi;
	}
}
