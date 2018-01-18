package com.tempus.gss.product.ift.api.entity.helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/* 
 * Excel 操作类
 *  */
public  class ExcelHelper {
	
	/**
	 * 07版本的excel处理模式   将字节数组转换成xsheet
	 *
	 * @param data       从文件服务器下载到的数据
	 * @return  XSSFSheet
	 * @throws IOException 
	 */
	public static XSSFSheet parseWhiteListOver2007(byte[] data) throws IOException {
		try{
		InputStream inputStream = new ByteArrayInputStream(data);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
			return sheet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 解析excle
	 * parseXSSFWorkbook:(这里用一句话描述这个方法的作用)
	 * @param  @param data
	 * @param  @return
	 * @param  @throws IOException    设定文件
	 * @return XSSFWorkbook    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public static XSSFWorkbook parseXSSFWorkbook(byte[] data) throws IOException {
		try{
		InputStream inputStream = new ByteArrayInputStream(data);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
		return xssfWorkbook;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 设置excl错误信息字体颜色
	 * getCellFont:(这里用一句话描述这个方法的作用)
	 * @param  @param cell    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public static void getCellFont(XSSFWorkbook xssfWorkbook,Cell cell,String errorInfo){
		XSSFCellStyle style = xssfWorkbook.createCellStyle();
		Font font = xssfWorkbook.createFont();
		font.setColor(Font.COLOR_RED);
		style.setFont(font);
		cell.setCellStyle(style);
		cell.setCellValue(errorInfo);
	}
	/**
	 * 07版本之前excel处理模式   将字节数组转换成xsheet
	 *
	 * @param data          从文件服务器下载到的数据
	 * @return HSSFSheet
	 */
	public static HSSFSheet parseWhiteList(byte[] data) {
		InputStream inputStream = new ByteArrayInputStream(data);
		try (HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream)) {
			HSSFSheet sheet = hssfWorkbook.getSheetAt(1);
			return sheet;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取单元格数据
	 */
	public static String getCellValue(Cell cell, int type) {
		try{
		CellType cellTypeEnum = cell.getCellTypeEnum();
		switch (cellTypeEnum) {
		case _NONE:
			break;
		case NUMERIC:
			if (type == 1) {
				// Excel中不包含浮点数，所以不处理
				return String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()).trim();
			} else {
				// Excel中不包含浮点数，所以不处理
				return String.valueOf(Double.valueOf(cell.getNumericCellValue()).doubleValue()).trim();
			}
		case STRING:
			return String.valueOf(cell.getStringCellValue());
		case FORMULA:
			break;
		case BLANK:
			break;
		case BOOLEAN:
			break;
		case ERROR:
			break;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
