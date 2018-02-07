package com.tempus.gss.product.hol.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 酒店订单列表查询条件
 */
public class HotelOrderVo implements Serializable{

    /** 销售订单编号 */
    private Long saleOrderNo;

    /** 交易单号 */
    private Long transactionNo;

    /** 下单开始时间 */
    private Date createStartTime;

    /** 下单结束时间 */
    private Date createEndTime;

    /** 入住开始日期 */
    private Date arrivalStartDate;
    /**
     * 入住结束日期
     */
    private Date arrivalEndDate;

    /** 离店开始日期 */
    private Date departureStartDate;
    /**
     * 离店结束日期
     */
    private Date departureEndDate;

    /** 酒店名称 */
    private String hotelName;

    /** 订单状态 */
    private String orderStatus;

    /** 入住人姓名 */
    private String guestName;

    /** 数据归属单位 */
    private Integer owner;

    /** 创建人 */
    private String creator;

    /** 客商编号 */
    private Long customerNo;
    /**
     * 订单号
     */
    private String hotelOrderNo;
    /**
     * 订单状态
     */
    private String resultCode;
    /**
     * 确认是否需要查询下级分销商
     */
    private Boolean subCustomer=false;
    
    /**
     * 联系人姓名
     */
    private String contactName;
    
    
    /**
     * 携程单号
     */
    private String supplierNumber;
    
    
    /**
     * 城市
     */
    private String cityName;
    
    /**
     * 供应订单状态--TC_ORDER_STATUS
     */
    private String supplierOrderStatus;
    
    
    
    /**
     * 保存前台搜索的日期类型
     * 1:入住  2离店 3预订
     * @return
     */
    private int dateType;
    
    /**
     * 锁定的用户的Id 有大于0的值，表示已被用户锁定.
     */
    private Long locker;
    
    public Long getSaleOrderNo() {
        return saleOrderNo;
    }

    public int getDateType() {
		return dateType;
	}

	public void setDateType(int dateType) {
		this.dateType = dateType;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public Date getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    public Date getArrivalStartDate() {
		return arrivalStartDate;
	}

	public void setArrivalStartDate(Date arrivalStartDate) {
		this.arrivalStartDate = arrivalStartDate;
	}

	public Date getArrivalEndDate() {
		return arrivalEndDate;
	}

	public void setArrivalEndDate(Date arrivalEndDate) {
		this.arrivalEndDate = arrivalEndDate;
	}

	public Date getDepartureStartDate() {
		return departureStartDate;
	}

	public void setDepartureStartDate(Date departureStartDate) {
		this.departureStartDate = departureStartDate;
	}

	public Date getDepartureEndDate() {
		return departureEndDate;
	}

	public void setDepartureEndDate(Date departureEndDate) {
		this.departureEndDate = departureEndDate;
	}

	public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(Long transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Long getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }

	public String getHotelOrderNo() {
		return hotelOrderNo;
	}

	public void setHotelOrderNo(String hotelOrderNo) {
		this.hotelOrderNo = hotelOrderNo;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public Boolean getSubCustomer() {
		return subCustomer;
	}

	public void setSubCustomer(Boolean subCustomer) {
		this.subCustomer = subCustomer;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getSupplierOrderStatus() {
		return supplierOrderStatus;
	}

	public void setSupplierOrderStatus(String supplierOrderStatus) {
		this.supplierOrderStatus = supplierOrderStatus;
	}

	public Long getLocker() {
		return locker;
	}

	public void setLocker(Long locker) {
		this.locker = locker;
	}
	
}
