package com.tempus.gss.product.hol.api.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;

/**
 * 搜索条件
 * Created by luofengjie on 2017/3/24.
 */
public class SearchCondition implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 热点
     */
    @JSONField(name = "CenterPort")
    private String centerPort;
    /**
     * 价格区间
     */
    @JSONField(name = "PriceFromTo")
    private List<PriceFromTo> priceFromToList;
    private String priceScope;
    /**
     * 星级
     */
    @JSONField(name = "HotelLevel")
    private List<String> hotelLevelList; 
    /**
     * 品牌
     */
    @JSONField(name = "Brand")
    private List<String> brandList;
    private String resBrandName;
    /**
     * 供应商来源(空是查所有,多个查询的时候,拼装即可,0 api供应商 1 协议  2 自有)
     */
    @JSONField(name = "Sources")
    private String sources;
    /**
     * 支付类型（0 预付,1 现付 ,空是查所有,多个查询的时候,拼装即可）
     */
    @JSONField(name = "PayTypes")
    private String payTypes;

    public String getCenterPort() {
        return centerPort;
    }

    public void setCenterPort(String centerPort) {
        this.centerPort = centerPort;
    }

    public String getPayTypes() {
        return payTypes;
    }

    public void setPayTypes(String payTypes) {
        this.payTypes = payTypes;
    }

    public List<PriceFromTo> getPriceFromToList() {
      	if(StringUtils.isNotEmpty(priceScope))
    	{
    	    priceFromToList = Lists.newArrayList();    
    		PriceFromTo pft = new PriceFromTo(); 
        	String[] priceScopeArray = priceScope.split("-"); 
        	pft.setPriceFrom(new BigDecimal(priceScopeArray[0]));
        	pft.setPriceTo(new BigDecimal(priceScopeArray[1]));
        	priceFromToList.add(pft);
    	}    	
        return priceFromToList;
    }

    public void setPriceFromToList(List<PriceFromTo> priceFromToList) {
        this.priceFromToList = priceFromToList;
    }

 

    public List<String> getHotelLevelList() {
		return hotelLevelList;
	}

	public void setHotelLevelList(List<String> hotelLevelList) {
		this.hotelLevelList = hotelLevelList;
	}

	public List<String> getBrandList() {
    	if(StringUtils.isNotEmpty(resBrandName))
    	{
    		brandList = Lists.newArrayList();
    		String[] brandName = resBrandName.split(",");
        	brandList.addAll(Arrays.asList(brandName));
    	}    	
        return brandList;
    }

    public void setBrandList(List<String> brandList) {    	    	
        this.brandList = brandList;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

	public String getPriceScope() {
		return priceScope;
	}

	public void setPriceScope(String priceScope) {
		this.priceScope = priceScope;
	}
 

	public String getResBrandName() {
		return resBrandName;
	}

	public void setResBrandName(String resBrandName) {
		this.resBrandName = resBrandName;
	}
    
}
