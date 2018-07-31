package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店房型信息
 * @author kai.yang
 *
 */
public class ResProBaseInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店 Id
	 */
	@JSONField(name = "ResId")
	private Long resId;
	/**
	 * 房型 Id
	 */
	@JSONField(name = "ProId")
	private String proId;
	/**
	 * 政策名称
	 */
	@JSONField(name = "SupPriceName")
	private String supPriceName;
	/**
	 * 酒店房型名称（对应房型名称/票型名称）
	 */
	@JSONField(name = "ResProName")
	private String resProName;
	/**
	 * 房型面积
	 */
	@JSONField(name = "RoomSize")
	private String roomSize;
	/**
	 * 单数楼层
	 */
	@JSONField(name = "RoomFloor")
	private String roomFloor;
	/**
	 * 成人数
	 */
	@JSONField(name = "AdultCount")
	private Integer adultCount;
	/**
	 * 儿童数
	 */
	@JSONField(name = "ChildCount")
	private Integer childCount;
	/**
	 * 酒店房型预订需提前天数，0 表示当天预订
	 */
	@JSONField(name = "AdvanceBooking")
	private Integer advanceBooking;
	/**
	 * 酒店房型预订说明(bqy取消政策)
	 */
	@JSONField(name = "BookingNotes")
	private String bookingNotes;
	/**
	 * 备注
	 */
	@JSONField(name = "Remark")
	private String remark;
	/**
	 * 销售策略 Id
	 */
	@JSONField(name = "ProductUniqueId")
	private String productUniqueId;
	/**
	 * 客房特点等信息：外宾专用等 (bqy用来存储预付房型检查字段   ratePlanCategory)
	 */
	@JSONField(name = "RoomFeature")
	private String roomFeature;
	/**
	 * 房型设施
	 */
	@JSONField(name = "RoomFacilities")
	private List<FacilityServices> roomFacilities;
	/**
	 * 早餐份数
	 */
	@JSONField(name = "BreakfastCount")
	private Integer breakfastCount;
	/**
	 * 政策来源(bqy政策类型)
	 */
	@JSONField(name = "SourceFrom")
	private Long sourceFrom;
	/**
	 * 宾客类型 (bqy用来存储酒店代理人Id supplierId)
	 */
	@JSONField(name = "CustomerType")
	private String customerType;
	/**
	 * 床型 ID
	 */
	@JSONField(name = "BedTypeId")
	private Long bedTypeId;
	/**
	 * 床型
	 */
	@JSONField(name = "BedTypeName")
	private String bedTypeName;
	/**
	 * 床宽
	 */
	@JSONField(name = "BedSize")
	private String bedSize;
	/**
	 * 是否有窗（0：无，1：有，2：部分有窗）
	 */
	@JSONField(name = "HasWindows")
	private Byte hasWindows;
	/**
	 * 是否可以无烟处理（0：无，1：有）
	 */
	@JSONField(name = "NonSmoking")
	private Byte nonSmoking;
	/**
	 * 是否有宽带
	 */
	@JSONField(name = "HasBroadband")
	private List<String> hasBroadband;
	/**
	 * 入住类型（0：标准；1：钟点房；2：午夜房)
	 */
	@JSONField(name = "OccupancyType")
	private Byte occupancyType;
	/**
	 * 支付方式（0：All-全部；1：SelfPay-现付；2：Prepay-预付）
	 */
	@JSONField(name = "PaymnetType")
	private Integer paymnetType;
	/**
	 * 用餐备注说明
	 */
	@JSONField(name = "DiningNotes")
	private String diningNotes;
	/**
	 * 限制几天内预订
	 */
	@JSONField(name = "AdvancedLimitDays")
	private Integer advancedLimitDays;
	/**
	 * 当宾客类型为 2 的时候，其他描述信息要根据
	 */
	@JSONField(name = "OtherDescription")
	private String otherDescription;
	/**
	 * 政策备注(bqy政策取消时间)
	 */
	@JSONField(name = "PolicyRemark")
	private String policyRemark;
	/**
	 * 禁止销售的渠道；1-携程，2-艺龙，3-去哪儿，4-飞猪，5-美团；多个用英文逗号隔开，例：1,2
	 */
	@JSONField(name = "FobidenSalesChannel")
	private String fobidenSalesChannel;
	/**
	 * 房型剩余库存量,价格详情value 为日期所对应的房型剩余库存量,价格详情
	 */
	@JSONField(name = "ProSaleInfoDetailsTarget")
	private TreeMap<String, ProSaleInfoDetail> proSaleInfoDetailsTarget;
	/**
	 * 平均价格
	 */
	@JSONField(name = "ConPrice")
	private Integer conPrice;
	/**
	 * 首日价
	 */
	@JSONField(name = "FirPrice")
	private Integer firPrice;
	/**
	 * 用户输入的天数
	 */
	private Integer userDays;
	/**
	 * 用户输入天数后计算的房间总价
	 */
	private Integer userSumPrice;
	/**
	 * 剩余库存 (bqy房间数)
	 */
	@JSONField(name = "ProMinInventory")
	private Integer proMinInventory;
	/**
	 * 返佣率
	 */
	private BigDecimal rebateRateProfit;
	/**
	 * 总佣金
	 */
	private BigDecimal totalRebateRateProfit;
	/**
	 * 0为下线，默认1上线
	 */
	private Integer saleStatus = 1;
	/**
	 * 0为不可售，默认1可售
	 */
	private Integer bookStatus = 1;
	/**
	 * 房间类型 1是TC, 默认2为BQY
	 */
	private Integer supplierType = 1;
	
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getSupPriceName() {
		return supPriceName;
	}
	public void setSupPriceName(String supPriceName) {
		this.supPriceName = supPriceName;
	}
	public String getResProName() {
		return resProName;
	}
	public void setResProName(String resProName) {
		this.resProName = resProName;
	}
	public String getRoomSize() {
		return roomSize;
	}
	public void setRoomSize(String roomSize) {
		this.roomSize = roomSize;
	}
	public String getRoomFloor() {
		return roomFloor;
	}
	public void setRoomFloor(String roomFloor) {
		this.roomFloor = roomFloor;
	}
	public Integer getAdultCount() {
		return adultCount;
	}
	public void setAdultCount(Integer adultCount) {
		this.adultCount = adultCount;
	}
	public Integer getChildCount() {
		return childCount;
	}
	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}
	public Integer getAdvanceBooking() {
		return advanceBooking;
	}
	public void setAdvanceBooking(Integer advanceBooking) {
		this.advanceBooking = advanceBooking;
	}
	public String getBookingNotes() {
		return bookingNotes;
	}
	public void setBookingNotes(String bookingNotes) {
		this.bookingNotes = bookingNotes;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProductUniqueId() {
		return productUniqueId;
	}
	public void setProductUniqueId(String productUniqueId) {
		this.productUniqueId = productUniqueId;
	}
	public String getRoomFeature() {
		return roomFeature;
	}
	public void setRoomFeature(String roomFeature) {
		this.roomFeature = roomFeature;
	}
	public List<FacilityServices> getRoomFacilities() {
		return roomFacilities;
	}
	public void setRoomFacilities(List<FacilityServices> roomFacilities) {
		this.roomFacilities = roomFacilities;
	}
	public Integer getBreakfastCount() {
		return breakfastCount;
	}
	public void setBreakfastCount(Integer breakfastCount) {
		this.breakfastCount = breakfastCount;
	}
	public Long getSourceFrom() {
		return sourceFrom;
	}
	public void setSourceFrom(Long sourceFrom) {
		this.sourceFrom = sourceFrom;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public Long getBedTypeId() {
		return bedTypeId;
	}
	public void setBedTypeId(Long bedTypeId) {
		this.bedTypeId = bedTypeId;
	}
	public String getBedTypeName() {
		return bedTypeName;
	}
	public void setBedTypeName(String bedTypeName) {
		this.bedTypeName = bedTypeName;
	}
	public String getBedSize() {
		return bedSize;
	}
	public void setBedSize(String bedSize) {
		this.bedSize = bedSize;
	}
	public Byte getHasWindows() {
		return hasWindows;
	}
	public void setHasWindows(Byte hasWindows) {
		this.hasWindows = hasWindows;
	}
	public Byte getNonSmoking() {
		return nonSmoking;
	}
	public void setNonSmoking(Byte nonSmoking) {
		this.nonSmoking = nonSmoking;
	}
	public List<String> getHasBroadband() {
		return hasBroadband;
	}
	public void setHasBroadband(List<String> hasBroadband) {
		this.hasBroadband = hasBroadband;
	}
	public Byte getOccupancyType() {
		return occupancyType;
	}
	public void setOccupancyType(Byte occupancyType) {
		this.occupancyType = occupancyType;
	}
	public Integer getPaymnetType() {
		return paymnetType;
	}
	public void setPaymnetType(Integer paymnetType) {
		this.paymnetType = paymnetType;
	}
	public String getDiningNotes() {
		return diningNotes;
	}
	public void setDiningNotes(String diningNotes) {
		this.diningNotes = diningNotes;
	}
	public Integer getAdvancedLimitDays() {
		return advancedLimitDays;
	}
	public void setAdvancedLimitDays(Integer advancedLimitDays) {
		this.advancedLimitDays = advancedLimitDays;
	}
	public String getOtherDescription() {
		return otherDescription;
	}
	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}
	public String getPolicyRemark() {
		return policyRemark;
	}
	public void setPolicyRemark(String policyRemark) {
		this.policyRemark = policyRemark;
	}
	public String getFobidenSalesChannel() {
		return fobidenSalesChannel;
	}
	public void setFobidenSalesChannel(String fobidenSalesChannel) {
		this.fobidenSalesChannel = fobidenSalesChannel;
	}
	public TreeMap<String, ProSaleInfoDetail> getProSaleInfoDetailsTarget() {
		return proSaleInfoDetailsTarget;
	}
	@JSONField(deserialize=false)
	public void setProSaleInfoDetailsTarget(TreeMap<String, ProSaleInfoDetail> proSaleInfoDetailsTarget) {
		this.proSaleInfoDetailsTarget = proSaleInfoDetailsTarget;
	}
	public Integer getConPrice() {
		return conPrice;
	}
	@JSONField(deserialize=false)
	public void setConPrice(Integer conPrice) {
		this.conPrice = conPrice;
	}
	public Integer getFirPrice() {
		return firPrice;
	}
	@JSONField(deserialize=false)
	public void setFirPrice(Integer firPrice) {
		this.firPrice = firPrice;
	}
	/*public Integer getProMinInventory() {
		return proMinInventory;
	}
	@JSONField(deserialize=false)
	public void setProMinInventory(Integer proMinInventory) {
		this.proMinInventory = proMinInventory;
	}*/
	public Integer getUserDays() {
		return userDays;
	}
	@JSONField(deserialize=false)
	public void setUserDays(Integer userDays) {
		this.userDays = userDays;
	}
	public Integer getUserSumPrice() {
		return userSumPrice;
	}
	@JSONField(deserialize=false)
	public void setUserSumPrice(Integer userSumPrice) {
		this.userSumPrice = userSumPrice;
	}
	public BigDecimal getRebateRateProfit() {
		return rebateRateProfit;
	}
	@JSONField(deserialize=false)
	public void setRebateRateProfit(BigDecimal rebateRateProfit) {
		this.rebateRateProfit = rebateRateProfit;
	}
	public BigDecimal getTotalRebateRateProfit() {
		return totalRebateRateProfit;
	}
	@JSONField(deserialize=false)
	public void setTotalRebateRateProfit(BigDecimal totalRebateRateProfit) {
		this.totalRebateRateProfit = totalRebateRateProfit;
	}
	public Integer getSaleStatus() {
		return saleStatus;
	}
	@JSONField(deserialize=false)
	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}
	public Integer getBookStatus() {
		return bookStatus;
	}
	@JSONField(deserialize=false)
	public void setBookStatus(Integer bookStatus) {
		this.bookStatus = bookStatus;
	}
	public Integer getSupplierType() {
		return supplierType;
	}
	@JSONField(deserialize=false)
	public void setSupplierType(Integer supplierType) {
		this.supplierType = supplierType;
	}
	public Integer getProMinInventory() {
		return proMinInventory;
	}
	public void setProMinInventory(Integer proMinInventory) {
		this.proMinInventory = proMinInventory;
	}
	
	
}
