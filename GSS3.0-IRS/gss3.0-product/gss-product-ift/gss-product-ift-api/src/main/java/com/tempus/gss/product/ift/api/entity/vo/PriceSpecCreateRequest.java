package com.tempus.gss.product.ift.api.entity.vo;

import com.tempus.gss.product.ift.api.entity.PriceSpec;
import com.tempus.gss.product.common.entity.RequestWithActor;

public class PriceSpecCreateRequest extends RequestWithActor {

	private PriceSpec priceSpec;

	public PriceSpec getPriceSpec() {

		return priceSpec;
	}

	public void setPriceSpec(PriceSpec priceSpec) {

		this.priceSpec = priceSpec;
	}

}
