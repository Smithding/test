package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;


public class RoomInfoItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<String> tagName;
	
	@JSONField(name="SupplierId")
	private String supplierId;
	
	private SmokeInfo smokeInfo;
	
	private BroadNetInfo broadNetInfo;
	
	private RoomBedTypeInfo roomBedTypeInfo;
	
	private RoomCurrencyInfo roomCurrencyInfo;
	
	private ApplicabilityInfo applicabilityInfo;
	
	private RoomFGToPPInfo roomFGToPPInfo;
	
	private PromotionInfo promotionInfo;
	
	private MemberLimitInfo memberLimitInfo;
	
	private ChannelLimitInfo channelLimitInfo;
	
	private CancelLimitInfo cancelLimitInfo;
	
	private ReserveTimeLimitInfo reserveTimeLimitInfo;
	
	private RoomPriceInfo roomPriceInfo;
	
	@JSONField(name="RoomTags")
	private List<String> roomTags;
	
	@JSONField(name="RoomID")
	private String roomID;
	
	@JSONField(name="RoomName")
    private String roomName;
	
	@JSONField(name="Person")
    private String person;
	
	@JSONField(name="AreaRange")
    private String areaRange;
	
	@JSONField(name="FloorRange")
    private String floorRange;
	
	@JSONField(name="HasWindow")
    private String hasWindow;
	
	@JSONField(name="AddBedFee")
    private String addBedFee;
	
	@JSONField(name="Children")
    private String children;
	
	@JSONField(name="Isstringerface")
    private Boolean isstringerface;
	
	@JSONField(name="IsShowAgent")
    private Boolean isShowAgent;
	
	@JSONField(name="IsGuaranteed")
    private Boolean isGuaranteed;
	
	@JSONField(name="IsHourRoom")
    private Boolean isHourRoom;
	
	@JSONField(name="InvoiceTargetType")
    private String invoiceTargetType;
	
	@JSONField(name="IsSupportAnticipation")
    private Boolean isSupportAnticipation;
	
	@JSONField(name="AnticipationCoefficient")
    private String anticipationCoefficient;

	public List<String> getTagName() {
		return tagName;
	}

	public void setTagName(List<String> tagName) {
		this.tagName = tagName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public SmokeInfo getSmokeInfo() {
		return smokeInfo;
	}

	public void setSmokeInfo(SmokeInfo smokeInfo) {
		this.smokeInfo = smokeInfo;
	}

	public BroadNetInfo getBroadNetInfo() {
		return broadNetInfo;
	}

	public void setBroadNetInfo(BroadNetInfo broadNetInfo) {
		this.broadNetInfo = broadNetInfo;
	}

	public RoomBedTypeInfo getRoomBedTypeInfo() {
		return roomBedTypeInfo;
	}

	public void setRoomBedTypeInfo(RoomBedTypeInfo roomBedTypeInfo) {
		this.roomBedTypeInfo = roomBedTypeInfo;
	}

	public RoomCurrencyInfo getRoomCurrencyInfo() {
		return roomCurrencyInfo;
	}

	public void setRoomCurrencyInfo(RoomCurrencyInfo roomCurrencyInfo) {
		this.roomCurrencyInfo = roomCurrencyInfo;
	}

	public ApplicabilityInfo getApplicabilityInfo() {
		return applicabilityInfo;
	}

	public void setApplicabilityInfo(ApplicabilityInfo applicabilityInfo) {
		this.applicabilityInfo = applicabilityInfo;
	}

	public RoomFGToPPInfo getRoomFGToPPInfo() {
		return roomFGToPPInfo;
	}

	public void setRoomFGToPPInfo(RoomFGToPPInfo roomFGToPPInfo) {
		this.roomFGToPPInfo = roomFGToPPInfo;
	}

	public PromotionInfo getPromotionInfo() {
		return promotionInfo;
	}

	public void setPromotionInfo(PromotionInfo promotionInfo) {
		this.promotionInfo = promotionInfo;
	}

	public MemberLimitInfo getMemberLimitInfo() {
		return memberLimitInfo;
	}

	public void setMemberLimitInfo(MemberLimitInfo memberLimitInfo) {
		this.memberLimitInfo = memberLimitInfo;
	}

	public ChannelLimitInfo getChannelLimitInfo() {
		return channelLimitInfo;
	}

	public void setChannelLimitInfo(ChannelLimitInfo channelLimitInfo) {
		this.channelLimitInfo = channelLimitInfo;
	}

	public CancelLimitInfo getCancelLimitInfo() {
		return cancelLimitInfo;
	}

	public void setCancelLimitInfo(CancelLimitInfo cancelLimitInfo) {
		this.cancelLimitInfo = cancelLimitInfo;
	}

	public ReserveTimeLimitInfo getReserveTimeLimitInfo() {
		return reserveTimeLimitInfo;
	}

	public void setReserveTimeLimitInfo(ReserveTimeLimitInfo reserveTimeLimitInfo) {
		this.reserveTimeLimitInfo = reserveTimeLimitInfo;
	}

	public RoomPriceInfo getRoomPriceInfo() {
		return roomPriceInfo;
	}

	public void setRoomPriceInfo(RoomPriceInfo roomPriceInfo) {
		this.roomPriceInfo = roomPriceInfo;
	}

	public List<String> getRoomTags() {
		return roomTags;
	}

	public void setRoomTags(List<String> roomTags) {
		this.roomTags = roomTags;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getAreaRange() {
		return areaRange;
	}

	public void setAreaRange(String areaRange) {
		this.areaRange = areaRange;
	}

	public String getFloorRange() {
		return floorRange;
	}

	public void setFloorRange(String floorRange) {
		this.floorRange = floorRange;
	}

	public String getHasWindow() {
		return hasWindow;
	}

	public void setHasWindow(String hasWindow) {
		this.hasWindow = hasWindow;
	}

	public String getAddBedFee() {
		return addBedFee;
	}

	public void setAddBedFee(String addBedFee) {
		this.addBedFee = addBedFee;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public Boolean getIsstringerface() {
		return isstringerface;
	}

	public void setIsstringerface(Boolean isstringerface) {
		this.isstringerface = isstringerface;
	}

	public Boolean getIsShowAgent() {
		return isShowAgent;
	}

	public void setIsShowAgent(Boolean isShowAgent) {
		this.isShowAgent = isShowAgent;
	}

	public Boolean getIsGuaranteed() {
		return isGuaranteed;
	}

	public void setIsGuaranteed(Boolean isGuaranteed) {
		this.isGuaranteed = isGuaranteed;
	}

	public Boolean getIsHourRoom() {
		return isHourRoom;
	}

	public void setIsHourRoom(Boolean isHourRoom) {
		this.isHourRoom = isHourRoom;
	}

	public String getInvoiceTargetType() {
		return invoiceTargetType;
	}

	public void setInvoiceTargetType(String invoiceTargetType) {
		this.invoiceTargetType = invoiceTargetType;
	}

	public Boolean getIsSupportAnticipation() {
		return isSupportAnticipation;
	}

	public void setIsSupportAnticipation(Boolean isSupportAnticipation) {
		this.isSupportAnticipation = isSupportAnticipation;
	}

	public String getAnticipationCoefficient() {
		return anticipationCoefficient;
	}

	public void setAnticipationCoefficient(String anticipationCoefficient) {
		this.anticipationCoefficient = anticipationCoefficient;
	}
	
}
