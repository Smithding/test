package com.tempus.gss.product.hol.api.service;

import java.io.Serializable;

import org.springframework.scheduling.annotation.AsyncResult;

public class FutureResult<V> extends AsyncResult<V> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public FutureResult(V value) {
		super(value);
		// TODO Auto-generated constructor stub
	}
	
}
