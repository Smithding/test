package com.tempus.gss.product.hol.api.contant;

import java.io.Serializable;
import java.util.LinkedList;

import com.tempus.gss.bbp.util.StringUtil;

public class QueryProperty implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private LinkedList<String> proNames = new LinkedList<String>();
	private LinkedList<OperateEnum> operates = new LinkedList<OperateEnum>();
	private LinkedList<Object> values = new LinkedList<Object>();

	public LinkedList<String> getProNames() {
		return proNames;
	}

	public LinkedList<OperateEnum> getOperates() {
		return operates;
	}

	public LinkedList<Object> getValues() {
		return values;
	}

	public QueryProperty addQueryProperties(String proName, OperateEnum operate, Object value) {
		if (StringUtil.isNotEmpty(proName)) {
			proNames.add(proName);
			operates.add(operate);
			values.add(value);
		}
		return this;
	}
}
