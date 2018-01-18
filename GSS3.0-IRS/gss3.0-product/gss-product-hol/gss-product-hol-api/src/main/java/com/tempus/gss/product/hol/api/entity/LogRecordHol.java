package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 酒店信息同步日志类
 *
 */
public class LogRecordHol implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 业务代码-- eg:ORDER,POLICY,USER...... */
	protected String bizCode;
	
	/** 应用代码--目前默认为GSS3.0 */
	protected String appCode;
	
	/** 日志创建时间--取系统时间 */
	protected Date createTime;
	
	/** 业务号 eg: 订单号、政策ID...... */
	protected String bizNo;
	
	/** 日志标题 */
	protected String title;
	
	/** 日志详细描述 */
	protected String desc;
	
	/** 操作者IP */
	protected String requestIp;
	
	/** 操作类型 */
	protected String optType;
	
	/** 操作者姓名 */
	protected String optName;
	
	/** 操作者登录名 */
	protected String optLoginName;
	
	protected Long resId;
	
	protected Long proId;
	
	protected Long productUniqueId;
	
	/** 其它自定义属性 */
	protected Map<String, Object> otherOpts = new HashMap<>();

	public LogRecordHol() {
		super();
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getOptLoginName() {
		return optLoginName;
	}

	public void setOptLoginName(String optLoginName) {
		this.optLoginName = optLoginName;
	}

	public Map<String, Object> getOtherOpts() {
		return otherOpts;
	}

	public void setOtherOpts(Map<String, Object> otherOpts) {
		this.otherOpts = otherOpts;
	}
	
	public Object addOtherOpts(String key, Object value) {
		if(otherOpts == null)
			otherOpts = new HashMap<>();
		Object ret = otherOpts.put(key, value);
		return ret;
	}
	
	public Object getOtherOptByKey(String key) {
		if(otherOpts == null) return null;
		return otherOpts.get(key);
	}
	
	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public Long getProId() {
		return proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
	}

	public Long getProductUniqueId() {
		return productUniqueId;
	}

	public void setProductUniqueId(Long productUniqueId) {
		this.productUniqueId = productUniqueId;
	}

	public String toString() {
		StringBuilder map = new StringBuilder();
		map.append("; bizCode="+bizCode);
		map.append("; appCode="+appCode);
		map.append("; createTime="+createTime);
		map.append("; bizNo="+bizNo);
		map.append("; title="+title);
		map.append("; desc="+desc);
		map.append("; optLoginName="+optLoginName);
		map.append("; optName="+optName);
		map.append("; otherOpts="+otherOpts);
		return map.toString();
	}
	
}
