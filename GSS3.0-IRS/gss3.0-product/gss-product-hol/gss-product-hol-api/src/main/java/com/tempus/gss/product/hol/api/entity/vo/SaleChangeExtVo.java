package com.tempus.gss.product.hol.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 酒店变更拓展单Vo
 */
public class SaleChangeExtVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 销售单号
     */
    private Long saleOrderNo;

    /**
     * 销售单废退改编号
     */
    private Long saleChangeNo;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 客商编号
     */
    private Long customerNo;
    /**
     * 销售变更单子状态(1申请退房 2退房中 3退房失败 4退房成功)
     */
    private Integer saleChangeChildStatus;

    /**
     * 数据归属单位
     */
    private Integer owner;

    /**
     * 创建人
     */
    private String creator;
    /**
     * 退房申请开始时间
     */
    private Date createStartTime;
    /**
     * 退房申请结束时间
     */
    private Date createEndTime;

    public Long getSaleChangeNo() {
        return this.saleChangeNo;
    }

    public void setSaleChangeNo(Long saleChangeNo) {
        this.saleChangeNo = saleChangeNo;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCustomerNo() {
        return this.customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }

    public Long getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public Integer getSaleChangeChildStatus() {
        return saleChangeChildStatus;
    }

    public void setSaleChangeChildStatus(Integer saleChangeChildStatus) {
        this.saleChangeChildStatus = saleChangeChildStatus;
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
}
