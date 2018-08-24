package com.tempus.gss.product.hol.api.report.utils;

/**
 * 用于保存使用注解类的相关信息
 * title：用于记录标题
 * order:用于记录标题在列的顺序
 * fieldName：用于记录在哪个属性使用的注解方便反射赋值
 * 
 */
public class ExcelHeader implements Comparable<ExcelHeader>{

	/*标题*/
	private String title;
	
	/*排序*/
	private int order;
	
	/*属性名*/
	private String fieldName;

	@Override
	public int compareTo(ExcelHeader o) {
		return order>o.order? 1:(order <o.order ? -1 :0 );
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public ExcelHeader(String title, int order, String fieldName) {
		this.title = title;
		this.order = order;
		this.fieldName = fieldName;
	}

	public ExcelHeader(int order, String fieldName) {
		this.order = order;
		this.fieldName = fieldName;
	}

	
	
	
}
