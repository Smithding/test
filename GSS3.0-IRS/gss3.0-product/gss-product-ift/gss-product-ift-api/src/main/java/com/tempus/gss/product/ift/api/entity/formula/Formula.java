package com.tempus.gss.product.ift.api.entity.formula;

import java.io.Serializable;

/**
 * 价格计算公司.
 */
public class Formula implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 当未配上有效政策时，将返回默认政策。
	 */
	private NoMatchFormulaPara noMatchFormulaPara;

	/**
	 * 当配上有效政策时，选择计算方式.
	 * 1（计算方式一），2（计算方式二）.
	 */
	private Integer selectFormulaNo;
	/**
	 * 计算方式一.
	 */
	private Formula01Para formula01Para;

	/**
	 * 计算方式二.
	 */
	private Formula02Para formula02Para;

	/**
	 * 往返程混舱、航线组合时，结算价组合计算方式.
	 * 1（分开算），2（1/2），3（较低），
	 */
	private Integer CombinationPrice;

	public NoMatchFormulaPara getNoMatchFormulaPara() {

		return noMatchFormulaPara;
	}

	public void setNoMatchFormulaPara(NoMatchFormulaPara noMatchFormulaPara) {

		this.noMatchFormulaPara = noMatchFormulaPara;
	}

	public Integer getSelectFormulaNo() {

		return selectFormulaNo;
	}

	public void setSelectFormulaNo(Integer selectFormulaNo) {

		this.selectFormulaNo = selectFormulaNo;
	}

	public Formula01Para getFormula01Para() {

		return formula01Para;
	}

	public void setFormula01Para(Formula01Para formula01Para) {

		this.formula01Para = formula01Para;
	}

	public Formula02Para getFormula02Para() {

		return formula02Para;
	}

	public void setFormula02Para(Formula02Para formula02Para) {

		this.formula02Para = formula02Para;
	}

	public Integer getCombinationPrice() {

		return CombinationPrice;
	}

	public void setCombinationPrice(Integer combinationPrice) {

		CombinationPrice = combinationPrice;
	}

}
