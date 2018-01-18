package com.tempus.gss.product.ift.api.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * <pre>
 * <b>描述信息</b>
 * <b>Description:用于像页面输出统一的支付账户数据</b>
 *
 * <b>Author:</b> Luyongjia
 * <b>Date:</b> 2016年12月14日  2:05 PM
 * <b>Copyright:</b> Copyright ©2016 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                             Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2016年12月14日  2:05 PM   Luyongjia
 *         new file.
 * </pre>
 */

public class IFTPayAccountVo {

    @JsonSerialize(using = LongSerializer.class)
    private Long id;
    
    private String name;
    
    private String type;
    /**支付类型*/
    private String payType;
	public IFTPayAccountVo(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
	public IFTPayAccountVo(Long id, String name, String type, String payType) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.payType = payType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	/**
	 * 支付类型
	 *
	 * @return  the payType
	 * @since   CodingExample Ver 1.0
	*/
	
	public String getPayType() {
	
		return payType;
	}
	

	
	/**
	 * 支付类型
	 *
	 * @param   payType    the 支付类型 to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setPayType(String payType) {
	
		this.payType = payType;
	}
	

}
