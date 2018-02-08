package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

/**
 * 国际机票预定信息dto
 * @author hongqiaoxin
 *
 */
public class IftBookDto implements Serializable{
	private static final long serialVersionUID = 1L;
    
    /**
     * 成人数量;
     */
    private Integer adultCount;

    /**
     * 儿童数量;
     */
    private Integer childCount;
    
    /**
     * 儿童数量;
     */
    private Integer infantCount;
    
    /**
     * 航程类型：1:单程; 2:往返
     */
    private Integer legType;
    
    /**
     * 选择的总舱位价格的下标
     */
    private Integer cabinPriceIndex;
    
    /**
     * 成人价格所对应的下标
     */
    private Integer adtPriceIndex;
    
    /**
     * 儿童价格所对应的下标
     */
    private Integer chdPriceIndex;
    
    /**
     * 婴儿价格所对应的下标
     */
    private Integer infPriceIndex;
    
    



	public Integer getAdultCount() {
		return adultCount;
	}

	public void setAdultCount(Integer adultCount) {
		this.adultCount = adultCount;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public Integer getLegType() {
		return legType;
	}

	public void setLegType(Integer legType) {
		this.legType = legType;
	}

	public Integer getCabinPriceIndex() {
		return cabinPriceIndex;
	}

	public void setCabinPriceIndex(Integer cabinPriceIndex) {
		this.cabinPriceIndex = cabinPriceIndex;
	}

	public Integer getAdtPriceIndex() {
		return adtPriceIndex;
	}

	public void setAdtPriceIndex(Integer adtPriceIndex) {
		this.adtPriceIndex = adtPriceIndex;
	}

	public Integer getChdPriceIndex() {
		return chdPriceIndex;
	}

	public void setChdPriceIndex(Integer chdPriceIndex) {
		this.chdPriceIndex = chdPriceIndex;
	}

	public Integer getInfantCount() {
		return infantCount;
	}

	public void setInfantCount(Integer infantCount) {
		this.infantCount = infantCount;
	}

	public Integer getInfPriceIndex() {
		return infPriceIndex;
	}

	public void setInfPriceIndex(Integer infPriceIndex) {
		this.infPriceIndex = infPriceIndex;
	}
	
	
	
	
}
