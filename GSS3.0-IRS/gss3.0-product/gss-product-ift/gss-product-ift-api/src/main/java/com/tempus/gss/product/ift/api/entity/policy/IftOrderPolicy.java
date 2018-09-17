package com.tempus.gss.product.ift.api.entity.policy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * <pre>
 * <b>国际订单政策信息表，简称:IOP,主要存储国际机票订单保存的政策信息.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年9月3日 上午10:24:43
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月3日 上午10:24:43    cz
 *         new file.
 * </pre>
 */
@TableName("IFT_ORDER_POLICY")
public class IftOrderPolicy implements Serializable{
	
	/** SVUID */
	private static final long serialVersionUID = 1L;
	
	/** ID，主键 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 归集单位 */
	private Integer owner;

	/** 政策ID集合，多个以"/"分割 */
	private String policyId;

	/** 销售单ID */
	private Long saleId;

	/** 采购单ID */
	private Long buyId;

	/** 产品名称（别名） */
	private String name;

	/** 产品来源 */
	private String source;

	/** 航程类型, 1:单程(默认); 2:往返; 多个以"/"分割,例：1/2 */
	private String voyageType;

	/** 票本类型，BSP、B2B、境外电子;多个以"/"分割，例:BSP/B2B */
	private String ticketWay;

	/** 航空公司编码 */
	private String airline;

	/** 供应商, 可多个, 例如:[{\\\\ */
	private String suppliers;

	/** 供应商Office, 多个以"/"分隔 */
	private String supplierOffice;

	/** 开票Office, 多个以"/"分隔 */
	private String ticketOffice;

	/** 上游代理费用 */
	private BigDecimal originalAgencyFee;

	/** 上游奖励费用 */
	private BigDecimal originalRewardFee;

	/** 下游代理费 */
	private BigDecimal agencyFee;

	/** 下游奖励费用 */
	private BigDecimal rewardFee;

	/** 控润点数 */
	private BigDecimal profitFee;
	
	/** 控润点数 */
	private BigDecimal profitPrice;

	/** 实际销售奖励费 */
	private BigDecimal saleRewardFee;

	/** 单程直减费用 */
	private BigDecimal oneWayPrivilege;

	/** 往返直减费用 */
	private BigDecimal roundTripPrivilege;

	/** 开票费 */
	private BigDecimal openTicketFee;

	/** 是否自动出票，0不自动出（默认），1自动出票 */
	private Integer autoTicket;

	/** 是否航司普发GDS政策,1是（默认），0否 */
	private Integer isGds;

	/** 平时开票时间（星期一至星期五），例:00:00-23:59 */
	private String ticketDate;

	/** 星期六是否开票,0不开票，1开票（默认） */
	private Integer satIsTicket;

	/** 星期六开票时间，例:00:00-23:59 */
	private String satTicketDate;

	/** 星期日是否开票,0不开票，1开票（默认） */
	private Integer sunIsTicket;

	/** 星期日开票时间，例:00:00-23:59 */
	private String sunTicketDate;

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

	/** PNR出票方式：1无需换编码（默认），2换编码出票，3大编出票（无需换编），4大编出票（需换编），5无位不换出票（外放有位时换编） */
	private Integer pnrTicketType;

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

	/** 出票备注 */
	private String ticketRemarks;

	/** 备注 */
	private String remark;

	/** 创建者, 默认:sys */
	private String creator;

	/** 创建时间 */
	private Date createTime;

	/** 修改者, 默认:sys */
	private String modifier;

	/** 修改时间 */
	private Date modifyTime;

