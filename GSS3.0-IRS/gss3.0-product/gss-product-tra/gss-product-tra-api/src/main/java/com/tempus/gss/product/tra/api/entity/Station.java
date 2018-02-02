package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;

public class Station implements Serializable{
	
	private String station;

	private String location;

	private String serialNumber;//序号

	private String trainNo;//车次

	private String fromStation;//出发点

	private String fromStationCode;//出发点拼音

	private String fromStationNo;//出发点编号

	private String departureTime;//出发时间

	private String arrivalTime;//到达时间

	private String stayTimeSpan;

	private String runTimeSpan;//行驶时间

	private String miles;
	
	
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getFromStationCode() {
		return fromStationCode;
	}

	public void setFromStationCode(String fromStationCode) {
		this.fromStationCode = fromStationCode;
	}

	public String getFromStationNo() {
		return fromStationNo;
	}

	public void setFromStationNo(String fromStationNo) {
		this.fromStationNo = fromStationNo;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getStayTimeSpan() {
		return stayTimeSpan;
	}

	public void setStayTimeSpan(String stayTimeSpan) {
		this.stayTimeSpan = stayTimeSpan;
	}

	public String getRunTimeSpan() {
		return runTimeSpan;
	}

	public void setRunTimeSpan(String runTimeSpan) {
		this.runTimeSpan = runTimeSpan;
	}

	public String getMiles() {
		return miles;
	}

	public void setMiles(String miles) {
		this.miles = miles;
	}

	public void setStation(String station){
		this.station = station;
	}
	
	public String getStation(){
		return this.station;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public String getLocation(){
		return this.location;
	}
	
}
