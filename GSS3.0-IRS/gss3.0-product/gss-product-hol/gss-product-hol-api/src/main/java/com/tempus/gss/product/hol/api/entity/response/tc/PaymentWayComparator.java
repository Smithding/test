package com.tempus.gss.product.hol.api.entity.response.tc;

import java.util.Comparator;

public class PaymentWayComparator implements Comparator<PaymentWay> {

	@Override
	public int compare(PaymentWay o1, PaymentWay o2) {
		if(Integer.parseInt(o1.getOrderBy())>Integer.parseInt(o2.getOrderBy()))
		{
			return 1;
		}else if(Integer.parseInt(o1.getOrderBy())<Integer.parseInt(o2.getOrderBy()))
		{
			return -1;
		}
		else
		{
			return 0;
		}		
	}

}
