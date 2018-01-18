package com.tempus.gss.product.ift.api.entity.formula;

import java.io.Serializable;

/**
 * 公式二的附加段和延伸段计奖.
 */
public class Formula02Carrier  implements Serializable{

	/**
	 * 航段计奖类型.
	 * 1：国内附加段自承运计奖方式。
	 * 2：国内附加段非自承运计奖方式。
	 * 3：国外附加段自承运计奖方式。
	 * 4：国外附加段非自承运计奖方式
	 */
	private int legType;

	/**
	 * 是否使用该计奖.
	 */
	private boolean useIt;

	/**
	 * 与主航段同一票价计算组计奖.
	 */
	private boolean sameWithMainLeg;

	/**
	 * 与主航段同一票价计算组计奖.
	 */
	private boolean differentWithMainLeg;

	public int getLegType() {
		return legType;
	}

	public void setLegType(int legType) {
		this.legType = legType;
	}

	public boolean isUseIt() {
		return useIt;
	}

	public void setUseIt(boolean useIt) {
		this.useIt = useIt;
	}

	public boolean isSameWithMainLeg() {
		return sameWithMainLeg;
	}

	public void setSameWithMainLeg(boolean sameWithMainLeg) {
		this.sameWithMainLeg = sameWithMainLeg;
	}

	public boolean isDifferentWithMainLeg() {
		return differentWithMainLeg;
	}

	public void setDifferentWithMainLeg(boolean differentWithMainLeg) {
		this.differentWithMainLeg = differentWithMainLeg;
	}
	
	
}
