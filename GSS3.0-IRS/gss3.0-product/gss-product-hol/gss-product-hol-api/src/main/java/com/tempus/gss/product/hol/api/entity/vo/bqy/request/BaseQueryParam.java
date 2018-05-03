package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import java.io.Serializable;

public class BaseQueryParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long AgentId;		//代理人Id
	
	private String Token;		//请求Token

	public long getAgentId() {
		return AgentId;
	}

	public void setAgentId(long agentId) {
		AgentId = agentId;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}
	
}
