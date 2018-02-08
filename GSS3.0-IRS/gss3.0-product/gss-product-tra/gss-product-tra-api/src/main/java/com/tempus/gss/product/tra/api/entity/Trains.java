package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;

public class Trains  implements Serializable {
	private int serialNumber;//编号

	private String trainNo;//车次

	private String fromTime;//出发时间

	private String toTime;//到达时间

	private String fromStation;//出发站点

	private String fromStationCode;//出发站点拼音

	private String toStation;//到达站点

	private String toStationCode;//到达站点拼音

	private String runTimeSpan;//时长

	private int fromPassType;

	private int toPassType;

	private int bookState;

	private String trainClass;//车次类型

	private String note;

	private Tickets tickets;//车票

	private int miles;//距离

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getSerialNumber() {
		return this.serialNumber;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getTrainNo() {
		return this.trainNo;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getFromTime() {
		return this.fromTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getToTime() {
		return this.toTime;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getFromStation() {
		return this.fromStation;
	}

	public void setFromStationCode(String fromStationCode) {
		this.fromStationCode = fromStationCode;
	}

	public String getFromStationCode() {
		return this.fromStationCode;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	public String getToStation() {
		return this.toStation;
	}

	public void setToStationCode(String toStationCode) {
		this.toStationCode = toStationCode;
	}

	public String getToStationCode() {
		return this.toStationCode;
	}

	public void setRunTimeSpan(String runTimeSpan) {
		this.runTimeSpan = runTimeSpan;
	}

	public String getRunTimeSpan() {
		return this.runTimeSpan;
	}

	public void setFromPassType(int fromPassType) {
		this.fromPassType = fromPassType;
	}

	public int getFromPassType() {
		return this.fromPassType;
	}

	public void setToPassType(int toPassType) {
		this.toPassType = toPassType;
	}

	public int getToPassType() {
		return this.toPassType;
	}

	public void setBookState(int bookState) {
		this.bookState = bookState;
	}

	public int getBookState() {
		return this.bookState;
	}

	public void setTrainClass(String trainClass) {
		this.trainClass = trainClass;
	}

	public String getTrainClass() {
		return this.trainClass;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return this.note;
	}

	public void setTickets(Tickets tickets) {
		this.tickets = tickets;
	}

	public Tickets getTickets() {
		return this.tickets;
	}

	public void setMiles(int miles) {
		this.miles = miles;
	}

	public int getMiles() {
		return this.miles;
	}

}
