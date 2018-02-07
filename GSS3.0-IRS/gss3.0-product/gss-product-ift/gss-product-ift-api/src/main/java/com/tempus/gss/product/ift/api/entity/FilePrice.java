package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.bbp.util.DateUtil;
import com.tempus.gss.serializer.LongSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
public class FilePrice implements Serializable {

    /*** 编号*/
    private Long id;

    @Transient
    private String ids;

    /*** 政策编号*/
    @JsonSerialize(using = LongSerializer.class)
    private Long policyNo;
    /**
     * 政策类型
     */
    private String policyType;
    /**
     * 出票航司
     */
    private String airline;
    /**
     * 行程类型 1:单程 2:往返
     */
    private Integer tripType;
    /**
     * 去程出发城市
     */
    private String goStart;
    /**
     * 去程到达城市
     */
    private String goEnd;
    /**
     * 舱位
     */
    @Transient
    private String cabin;
    /**
     * 游客类型
     */
    private String travellerType;
    /**
     * 适用最小年龄
     */
    private Integer fitAgeMin;
    /**
     * 适用最大年龄
     */
    private Integer fitAgeMax;
    /**
     * 适用国籍
     */
    private String fitCitizenship;
    /**
     * 不适用国籍
     */
    private String unfitCitizenship;
    /**
     * 舱位等级
     */
    @Transient
    private String cabinGrade;
    /**
     * 价格
     */
    @Transient
    private Double price;
    /**
     * 游客人数下限
     */
    private Integer travellerLimit;
    /**
     * 票本类型
     */
    private String ticketWay;
    /**
     * 币种
     */
    private String currency;
    /**
     * 汇率
     */
    private String exchangeRate;
    /**
     * 代理费
     */
    private Double agencyFee;
    /**
     * 奖励
     */
    private Double award;
    /**
     * 出票时间
     */
    private String ticketDate;
    /**
     * 出票时间起
     */
    @Transient
    private String ticketDateStart;
    /**
     * 出票时间止
     */
    @Transient
    private String ticketDateEnd;
    /**
     * 去程适用航班
     */
    private String goFlightNo;
    /**
     * 去程限制航班
     */
    private String goExceptFlightNo;
    /**
     * 回程适用航班
     */
    private String backFlightNo;
    /**
     * 回程限制航班
     */
    private String backExceptFlightNo;
    /**
     * 政策开始日期
     */
    private String policyEffectStart;
    /**
     * 政策结束日期
     */
    private String policyEffectEnd;
    /**
     * 文件编号
     */
    private String fileNo;
    /**
     * 备注
     */
    private String remark;
    /**
     * 数据归属单位
     */
    private Integer owner                 ;
    /**
     * 投放分销商
     */
    private String distributor           ;
    /**
     * 产品商
     */
    private String productor             ;
    /**
     * 销售配置
     */
    private String saleConfig           ;
    /**
     * 产品号
     */
    private String productNo            ;
    /**
     * 团队出票类型
     */
    private Integer teamTicketType      ;
    /**
     * 去程起点除外
     */
    private String exceptGoStart       ;
    /**
     * 去程终点/折返点除外
     */
    private String exceptGoEnd         ;
    /**
     * 回程终点
     */
    private String backEnd              ;
    /**
     * 回程终点除外
     */
    private String exceptBackEnd       ;
    /**
     * 跨航线组合匹配
     */
    private Integer allowCrossLineMatch;
    /**
     * 去程旅行日期
     */

    private String travelDate           ;

    /***
     * 去程旅行日期始
     */
    @Transient
    private String travelDateStart;

    /***
     * 去程旅行日期止
     */
    @Transient
    private String travelDateEnd;

    /**
     * 回程旅行日期
     */

    private String backDate             ;

    /**
     * 回程旅行日期始
     */
    @Transient
    private String backDateStart;

    /**
     * 回程旅行日期止
     */
    @Transient
    private String backDateEnd;

    /***
     * 全程是否同一承运人
     */
    private Integer isSameCarrier       ;
    /***
     * 是否代码共享
     */
    private Integer isCodeShare         ;
    /**
     * 共享航司
     */
    private String shareAirline         ;
    /***
     * 是否必须直飞
     */
    private Integer onlyDirectFlight    ;
    /***
     * 去程不能经过
     */
    private String goNotFlight         ;
    /**
     * 去程必须经过
     */
    private String goMustFlight        ;
    /**
     * 回程不能经过
     */
    private String backNotFlight       ;
    /**
     * 回程必须经过
     */
    private String backMustFlight      ;
    /**
     * 状态
     */
    private Integer status                ;
    /**
     * 创建时间
     */
    private Date createTime           ;
    /**
     * 修改时间
     */
    private Date modifyTime           ;
    /**
     * 创建者
     */
    private String creator               ;
    /**
     * 修改者
     */
    private String modifier              ;
    /**
     *删除标志 0 无效 已删除 1 有效
     */
    private Integer valid                 ;
    /**
     * 数据版本号
     */
    private Integer version               ;
    /**
     * 是否生成PNR: 0:否(默认); 1:是
     */
    private Integer advPnr               ;
    /**
     * 打底政策标识, 0:否(默认); 1:是
     */
    private Integer isDefault            ;
    /**
     * 开票office
     */
    private String  invoiceOffice;
    /**
     * 旅行时间范围
     */
    @Transient
    private String travelDateScope;

