package com.tempus.gss.product.tra.api.entity.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.tempus.gss.cps.entity.Customer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;


public class TraSaleOrderExtVo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 销售单号
     */
    private Long saleOrderNo;

    /**
     *
     */
    private Integer owner;

    /**
     * 采购单号
     */
    private Long buyOrderNo;

    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 电话
     */
    private String cellphone;

    /**
     * 订票人邮件
     */
    private String email;

    /**
     * 销售价
     */
    private Long salePrice;

    /**
     * 采购价
     */
    private Long buyPrice;

    /**
     * 总价
     */
    private Long premium;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否团队
     */
    private Byte isTeam;

    /**
     * 渠道名称
     */
    private String sourceName;

    /**
     * 扩展
     */
    private String extendedFieldsJson;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 创建人 
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 是否可用
     */
    private Boolean valid;

    /**
     * 买票时间
     */
    private Date issueDate;

    /**
     * 订票人姓名
     */
    private String person;

    /**
     * 火车类型
     */
    private String trainName;
    
    /**
     * 外部订单号
     */
    private String outOrderNo;
    
    /**
     * 
     */
    private Date createStartTime;
    
    /**
     * 
     */
    private Date createEndTime;
    
    /**
     * 乘客姓
     */
    private String surname;
    
    /**
     * 乘客名
     */
    private String name;
    
    /**
     * 客商类型
     */
    private Long customerNo;
    
    /**
     * 客商编号
     */
    private Integer customerTypeNo;
    
    /**
     * 状态  1（已创建），2（支付中），3（出票中），4（已出票）5（退票中），
     * 6（改签中） 7（已退），8（已废），9（已改签），10（异常订单）
     */
    private int status;
    
    /**
     * 车次号
     */
    private String trainNo;
    
    /**
     * 发车时间
     */
    private Date departureTime;

    /*
    * 发车开始时间
    * */
    private Date departureStartTime;
    /*
     * 发车结束时间
     * */
    private Date departureEndTime;
   /**
    * 是否查询子账号下面的所有订单
    */
    private boolean isLowerLevel;
    /*
     * 查询子账户所有订单
     */
    private List<Customer> lowerCustomers;
    public List<Customer> getLowerCustomers() {
		return lowerCustomers;
	}

	public void setLowerCustomers(List<Customer> lowerCustomers) {
		this.lowerCustomers = lowerCustomers;
	}

	public boolean isLowerLevel(){
	return isLowerLevel;
}

public void setLowerLevel(boolean isLowerLevel) {
	this.isLowerLevel = isLowerLevel;
}

	/** 交易单号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long transationOrderNo;
    
    public Long getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Long getBuyOrderNo() {
        return buyOrderNo;
    }

    public void setBuyOrderNo(Long buyOrderNo) {
        this.buyOrderNo = buyOrderNo;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone == null ? null : cellphone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public Long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Long buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Long getPremium() {
        return premium;
    }

    public void setPremium(Long premium) {
        this.premium = premium;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getIsTeam() {
        return isTeam;
    }

    public void setIsTeam(Byte isTeam) {
        this.isTeam = isTeam;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName == null ? null : sourceName.trim();
    }

    public String getExtendedFieldsJson() {
        return extendedFieldsJson;
    }

    public void setExtendedFieldsJson(String extendedFieldsJson) {
        this.extendedFieldsJson = extendedFieldsJson == null ? null : extendedFieldsJson.trim();
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
        this.creator = creator == null ? null : creator.trim();
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Long getTransationOrderNo() {
		return transationOrderNo;
	}

	public void setTransationOrderNo(Long transationOrderNo) {
		this.transationOrderNo = transationOrderNo;
	}

	public Long getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(Long customerNo) {
		this.customerNo = customerNo;
	}

	public Integer getCustomerTypeNo() {
		return customerTypeNo;
	}

	public void setCustomerTypeNo(Integer customerTypeNo) {
		this.customerTypeNo = customerTypeNo;
	}

    public Date getDepartureStartTime() {
        return departureStartTime;
    }

    public void setDepartureStartTime(Date departureStartTime) {
        this.departureStartTime = departureStartTime;
    }

    public Date getDepartureEndTime() {
        return departureEndTime;
    }

    public void setDepartureEndTime(Date departureEndTime) {
        this.departureEndTime = departureEndTime;
    }
}