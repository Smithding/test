package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class Train implements Serializable {

	/**  
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）  
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String ymd;
	private String train_number;//车次
	private String train_type_name;//车次类型
	private String start_station_name;//始发站
	private String end_station_name;//终点站
	private String seat;//
	private String start_time;//出发时间
	private String end_time;//到达时间
	private String run_time;//行程时间
	private String km;//距离
	private List<Station> station;
	private List<Seat> seats;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYmd() {
		return ymd;
	}
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}
	public String getTrain_number() {
		return train_number;
	}
	public void setTrain_number(String train_number) {
		this.train_number = train_number;
	}
	public String getTrain_type_name() {
		return train_type_name;
	}
	public void setTrain_type_name(String train_type_name) {
		try {
			this.train_type_name = URLDecoder.decode(train_type_name, "gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.train_type_name = train_type_name;
		}
	}
	public String getStart_station_name() {
		return start_station_name;
	}
	public void setStart_station_name(String start_station_name) {
		try {
			this.start_station_name = URLDecoder.decode(start_station_name, "gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.start_station_name = start_station_name;
		}
	}
	public String getEnd_station_name() {
		return end_station_name;
	}
	public void setEnd_station_name(String end_station_name) {
		try {
			this.end_station_name = URLDecoder.decode(end_station_name, "gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.end_station_name = end_station_name;
		}
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		try {
			this.seat = URLDecoder.decode(seat, "gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.seat = seat;
		}
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		try {
			this.start_time = URLDecoder.decode(start_time, "gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.start_time = start_time;
		}
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		try {
			this.end_time = URLDecoder.decode(end_time, "gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.end_time = end_time;
		}
	}
	public String getRun_time() {
		return run_time;
	}
	public void setRun_time(String run_time) {
		this.run_time = run_time;
	}
	public String getKm() {
		return km;
	}
	public void setKm(String km) {
		this.km = km;
	}
	public List<Station> getStation() {
		return station;
	}
	public void setStation(List<Station> station) {
		this.station = station;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
}
