package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

public class InRefundApprefRequest implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  Long id;
	private String beforeDate;
	private String endDate;
	private String operator;
	private String operatetime;
	private Integer[] refundStatus;
	protected String accountnumber;
    protected String applytime;
    protected String applyuser;
    protected String bankname;
    protected String beneficiarysname;
    protected String businessline;
    protected String customername;
    protected String oldorderid;
    protected String orderid;
    protected String platformOrderid;
    protected Double refundaccount;
    protected String remark;
    protected String saledep;
    protected Long settlementId;
    protected String status;
    protected String tanagementarea;
    protected String ticketno;
      protected String refType;

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBeforeDate() {
		return beforeDate;
	}

	public void setBeforeDate(String beforeDate) {
		this.beforeDate = beforeDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatetime() {
		return operatetime;
	}

	public void setOperatetime(String operatetime) {
		this.operatetime = operatetime;
	}

	public Integer[] getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer[] refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String value) {
        this.accountnumber = value;
    }

    public String getApplytime() {
        return applytime;
    }

    public void setApplytime(String value) {
        this.applytime = value;
    }

    public String getApplyuser() {
        return applyuser;
    }

    public void setApplyuser(String value) {
        this.applyuser = value;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String value) {
        this.bankname = value;
    }

    public String getBeneficiarysname() {
        return beneficiarysname;
    }

    public void setBeneficiarysname(String value) {
        this.beneficiarysname = value;
    }

    public String getBusinessline() {
        return businessline;
    }

    public void setBusinessline(String value) {
        this.businessline = value;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String value) {
        this.customername = value;
    }

    public String getOldorderid() {
        return oldorderid;
    }

    public void setOldorderid(String value) {
        this.oldorderid = value;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String value) {
        this.orderid = value;
    }

    public String getPlatformOrderid() {
        return platformOrderid;
    }

    /**
     * Sets the value of the platformOrderid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlatformOrderid(String value) {
        this.platformOrderid = value;
    }

    /**
     * Gets the value of the refundaccount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRefundaccount() {
        return refundaccount;
    }

    /**
     * Sets the value of the refundaccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRefundaccount(Double value) {
        this.refundaccount = value;
    }

    /**
     * Gets the value of the remark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the value of the remark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    /**
     * Gets the value of the saledep property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSaledep() {
        return saledep;
    }

    /**
     * Sets the value of the saledep property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSaledep(String value) {
        this.saledep = value;
    }

    /**
     * Gets the value of the settlementId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSettlementId() {
        return settlementId;
    }

    /**
     * Sets the value of the settlementId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSettlementId(Long value) {
        this.settlementId = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the tanagementarea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTanagementarea() {
        return tanagementarea;
    }

    /**
     * Sets the value of the tanagementarea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTanagementarea(String value) {
        this.tanagementarea = value;
    }

    /**
     * Gets the value of the ticketno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketno() {
        return ticketno;
    }

    /**
     * Sets the value of the ticketno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketno(String value) {
        this.ticketno = value;
    }

}
