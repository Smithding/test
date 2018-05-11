package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ChannelLimitInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="App")
	private Boolean app;
	
	@JSONField(name="Online")
    private Boolean online;

	public Boolean getApp() {
		return app;
	}

	public void setApp(Boolean app) {
		this.app = app;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

}
