package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class MemberLimitInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="EDM")
	private Boolean EDM;
	
	@JSONField(name="General")
	private Boolean general;
	
	@JSONField(name="Gold")
    private Boolean gold;
	
	@JSONField(name="Platinum")
    private Boolean platinum;
	
	@JSONField(name="Diamond")
    private Boolean diamond;
	
	@JSONField(name="WeChat")
    private Boolean weChat;

	public Boolean getEDM() {
		return EDM;
	}

	public void setEDM(Boolean eDM) {
		EDM = eDM;
	}

	public Boolean getGeneral() {
		return general;
	}

	public void setGeneral(Boolean general) {
		this.general = general;
	}

	public Boolean getGold() {
		return gold;
	}

	public void setGold(Boolean gold) {
		this.gold = gold;
	}

	public Boolean getPlatinum() {
		return platinum;
	}

	public void setPlatinum(Boolean platinum) {
		this.platinum = platinum;
	}

	public Boolean getDiamond() {
		return diamond;
	}

	public void setDiamond(Boolean diamond) {
		this.diamond = diamond;
	}

	public Boolean getWeChat() {
		return weChat;
	}

	public void setWeChat(Boolean weChat) {
		this.weChat = weChat;
	}

}
