package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

public class LegVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 航程编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long legNo;

	/**
	 * Id
	 */
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
	 * 明细编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderDetailNo;
	/**
	 * 航司
	 */
	private String airline;

	/**
	 * 航班号
	 */
	private String flightNo;

	/**
	 * 起点机场
	 */
	private String depAirport;

	/**
	 * 经停机场
	 */
	private String stopAirport;

	/**
	 * 停留时间 48:30,表示停留48小时30分钟.
	 */
	private Integer stopTime;
	
	/**
	 * 舱位
	 */
	private String cabin;

	/**
	 * 终点机场
	 */
	private String arrAirport;

	/**
	 * 起飞时间
	 */
	private Date depTime;

	/**
	 * 到达时间
	 */
	private Date arrTime;

	/**
	 * 父航段序号. 表示该航程属于第几个航段，便于还原用户选择的多段。
	 * 一个航段包含多个航程时，
	 * <p>
	 * 比如用户的总航程是SZX-SIN。
	 * 选择的航线是：SZX-KUL-SIN。
	 * 那么这里就是两条记录，section都为1，SZX-KUL的section是1，KUL-SIN的section是2.
	 */
	private Integer parentSection;

	/**
	 * 子航段序号 从01开始.保存格式为 复航段序号+子航段序号，即：0101.
	 */
	private String childSection;

	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Byte valid;

	/**
	 * 启用状态 1：启用，2：改签，3 废票，4：退票
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
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	//航程航段信息
	private String legValue;

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

	public Integer getStopTime() {

		return stopTime;
	}

	public void setStopTime(Integer stopTime) {

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

	public String getChildSection() {

		return childSection;
	}

	public void setChildSection(String childSection) {

		this.childSection = childSection;
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

	public Long getSaleOrderDetailNo() {
	
		return saleOrderDetailNo;
	}

	public void setSaleOrderDetailNo(Long saleOrderDetailNo) {
	
		this.saleOrderDetailNo = saleOrderDetailNo;
	}

	public String getLegValue() {
		return legValue;
	}

	public void setLegValue(String legValue) {
		this.legValue = legValue;
	}

	
}
