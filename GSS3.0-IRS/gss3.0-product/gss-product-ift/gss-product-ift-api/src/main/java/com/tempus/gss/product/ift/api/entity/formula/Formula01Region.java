package com.tempus.gss.product.ift.api.entity.formula;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 计算公式一的区间.
 */
public class Formula01Region  implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 承运里程占比下限.
	 */
	private BigDecimal low;

	/**
	 * 承运里程占比上限.
	 */
	private BigDecimal hing;

	/**
	 * 调整返点.
	 */
	private BigDecimal rebate;

	
	public BigDecimal getLow() {
	
		return low;
	}

	
	public void setLow(BigDecimal low) {
	
		this.low = low;
	}

	
	public BigDecimal getHing() {
	
		return hing;
	}

	
	public void setHing(BigDecimal hing) {
	
		this.hing = hing;
	}

	
	public BigDecimal getRebate() {
	
		return rebate;
	}

	
	public void setRebate(BigDecimal rebate) {
	
		this.rebate = rebate;
	}
	
}
