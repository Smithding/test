package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class Seat  implements Serializable {

	/**  
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）  
	 */
	private static final long serialVersionUID = 1L;

	private String seat_id;
	private String seat_price;//价格
	private String service_price;//服务费
	private String seat_name;//座位名字
	private String seat_bookable;
	private String seat_yupiao;//票量
	private List<Passenger> noPolicyPassengerList;
	public String getSeat_id() {
		return seat_id;
	}
	public void setSeat_id(String seat_id) {
		this.seat_id = seat_id;
	}
	public String getSeat_price() {
		return seat_price;
	}
	public void setSeat_price(String seat_price) {
		this.seat_price = seat_price;
	}
	public String getSeat_name() {
		return seat_name;
	}
	public void setSeat_name(String seat_name) {
		try {
			this.seat_name = URLDecoder.decode(seat_name, "gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.seat_name = seat_name;
		}
	}
	public String getSeat_bookable() {
		return seat_bookable;
	}
	public void setSeat_bookable(String seat_bookable) {
		this.seat_bookable = seat_bookable;
	}
	public String getSeat_yupiao() {
		return seat_yupiao;
	}
	public void setSeat_yupiao(String seat_yupiao) {
		this.seat_yupiao = seat_yupiao;
	}
	public List<Passenger> getNoPolicyPassengerList() {
		return noPolicyPassengerList;
	}
	public void setNoPolicyPassengerList(List<Passenger> noPolicyPassengerList) {
		this.noPolicyPassengerList = noPolicyPassengerList;
	}
	public String getService_price() {
		return service_price;
	}
	public void setService_price(String service_price) {
		this.service_price = service_price;
	}
}
