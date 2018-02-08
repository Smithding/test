package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 获取同程单个酒店及其房型、图片的基本信息入参
 * @author kai.yang
 *
 */
public class SingleHotelDetailReq implements Serializable{
	private static final long serialVersionUID= 1L;
	
	/**
	 * 酒店 id
	 */
	@JSONField(name="ResId")
	private String resId;
	
	/**
	 * 房型（多个用英文逗号分隔）
	 */
	@JSONField(name = "ProId")
	private String proId;
	
	/**
	 * 请求类型（多个用英文逗号隔开，酒店信息：res;酒店图片信息：rimg;酒店房型信息：respro;）
	 */
	@JSONField(name = "RequestContent")
	private String requestContent;
	
	/**
	 * 政策来源,支持多个用英文逗号隔开
	 * 1：自签（默认）
	 * 2：携程
	 * 4：艺龙
	 * 8：Switch（默认）
	 * 不传默认同程自签和 Switch 同程对接资源
	 */
	@JSONField(name = "SourceForm")
	private String sourceForm;

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	public String getSourceForm() {
		return sourceForm;
	}

	public void setSourceForm(String sourceForm) {
		this.sourceForm = sourceForm;
	}
	
	
}
