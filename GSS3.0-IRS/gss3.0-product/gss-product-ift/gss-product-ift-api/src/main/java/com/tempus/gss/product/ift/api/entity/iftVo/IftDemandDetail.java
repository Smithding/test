package com.tempus.gss.product.ift.api.entity.iftVo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.Date;

public class IftDemandDetail implements Serializable {

	/**
	 * 需求单明细编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long demandDetailNo;

	/**
	 * 需求单编号 需求单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long demandNo;

	/**
	 * 航段顺序 表示该航程属于第几个航段，便于还原用户选择的多段。
	 * 一个航段包含多个航程时，
	 * <p>
	 * 比如用户的总航程是SZX-SIN。
	 * 选择的航线是：SZX-KUL-SIN。
	 * 那么这里就是两条记录，section都为1，SZX-KUL的section是1，KUL-SIN的section是2.
	 */
	private Integer sectionNo;

	/**
	 * 起点机场
	 */
	private String depAirport;

	/**
	 * 终点机场
	 */
	private String arrAirport;

	/**
	 * 起飞时间 yyyy-mm-dd HH:mm
	 */
	private Date depTime;

	/**
	 * 到达时间 yyyy-mm-dd HH:mm
	 */
	private Date arrTime;


	private static final long serialVersionUID = 1L;

	public Long getDemandDetailNo() {

		return demandDetailNo;
	}

	public void setDemandDetailNo(Long demandDetailNo) {

		this.demandDetailNo = demandDetailNo;
	}

	public Long getDemandNo() {

		return demandNo;
	}

	public void setDemandNo(Long demandNo) {

		this.demandNo = demandNo;
	}

	public Integer getSectionNo() {

		return sectionNo;
	}

	public void setSectionNo(Integer sectionNo) {

		this.sectionNo = sectionNo;
	}

	public String getDepAirport() {

		return depAirport;
	}

	public void setDepAirport(String depAirport) {

		this.depAirport = depAirport;
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
}