	/** 有效, 0:无效,已删除; 1:有效,正常(默认) */
	private Boolean valid;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOwner() {
		return this.owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public String getPolicyId() {
		return this.policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Long getSaleId() {
		return this.saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public Long getBuyId() {
		return this.buyId;
	}

	public void setBuyId(Long buyId) {
		this.buyId = buyId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getVoyageType() {
		return this.voyageType;
	}

	public void setVoyageType(String voyageType) {
		this.voyageType = voyageType;
	}

	public String getTicketWay() {
		return this.ticketWay;
	}

	public void setTicketWay(String ticketWay) {
		this.ticketWay = ticketWay;
	}

	public String getAirline() {
		return this.airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getSuppliers() {
		return this.suppliers;
	}

	public void setSuppliers(String suppliers) {
		this.suppliers = suppliers;
	}

	public String getSupplierOffice() {
		return this.supplierOffice;
	}

	public void setSupplierOffice(String supplierOffice) {
		this.supplierOffice = supplierOffice;
	}

	public String getTicketOffice() {
		return this.ticketOffice;
	}

	public void setTicketOffice(String ticketOffice) {
		this.ticketOffice = ticketOffice;
	}

	public BigDecimal getOriginalAgencyFee() {
		return this.originalAgencyFee;
	}

	public void setOriginalAgencyFee(BigDecimal originalAgencyFee) {
		this.originalAgencyFee = originalAgencyFee;
	}

	public BigDecimal getOriginalRewardFee() {
		return this.originalRewardFee;
	}

	public void setOriginalRewardFee(BigDecimal originalRewardFee) {
		this.originalRewardFee = originalRewardFee;
	}

	public BigDecimal getAgencyFee() {
		return this.agencyFee;
	}

	public void setAgencyFee(BigDecimal agencyFee) {
		this.agencyFee = agencyFee;
	}

	public BigDecimal getRewardFee() {
		return this.rewardFee;
	}

	public void setRewardFee(BigDecimal rewardFee) {
		this.rewardFee = rewardFee;
	}

	public BigDecimal getProfitFee() {
		return this.profitFee;
	}

	public void setProfitFee(BigDecimal profitFee) {
		this.profitFee = profitFee;
	}

	public BigDecimal getProfitPrice() {
		return profitPrice;
	}

	public void setProfitPrice(BigDecimal profitPrice) {
		this.profitPrice = profitPrice;
	}

	public BigDecimal getSaleRewardFee() {
		return this.saleRewardFee;
	}

	public void setSaleRewardFee(BigDecimal saleRewardFee) {
		this.saleRewardFee = saleRewardFee;
	}

	public BigDecimal getOneWayPrivilege() {
		return this.oneWayPrivilege;
	}

	public void setOneWayPrivilege(BigDecimal oneWayPrivilege) {
		this.oneWayPrivilege = oneWayPrivilege;
	}

	public BigDecimal getRoundTripPrivilege() {
		return this.roundTripPrivilege;
	}

	public void setRoundTripPrivilege(BigDecimal roundTripPrivilege) {
		this.roundTripPrivilege = roundTripPrivilege;
	}

	public BigDecimal getOpenTicketFee() {
		return this.openTicketFee;
	}

	public void setOpenTicketFee(BigDecimal openTicketFee) {
		this.openTicketFee = openTicketFee;
	}

	public Integer getAutoTicket() {
		return this.autoTicket;
	}

	public void setAutoTicket(Integer autoTicket) {
		this.autoTicket = autoTicket;
	}

	public Integer getIsGds() {
		return this.isGds;
	}

	public void setIsGds(Integer isGds) {
		this.isGds = isGds;
	}

	public String getTicketDate() {
		return this.ticketDate;
	}

	public void setTicketDate(String ticketDate) {
		this.ticketDate = ticketDate;
	}

	public Integer getSatIsTicket() {
		return this.satIsTicket;
	}

	public void setSatIsTicket(Integer satIsTicket) {
		this.satIsTicket = satIsTicket;
	}

	public String getSatTicketDate() {
		return this.satTicketDate;
	}

	public void setSatTicketDate(String satTicketDate) {
		this.satTicketDate = satTicketDate;
	}

	public Integer getSunIsTicket() {
		return this.sunIsTicket;
	}

	public void setSunIsTicket(Integer sunIsTicket) {
		this.sunIsTicket = sunIsTicket;
	}

	public String getSunTicketDate() {
		return this.sunTicketDate;
	}

	public void setSunTicketDate(String sunTicketDate) {
		this.sunTicketDate = sunTicketDate;
	}

	public Integer getChdRewardType() {
		return this.chdRewardType;
	}

	public void setChdRewardType(Integer chdRewardType) {
		this.chdRewardType = chdRewardType;
	}

	public BigDecimal getChdAssignRewardFee() {
		return this.chdAssignRewardFee;
	}

	public void setChdAssignRewardFee(BigDecimal chdAssignRewardFee) {
		this.chdAssignRewardFee = chdAssignRewardFee;
	}

	public Boolean getChdIsAddHandlingFee() {
		return this.chdIsAddHandlingFee;
	}

	public void setChdIsAddHandlingFee(Boolean chdIsAddHandlingFee) {
		this.chdIsAddHandlingFee = chdIsAddHandlingFee;
	}

	public BigDecimal getChdAddHandlingFee() {
		return this.chdAddHandlingFee;
	}

	public void setChdAddHandlingFee(BigDecimal chdAddHandlingFee) {
		this.chdAddHandlingFee = chdAddHandlingFee;
	}

	public Boolean getChdTicketNoAgencyFee() {
		return this.chdTicketNoAgencyFee;
	}

	public void setChdTicketNoAgencyFee(Boolean chdTicketNoAgencyFee) {
		this.chdTicketNoAgencyFee = chdTicketNoAgencyFee;
	}

	public Boolean getChdNotAloneTicket() {
		return this.chdNotAloneTicket;
	}

	public void setChdNotAloneTicket(Boolean chdNotAloneTicket) {
		this.chdNotAloneTicket = chdNotAloneTicket;
	}

	public Boolean getChdPrivilege() {
		return this.chdPrivilege;
	}

	public void setChdPrivilege(Boolean chdPrivilege) {
		this.chdPrivilege = chdPrivilege;
	}

	public Integer getInfTicket() {
		return this.infTicket;
	}

	public void setInfTicket(Integer infTicket) {
		this.infTicket = infTicket;
	}

	public Boolean getInfIsAddHandlingFee() {
		return this.infIsAddHandlingFee;
	}

	public void setInfIsAddHandlingFee(Boolean infIsAddHandlingFee) {
		this.infIsAddHandlingFee = infIsAddHandlingFee;
	}

	public BigDecimal getInfAddHandlingFee() {
		return this.infAddHandlingFee;
	}

	public void setInfAddHandlingFee(BigDecimal infAddHandlingFee) {
		this.infAddHandlingFee = infAddHandlingFee;
	}

	public Integer getPnrTicketType() {
		return this.pnrTicketType;
	}

	public void setPnrTicketType(Integer pnrTicketType) {
		this.pnrTicketType = pnrTicketType;
	}

	public Integer getShareRewardType() {
		return this.shareRewardType;
	}

	public void setShareRewardType(Integer shareRewardType) {
		this.shareRewardType = shareRewardType;
	}

	public BigDecimal getShareAssignReward() {
		return this.shareAssignReward;
	}

	public void setShareAssignReward(BigDecimal shareAssignReward) {
		this.shareAssignReward = shareAssignReward;
	}

	public BigDecimal getShareLegReward() {
		return this.shareLegReward;
	}

	public void setShareLegReward(BigDecimal shareLegReward) {
		this.shareLegReward = shareLegReward;
	}

	public Boolean getShareIsSuitAirline() {
		return this.shareIsSuitAirline;
	}

	public void setShareIsSuitAirline(Boolean shareIsSuitAirline) {
		this.shareIsSuitAirline = shareIsSuitAirline;
	}

	public String getShareSuitAirline() {
		return this.shareSuitAirline;
	}

	public void setShareSuitAirline(String shareSuitAirline) {
		this.shareSuitAirline = shareSuitAirline;
	}

	public String getTicketRemarks() {
		return this.ticketRemarks;
	}

	public void setTicketRemarks(String ticketRemarks) {
		this.ticketRemarks = ticketRemarks;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Boolean getValid() {
		return this.valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}
}
