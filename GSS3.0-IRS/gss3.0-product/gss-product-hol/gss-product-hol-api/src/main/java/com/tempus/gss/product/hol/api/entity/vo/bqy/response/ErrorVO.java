package com.tempus.gss.product.hol.api.entity.vo.bqy.response;

import java.io.Serializable;

public class ErrorVO implements Serializable {
	
	private String ErrorCode;
	
	private String ErrorMsg;

	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}

	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}
	
}
