package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;

import com.tempus.gss.product.hol.api.entity.vo.bqy.response.ErrorVO;

public class ResponseStatus implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int Ack;
	
	private String Message;
	
	private ErrorVO Error;
	
	private String Timestamp;
	
	private String Version;

	public int getAck() {
		return Ack;
	}

	public void setAck(int ack) {
		Ack = ack;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public ErrorVO getError() {
		return Error;
	}

	public void setError(ErrorVO error) {
		Error = error;
	}

	public String getTimestamp() {
		return Timestamp;
	}

	public void setTimestamp(String timestamp) {
		Timestamp = timestamp;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}
	
}
