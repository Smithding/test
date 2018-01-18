package com.tempus.gss.product.tra.api.entity.vo;

import java.io.Serializable;
import java.util.List;

import com.tempus.gss.product.tra.api.entity.TraProfitControl;

public class TraProfitControlListVo  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TraProfitControl> traProfitControlListList;
	public List<TraProfitControl> getTraProfitControlListList() {
		return traProfitControlListList;
	}
	public void setTraProfitControlListList(List<TraProfitControl> traProfitControlListList) {
		this.traProfitControlListList = traProfitControlListList;
	}
	
	
}