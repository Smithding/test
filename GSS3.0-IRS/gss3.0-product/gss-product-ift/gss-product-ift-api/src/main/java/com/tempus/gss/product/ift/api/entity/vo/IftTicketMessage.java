/**
 * IftTicketMessage.java
 * com.tempus.gss.product.ift.api.entity.vo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年11月10日 		shuo.cheng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

/**
 * ClassName:IftTicketMessage
 * Function: 国际机票出票消息
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年11月10日		下午3:03:29
 *
 * @see 	 
 *  
 */
public class IftTicketMessage implements Serializable {
	/**
	 * serialVersionUID:序列ID
	 *
	 * @since Ver 1.1
	 */
	private static final long serialVersionUID = -6393569470990706364L;

	/** 归集单位 */
	protected Integer owner;

	/** 交易单编号 */
	protected Long tradeNo;

	public Integer getOwner() {

		return owner;
	}

	public void setOwner(Integer owner) {

		this.owner = owner;
	}

	public Long getTradeNo() {

		return tradeNo;
	}

	public void setTradeNo(Long tradeNo) {

		this.tradeNo = tradeNo;
	}

}