    /**
     * 修改信息
     */
    @Transient
    private String changeInfo;

    /***
     *返佣信息
     */
    @Transient
    private String fyInfo;

    /**
     * 是否需要审核
     * 0:不需要 1:需要
     */
    private Integer isReview;
    /**舱位价格信息集合*/
    private List<Ift_cabin> cabinList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Long getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(Long policyNo) {
        this.policyNo = policyNo;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public Integer getTripType() {
        return tripType;
    }

    public void setTripType(Integer tripType) {
        this.tripType = tripType;
    }

    public String getGoStart() {
        return goStart;
    }

    public void setGoStart(String goStart) {
        this.goStart = goStart;
    }

    public String getGoEnd() {
        return goEnd;
    }

    public void setGoEnd(String goEnd) {
        this.goEnd = goEnd;
    }

    public String getBackEnd() {
        return backEnd;
    }

    public void setBackEnd(String backEnd) {
        this.backEnd = backEnd;
    }

    public String getExceptBackEnd() {
        return exceptBackEnd;
    }

    public void setExceptBackEnd(String exceptBackEnd) {
        this.exceptBackEnd = exceptBackEnd;
    }

    public Integer getAllowCrossLineMatch() {
        return allowCrossLineMatch;
    }

    public void setAllowCrossLineMatch(Integer allowCrossLineMatch) {
        this.allowCrossLineMatch = allowCrossLineMatch;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getTravelDateStart() {
        return travelDateStart;
    }

    public void setTravelDateStart(String travelDateStart) {
        this.travelDateStart = travelDateStart;
    }

    public String getTravelDateEnd() {
        return travelDateEnd;
    }

    public void setTravelDateEnd(String travelDateEnd) {
        this.travelDateEnd = travelDateEnd;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public String getBackDateStart() {
        return backDateStart;
    }

    public void setBackDateStart(String backDateStart) {
        this.backDateStart = backDateStart;
    }

    public String getBackDateEnd() {
        return backDateEnd;
    }

    public void setBackDateEnd(String backDateEnd) {
        this.backDateEnd = backDateEnd;
    }

    public Integer getIsSameCarrier() {
        return isSameCarrier;
    }

    public void setIsSameCarrier(Integer isSameCarrier) {
        this.isSameCarrier = isSameCarrier;
    }

    public Integer getIsCodeShare() {
        return isCodeShare;
    }

    public void setIsCodeShare(Integer isCodeShare) {
        this.isCodeShare = isCodeShare;
    }

    public String getShareAirline() {
        return shareAirline;
    }

    public void setShareAirline(String shareAirline) {
        this.shareAirline = shareAirline;
    }

    public Integer getOnlyDirectFlight() {
        return onlyDirectFlight;
    }

    public void setOnlyDirectFlight(Integer onlyDirectFlight) {
        this.onlyDirectFlight = onlyDirectFlight;
    }

    public String getGoNotFlight() {
        return goNotFlight;
    }

    public void setGoNotFlight(String goNotFlight) {
        this.goNotFlight = goNotFlight;
    }

    public String getGoMustFlight() {
        return goMustFlight;
    }

    public void setGoMustFlight(String goMustFlight) {
        this.goMustFlight = goMustFlight;
    }

    public String getBackNotFlight() {
        return backNotFlight;
    }

    public void setBackNotFlight(String backNotFlight) {
        this.backNotFlight = backNotFlight;
    }

    public String getBackMustFlight() {
        return backMustFlight;
    }

    public void setBackMustFlight(String backMustFlight) {
        this.backMustFlight = backMustFlight;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getAdvPnr() {
        return advPnr;
    }

    public void setAdvPnr(Integer advPnr) {
        this.advPnr = advPnr;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public String getInvoiceOffice() {
        return invoiceOffice;
    }

    public void setInvoiceOffice(String invoiceOffice) {
        this.invoiceOffice = invoiceOffice;
    }

    public String getTravelDateScope() {
        String StrDate="";
        if(StringUtils.isNotBlank(this.travelDate)&&StringUtils.isNotBlank(this.backDate)){
            StrDate = this.ticketDate+"-"+this.backDate;
        }
        return StrDate;
    }

    public void setTravelDateScope(String travelDateScope) {
        this.travelDateScope = travelDateScope;
    }

    public String getChangeInfo() {
        String info = "";
        if(StringUtils.isNotBlank(this.modifier)&&this.modifyTime!=null){
            String modify_date = DateUtil.dateToDateString(this.modifyTime,"YYYYMMdd HH:mm:ss");
            info = this.modifier+"<br/>"+modify_date;
        }
        return info;
    }

    public void setChangeInfo(String changeInfo) {
        this.changeInfo = changeInfo;
    }

    public String getFyInfo() {
        return fyInfo;
    }

    public void setFyInfo(String fyInfo) {
        this.fyInfo = fyInfo;
    }

    public List<Ift_cabin> getCabinList() {
        return cabinList;
    }

    public void setCabinList(List<Ift_cabin> cabinList) {
        this.cabinList = cabinList;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public String getTravellerType() {
        return travellerType;
    }

    public void setTravellerType(String travellerType) {
        this.travellerType = travellerType;
    }

    public Integer getFitAgeMin() {
        return fitAgeMin;
    }

    public void setFitAgeMin(Integer fitAgeMin) {
        this.fitAgeMin = fitAgeMin;
    }

    public Integer getFitAgeMax() {
        return fitAgeMax;
    }

    public void setFitAgeMax(Integer fitAgeMax) {
        this.fitAgeMax = fitAgeMax;
    }

    public String getFitCitizenship() {
        return fitCitizenship;
    }

    public void setFitCitizenship(String fitCitizenship) {
        this.fitCitizenship = fitCitizenship;
    }

    public String getUnfitCitizenship() {
        return unfitCitizenship;
    }

    public void setUnfitCitizenship(String unfitCitizenship) {
        this.unfitCitizenship = unfitCitizenship;
    }

    public String getCabinGrade() {
        return cabinGrade;
    }

    public void setCabinGrade(String cabinGrade) {
        this.cabinGrade = cabinGrade;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTravellerLimit() {
        return travellerLimit;
    }

    public void setTravellerLimit(Integer travellerLimit) {
        this.travellerLimit = travellerLimit;
    }

    public String getTicketWay() {
        return ticketWay;
    }

    public void setTicketWay(String ticketWay) {
        this.ticketWay = ticketWay;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Double getAgencyFee() {
        return agencyFee;
    }

    public void setAgencyFee(Double agencyFee) {
        this.agencyFee = agencyFee;
    }

    public Double getAward() {
        return award;
    }

    public void setAward(Double award) {
        this.award = award;
    }

    public String getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }

    public String getTicketDateStart() {
        return ticketDateStart;
    }

    public void setTicketDateStart(String ticketDateStart) {
        this.ticketDateStart = ticketDateStart;
    }

    public String getTicketDateEnd() {
        return ticketDateEnd;
    }

    public void setTicketDateEnd(String ticketDateEnd) {
        this.ticketDateEnd = ticketDateEnd;
    }

    public String getGoFlightNo() {
        return goFlightNo;
    }

    public void setGoFlightNo(String goFlightNo) {
        this.goFlightNo = goFlightNo;
    }

    public String getGoExceptFlightNo() {
        return goExceptFlightNo;
    }

    public void setGoExceptFlightNo(String goExceptFlightNo) {
        this.goExceptFlightNo = goExceptFlightNo;
    }

    public String getBackFlightNo() {
        return backFlightNo;
    }

    public void setBackFlightNo(String backFlightNo) {
        this.backFlightNo = backFlightNo;
    }

    public String getBackExceptFlightNo() {
        return backExceptFlightNo;
    }

    public void setBackExceptFlightNo(String backExceptFlightNo) {
        this.backExceptFlightNo = backExceptFlightNo;
    }

    public String getPolicyEffectStart() {
        return policyEffectStart;
    }

    public void setPolicyEffectStart(String policyEffectStart) {
        this.policyEffectStart = policyEffectStart;
    }

    public String getPolicyEffectEnd() {
        return policyEffectEnd;
    }

    public void setPolicyEffectEnd(String policyEffectEnd) {
        this.policyEffectEnd = policyEffectEnd;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getSaleConfig() {
        return saleConfig;
    }

    public void setSaleConfig(String saleConfig) {
        this.saleConfig = saleConfig;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Integer getTeamTicketType() {
        return teamTicketType;
    }

    public void setTeamTicketType(Integer teamTicketType) {
        this.teamTicketType = teamTicketType;
    }

    public String getExceptGoStart() {
        return exceptGoStart;
    }

    public void setExceptGoStart(String exceptGoStart) {
        this.exceptGoStart = exceptGoStart;
    }

    public String getExceptGoEnd() {
        return exceptGoEnd;
    }

    public void setExceptGoEnd(String exceptGoEnd) {
        this.exceptGoEnd = exceptGoEnd;
    }

    public Integer getIsReview() {
        return isReview;
    }

    public void setIsReview(Integer isReview) {
        this.isReview = isReview;
    }
}


