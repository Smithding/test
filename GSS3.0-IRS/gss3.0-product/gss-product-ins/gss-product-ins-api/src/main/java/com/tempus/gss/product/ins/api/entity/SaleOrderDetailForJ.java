package com.tempus.gss.product.ins.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.serializer.LongSerializer;

/**
 * 
 * ClassName:SaleOrderDetail Function: 被保人信息
 *
 * @author shuo.cheng
 * @version
 * @since Ver 1.1
 * @Date 2016 2016年10月27日 上午8:47:35
 *
 * @see
 *
 */
@JsonInclude(Include.NON_NULL)
@Alias("SaleOrderDetailForJ")
public class SaleOrderDetailForJ implements Serializable {
    
	/**
	 * 被保人编号
	 */
	@JsonSerialize(using = LongSerializer.class)
    private Long insuredNo;

    /**
     * 编号
     */
	@JsonSerialize(using = LongSerializer.class)
    private Long id;

    /**
     * 数据归属单位
     */
    private Integer owner;

    /**
     * 订单编号
     */
    private Long saleOrderNo;

    /**
     * 被保险人姓名
     */
    private String insuredName;

    /**
	 * 被保人证件类型  1:身份证,2:护照,3:出生证,4:驾照,5:军官证,6:其他
	 */
	private String insuredCertiType;

    /**
     * 被保人证件号码
     */
	private String insuredCertiNo;

    /**
     * 子保单下载地址
     */
    private String eleUrl;

    /**
     * 被保人出生日期(yyyy-MM-dd)
     */
    private String insuredBirthday;

    /**
     * 被保人性别
     */
	private Integer insuredSex;

    /**
     * 被保人手机号码
     */
	private String insuredPhone;

    /**
     * 被保人邮箱
     */
    private String insuredEmail;

    /**
     * 保费
     */
    private BigDecimal premium;

    /**
     * 受益人类型
     */
    private Integer beneficiaryType;

    /**
     * 与投保人关系(投保人不用填)
     */
    private String customerRelations;

    /**
     * 购买份数
     */
    private Integer insuranceNum;

	/**
	 * 创建时间
	 */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 操作人 默认为：sys
     */
    private String modifier;

    /**
     * 删除标志 0 无效 已删除 1 有效
     */
    private Boolean valid;

	/**
	 * 子订单状态 1.未投保 2.已投保 3.已退保
    */
    private Integer status;

	/**
	 * 保单号
	 */
	private String policyNo;

	/**
	 * 保险销售单
	 */
	private SaleOrder saleOrder;
	
    private static final long serialVersionUID = 1L;

	public String getPolicyNo() {

		return policyNo;
	}

	public void setPolicyNo(String policyNo) {

		this.policyNo = policyNo;
	}

	public Integer getStatus() {

		return status;
	}

	public void setStatus(Integer status) {

		this.status = status;
	}

	public Long getInsuredNo() {
        return insuredNo;
    }

    public void setInsuredNo(Long insuredNo) {
        this.insuredNo = insuredNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Long getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public String getEleUrl() {
        return eleUrl;
    }

    public void setEleUrl(String eleUrl) {
        this.eleUrl = eleUrl;
    }

    public String getInsuredBirthday() {
        return insuredBirthday;
    }

    public void setInsuredBirthday(String insuredBirthday) {
        this.insuredBirthday = insuredBirthday;
    }


	public Integer getInsuredSex() {

		return insuredSex;
	}

	public void setInsuredSex(Integer insuredSex) {

		this.insuredSex = insuredSex;
	}

	public String getInsuredCertiType() {

		return insuredCertiType;
	}

	public void setInsuredCertiType(String insuredCertiType) {

		this.insuredCertiType = insuredCertiType;
	}

	public String getInsuredCertiNo() {

		return insuredCertiNo;
	}

	public void setInsuredCertiNo(String insuredCertiNo) {

		this.insuredCertiNo = insuredCertiNo;
	}

	public String getInsuredPhone() {

		return insuredPhone;
	}

	public void setInsuredPhone(String insuredPhone) {

		this.insuredPhone = insuredPhone;
	}

	public String getInsuredEmail() {
        return insuredEmail;
    }


	public void setInsuredEmail(String insuredEmail) {
        this.insuredEmail = insuredEmail;
    }


	public Integer getBeneficiaryType() {
        return beneficiaryType;
    }

    public BigDecimal getPremium() {
		return premium;
	}

	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}

	public void setBeneficiaryType(Integer beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public String getCustomerRelations() {
        return customerRelations;
    }

    public void setCustomerRelations(String customerRelations) {
        this.customerRelations = customerRelations;
    }

    public Integer getInsuranceNum() {
        return insuranceNum;
    }

    public void setInsuranceNum(Integer insuranceNum) {
        this.insuranceNum = insuranceNum;
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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}
    
}