package com.tempus.gss.product.ift.api.entity.policy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

/**
 *
 * 新国际政策表, 简称:IPOLICY, 主要存储国际机票所有类型的产品细节信息. -- chenzhao
 *
 */
public class IftPolicy implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** ID主键 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 归集单位 */
	private Integer owner;

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

	/** 同时适用的航司编码，多个以"/"分割 */
	private String suitAirline;

	/** 供应商, 可多个, 例如:[{\ */
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

	/** 提前出票天数 */
	private String ticketRange;

	/** 去程有效期开始时间 */
	private Date flyStartDate;

	/** 去程有效期结束时间 */
	private Date flyEndDate;

	/** 回程有效期开始时间 */
	private Date rtnStartDate;

	/** 回程有效期结束时间 */
	private Date rtnEndDate;

	/** 开票有效开始时间 */
	private Date ticketValidStartDate;

	/** 开票有效结束时间 */
	private Date ticketValidEndDate;

	/** 平时开票时间（星期一至星期五），例:00:00-23:59 */
	private String ticketDate;

	/** 星期六是否开票,0不开票（默认），1开票 */
	private Integer satIsTicket;

	/** 星期六开票时间，例:00:00-23:59 */
	private String satTicketDate;

	/** 星期日是否开票,0不开票（默认），1开票 */
	private Integer sunIsTicket;

	/** 星期日开票时间，例:00:00-23:59 */
	private String sunTicketDate;

	/** 行程类型：1国内始发，2直飞soto程，3国内始发Add-on，4国外始发Add-on，5境外到境外，6国内始发中转，7国外始发中转，8境外境外境外，9境外境内境外，10全球到全球，11全球中转全球 */
	private Integer flightType;

	/** 出发国家，A00代表全球，CN0代表境内，CN1代表境外，国家多个以"/"分割 */
	private String departCountry;

	/** 出发排除国家，多个以"/"分割 */
	private String departExcludeCountry;

	/** 出发机场，多个以"/"分割 */
	private String departAirport;

	/** 出发排除机场，多个以"/"分割 */
	private String departExcludeAirport;

	/** 出发舱位 */
	private String departCabin;

	/** 中转国家，A00代表全球，CN0代表境内，CN1代表境外，国家多个以"/"分割 */
	private String transitCountry;

	/** 中转排除国家，多个以"/"分割 */
	private String transitExcludeCountry;

	/** 中转机场，多个以"/"分割 */
	private String transitAirport;

	/** 中转排除机场，多个以"/"分割 */
	private String transitExcludeAirport;

	/** 中转舱位 */
	private String transitCabin;

	/** 到达国家，A00代表全球，CN0代表境内，CN1代表境外，国家多个以"/"分割 */
	private String arriveCountry;

	/** 到达排除国家，多个以"/"分割 */
	private String arriveExcludeCountry;

	/** 到达机场，多个以"/"分割 */
	private String arriveAirport;

	/** 到达排除机场，多个以"/"分割 */
	private String arriveExcludeAirport;

	/** 往返时返回机场类型：1政策始发机场范围（默认），2同一洲际范围内，3必须返回行程出发机场，4指定返回国家或机场范围 */
	private Integer roundTripAirportType;

	/** 往返时返回机场类型为4时，指定的国家或机场范围，多个以"/"分割 */
	private String roundTripAirport;

	/** 是否存在不适用转机机场，0不存在（默认），1存在 */
	private Boolean isNotsuitTransferAirport;

	/** 不适用转机机场,多个以"/"分割 */
	private String notsuitTransferAirport;

	/** 适用年龄，例:0-100 */
	private String suitAge;

	/** 适用人数，例：0-9 */
	private String suitPeopleNumber;

	/** 是否只适用双人，0否（默认），1是 */
	private Boolean suitDoublePeople;

	/** 是否同改同退，0否（默认），1是 */
	private Boolean changeAndRefund;

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

	/** 换编出票时是否自动换编，0否（默认），1是 */
	private Boolean autoChangePnr;

	/** 换编出票时无位是否接受做空编，0否（默认），1是 */
	private Boolean makeNullPnr;

	/** 换编不授权，0否（默认），1是 */
	private Boolean changePnrNotAuth;

	/** 是否指定office号订位，0否（默认），1是 */
	private Boolean assignBookOffice;

	/** 指定订位的office号，多个以"/"分割 */
	private String bookOffice;

	/** 指定office订位时编码出票类型：1原编出票，2换编出票，3不开票 */
	private Integer assignBookOfficeType;

	/** 不适用乘客类型，1留学生，2移民，3劳工，4海员，5青年，6老年，7探亲，8港澳台，9外籍，10非汉字姓名，11Add-On，12境外中转，13OPEN，14SPA，15三方协议，16套票，17整团，18小团，多个以"/"分割 */
	private String notsuitPassengerType;

	/** 缺口程类型：1适用政策内缺口，2适用国家内缺口，3不适用 */
	private Integer arnkType;

	/** 缺口程适用国家，多个以"/"分割 */
	private String arnkSuitCountry;

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

	/** 是否允许混舱1/2RT，0否（默认），1是 */
	private Boolean isMixCabin;

	/** 混舱时运价类型，1运价基础需一致，2票面略低，多个以"/"分割 */
	private String mixCabinType;

	/** 是否勾选TOUR CODE中含内容 */
	private Boolean isTourCodeIncludeText;

	/** TOUR CODE中含内容 */
	private String tourCodeIncludeText;

	/** TOUR CODE中含内容是否无代理费，0否（默认），1是 */
	private Boolean tourCodeNoAgency;

	/** TOUR CODE中含内容是否无奖励，0否（默认），1是 */
	private Boolean tourCodeNoReward;

	/** 仅适用于以下TOUR CODE */
	private String tourCodeContent;

	/** 往返是否存在必须包含舱位 */
	private Boolean isRoundTripIncludeCabin;

	/** 往返必须包含舱位 */
	private String roundTripIncludeCabin;

	/** 是否限制票面价格（不含税） */
	private Boolean isParLimit;

	/** 票面限制价格，例:100-1000 */
	private String parLimit;

	/** Q值是否有代理费，0否（默认），1是 */
	private Boolean fareQAgency;

	/** Q值是否有奖励，0否（默认），1是 */
	private Boolean fareQReward;

	/** S值是否有代理费，0否（默认），1是 */
	private Boolean fareSAgency;

	/** S值是否有奖励，0否（默认），1是 */
	private Boolean fareSReward;

	/** 是否勾选运价基础中包含内容 */
	private Boolean isFareIncludeText;

	/** 运价基础中包含内容 */
	private String fareIncludeText;

	/** 运价基础中含内容计算类型，1无奖励，2无代理费，3不可用 */
	private Integer fareIncludeType;

	/** 是否勾选仅适用于以下运价基础，0否（默认），1是 */
	private Boolean isSuitFareBase;

	/** 适用于以下运价基础 */
	private String suitFareBase;

	/** 是否勾选去程适用航班，0否（默认），1是 */
	private Boolean isFlySuitFlight;

	/** 去程适用航班，多个航班号以"/"分割 */
	private String flySuitFlight;

	/** 是否勾选回程适用航班，0否（默认），1是 */
	private Boolean isRtnSuitFlight;

	/** 回程适用航班，多个航班号以"/"分割 */
	private String rtnSuitFlight;

	/** 是否勾选不适用航班，0否（默认），1是 */
	private Boolean isNotSuitFlight;

	/** 不适用航班，多个航班号以"/"分割 */
	private String notSuitFlight;

	/** 是否勾选不适用航线，0否（默认），1是 */
	private Boolean isNotSuitRoute;

	/** 不适用航线，多个航线以"/"分割，例:SZXPEK/PEKSHA */
	private String notSuitRoute;

	/** 去程不适用周期,1代表周一至7代表周日，多个以"/"分割 */
	private String flyNotsuitCycle;

	/** 回程不适用周期,1代表周一至7代表周日，多个以"/"分割 */
	private String rtnNotsuitCycle;

	/** 去程不适用日期,例:2018-08-01|2018-08-31，多个以"/"分割 */
	private String flyNotsuitDate;

	/** 回程不适用日期,例:2018-08-01|2018-08-31，多个以"/"分割 */
	private String rtnNotsuitDate;

	/** 备注 */
	private String remark;

	/** 状态, 1:待审核(默认); 2:已审核; 3:启用; 4:禁用; */
	private Integer status;

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

	public String getSuitAirline() {
		return this.suitAirline;
	}

	public void setSuitAirline(String suitAirline) {
		this.suitAirline = suitAirline;
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

	public String getTicketRange() {
		return this.ticketRange;
	}

	public void setTicketRange(String ticketRange) {
		this.ticketRange = ticketRange;
	}

	public Date getFlyStartDate() {
		return this.flyStartDate;
	}

	public void setFlyStartDate(Date flyStartDate) {
		this.flyStartDate = flyStartDate;
	}

	public Date getFlyEndDate() {
		return this.flyEndDate;
	}

	public void setFlyEndDate(Date flyEndDate) {
		this.flyEndDate = flyEndDate;
	}

	public Date getRtnStartDate() {
		return this.rtnStartDate;
	}

	public void setRtnStartDate(Date rtnStartDate) {
		this.rtnStartDate = rtnStartDate;
	}

	public Date getRtnEndDate() {
		return this.rtnEndDate;
	}

	public void setRtnEndDate(Date rtnEndDate) {
		this.rtnEndDate = rtnEndDate;
	}

	public Date getTicketValidStartDate() {
		return this.ticketValidStartDate;
	}

	public void setTicketValidStartDate(Date ticketValidStartDate) {
		this.ticketValidStartDate = ticketValidStartDate;
	}

	public Date getTicketValidEndDate() {
		return this.ticketValidEndDate;
	}

	public void setTicketValidEndDate(Date ticketValidEndDate) {
		this.ticketValidEndDate = ticketValidEndDate;
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

	public Integer getFlightType() {
		return this.flightType;
	}

	public void setFlightType(Integer flightType) {
		this.flightType = flightType;
	}

	public String getDepartCountry() {
		return this.departCountry;
	}

	public void setDepartCountry(String departCountry) {
		this.departCountry = departCountry;
	}

	public String getDepartExcludeCountry() {
		return this.departExcludeCountry;
	}

	public void setDepartExcludeCountry(String departExcludeCountry) {
		this.departExcludeCountry = departExcludeCountry;
	}

	public String getDepartAirport() {
		return this.departAirport;
	}

	public void setDepartAirport(String departAirport) {
		this.departAirport = departAirport;
	}

	public String getDepartExcludeAirport() {
		return this.departExcludeAirport;
	}

	public void setDepartExcludeAirport(String departExcludeAirport) {
		this.departExcludeAirport = departExcludeAirport;
	}

	public String getDepartCabin() {
		return this.departCabin;
	}

	public void setDepartCabin(String departCabin) {
		this.departCabin = departCabin;
	}

	public String getTransitCountry() {
		return this.transitCountry;
	}

	public void setTransitCountry(String transitCountry) {
		this.transitCountry = transitCountry;
	}

	public String getTransitExcludeCountry() {
		return this.transitExcludeCountry;
	}

	public void setTransitExcludeCountry(String transitExcludeCountry) {
		this.transitExcludeCountry = transitExcludeCountry;
	}

	public String getTransitAirport() {
		return this.transitAirport;
	}

	public void setTransitAirport(String transitAirport) {
		this.transitAirport = transitAirport;
	}

	public String getTransitExcludeAirport() {
		return this.transitExcludeAirport;
	}

	public void setTransitExcludeAirport(String transitExcludeAirport) {
		this.transitExcludeAirport = transitExcludeAirport;
	}

	public String getTransitCabin() {
		return this.transitCabin;
	}

	public void setTransitCabin(String transitCabin) {
		this.transitCabin = transitCabin;
	}

	public String getArriveCountry() {
		return this.arriveCountry;
	}

	public void setArriveCountry(String arriveCountry) {
		this.arriveCountry = arriveCountry;
	}

	public String getArriveExcludeCountry() {
		return this.arriveExcludeCountry;
	}

	public void setArriveExcludeCountry(String arriveExcludeCountry) {
		this.arriveExcludeCountry = arriveExcludeCountry;
	}

	public String getArriveAirport() {
		return this.arriveAirport;
	}

	public void setArriveAirport(String arriveAirport) {
		this.arriveAirport = arriveAirport;
	}

	public String getArriveExcludeAirport() {
		return this.arriveExcludeAirport;
	}

	public void setArriveExcludeAirport(String arriveExcludeAirport) {
		this.arriveExcludeAirport = arriveExcludeAirport;
	}

	public Integer getRoundTripAirportType() {
		return this.roundTripAirportType;
	}

	public void setRoundTripAirportType(Integer roundTripAirportType) {
		this.roundTripAirportType = roundTripAirportType;
	}

	public String getRoundTripAirport() {
		return this.roundTripAirport;
	}

	public void setRoundTripAirport(String roundTripAirport) {
		this.roundTripAirport = roundTripAirport;
	}

	public Boolean getIsNotsuitTransferAirport() {
		return this.isNotsuitTransferAirport;
	}

	public void setIsNotsuitTransferAirport(Boolean isNotsuitTransferAirport) {
		this.isNotsuitTransferAirport = isNotsuitTransferAirport;
	}

	public String getNotsuitTransferAirport() {
		return this.notsuitTransferAirport;
	}

	public void setNotsuitTransferAirport(String notsuitTransferAirport) {
		this.notsuitTransferAirport = notsuitTransferAirport;
	}

	public String getSuitAge() {
		return this.suitAge;
	}

	public void setSuitAge(String suitAge) {
		this.suitAge = suitAge;
	}

	public String getSuitPeopleNumber() {
		return this.suitPeopleNumber;
	}

	public void setSuitPeopleNumber(String suitPeopleNumber) {
		this.suitPeopleNumber = suitPeopleNumber;
	}

	public Boolean getSuitDoublePeople() {
		return this.suitDoublePeople;
	}

	public void setSuitDoublePeople(Boolean suitDoublePeople) {
		this.suitDoublePeople = suitDoublePeople;
	}

	public Boolean getChangeAndRefund() {
		return this.changeAndRefund;
	}

	public void setChangeAndRefund(Boolean changeAndRefund) {
		this.changeAndRefund = changeAndRefund;
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

	public Boolean getAutoChangePnr() {
		return this.autoChangePnr;
	}

	public void setAutoChangePnr(Boolean autoChangePnr) {
		this.autoChangePnr = autoChangePnr;
	}

	public Boolean getMakeNullPnr() {
		return this.makeNullPnr;
	}

	public void setMakeNullPnr(Boolean makeNullPnr) {
		this.makeNullPnr = makeNullPnr;
	}

	public Boolean getChangePnrNotAuth() {
		return this.changePnrNotAuth;
	}

	public void setChangePnrNotAuth(Boolean changePnrNotAuth) {
		this.changePnrNotAuth = changePnrNotAuth;
	}

	public Boolean getAssignBookOffice() {
		return this.assignBookOffice;
	}

	public void setAssignBookOffice(Boolean assignBookOffice) {
		this.assignBookOffice = assignBookOffice;
	}

	public String getBookOffice() {
		return this.bookOffice;
	}

	public void setBookOffice(String bookOffice) {
		this.bookOffice = bookOffice;
	}

	public Integer getAssignBookOfficeType() {
		return this.assignBookOfficeType;
	}

	public void setAssignBookOfficeType(Integer assignBookOfficeType) {
		this.assignBookOfficeType = assignBookOfficeType;
	}

	public String getNotsuitPassengerType() {
		return this.notsuitPassengerType;
	}

	public void setNotsuitPassengerType(String notsuitPassengerType) {
		this.notsuitPassengerType = notsuitPassengerType;
	}

	public Integer getArnkType() {
		return this.arnkType;
	}

	public void setArnkType(Integer arnkType) {
		this.arnkType = arnkType;
	}

	public String getArnkSuitCountry() {
		return this.arnkSuitCountry;
	}

	public void setArnkSuitCountry(String arnkSuitCountry) {
		this.arnkSuitCountry = arnkSuitCountry;
	}

	public Integer getShareRewardType() {
		return this.shareRewardType;
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

	public Boolean getIsMixCabin() {
		return this.isMixCabin;
	}

	public void setIsMixCabin(Boolean isMixCabin) {
		this.isMixCabin = isMixCabin;
	}

	public String getMixCabinType() {
		return this.mixCabinType;
	}

	public void setMixCabinType(String mixCabinType) {
		this.mixCabinType = mixCabinType;
	}

	public Boolean getIsTourCodeIncludeText() {
		return this.isTourCodeIncludeText;
	}

	public void setIsTourCodeIncludeText(Boolean isTourCodeIncludeText) {
		this.isTourCodeIncludeText = isTourCodeIncludeText;
	}

	public String getTourCodeIncludeText() {
		return this.tourCodeIncludeText;
	}

	public void setTourCodeIncludeText(String tourCodeIncludeText) {
		this.tourCodeIncludeText = tourCodeIncludeText;
	}

	public Boolean getTourCodeNoAgency() {
		return this.tourCodeNoAgency;
	}

	public void setTourCodeNoAgency(Boolean tourCodeNoAgency) {
		this.tourCodeNoAgency = tourCodeNoAgency;
	}

	public Boolean getTourCodeNoReward() {
		return this.tourCodeNoReward;
	}

	public void setTourCodeNoReward(Boolean tourCodeNoReward) {
		this.tourCodeNoReward = tourCodeNoReward;
	}

	public String getTourCodeContent() {
		return this.tourCodeContent;
	}

	public void setTourCodeContent(String tourCodeContent) {
		this.tourCodeContent = tourCodeContent;
	}

	public Boolean getIsRoundTripIncludeCabin() {
		return this.isRoundTripIncludeCabin;
	}

	public void setIsRoundTripIncludeCabin(Boolean isRoundTripIncludeCabin) {
		this.isRoundTripIncludeCabin = isRoundTripIncludeCabin;
	}

	public String getRoundTripIncludeCabin() {
		return this.roundTripIncludeCabin;
	}

	public void setRoundTripIncludeCabin(String roundTripIncludeCabin) {
		this.roundTripIncludeCabin = roundTripIncludeCabin;
	}

	public Boolean getIsParLimit() {
		return this.isParLimit;
	}

	public void setIsParLimit(Boolean isParLimit) {
		this.isParLimit = isParLimit;
	}

	public String getParLimit() {
		return this.parLimit;
	}

	public void setParLimit(String parLimit) {
		this.parLimit = parLimit;
	}

	public Boolean getFareQAgency() {
		return this.fareQAgency;
	}

	public void setFareQAgency(Boolean fareQAgency) {
		this.fareQAgency = fareQAgency;
	}

	public Boolean getFareQReward() {
		return this.fareQReward;
	}

	public void setFareQReward(Boolean fareQReward) {
		this.fareQReward = fareQReward;
	}

	public Boolean getFareSAgency() {
		return this.fareSAgency;
	}

	public void setFareSAgency(Boolean fareSAgency) {
		this.fareSAgency = fareSAgency;
	}

	public Boolean getFareSReward() {
		return this.fareSReward;
	}

	public void setFareSReward(Boolean fareSReward) {
		this.fareSReward = fareSReward;
	}

	public Boolean getIsFareIncludeText() {
		return this.isFareIncludeText;
	}

	public void setIsFareIncludeText(Boolean isFareIncludeText) {
		this.isFareIncludeText = isFareIncludeText;
	}

	public String getFareIncludeText() {
		return this.fareIncludeText;
	}

	public void setFareIncludeText(String fareIncludeText) {
		this.fareIncludeText = fareIncludeText;
	}

	public Integer getFareIncludeType() {
		return this.fareIncludeType;
	}

	public void setFareIncludeType(Integer fareIncludeType) {
		this.fareIncludeType = fareIncludeType;
	}

	public Boolean getIsSuitFareBase() {
		return this.isSuitFareBase;
	}

	public void setIsSuitFareBase(Boolean isSuitFareBase) {
		this.isSuitFareBase = isSuitFareBase;
	}

	public String getSuitFareBase() {
		return this.suitFareBase;
	}

	public void setSuitFareBase(String suitFareBase) {
		this.suitFareBase = suitFareBase;
	}

	public Boolean getIsFlySuitFlight() {
		return this.isFlySuitFlight;
	}

	public void setIsFlySuitFlight(Boolean isFlySuitFlight) {
		this.isFlySuitFlight = isFlySuitFlight;
	}

	public String getFlySuitFlight() {
		return this.flySuitFlight;
	}

	public void setFlySuitFlight(String flySuitFlight) {
		this.flySuitFlight = flySuitFlight;
	}

	public Boolean getIsRtnSuitFlight() {
		return this.isRtnSuitFlight;
	}

	public void setIsRtnSuitFlight(Boolean isRtnSuitFlight) {
		this.isRtnSuitFlight = isRtnSuitFlight;
	}

	public String getRtnSuitFlight() {
		return this.rtnSuitFlight;
	}

	public void setRtnSuitFlight(String rtnSuitFlight) {
		this.rtnSuitFlight = rtnSuitFlight;
	}

	public Boolean getIsNotSuitFlight() {
		return this.isNotSuitFlight;
	}

	public void setIsNotSuitFlight(Boolean isNotSuitFlight) {
		this.isNotSuitFlight = isNotSuitFlight;
	}

	public String getNotSuitFlight() {
		return this.notSuitFlight;
	}

	public void setNotSuitFlight(String notSuitFlight) {
		this.notSuitFlight = notSuitFlight;
	}

	public Boolean getIsNotSuitRoute() {
		return this.isNotSuitRoute;
	}

	public void setIsNotSuitRoute(Boolean isNotSuitRoute) {
		this.isNotSuitRoute = isNotSuitRoute;
	}

	public String getNotSuitRoute() {
		return this.notSuitRoute;
	}

	public void setNotSuitRoute(String notSuitRoute) {
		this.notSuitRoute = notSuitRoute;
	}

	public String getFlyNotsuitCycle() {
		return this.flyNotsuitCycle;
	}

	public void setFlyNotsuitCycle(String flyNotsuitCycle) {
		this.flyNotsuitCycle = flyNotsuitCycle;
	}

	public String getRtnNotsuitCycle() {
		return this.rtnNotsuitCycle;
	}

	public void setRtnNotsuitCycle(String rtnNotsuitCycle) {
		this.rtnNotsuitCycle = rtnNotsuitCycle;
	}

	public String getFlyNotsuitDate() {
		return this.flyNotsuitDate;
	}

	public void setFlyNotsuitDate(String flyNotsuitDate) {
		this.flyNotsuitDate = flyNotsuitDate;
	}

	public String getRtnNotsuitDate() {
		return this.rtnNotsuitDate;
	}

	public void setRtnNotsuitDate(String rtnNotsuitDate) {
		this.rtnNotsuitDate = rtnNotsuitDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
