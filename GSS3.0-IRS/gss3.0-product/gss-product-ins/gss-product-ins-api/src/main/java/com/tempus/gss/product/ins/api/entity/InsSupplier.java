package com.tempus.gss.product.ins.api.entity;

import java.io.Serializable;
import java.util.Date;

public class InsSupplier implements Serializable {
    /**
     * 供应商编号
     */
    private Long supplierNo;

    /**
	 *供应商名称
	 */
    private String name;
    
    private Long id;

    /**
     * 是否有效
     */
    private Byte valid;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 排序编号
     */
    private Long sort;

	/**
	 * 开户账号
	 */
	private Long accountNo;

	/**
	 * 支付账号
	 */
	private Long capitalAccountNo;

    private static final long serialVersionUID = 1L;

	public Long getAccountNo() {

		return accountNo;
	}

	public void setAccountNo(Long accountNo) {

		this.accountNo = accountNo;
	}

	public Long getCapitalAccountNo() {

		return capitalAccountNo;
	}

	public void setCapitalAccountNo(Long capitalAccountNo) {

		this.capitalAccountNo = capitalAccountNo;
	}

	public Long getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(Long supplierNo) {
        this.supplierNo = supplierNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}