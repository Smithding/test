package com.tempus.gss.product.ift.api.entity.search;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <pre>
 * <b>国际航班查询参数实体类.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年9月3日 上午9:08:03
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月3日 上午9:08:03    cz
 *         new file.
 * </pre>
 */
public class FlightQuery implements Serializable{

	/** SVUID */
	private static final long serialVersionUID = 1L;
	/** 实际出票航空公司 */
	private String airline;
	/** 航程类型, 1:单程(默认); 2:往返; */
	private String voyageType;
	/**去程航班起飞时间 2018-09-03*/
	private Date flyDate;
	/**回程起飞时间 2018-09-09*/
	private Date rtnFlyDate;
	/**当前销售时间 08:30，*/
	private String saleHous;
	/**当前星期几 1，2，3，4，5，6，7*/
	private int saleWeek;
	/** 出发所属洲 */
	private String departContinent;
	/** 出发国家 */
	private String departCountry;
	/** 出发机场，多个以"/"分割 */
	private String departAirport;
	/** 出发舱位 */
	private String[] departCabin;
	/** 中转机场，多个以"/"分割 */
	private String[] transitAirport;
	/** 中转舱位 */
	private String[] transitCabin;
	/** 到达所属洲 */
	private String arriveContinent;
	/** 到达国家，A00代表全球，CN0代表境内，CN1代表境外，国家多个以"/"分割 */
	private String arriveCountry;
	/** 到达机场，多个以"/"分割 */
	private String arriveAirport;
	/**乘客人数*/
	private int psgerNum;
	/**乘客类型 1：成人；2:儿童;3:婴儿；4：成人+儿童；5成人+婴儿；*/
	private int psgerType;
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getVoyageType() {
		return voyageType;
	}
	public void setVoyageType(String voyageType) {
		this.voyageType = voyageType;
	}
	public Date getFlyDate() {
		return flyDate;
	}
	public void setFlyDate(Date flyDate) {
		this.flyDate = flyDate;
	}
	public Date getRtnFlyDate() {
		return rtnFlyDate;
	}
	public void setRtnFlyDate(Date rtnFlyDate) {
		this.rtnFlyDate = rtnFlyDate;
	}
	public String getSaleHous() {
		return saleHous;
	}
	public void setSaleHous(String saleHous) {
		this.saleHous = saleHous;
	}
	public int getSaleWeek() {
		return saleWeek;
	}
	public void setSaleWeek(int saleWeek) {
		this.saleWeek = saleWeek;
	}
	public String getDepartContinent() {
		return departContinent;
	}
	public void setDepartContinent(String departContinent) {
		this.departContinent = departContinent;
	}
	public String getDepartCountry() {
		return departCountry;
	}
	public void setDepartCountry(String departCountry) {
		this.departCountry = departCountry;
	}
	public String getDepartAirport() {
		return departAirport;
	}
	public void setDepartAirport(String departAirport) {
		this.departAirport = departAirport;
	}
	public String[] getDepartCabin() {
		return departCabin;
	}
	public void setDepartCabin(String[] departCabin) {
		this.departCabin = departCabin;
	}
	public String[] getTransitAirport() {
		return transitAirport;
	}
	public void setTransitAirport(String[] transitAirport) {
		this.transitAirport = transitAirport;
	}
	public String[] getTransitCabin() {
		return transitCabin;
	}
	public void setTransitCabin(String[] transitCabin) {
		this.transitCabin = transitCabin;
	}
	public String getArriveContinent() {
		return arriveContinent;
	}
	public void setArriveContinent(String arriveContinent) {
		this.arriveContinent = arriveContinent;
	}
	public String getArriveCountry() {
		return arriveCountry;
	}
	public void setArriveCountry(String arriveCountry) {
		this.arriveCountry = arriveCountry;
	}
	public String getArriveAirport() {
		return arriveAirport;
	}
	public void setArriveAirport(String arriveAirport) {
		this.arriveAirport = arriveAirport;
	}
	public int getPsgerNum() {
		return psgerNum;
	}
	public void setPsgerNum(int psgerNum) {
		this.psgerNum = psgerNum;
	}
	public int getPsgerType() {
		return psgerType;
	}
	public void setPsgerType(int psgerType) {
		this.psgerType = psgerType;
	}
}
