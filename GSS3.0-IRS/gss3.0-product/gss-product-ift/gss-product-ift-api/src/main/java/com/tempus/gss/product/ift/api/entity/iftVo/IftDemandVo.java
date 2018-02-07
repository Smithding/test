package com.tempus.gss.product.ift.api.entity.iftVo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.List;

public class IftDemandVo implements Serializable {

	/**
	 * 需求单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long demandNo;

	/**
	 * 申请开始时间
	 */
	private String startTime;
	/**
	 * 申请结束时间
	 */
	private String endTime;

	/**
	 * 起飞开始时间
	 */
	private String flightStartTime;

	/**
	 * 起飞结束时间
	 */
	private String flightEndTime;

	/**
	 * 起飞机场
	 */
	private String depAirport;
	/**
	 * 到达机场
	 */
	private String arrAirport;
	
	/**
	 * 航程类型 航程类型，1:单程; 2:往返; 3:多程
	 */
	private Integer legType;
	
	/**
	 * 联系人姓名
	 */
	private String contactName;
	
	/**
	 * 状态  1：待核价，2：已核价，3：已取消    默认展示待处理
	 */
	private Integer[] statusArray;

	/**
	 * 联系电话
	 */
	private String contactPhone;
	
	/**
	 * 乘客类型：1.普通，2.学生，3.新移民 4.劳务 根据数据字段定义
	 */
	private String passengerType;
	
	private int curPage;// 当前页

	private int rowNum;// 每页多少行

	/**
	* 需求单乘客集合
	* */
	private List<IftDemandPassenger> demandPassengerList;

	private static final long serialVersionUID = 1L;

	public String getStartTime() {

		return startTime;
	}

	public void setStartTime(String startTime) {

		this.startTime = startTime;
	}

	public String getEndTime() {

		return endTime;
	}

	public void setEndTime(String endTime) {

		this.endTime = endTime;
	}

	public Integer[] getStatusArray() {

		return statusArray;
	}

	public void setStatusArray(Integer[] statusArray) {

		this.statusArray = statusArray;
	}

	public Integer getLegType() {
		return legType;
	}

	public void setLegType(Integer legType) {
		this.legType = legType;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public List<IftDemandPassenger> getDemandPassengerList() {
		return demandPassengerList;
	}

	public void setDemandPassengerList(List<IftDemandPassenger> demandPassengerList) {
		this.demandPassengerList = demandPassengerList;
	}

	public Long getDemandNo() {

		return demandNo;
	}

	public void setDemandNo(Long demandNo) {

		this.demandNo = demandNo;
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

	public String getFlightStartTime() {

		return flightStartTime;
	}

	public void setFlightStartTime(String flightStartTime) {

		this.flightStartTime = flightStartTime;
	}

	public String getFlightEndTime() {

		return flightEndTime;
	}

	public void setFlightEndTime(String flightEndTime) {

		this.flightEndTime = flightEndTime;
	}


	public String getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
	
}