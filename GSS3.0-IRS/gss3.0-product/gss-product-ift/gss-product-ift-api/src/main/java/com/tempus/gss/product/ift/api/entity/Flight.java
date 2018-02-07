package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.product.ift.api.entity.vo.FlightCabinPriceVo;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yangwei on 2016/10/13.
 */
public class Flight implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 航司.
	 */
	private String airline;

	/**
	 * 航班号.
	 */
	private String flightNo;
	/**
	 * 服务等级.
	 */
	private String grade;

	/**
	 * 行李.
	 */
	private String baggage;
	/**
	 * 起飞时间.
	 */
	private Date depTime;

	/**
	 * 到达时间.
	 */
	private Date arrTime;

	/**
	 * 到达机场.
	 */
	private String arrAirport;

	/**
	 * 起点机场.
	 */
	private String depAirport;

	/**
	 * 起点航站楼.
	 */
	private String depTerminal;

	/**
	 * 到达航站楼.
	 */
	private String arrTerminal;
	/**
	 * 里程.
	 */
	private Integer Tpm;
	/**
	 * 时长
	 */
	private String duration;
	/**
	 * 方向标识 go/back
	 */
	private String direction;

	/**
	 * 代码共享
	 */
	private String codeshare;
	/**
	 * 机型
	 */
	private String equipment;

	/**
	 * 票证类型
	 */
	private String ticketType;

	/**
	 * 中转停 留时间
	 */
	private String  transferTime;


	/**
	 * 政策编号.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private long policyNo;

	/**
	 * 政策版本.
	 */
	private Integer policyVersion;

	/**
	 * 旅行代码
	 */
	private String tourCode;
	/**
	 *  经停机场
	 */

	private String  stopOverAirport;
	/**
	 * 经停时间
	 */
	private String  stopOverDuration;

	/**
	 * 仓位组合运价
	 */

	private List<FlightCabinPriceVo> flightCabinPriceVos;
	/**
	 * 航段序号
	 */
	private Integer flightNum;


	private String fsi;



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

	public String getGrade() {

		return grade;
	}

	public void setGrade(String grade) {

		this.grade = grade;
	}

	public String getIncludeBaggage() {

		return baggage;
	}

	public void setBaggage(String baggage) {

		this.baggage = baggage;
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

	public Integer getTpm() {

		return Tpm;
	}

	public void setTpm(Integer tpm) {

		Tpm = tpm;
	}

	public String getDepAirport() {

		return depAirport;
	}

	public void setDepAirport(String depAirport) {

		this.depAirport = depAirport;
	}

	public String getDepTerminal() {

		return depTerminal;
	}

	public void setDepTerminal(String depTerminal) {

		this.depTerminal = depTerminal;
	}

	public String getArrAirport() {

		return arrAirport;
	}

	public void setArrAirport(String arrAirport) {

		this.arrAirport = arrAirport;
	}

	public String getArrTerminal() {

		return arrTerminal;
	}

	public void setArrTerminal(String arrTerminal) {

		this.arrTerminal = arrTerminal;
	}

	public String getDirection() {

		return direction;
	}

	public void setDirection(String direction) {

		this.direction = direction;
	}

	public String getBaggage() {

		return baggage;
	}

	public void setArrTime(Date arrTime) {

		this.arrTime = arrTime;
	}

	public String getDuration() {

		return duration;
	}

	public void setDuration(String duration) {

		this.duration = duration;
	}

	public String getCodeshare() {

		return codeshare;
	}

	public void setCodeshare(String codeshare) {

		this.codeshare = codeshare;
	}

	public String getTicketType() {

		return ticketType;
	}

	public void setTicketType(String ticketType) {

		this.ticketType = ticketType;
	}

	public long getPolicyNo() {

		return policyNo;
	}

	public void setPolicyNo(long policyNo) {

		this.policyNo = policyNo;
	}

	public Integer getPolicyVersion() {

		return policyVersion;
	}

	public void setPolicyVersion(Integer policyVersion) {

		this.policyVersion = policyVersion;
	}

	public String getTourCode() {

		return tourCode;
	}

	public void setTourCode(String tourCode) {

		this.tourCode = tourCode;
	}

	public List<FlightCabinPriceVo> getFlightCabinPriceVos() {

		return flightCabinPriceVos;
	}

	public void setFlightCabinPriceVos(List<FlightCabinPriceVo> flightCabinPriceVos) {

		this.flightCabinPriceVos = flightCabinPriceVos;
	}

	public Integer getFlightNum() {

		return flightNum;
	}

	public void setFlightNum(Integer flightNum) {

		this.flightNum = flightNum;
	}

	public String getTransferTime() {

		return transferTime;
	}

	public void setTransferTime(String transferTime) {

		this.transferTime = transferTime;
	}

	public String getStopOverAirport() {

		return stopOverAirport;
	}

	public void setStopOverAirport(String stopOverAirport) {

		this.stopOverAirport = stopOverAirport;
	}

	public String getStopOverDuration() {

		return stopOverDuration;
	}

	public void setStopOverDuration(String stopOverDuration) {

		this.stopOverDuration = stopOverDuration;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}


	public String getFsi() {
		return fsi;
	}

	public void setFsi(String fsi) {
		this.fsi = fsi;
	}
}
