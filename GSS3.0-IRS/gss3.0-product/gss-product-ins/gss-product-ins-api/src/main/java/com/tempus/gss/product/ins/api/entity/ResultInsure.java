package com.tempus.gss.product.ins.api.entity;

import java.io.Serializable;

public class ResultInsure implements Serializable {
	  //返回代码标识
private String code;
      //返回信息
private String message;
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
}
