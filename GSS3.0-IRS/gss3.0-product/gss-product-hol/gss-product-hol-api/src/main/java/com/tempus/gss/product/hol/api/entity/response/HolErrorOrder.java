package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * 酒店订单mysql数据库保存信息
 */
@TableName("HOL_ERROR_ORDER")
public class HolErrorOrder implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**
     * Id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 销售订单编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long saleOrderNo;
    
    /**
     * 酒店编号
     */
    private String hotelCode;
    
    /**
     * 酒店名字
     */
    private String hotelName;
    
    /**
	 * 房型名称
	 */
	private String proName;
	
	/**
	 * 销售策略 Id
	 */
    @JsonSerialize(using = LongSerializer.class)
	private Long productUniqueId;
	
	/**
     * 入住人姓名
     */
    private String guestName;
    
    /**
     * 到住日期
     */
    private Date arrivalDate;

    /**
     * 离店日期
     */
    private Date departureDate;
    
    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactNumber;
    
    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createOrderTime;
    
    /**
     * 当前状态
     */
    private String orderStatus;
    
    /**
     * 下单返回的信息
     */
    private String msg;
    
    /**
     * 数据归属单位
     */
    //@TableField(exist = false)
    private Integer owner;
    
    /**
     * 订单状态
     */
    private String resultCode;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateOrderTime() {
		return createOrderTime;
	}

	public void setCreateOrderTime(Date createOrderTime) {
		this.createOrderTime = createOrderTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public Long getProductUniqueId() {
		return productUniqueId;
	}

	public void setProductUniqueId(Long productUniqueId) {
		this.productUniqueId = productUniqueId;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
}
