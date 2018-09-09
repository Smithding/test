package com.tempus.gss.product.ift.api.entity.policy;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <pre>
 * <b>国际政策分页查询数据传输实体.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年8月27日 下午4:09:43
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年8月27日 下午4:09:43    cz
 *         new file.
 * </pre>
 */
public class IftPolicyQuery implements Serializable{

	/** SVUID */
	private static final long serialVersionUID = 1L;
	
	/** 政策编号，ID */
	private Long policyId;

	/** 归集单位 */
	private Integer owner;
	
	/** 行程类型：1国内始发，2直飞soto程，3国内始发Add-on，4国外始发Add-on，5境外到境外，6国内始发中转，7国外始发中转，8境外境外境外，9境外境内境外，10全球到全球，11全球中转全球 */
	private Integer flightType;
	
	/** 航空公司编码 */
	private String airline;
	
	/** 出发机场 */
	private String departAirport;
	
	/** 到达机场 */
	private String arriveAirport;
	
	/** 政策来源 */
	private String source;
	
	/** 航程类型(政策类型)1:单程(默认); 2:往返; 3:单程/往返; */
	private Integer voyageType;
	
	/** 状态, 1:待审核; 2:已审核; 3:启用; 4:禁用; */
	private Integer status;
	
	/** 舱位 */
	private String cabin;
	
	/** 开票Office */
	private String ticketOffice;
	
	/** 是否自动出票，0不自动出（默认），1自动出票 */
	private Integer autoTicket;
	
	/** 政策有效期开始时间 */
	private Date policyStartDate;

	/** 政策有效期结束时间 */
	private Date policyEndDate;
	
	/** 票本类型，BSP、B2B、境外电子;多个以"/"分割，例:BSP/B2B */
	private String ticketWay;
	
	/** 产品名称（别名） */
	private String name;
	
	/** 是否换编码：1无需换编码（默认），2换编码出票，3大编出票（无需换编），4大编出票（需换编），5无位不换出票（外放有位时换编） */
	private Integer pnrTicketType;

	/**
	 * 无参构造
	 */
	public IftPolicyQuery() {
	}
	
	/**
	 * 国际政策查询实体构造
	 * 
	 * @param policyId 政策编号，ID
	 * @param owner 归集单位 
	 * @param flightType 行程类型：1国内始发，2直飞soto程，3国内始发Add-on，4国外始发Add-on，5境外到境外，6国内始发中转，7国外始发中转，8境外境外境外，9境外境内境外，10全球到全球，11全球中转全球
	 * @param airline 航空公司编码
	 * @param departAirport 出发机场
	 * @param arriveAirport 到达机场
	 * @param source 政策来源
	 * @param voyageType 航程类型(政策类型)
	 * @param status 状态, 1:待审核; 2:已审核; 3:启用; 4:禁用
	 * @param cabin 舱位
	 * @param ticketOffice  开票Office
	 * @param autoTicket 是否自动出票，0不自动出（默认），1自动出票
	 * @param policyStartDate 政策有效期开始时间
	 * @param policyEndDate 政策有效期结束时间
	 * @param ticketWay 票本类型，BSP、B2B、境外电子;多个以"/"分割，例:BSP/B2B
	 * @param name 产品名称（别名）
	 * @param pnrTicketType 是否换编码：1无需换编码（默认），2换编码出票，3大编出票（无需换编），4大编出票（需换编），5无位不换出票（外放有位时换编）
	 */
	public IftPolicyQuery(Long policyId, Integer owner, Integer flightType, String airline, String departAirport,
			String arriveAirport, String source, Integer voyageType, Integer status, String cabin, String ticketOffice,
			Integer autoTicket, Date policyStartDate, Date policyEndDate, String ticketWay, String name, Integer pnrTicketType) {
		this.policyId = policyId;
		this.owner = owner;
		this.flightType = flightType;
		this.airline = airline;
		this.departAirport = departAirport;
		this.arriveAirport = arriveAirport;
		this.source = source;
		this.voyageType = voyageType;
		this.status = status;
		this.cabin = cabin;
		this.ticketOffice = ticketOffice;
		this.autoTicket = autoTicket;
		this.policyStartDate = policyStartDate;
		this.policyEndDate = policyEndDate;
		this.ticketWay = ticketWay;
		this.name = name;
		this.pnrTicketType = pnrTicketType;
	}
	
	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Integer getFlightType() {
		return flightType;
	}

	public void setFlightType(Integer flightType) {
		this.flightType = flightType;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getDepartAirport() {
		return departAirport;
	}

	public void setDepartAirport(String departAirport) {
		this.departAirport = departAirport;
	}

	public String getArriveAirport() {
		return arriveAirport;
	}

	public void setArriveAirport(String arriveAirport) {
		this.arriveAirport = arriveAirport;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getVoyageType() {
		return voyageType;
	}

	public void setVoyageType(Integer voyageType) {
		this.voyageType = voyageType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCabin() {
		return cabin;
	}

	public void setCabin(String cabin) {
		this.cabin = cabin;
	}

	public String getTicketOffice() {
		return ticketOffice;
	}

	public void setTicketOffice(String ticketOffice) {
		this.ticketOffice = ticketOffice;
	}

	public Integer getAutoTicket() {
		return autoTicket;
	}

	public void setAutoTicket(Integer autoTicket) {
		this.autoTicket = autoTicket;
	}

	public Date getPolicyStartDate() {
		return policyStartDate;
	}

	public void setPolicyStartDate(Date policyStartDate) {
		this.policyStartDate = policyStartDate;
	}

	public Date getPolicyEndDate() {
		return policyEndDate;
	}

	public void setPolicyEndDate(Date policyEndDate) {
		this.policyEndDate = policyEndDate;
	}

	public String getTicketWay() {
		return ticketWay;
	}

	public void setTicketWay(String ticketWay) {
		this.ticketWay = ticketWay;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPnrTicketType() {
		return pnrTicketType;
	}

	public void setPnrTicketType(Integer pnrTicketType) {
		this.pnrTicketType = pnrTicketType;
	}
	
}
