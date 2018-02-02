package com.tempus.gss.product.ift.api.entity.formula;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 计算方式二.
 */
public class Formula02Para  implements Serializable{

	//<editor-fold desc="当前航段从 inlandDep  至 inlandArr 主航段航司是本出票航司，且国内附加段是 inlandCarrier 承运时，国内附加段计奖。">
	/**
	 * 当前国内段从…………开始， SZX/CAN/PEK
	 */
	private String inlandDep;

	/**
	 * 当前国内段到达…………， SZX/CAN/PEK
	 */
	private String inlandArr;

	/**
	 * 国内段承运航司， CA/MU/CZ
	 */
	private String inlandCarrier;
	//</editor-fold>

	/**
	 * 使用国内附加段计奖
	 */
	private boolean inlandRewards;

	/**
	 * 使用境外延伸段计奖
	 */
	private boolean foreignRewards;

	private List<Formula02Carrier> carrierList;

	/**
	 * Q值计奖.
	 */
	private boolean useQ;

	/**
	 * 选择的计算公式编号
	 */
	private Integer formulaNo;

	public String getInlandDep() {
		return inlandDep;
	}

	public void setInlandDep(String inlandDep) {
		this.inlandDep = inlandDep;
	}

	public String getInlandArr() {
		return inlandArr;
	}

	public void setInlandArr(String inlandArr) {
		this.inlandArr = inlandArr;
	}

	public String getInlandCarrier() {
		return inlandCarrier;
	}

	public void setInlandCarrier(String inlandCarrier) {
		this.inlandCarrier = inlandCarrier;
	}

	public boolean isInlandRewards() {
		return inlandRewards;
	}

	public void setInlandRewards(boolean inlandRewards) {
		this.inlandRewards = inlandRewards;
	}

	public boolean isForeignRewards() {
		return foreignRewards;
	}

	public void setForeignRewards(boolean foreignRewards) {
		this.foreignRewards = foreignRewards;
	}

	public List<Formula02Carrier> getCarrierList() {
		return carrierList;
	}

	public void setCarrierList(List<Formula02Carrier> carrierList) {
		this.carrierList = carrierList;
	}

	public boolean isUseQ() {
		return useQ;
	}

	public void setUseQ(boolean useQ) {
		this.useQ = useQ;
	}

	public Integer getFormulaNo() {
		return formulaNo;
	}

	public void setFormulaNo(Integer formulaNo) {
		this.formulaNo = formulaNo;
	}
	
	/**
	 * 公式1:计奖的部分*（1-代理费率-返点）+ 无奖励的部分*（1-代理费率） + 税款 + 手续费
	 */
	public BigDecimal formulaMethod1(FormulaParameters formulaParameters) {

		return formulaParameters.getAwardPrice().multiply(
				new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))))
				.add(
						formulaParameters.getFare().subtract(formulaParameters.getAwardPrice())
								.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
								.add(formulaParameters.getTax().add(formulaParameters.getBrokerage())));
	}

	/**
	 * 公式2:计奖的部分*（1-代理费率）（1-返点）+ 无奖励的部分*（1-代理费率） + 税款 + 手续费
	 */
	public BigDecimal formulaMethod2(FormulaParameters formulaParameters) {

		return formulaParameters.getAwardPrice().multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
				.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100)))).add(
						formulaParameters.getFare().subtract(formulaParameters.getAwardPrice())
								.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
								.add(formulaParameters.getTax().add(formulaParameters.getBrokerage())));
	}
}
