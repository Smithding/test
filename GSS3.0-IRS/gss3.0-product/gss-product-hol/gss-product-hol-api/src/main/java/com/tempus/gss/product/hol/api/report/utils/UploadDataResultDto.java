package com.tempus.gss.product.hol.api.report.utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Excel导入工具类~~导入返回信息
 *
 */
public class UploadDataResultDto<T> {
	
	/*返回状态 */
	private String resultCode;
	
	/*返回错误信息*/
	private List<String> resultMsg = new ArrayList<String>();
	
	/*返回列表，里面会放入校验过的数据*/
	private List<T> datas = new ArrayList<T>();

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public List<String> getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(List<String> resultMsg) {
		this.resultMsg = resultMsg;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	

}
