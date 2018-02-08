package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;
import java.util.List;

public class TrainResponse  implements Serializable {
	private String queryKey;

	private String fromStation;//起始点中文名

	private String fromStationCode;//起始点拼音

	private String toStation;//终点中文名

	private String toStationCode;//终点拼音

	private String trainDate;//出发日期

	private int pageIndex;//第几页

	private int pageSize;//页面大小

	private int totalCount;//总数

	private int totalSize;

	private List<Trains> trains;//

	private List<Station> froms;

	private List<Station> tos;

	private String canNotBookEndTime;//最后订票时间

	private int FromInterval;

	private int ToInterval;

	private String msgCode;//结果代码

	private String msgInfo;//结果信息

	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}

	public String getQueryKey() {
		return this.queryKey;
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

	public void setTrainDate(String trainDate) {
		this.trainDate = trainDate;
	}

	public String getTrainDate() {
		return this.trainDate;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageIndex() {
		return this.pageIndex;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getTotalSize() {
		return this.totalSize;
	}

	public void setTrains(List<Trains> trains) {
		this.trains = trains;
	}

	public List<Trains> getTrains() {
		return this.trains;
	}

	public void setFroms(List<Station> froms) {
		this.froms = froms;
	}

	public List<Station> getFroms() {
		return this.froms;
	}

	public void setTos(List<Station> tos) {
		this.tos = tos;
	}

	public List<Station> getTos() {
		return this.tos;
	}

	public void setCanNotBookEndTime(String canNotBookEndTime) {
		this.canNotBookEndTime = canNotBookEndTime;
	}

	public String getCanNotBookEndTime() {
		return this.canNotBookEndTime;
	}

	public void setFromInterval(int FromInterval) {
		this.FromInterval = FromInterval;
	}

	public int getFromInterval() {
		return this.FromInterval;
	}

	public void setToInterval(int ToInterval) {
		this.ToInterval = ToInterval;
	}

	public int getToInterval() {
		return this.ToInterval;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgCode() {
		return this.msgCode;
	}

	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}

	public String getMsgInfo() {
		return this.msgInfo;
	}
}
