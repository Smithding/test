package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 可订检查接口返回的担保数据
 * @author kai.yang
 *
 */
public class CtripGuaranteeInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 担保 code,可能为空（不担保），
	 * 1-峰时担保；
	 * 2-全额担保；
	 * 3-超时担保；
	 * 4-一律担保；
	 * 5-手机担保(不担保,要有手机号);多个逗号分隔
	 */
	@JSONField(name = "CgCode")
	private String[] cgCode;
	
	/**
	 * 担保额
	 */
	@JSONField(name = "AmountPercent")
	private BigDecimal[] amountPercent;
	/**
	 * 担保额币种
	 */
	@JSONField(name = "AmountPercentCurrencyCode")
	private String[] amountPercentCurrencyCode;
	/**
	 * 其它币种担保额
	 */
	@JSONField(name = "AmountPercentOther")
	private BigDecimal[] amountPercentOther;
	/**
	 * 其它币种担保额币种
	 */
	@JSONField(name = "AmountPercentOtherCurrencyCode")
	private String[] amountPercentOtherCurrencyCode;
	/**
	 * 取消罚金
	 */
	@JSONField(name = "CancelPenaltyAmount")
	private BigDecimal cancelPenaltyAmount;
	
	/**
	 * 取消罚金币种
	 */
	@JSONField(name = "CancelPenaltyAmountCurrencyCode")
	private String cancelPenaltyAmountCurrencyCode;
	
	/**
	 * 最迟的取消时间，在这个时间前取消不需要扣除罚金
	 */
	@JSONField(name = "CancelPenaltyStart")
	private Date cancelPenaltyStart;
	/**
	 * 结束时间，表示在这个时间段取消是需要扣除罚金
	 */
	@JSONField(name = "CancelPenaltyEnd")
	private Date cancelPenaltyEnd;
	public String[] getCgCode() {
		return cgCode;
	}
	public void setCgCode(String[] cgCode) {
		this.cgCode = cgCode;
	}
	public BigDecimal[] getAmountPercent() {
		return amountPercent;
	}
	public void setAmountPercent(BigDecimal[] amountPercent) {
		this.amountPercent = amountPercent;
	}
	public String[] getAmountPercentCurrencyCode() {
		return amountPercentCurrencyCode;
	}
	public void setAmountPercentCurrencyCode(String[] amountPercentCurrencyCode) {
		this.amountPercentCurrencyCode = amountPercentCurrencyCode;
	}
	public BigDecimal[] getAmountPercentOther() {
		return amountPercentOther;
	}
	public void setAmountPercentOther(BigDecimal[] amountPercentOther) {
		this.amountPercentOther = amountPercentOther;
	}
	public String[] getAmountPercentOtherCurrencyCode() {
		return amountPercentOtherCurrencyCode;
	}
	public void setAmountPercentOtherCurrencyCode(String[] amountPercentOtherCurrencyCode) {
		this.amountPercentOtherCurrencyCode = amountPercentOtherCurrencyCode;
	}
	public BigDecimal getCancelPenaltyAmount() {
		return cancelPenaltyAmount;
	}
	public void setCancelPenaltyAmount(BigDecimal cancelPenaltyAmount) {
		this.cancelPenaltyAmount = cancelPenaltyAmount;
	}
	public String getCancelPenaltyAmountCurrencyCode() {
		return cancelPenaltyAmountCurrencyCode;
	}
	public void setCancelPenaltyAmountCurrencyCode(String cancelPenaltyAmountCurrencyCode) {
		this.cancelPenaltyAmountCurrencyCode = cancelPenaltyAmountCurrencyCode;
	}
	public Date getCancelPenaltyStart() {
		return cancelPenaltyStart;
	}
	public void setCancelPenaltyStart(Date cancelPenaltyStart) {
		this.cancelPenaltyStart = cancelPenaltyStart;
	}
	public Date getCancelPenaltyEnd() {
		return cancelPenaltyEnd;
	}
	public void setCancelPenaltyEnd(Date cancelPenaltyEnd) {
		this.cancelPenaltyEnd = cancelPenaltyEnd;
	}
	
}
