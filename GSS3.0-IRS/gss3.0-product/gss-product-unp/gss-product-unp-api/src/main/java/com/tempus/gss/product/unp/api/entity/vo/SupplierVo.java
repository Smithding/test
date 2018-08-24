package com.tempus.gss.product.unp.api.entity.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ZhangBro
 */
public class SupplierVo implements Serializable {
    
    /** 供应商编号 */
    private Long supplierNo;
    
    private Long customerNo;
    
    /** 客户类型编号，2级 */
    private Long customerTypeNo;
    
    /** 客户类型名称 */
    
    private String ctName;
    
    /** 客户类型NO */
    
    private Long ctNo;
    
    /** 客户分组名称 */
    
    private String groupName;
    
    /**
     * 供应产品类型
     * :1000001国内机票，1000002国际机票，1000003保险，1000004酒店，1000005机场服务，1000006物流配送，
     * 1000007票证，1000008其它 ，1000009"通用产品，1000010火车票
     *
     * @see com.tempus.gss.cps.ProductTypeEnum
     */
    private String productType;
    
    /** 简称 */
    private String shortName;
    
    /** 联系人 */
    private String contactor;
    
    /** 身份证号码 */
    private String cardNo;
    
    /** 手机 */
    private String phone;
    
    /** 状态 */
    private Integer status;
    
    /** 是否有效 */
    private Integer valid;
    
    /** 创建时间 */
    private Date createTime;
    
    /** 修改人 */
    private String modifier;
    
    /** 账户名称 */
    private String accountName;
    
    /** 账号 */
    private String accountNo;
    /**
     * UNP_ITEM_TYPE通用产品小类编号
     */
    private List<Long> itemTypeNos;
    /**
     * 是否供应整个大类 默认false
     */
    private Integer itemType;
    
    public Long getSupplierNo() {
        return supplierNo;
    }
    
    public void setSupplierNo(Long supplierNo) {
        this.supplierNo = supplierNo;
    }
    
    public Long getCustomerNo() {
        return customerNo;
    }
    
    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }
    
    public Long getCustomerTypeNo() {
        return customerTypeNo;
    }
    
    public void setCustomerTypeNo(Long customerTypeNo) {
        this.customerTypeNo = customerTypeNo;
    }
    
    public String getCtName() {
        return ctName;
    }
    
    public void setCtName(String ctName) {
        this.ctName = ctName;
    }
    
    public Long getCtNo() {
        return ctNo;
    }
    
    public void setCtNo(Long ctNo) {
        this.ctNo = ctNo;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public String getProductType() {
        return productType;
    }
    
    public void setProductType(String productType) {
        this.productType = productType;
    }
    
    public String getShortName() {
        return shortName;
    }
    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public String getContactor() {
        return contactor;
    }
    
    public void setContactor(String contactor) {
        this.contactor = contactor;
    }
    
    public String getCardNo() {
        return cardNo;
    }
    
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getValid() {
        return valid;
    }
    
    public void setValid(Integer valid) {
        this.valid = valid;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getModifier() {
        return modifier;
    }
    
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
    
    public String getAccountName() {
        return accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public String getAccountNo() {
        return accountNo;
    }
    
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    
    public List<Long> getItemTypeNos() {
        return itemTypeNos;
    }
    
    public void setItemTypeNos(List<Long> itemTypeNos) {
        this.itemTypeNos = itemTypeNos;
    }
    
    public Integer getItemType() {
        return itemType;
    }
    
    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }
}
