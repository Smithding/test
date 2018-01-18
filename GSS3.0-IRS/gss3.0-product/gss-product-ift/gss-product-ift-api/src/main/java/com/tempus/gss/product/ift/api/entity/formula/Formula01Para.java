package com.tempus.gss.product.ift.api.entity.formula;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 计算方式一.
 */
public class Formula01Para extends Formula implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 是否设置非出票航司实际承运里程占比.
	 */
	private boolean useNotTicketAirline;

	/**
	 * 非出票航司实际承运里程占比区间返点设置.
	 */
	private List<Formula01Region> regionList;

	/**
	 * 国内附加段返点
	 */
	private List<BigDecimal> inlandRebateList;

	/**
	 * 境外延伸段返点
	 */
	private List<BigDecimal> foreignRebateList;

	/**
	 * 选择的计算公式编号
	 */
	private Integer formulaNo;

	public boolean isUseNotTicketAirline() {

		return useNotTicketAirline;
	}

	public void setUseNotTicketAirline(boolean useNotTicketAirline) {

		this.useNotTicketAirline = useNotTicketAirline;
	}

	public List<Formula01Region> getRegionList() {

		return regionList;
	}

	public void setRegionList(List<Formula01Region> regionList) {

		this.regionList = regionList;
	}

	public List<BigDecimal> getInlandRebateList() {

		return inlandRebateList;
	}

	public void setInlandRebateList(List<BigDecimal> inlandRebateList) {

		this.inlandRebateList = inlandRebateList;
	}

	public List<BigDecimal> getForeignRebateList() {

		return foreignRebateList;
	}

	public void setForeignRebateList(List<BigDecimal> foreignRebateList) {

		this.foreignRebateList = foreignRebateList;
	}

	public Integer getFormulaNo() {

		return formulaNo;
	}

	public void setFormulaNo(Integer formulaNo) {

		this.formulaNo = formulaNo;
	}
	/**
	 * 公式1:票价*（1-代理费率-返点）+ 税款 + 手续费
	 */
	public BigDecimal formulaMethod1(FormulaParameters formulaParameters) {
		return formulaParameters.getFare().multiply(
				new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))))
								.add(formulaParameters.getTax().add(formulaParameters.getBrokerage()));
	}
	/**
	 * 公式2:票价*（1-代理费率）*（1-返点）+ 税款 + 手续费
	 */
	public BigDecimal formulaMethod2(FormulaParameters formulaParameters) {
		return formulaParameters.getFare().multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
				.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))))
								.add(formulaParameters.getTax().add(formulaParameters.getBrokerage()));
	}
	/**
	 * 公式3:（票价-Q）*（1-代理费率-返点）+ Q*（1-代理费率） + 税款 + 手续费
	 */
	public BigDecimal formulaMethod3(FormulaParameters formulaParameters) {
		return formulaParameters.getAwardPrice().multiply(
				new BigDecimal(1).subtract(formulaParameters.getAgencyFee()).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))))
				.add(
						formulaParameters.getFare().subtract(formulaParameters.getAwardPrice().divide(new BigDecimal(100)))
								.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
								.add(formulaParameters.getTax().add(formulaParameters.getBrokerage())));
	}
	/**
	 * 公式4:（票价-Q）*（1-代理费率）*（1-返点）+ Q*（1-代理费率）+ 税款 + 手续费
	 */
	public BigDecimal formulaMethod4(FormulaParameters formulaParameters) {
		return formulaParameters.getAwardPrice().multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
				.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100)))).add(
						formulaParameters.getFare().subtract(formulaParameters.getAwardPrice())
								.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
								.add(formulaParameters.getTax().add(formulaParameters.getBrokerage())));
	}

}

