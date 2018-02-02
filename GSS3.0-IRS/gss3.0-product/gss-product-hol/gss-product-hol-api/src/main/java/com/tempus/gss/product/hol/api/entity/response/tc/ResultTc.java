package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
/**
 * 同程所有接口返回的第一层数据
 * @author kai.yang
 * @param <T>
 */
public class ResultTc<T>  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String ret_code;
	private String err_msg;
	private T result;
	public String getRet_code() {
		return ret_code;
	}
	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	
	
	

}
