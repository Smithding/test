package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;


public class IftSupplierVo implements Serializable {
    /**
     * 供应商编号
     */
	@JsonSerialize(using = LongSerializer.class)
    private Long supplierNo;

    /**
     * 供应商名称
     */
    private String name;
    

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
     * officeNO
     */
    private String officeNo;
    public String getOfficeNo() {
		return officeNo;
	}

	public void setOfficeNo(String officeNo) {
		this.officeNo = officeNo;
	}

	public Long getChannelType() {
		return channelType;
	}

	public void setChannelType(Long channelType) {
		this.channelType = channelType;
	}

	/**
     * 客商类型
     */
    private Long channelType;

    /**
     * 排序编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long sort;

    private static final long serialVersionUID = 1L;

    public Long getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(Long supplierNo) {
        this.supplierNo = supplierNo;
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