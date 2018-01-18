package com.tempus.gss.product.ift.api.entity;
import java.io.Serializable;
import java.util.Set;

/***
 */
public class PolicyIndex implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3310814796265224929L;
	private String indexPk;
    private Set<Long> policyIds;

    public PolicyIndex(String indexPk, Set<Long> policyIds) {
        this.policyIds = policyIds;
        this.indexPk = indexPk;
    }

    public PolicyIndex() {
    }

	public String getIndexPk() {
		return indexPk;
	}

	public void setIndexPk(String indexPk) {
		this.indexPk = indexPk;
	}

	public Set<Long> getPolicyIds() {
		return policyIds;
	}

	public void setPolicyIds(Set<Long> policyIds) {
		this.policyIds = policyIds;
	}
}


