/**
 * OrderCancelVo.java
 * com.tempus.gss.product.ins.api.entity.vo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2016年10月17日 		shuo.cheng
 *
 * Copyright (c) 2016, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ins.api.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:InsApiOrderQuitVo
 *
 * @author   Fengjie.luo
 * @version
 * @since    Ver 1.1
 * @Date	 2017年02月20日		上午10:40:37
 *
 * @see
 *
 */
public class InsApiOrderQuitVo implements Serializable {

	/**
	 * 保单号列表(调用退保接口时,传保单号使用此属性)
	 */
	private List<String> policyNoList;

	public List<String> getPolicyNoList() {
		return policyNoList;
	}

	public void setPolicyNoList(List<String> policyNoList) {
		this.policyNoList = policyNoList;
	}
}

