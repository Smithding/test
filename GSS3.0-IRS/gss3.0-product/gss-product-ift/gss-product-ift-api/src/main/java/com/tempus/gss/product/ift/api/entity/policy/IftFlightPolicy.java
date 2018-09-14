package com.tempus.gss.product.ift.api.entity.policy;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单预订页面政策实体类
 * @author juan.yin
 *
 */
public class IftFlightPolicy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 订单优惠总金额
	 */
	private BigDecimal favorableCount;
	/**
	 * 订单销售总金额
	 */
	private BigDecimal salePrice;
	/** 上游代理费用 */
	private BigDecimal originalAgencyFee;

	/** 上游奖励费用 */
	private BigDecimal originalRewardFee;

	/** 下游代理费 */
	private BigDecimal agencyFee;

	/** 下游奖励费用 */
	private BigDecimal rewardFee;

	/** 单程直减费用 */
	private BigDecimal oneWayPrivilege;

	/** 往返直减费用 */
	private BigDecimal roundTripPrivilege;

	/** 开票费 */
	private BigDecimal openTicketFee;
	/** 供应商Office, 多个以"/"分隔 */
	private String supplierOffice;

	/** 开票Office, 多个以"/"分隔 */
	private String ticketOffice;

	/** 儿童票奖励方式，1奖励与成人一致（默认）,2可开无奖励，3不可开，4指定奖励 */
	private Integer chdRewardType;

	/** 儿童指定奖励 */
	private BigDecimal chdAssignRewardFee;

	/** 儿童是否加收手续费，0否（默认），1是 */
	private Boolean chdIsAddHandlingFee;

	/** 儿童加收的手续费 */
	private BigDecimal chdAddHandlingFee;

	/** 儿童是否可开无代理费，0否（默认），1是 */
	private Boolean chdTicketNoAgencyFee;

	/** 儿童是否不单开，0否（默认），1是 */
	private Boolean chdNotAloneTicket;

	/** 儿童是否不享受直减，0否（默认），1是 */
	private Boolean chdPrivilege;

	/** 婴儿票：1可开无奖励,2不可开 */
	private Integer infTicket;

	/** 婴儿是否加收手续费，0否（默认），1是 */
	private Boolean infIsAddHandlingFee;

	/** 婴儿加收的手续费 */
	private BigDecimal infAddHandlingFee;

	/** 共享奖励类型：1全程无奖励，2全程指定奖励，3共享段无奖励，4共享段指定奖励，5给全部奖励 */
	private Integer shareRewardType;

	/** 存在共享航班时全程指定奖励 */
	private BigDecimal shareAssignReward;

	/** 共享航班航段指定奖励 */
	private BigDecimal shareLegReward;

	/** 共享是否勾选仅和以下航司间共享时给全部奖励 ，0否（默认），1是 */
	private Boolean shareIsSuitAirline;

	/** 共享以下航司间给全部奖励，航司二字代码，多个以"/"分割 */
	private String shareSuitAirline;
	/** 平时开票时间（星期一至星期五），例:00:00-23:59 */
	private String ticketDate;
	/** 备注 */
	private String remark;
	/**
	 * 出票方式
	 */
	private String ticketWay;
	/**
	 * 儿童奖励说明
	 */
	private String chdRemark;
	/**
	 * 婴儿奖励说明
	 */
	private String infRemark;
	/**
	 * 共享奖励说明
	 */
	private String shareRemark;
	/**
	 * 政策id
	 */
	private Long policyId;
	public String getTicketWay() {
		return ticketWay;
	}

	public void setTicketWay(String ticketWay) {
		this.ticketWay = ticketWay;
	}

	public String getChdRemark() {
		return chdRemark;
	}

	public void setChdRemark(String chdRemark) {
		this.chdRemark = chdRemark;
	}

	public String getInfRemark() {
		return infRemark;
	}

	public void setInfRemark(String infRemark) {
		this.infRemark = infRemark;
	}

	public String getShareRemark() {
		return shareRemark;
	}

	public void setShareRemark(String shareRemark) {
		this.shareRemark = shareRemark;
	}

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public String getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(String ticketDate) {
		this.ticketDate = ticketDate;
	}
	public BigDecimal getOriginalAgencyFee() {
		return originalAgencyFee;
	}

	public void setOriginalAgencyFee(BigDecimal originalAgencyFee) {
		this.originalAgencyFee = originalAgencyFee;
	}

	public BigDecimal getOriginalRewardFee() {
		return originalRewardFee;
	}

	public void setOriginalRewardFee(BigDecimal originalRewardFee) {
		this.originalRewardFee = originalRewardFee;
	}

	public BigDecimal getRewardFee() {
		return rewardFee;
	}

	public void setRewardFee(BigDecimal rewardFee) {
		this.rewardFee = rewardFee;
	}

	public BigDecimal getOneWayPrivilege() {
		return oneWayPrivilege;
	}

	public void setOneWayPrivilege(BigDecimal oneWayPrivilege) {
		this.oneWayPrivilege = oneWayPrivilege;
	}

	public BigDecimal getRoundTripPrivilege() {
		return roundTripPrivilege;
	}

	public void setRoundTripPrivilege(BigDecimal roundTripPrivilege) {
		this.roundTripPrivilege = roundTripPrivilege;
	}

	public BigDecimal getOpenTicketFee() {
		return openTicketFee;
	}

	public void setOpenTicketFee(BigDecimal openTicketFee) {
		this.openTicketFee = openTicketFee;
	}

	public String getSupplierOffice() {
		return supplierOffice;
	}

	public void setSupplierOffice(String supplierOffice) {
		this.supplierOffice = supplierOffice;
	}

	public String getTicketOffice() {
		return ticketOffice;
	}

	public void setTicketOffice(String ticketOffice) {
		this.ticketOffice = ticketOffice;
	}

	public Integer getChdRewardType() {
		return chdRewardType;
	}

	public void setChdRewardType(Integer chdRewardType) {
		this.chdRewardType = chdRewardType;
	}

	public BigDecimal getChdAssignRewardFee() {
		return chdAssignRewardFee;
	}

	public void setChdAssignRewardFee(BigDecimal chdAssignRewardFee) {
		this.chdAssignRewardFee = chdAssignRewardFee;
	}

	public Boolean getChdIsAddHandlingFee() {
		return chdIsAddHandlingFee;
	}

	public void setChdIsAddHandlingFee(Boolean chdIsAddHandlingFee) {
		this.chdIsAddHandlingFee = chdIsAddHandlingFee;
	}

	public BigDecimal getChdAddHandlingFee() {
		return chdAddHandlingFee;
	}

	public void setChdAddHandlingFee(BigDecimal chdAddHandlingFee) {
		this.chdAddHandlingFee = chdAddHandlingFee;
	}

	public Boolean getChdTicketNoAgencyFee() {
		return chdTicketNoAgencyFee;
	}

	public void setChdTicketNoAgencyFee(Boolean chdTicketNoAgencyFee) {
		this.chdTicketNoAgencyFee = chdTicketNoAgencyFee;
	}

	public Boolean getChdNotAloneTicket() {
		return chdNotAloneTicket;
	}

	public void setChdNotAloneTicket(Boolean chdNotAloneTicket) {
		this.chdNotAloneTicket = chdNotAloneTicket;
	}

	public Boolean getChdPrivilege() {
		return chdPrivilege;
	}

	public void setChdPrivilege(Boolean chdPrivilege) {
		this.chdPrivilege = chdPrivilege;
	}

	public Integer getInfTicket() {
		return infTicket;
	}

	public void setInfTicket(Integer infTicket) {
		this.infTicket = infTicket;
	}

	public Boolean getInfIsAddHandlingFee() {
		return infIsAddHandlingFee;
	}

	public void setInfIsAddHandlingFee(Boolean infIsAddHandlingFee) {
		this.infIsAddHandlingFee = infIsAddHandlingFee;
	}

	public BigDecimal getInfAddHandlingFee() {
		return infAddHandlingFee;
	}

	public void setInfAddHandlingFee(BigDecimal infAddHandlingFee) {
		this.infAddHandlingFee = infAddHandlingFee;
	}

	public Integer getShareRewardType() {
		return shareRewardType;
	}

	public void setShareRewardType(Integer shareRewardType) {
		this.shareRewardType = shareRewardType;
	}

	public BigDecimal getShareAssignReward() {
		return shareAssignReward;
	}

	public void setShareAssignReward(BigDecimal shareAssignReward) {
		this.shareAssignReward = shareAssignReward;
	}

	public BigDecimal getShareLegReward() {
		return shareLegReward;
	}

	public void setShareLegReward(BigDecimal shareLegReward) {
		this.shareLegReward = shareLegReward;
	}

	public Boolean getShareIsSuitAirline() {
		return shareIsSuitAirline;
	}

	public void setShareIsSuitAirline(Boolean shareIsSuitAirline) {
		this.shareIsSuitAirline = shareIsSuitAirline;
	}

	public String getShareSuitAirline() {
		return shareSuitAirline;
	}

	public void setShareSuitAirline(String shareSuitAirline) {
		this.shareSuitAirline = shareSuitAirline;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getAgencyFee() {
		return agencyFee;
	}

	public void setAgencyFee(BigDecimal agencyFee) {
		this.agencyFee = agencyFee;
	}
	public BigDecimal getFavorableCount() {
		return favorableCount;
	}

	public void setFavorableCount(BigDecimal favorableCount) {
		this.favorableCount = favorableCount;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

}
