package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TC主题标签
 * @author kai.yang
 *
 */
public class ResThemeRelation implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 主题名称s
	 */
	@JSONField(name = "ThemeName")
	private String themeName;
	/**
	 * 主题 Id
	 */
	@JSONField(name = "ThemeId")
	private Long themeId;
	public String getThemeName() {
		return themeName;
	}
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	public Long getThemeId() {
		return themeId;
	}
	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}
	
	
	
	
}
