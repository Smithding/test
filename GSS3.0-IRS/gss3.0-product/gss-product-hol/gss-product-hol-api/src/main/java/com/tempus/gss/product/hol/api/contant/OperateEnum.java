package com.tempus.gss.product.hol.api.contant;

import java.io.Serializable;

public enum OperateEnum implements Serializable {

	GT("$gt"),
	LT("$lt"), 
	GTE("$gte"), 
	LTE("$lte"), 
	EQ("$eq"), 
	NE("$ne"), 
	IN("$in"), 
	NIN("$nin"), 
	ALL("$all"), 
	NOT("$not"), 
	REGEX("$lte"), 
	ELEMMATCH("$elemMatch"),
	OROPERATOR("OROPERATOR"),
	BETWEENLONG("此操作mongoTemplate中没有自己封装,依照betweenlong可以类推betweenDate,betweenint等between的比较");

	private String op;

	OperateEnum(String op) {
		this.op = op;
	}

	@Override
	public String toString() {
		return op;
	}

	public String getValue() {
		return op;
	}
}
