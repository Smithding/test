package com.tempus.gss.product.hol.api.entity.vo.bqy.response;

import java.io.Serializable;

/**
 * 同程酒店返回结果
 */
public class ResponseResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private ResponseStatus ResponseStatus;
	
	private T Result;

	public ResponseStatus getResponseStatus() {
		return ResponseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		ResponseStatus = responseStatus;
	}

	public T getResult() {
		return Result;
	}

	public void setResult(T result) {
		Result = result;
	}
	
}
