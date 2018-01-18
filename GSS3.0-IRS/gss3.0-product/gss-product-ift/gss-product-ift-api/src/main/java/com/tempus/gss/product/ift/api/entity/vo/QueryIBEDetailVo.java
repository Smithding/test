package com.tempus.gss.product.ift.api.entity.vo;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * Created by 杨威 on 2016/10/13.
 */
public class QueryIBEDetailVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 出票航司.
	 */
	private String ticketAirline;

	/**
	 * 是否直飞.
	 */
	private boolean isDirect;
	/**
	 * 航程类型：1:单程; 2:往返.
	 * 目前仅支持单程和往返.
	 */
	private Integer legType;

	/**
	 * 去程起飞时间.
	 */
	private Date goDepTime;

	/**
	 * 去程到达时间.
	 */
	private Date goArrTime;

	/**
	 * 去程起点机场.
	 */
	private String goDepAirport;

	/**
	 * 去程终点机场.
	 */
	private String goArrAirport;
	/**
	 * 回程起飞时间.
	 */
	private Date backDepTime;

	/**
	 * 回程到达时间.
	 */
	private Date backArrTime;

	/**
	 * 回程起点机场.
	 */
	private String backDepAirport;

	/**
	 * 回程终点机场.
	 */
	private String backArrAirport;

	/**
	 * 时长
	 */
	private String goDuration;

	/**
	 * 时长
	 */
	private String backDuration;

	/**
	 * 总则编号.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private long priceSpecNo;

	/**
	 * 航线仓位价格总和.
	 */
	private List<CabinsPricesTotalsVo> cabinsPricesTotalses;

	/**
	 * 航段具体信息.
	 */
	private List<FlightVo> flights;

	public String getTicketAirline() {

		return ticketAirline;
	}

	public void setTicketAirline(String ticketAirline) {

		this.ticketAirline = ticketAirline;
	}

	public boolean isDirect() {

		return isDirect;
	}

	public void setDirect(boolean direct) {

		isDirect = direct;
	}

	public Date getGoDepTime() {

		return goDepTime;
	}

	public void setGoDepTime(Date goDepTime) {

		this.goDepTime = goDepTime;
	}

	public Date getGoArrTime() {

		return goArrTime;
	}

	public void setGoArrTime(Date goArrTime) {

		this.goArrTime = goArrTime;
	}

	public String getGoDepAirport() {

		return goDepAirport;
	}

	public void setGoDepAirport(String goDepAirport) {

		this.goDepAirport = goDepAirport;
	}

	public String getGoArrAirport() {

		return goArrAirport;
	}

	public void setGoArrAirport(String goArrAirport) {

		this.goArrAirport = goArrAirport;
	}

	public Date getBackDepTime() {

		return backDepTime;
	}

	public void setBackDepTime(Date backDepTime) {

		this.backDepTime = backDepTime;
	}

	public Date getBackArrTime() {

		return backArrTime;
	}

	public void setBackArrTime(Date backArrTime) {

		this.backArrTime = backArrTime;
	}

	public String getBackDepAirport() {

		return backDepAirport;
	}

	public void setBackDepAirport(String backDepAirport) {

		this.backDepAirport = backDepAirport;
	}

	public String getBackArrAirport() {

		return backArrAirport;
	}

	public void setBackArrAirport(String backArrAirport) {

		this.backArrAirport = backArrAirport;
	}

	public List<CabinsPricesTotalsVo> getCabinsPricesTotalses() {

		return cabinsPricesTotalses;
	}

	public void setCabinsPricesTotalses(List<CabinsPricesTotalsVo> cabinsPricesTotalses) {

		this.cabinsPricesTotalses = cabinsPricesTotalses;
	}

	public List<FlightVo> getFlights() {

		return flights;
	}

	public void setFlights(List<FlightVo> flights) {

		this.flights = flights;
	}

	public long getPriceSpecNo() {

		return priceSpecNo;
	}

	public void setPriceSpecNo(long priceSpecNo) {

		this.priceSpecNo = priceSpecNo;
	}

	public Integer getLegType() {

		return legType;
	}

	public void setLegType(Integer legType) {

		this.legType = legType;
	}

	public String getGoDuration() {
		return goDuration;
	}

	public void setGoDuration(String goDuration) {
		this.goDuration = goDuration;
	}

	public String getBackDuration() {
		return backDuration;
	}

	public void setBackDuration(String backDuration) {
		this.backDuration = backDuration;
	}
}
