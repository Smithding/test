package com.tempus.gss.product.hol.api.report.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.tempus.gss.product.hol.api.report.Exception.ExcelException;
import com.tempus.gss.product.hol.api.report.annotation.util.ExcelField;
import com.tempus.gss.product.hol.api.report.annotation.util.ExcelImportField;
import com.tempus.gss.product.hol.api.report.annotation.validate.*;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * EXCEL操作工具类
 *
 */
@SuppressWarnings("resource")
public class FlagInfoExcelUtils{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FlagInfoExcelUtils.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/*标题*/
	private String title; 
	
	/*标题行高*/
	private Integer titleRow;
	
	/*当前行的下标*/
	private int currRowIndex;
	
	/*开始读取的行数*/
	private int startLine;
	
	/*结束行数*/
	private int endLine;
	
	/*当前列的下标*/
	private int curCollIndex;
	
	private Sheet sheet;
	
	/*当前列*/
	private Cell curCell;
	
	/*当前工作簿下标*/
	private int currSheetIndex;
	
	/*当前行*/
	private Row curRow;
	
	public Workbook wb;

	@SuppressWarnings("rawtypes")
	private static UploadDataResultDto uploadDataResultDto;
	/*获取实例*/
	@SuppressWarnings("rawtypes")
	public static FlagInfoExcelUtils getInstance(){
		 uploadDataResultDto = new UploadDataResultDto();
		return new FlagInfoExcelUtils();
	}
	//==========================<<<<<EXCEL 导出  START>>>>>=======================

	
	/**
	 * 不带标题的导出
	 * @param clz 导出对象的class
	 * @param list 导出集合
	 * @param isXssf 是否是xlsx 
	 * @return
	 * @throws
	 */
    public Workbook exportByObj(Class clz, List list, boolean isXssf) throws ExcelException {
		return this.exportByObj(clz, list, isXssf,null,null);
	}
    
    
	/**
	 * 带标题的导出
	 * @param clz 导出对象的class
	 * @param list 导出集合
	 * @param isXssf 是否是xlsx 
	 * @param title 标题
	 * @param titleRow 标题行高
	 * @return
	 * @throws ExcelException 
	 */
	
	@SuppressWarnings("rawtypes")
	public Workbook exportByObj(Class clz, List list, boolean isXssf, String title, Integer titleRow) throws ExcelException {
		Workbook wb;
		//判断是创建 xlsx 或者 xls
		if(isXssf){
			/*xlsx*/
			wb = new XSSFWorkbook();
		}else{
			/*xls*/
			wb = new HSSFWorkbook();
		}
		CellStyle cellStyle = wb.createCellStyle();
		
		Font font = wb.createFont();
		
		/*创建工作簿*/
		Sheet sheet = wb.createSheet();
		sheet.autoSizeColumn(1); 
		/*处理标题 设置标题高度*/
		if(!StringUtils.isEmpty(title)&& titleRow != null){
			this.titleRow = titleRow;
			this.title = title;
		}
		
		/*通过注解，对导出对象的标题，属性，排序进行设置*/
		List<ExcelHeader> headers = getExcelHeaders(clz);
		
		/*设置标题*/
		setTitle(title,titleRow,sheet,headers,cellStyle,font);
		
		/*设置导航标题*/
		setHeader(sheet, headers);
		
		Row row;
		if(list != null && list.size() >0){
			for(int i = 0 ;i < list.size();i++){
				row =sheet.createRow(i+currRowIndex);
				/*开始给每一列进行赋值*/
				for(int j = 0; j < headers.size(); j++){
					String methodStr = getMethod(headers.get(j));
					try {
						Method method = clz.getMethod(methodStr,null);
						Object value = method.invoke(list.get(i), null);
						row.createCell(j).setCellValue(value.toString());
						} catch (Exception e) {
						  LOGGER.error("export Excel by Obj ====>>>> Exception：{}",e);
						  throw new ExcelException(e);
					 }
				}
			}
		}
		return wb;
	}

	/**
	 * 根据属性名获取get方法名称
	 * @param header
	 * @return
	 */
	private String getMethod(ExcelHeader header) {
		 String fieldName =header.getFieldName();
		 fieldName = "get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
		return fieldName;
	}

	private void setHeader(Sheet sheet, List<ExcelHeader> headers) {
		Row row;
		row = sheet.createRow(currRowIndex);
		for (int i = 0; i < headers.size(); i++) {
			row.createCell(i).setCellValue(headers.get(i).getTitle());
		}
		++currRowIndex;
	}

	
	
	/**
	 * 设置标题
	 * @param title
	 * @param titleRow
	 */
	private void setTitle(String title, Integer titleRow, Sheet sheet, List<ExcelHeader> headers, CellStyle cellStyle, Font font) {
		if(!StringUtils.isEmpty(title)&& titleRow != null){
			Row row =sheet.createRow(0);
			//设置行高
			row.setHeightInPoints(Float.valueOf(titleRow.toString()));
			//行合并
			CellRangeAddress cra = new CellRangeAddress(0, 0, 0, headers.size()-1);
			sheet.addMergedRegion(cra);  
		/*	cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中
			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);*/
			cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			//设置字体
			//font.setBold(true);
			font.setFontHeightInPoints((short)(titleRow*0.5));
			cellStyle.setFont(font);
			//给标题赋值
			Cell cell = row.createCell(0);
			//设置字体居中
			cell.setCellStyle(cellStyle);
			cell.setCellValue(title);
			++currRowIndex;
		}
		
	}

	/**
	 * 根据传入的 导出对象，查询导出对象中注解的内容
	 * @param clz
	 * @return
	 * @throws Exception 
	 */
	private List<ExcelHeader> getExcelHeaders(Class clz){
		LOGGER.info("export Excel by Obj ====>>>> 导出的模板对象是：{}",clz);

		List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
		
		/*获得所有属性*/
		Field[] declaredFields = clz.getDeclaredFields();
		/*遍历所有属性*/
		for(Field field : declaredFields){
			//判断当前属性上面是否有ExcelField注解存在
			if(field.isAnnotationPresent(ExcelField.class)){
				ExcelField annotation = field.getAnnotation(ExcelField.class);
				headers.add(new ExcelHeader(annotation.title(),annotation.order(),field.getName()));
			}
		}
		Collections.sort(headers);
		LOGGER.info("export Excel by Obj ====>>>> 标题对象排序以后结果是：{}", JSON.toJSONString(headers));
		return headers;
	}

	
	
	//#######################################################################################################
	
	//==========================<<<<<EXCEL 导入开始>>>>>=======================
	
	/**
	 * Excel导入
	 * @return
	 * @throws Exception 
	 */
	public <T>UploadDataResultDto<T> importDataByFile(InputStream inputStream, Class clz, int currSheetIndex, int startLine, int endLine) throws ExcelException{
		LOGGER.info("impory Excel by importDataByClassPath ====>>>> start");
		try {
			try {
				wb = WorkbookFactory.create(inputStream);
			} catch (EncryptedDocumentException e) {
				e.printStackTrace();
			} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
				e.printStackTrace();
			}
			this.startLine = startLine;
			this.endLine = endLine;
			this.currSheetIndex = currSheetIndex;
			/*执行导入数据*/
			try {
				return importDatas(clz);
			} catch (Exception e) {
				throw new ExcelException(e);
			}
			
		} catch (InvalidFormatException e) {
			LOGGER.error("impory Excel ERROR BY========>>>>:{}",e);
		} catch (IOException e) {
			LOGGER.error("impory Excel ERROR BY========>>>>>:{}",e);
		}
		return null;
		
		
	}
	
	
   /* public <T>UploadDataResultDto<T> importDataByPath(String path,Class clz,int currSheetIndex,int startLine, int endLine) throws ExcelException{
    	
    	return importDataByFile(new File(path),clz,currSheetIndex,startLine,endLine);
	}*/
    
    /**
     * 读数据
     * @param
     * @param
     * @param
     * @param
     * @return
     * @throws
     */
	 @SuppressWarnings("unchecked")
	private <T>UploadDataResultDto<T> importDatas(Class clz) throws Exception {
         //最终返回结果
		 
		 /*获取headers*/
		List<ExcelHeader> headers = getImportExcelHeaders(clz);
		if(headers == null || headers.size() == 0 ){
			throw new ExcelException("传入的导入对象没有使用<<ExcelImportField>>注解");
		}
		/*读取工作簿*/
	    sheet = wb.getSheetAt(currSheetIndex);
	    
	    //遍历列
	    T newInstance = null;
	    
	    /*读取行*/
	    for(int i = currRowIndex;i <= sheet.getLastRowNum()-endLine;i++){
	    	
	    	boolean resultflag = true;
	    	
	    	curRow = sheet.getRow(i);
	    	 /*读取列*/
		    int lastCellNum = (int)curRow.getLastCellNum();
		    
		    if(lastCellNum != headers.size()){
		    	throw new ExcelException("传入的导入对象 列数和 导入对象"+clz+"，不一致");
		    }
		    String fieldName = null;
		    Field field = null;
		    Object formatValue;
		    StringBuffer errorMsg = new StringBuffer() ;
		  	//创建对象实例
	    	newInstance = (T) clz.newInstance();
		    for(int c = 0;c < lastCellNum;c ++ ){
		    	//获得当前列
		    	curCell = curRow.getCell(c);
		    	//获得该列对应的属性名称
		    	fieldName = headers.get(c).getFieldName();
		    	//获得当前字段的 属性
		    	field = clz.getDeclaredField(fieldName);
		    	//获得属性的类型
		    	Class<?> type = field.getType();
		    	//获取这个数据
				String setfieldName = "set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
				//获得方法
				Method method = clz.getMethod(setfieldName, type);
				//读取字段数据
				formatValue = getFormatValue(curCell);
				
	       		 //电话校验
	       		 if(field.isAnnotationPresent(Telephone.class)){
	       			Telephone annotationTelephone = field.getAnnotation(Telephone.class);
	       			boolean isTelephone = HandleTelephone(formatValue,resultflag);
	       			if(!isTelephone){
	       				if(StringUtils.isEmpty(annotationTelephone.message())){
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,"不是电话号"));
	       				}else{
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,annotationTelephone.message()));
	       				}
	       				resultflag = false;
	       			}
	       				
	       		 }
	       		//范围校验
	       		 if(field.isAnnotationPresent(Range.class)){
	       			Range annotationRange = field.getAnnotation(Range.class);
	       			boolean handelIsNum = handelIsNum(formatValue);
	       			if(!handelIsNum){
	       				resultflag = false;
	       				errorMsg.append(addErrMsg(currRowIndex+1,c+1,"不是数字类型"));
	       			}else{
	       				if(formatValue != null){
	       					if(annotationRange.min() <= Integer.valueOf(formatValue.toString())&& Integer.valueOf(formatValue.toString()) <= annotationRange.max()){
		       				}else{
		       					if(StringUtils.isEmpty(annotationRange.message())){
			       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,"不在指定范围内"));
			       				}else{
			       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,annotationRange.message()));
			       				}
		       					resultflag = false;
		       				}
	       				}
	       			}
	       		 }
	       		//手机号校验
	       		 if(field.isAnnotationPresent(Phone.class)){
	       			Phone annotationPhone = field.getAnnotation(Phone.class);
	       			boolean isTelephone = HandlePhone(formatValue,resultflag);
	       			if(!isTelephone){
	       				if(StringUtils.isEmpty(annotationPhone.message())){
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,"不是手机号"));
	       				}else{
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,annotationPhone.message()));
	       				}
	       				resultflag = false;
	       			}
	       		 }
	       		//非空校验
	       		 if(field.isAnnotationPresent(NotNull.class)){
	       			NotNull annotationNotNull = field.getAnnotation(NotNull.class);
	       			if(formatValue == null || "".equals(formatValue.toString())){
	       				if(StringUtils.isEmpty(annotationNotNull.message())){
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,"不能为空"));
	       				}else{
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,annotationNotNull.message()));
	       				}
	       				resultflag = false;
	       			}
	       		 }

	       		 //日期校验
				if(field.isAnnotationPresent(IsDate.class)){
					IsDate annotationIsDate = field.getAnnotation(IsDate.class);
					boolean handelIsDate = handelIsDate(formatValue);
					if(!handelIsDate) {
						if (StringUtils.isEmpty(annotationIsDate.message())) {
							errorMsg.append(addErrMsg(currRowIndex+1,c+1,"不是日期类型,格式如:1990-01-01 08:00:00"));
						}else{
							errorMsg.append(addErrMsg(currRowIndex+1,c+1,annotationIsDate.message()));
						}
						resultflag = false;
					}
				}
	       		 
	       		//最小值验
	       		 if(field.isAnnotationPresent(Min.class)){
	       			Min annotationNotMin = field.getAnnotation(Min.class);
	       			boolean handelIsNum = handelIsNum(formatValue);
	       			if(!handelIsNum){
	       				resultflag = false;
	       				errorMsg.append(addErrMsg(currRowIndex+1,c+1,"不是数字类型"));
	       			}else{
	       				if(formatValue != null){
	       					if(Integer.valueOf(formatValue.toString()) < annotationNotMin.value()){
	       						if(StringUtils.isEmpty(annotationNotMin.message())){
			       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,"小于最小值"));
			       				}else{
			       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,annotationNotMin.message()));
			       				}
	       						resultflag = false;
	       					}
	       				}
	       			}
	       		 }
	       		//最大值校验
	       		 if(field.isAnnotationPresent(Max.class)){
	       			Max annotationNotMax = field.getAnnotation(Max.class);
	       			boolean handelIsNum = handelIsNum(formatValue);
	       			if(!handelIsNum){
	       				resultflag = false;
	       				errorMsg.append(addErrMsg(currRowIndex+1,c+1,"不是数字类型"));
	       			}else{
	       				if(formatValue != null){
	       					if(Integer.valueOf(formatValue.toString()) > annotationNotMax.value()){
	       						if(StringUtils.isEmpty(annotationNotMax.message())){
			       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,"大于最大值"));
			       				}else{
			       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,annotationNotMax.message()));
			       				}
	       						resultflag = false;
	       					}
	       				}
	       			}
	       		 }
	       		 
	       		//url校验
	       		 if(field.isAnnotationPresent(IsUrl.class)){
	       			IsUrl annotationIsUrl = field.getAnnotation(IsUrl.class);
	       			boolean handelIsUrl = handelIsUrl(formatValue);
	       			if(!handelIsUrl){
	       				if(StringUtils.isEmpty(annotationIsUrl.message())){
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,"不是url格式"));
	       				}else{
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,annotationIsUrl.message()));
	       				}
	       				resultflag = false;
	       			}
	       		 }
	       		 //数字校验
	       		 if(field.isAnnotationPresent(IsNum.class)){
	       			IsNum annotationIsNum = field.getAnnotation(IsNum.class);
	       			boolean handelIsNum = handelIsNum(formatValue);
	       			if(!handelIsNum){
	       				if(StringUtils.isEmpty(annotationIsNum.message())){
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,"不是数字格式"));
	       				}else{
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,annotationIsNum.message()));
	       				}
	       				resultflag = false;
	       			}
	       		 }
	       		 //中文校验
	       		 if(field.isAnnotationPresent(IsChinese.class)){
	       			IsChinese annotationIsChinese = field.getAnnotation(IsChinese.class);
	       			boolean handelChinese = handelChinese(formatValue);
	       			if(!handelChinese){
	       				if(StringUtils.isEmpty(annotationIsChinese.message())){
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,"不是中文格式"));
	       				}else{
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,annotationIsChinese.message()));
	       				}
	       				resultflag = false;
	       			}
	       		 }
	       		 //身份证校验
	       		 if(field.isAnnotationPresent(IdCard.class)){
	       			IdCard annotationIdCard = field.getAnnotation(IdCard.class);
	       			boolean handelIdCard = handelIdCard(formatValue);
	       			if(!handelIdCard){
	       				if(StringUtils.isEmpty(annotationIdCard.message())){
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,"不是身份证格式"));
	       				}else{
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,annotationIdCard.message()));
	       				}
	       				resultflag = false;
	       			}
	       		 }
	       		 //邮箱校验
	       		 if(field.isAnnotationPresent(Email.class)){
	       			Email annotationEmail = field.getAnnotation(Email.class);
	       			boolean handelEmail = handelEmail(formatValue);
	       			if(!handelEmail){
	       				if(StringUtils.isEmpty(annotationEmail.message())){
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,"不是邮箱格式"));
	       				}else{
	       					errorMsg.append(addErrMsg(currRowIndex+1,c+1,annotationEmail.message()));
	       				}
	       				resultflag = false;
	       			}
	       		 }
	       		 
	       		 //自定义 正则表达式校验
	       		 if(field.isAnnotationPresent(CustomValidate.class)){
	       			CustomValidate CustomValidate = field.getAnnotation(CustomValidate.class);
	       			boolean handelCustomValidate = handelCustomValidate(formatValue,CustomValidate.regex());
	       			if(!handelCustomValidate){
	       				errorMsg.append(addErrMsg(currRowIndex+1,c+1,CustomValidate.message()));
	       				resultflag = false;
	       			}
	       		 }
	       		 //别名校验
	       		 if(field.isAnnotationPresent(Alias.class)){
	       			Alias annotationAlias = field.getAnnotation(Alias.class);
	       			AliasItem[] values = annotationAlias.values();
	       			for(AliasItem alias : values){
	       				String source = alias.source();
	       				String target = alias.target();
	       				if(source.equals(formatValue.toString())){
	       					formatValue = target;
	       				}
	       			}
	       		 }
	       		 if(!StringUtils.isEmpty(errorMsg.toString())){
	       			uploadDataResultDto.getResultMsg().add(errorMsg.toString());
	       			errorMsg.setLength(0);
	       		 }
	       		 //该列的数据最终结果
	       		 if(resultflag){
	       			 if(type.getName().equals("java.lang.Integer")){
	       			     method.invoke(newInstance, new Object[]{Integer.valueOf(formatValue.toString())});continue;
	       			 }
	       			if(type.getName().equals("java.lang.Double")){
	       			   method.invoke(newInstance, new Object[]{Double.valueOf(formatValue.toString())}); continue;
	       			 }
	       			if(type.getName().equals("java.lang.Float")){
	       			   method.invoke(newInstance, new Object[]{Float.valueOf(formatValue.toString())}); continue;
	       			 }
	       			if(type.getName().equals("java.lang.Long")){
	       			   method.invoke(newInstance, new Object[]{Long.valueOf(formatValue.toString())}); continue;
	       			 }
	       			if(type.getName().equals("java.lang.Short")){
	       			   method.invoke(newInstance, new Object[]{Short.valueOf(formatValue.toString())}); continue;
	       			 }
	       			if(type.getName().equals("java.lang.Byte")){
	       			  method.invoke(newInstance, new Object[]{Byte.valueOf(formatValue.toString())}); continue;
	       			 }
	       			if(type.getName().equals("java.lang.Boolean")){
	       			   method.invoke(newInstance, new Object[]{Boolean.valueOf(formatValue.toString())}); continue;
	       			 }
	       			if(type.getName().equals("java.lang.String")){
	       			  method.invoke(newInstance, new Object[]{String.valueOf(formatValue.toString())}); continue;
	       			 }
	       			 if(type.getName().equals("java.util.Date")){
	       			  method.invoke(newInstance, new Object[]{sdf.parse(formatValue.toString())}); continue;
	       			 }
	       			 if(type.getName().equals("java.math.BigDecimal")){
	       			  method.invoke(newInstance, new Object[]{new BigDecimal(formatValue.toString())}); continue;
	       			 }
	       			if(type.getName().equals("int")){
	       			 method.invoke(newInstance, new Object[]{(int)formatValue});continue;
	       			 }
	       			if(type.getName().equals("double")){
	       			 method.invoke(newInstance, new Object[]{(double)formatValue});continue;
	       			 }
	       			if(type.getName().equals("long")){
	       			 method.invoke(newInstance, new Object[]{(long)formatValue});continue;
	       			 }
	       			if(type.getName().equals("short")){
	       			 method.invoke(newInstance, new Object[]{(short)formatValue});continue;
	       			 }
	       			if(type.getName().equals("boolean")){
	       			 method.invoke(newInstance, new Object[]{(boolean)formatValue});continue;
	       			 }
	       			if(type.getName().equals("float")){
	       			 method.invoke(newInstance, new Object[]{(float)formatValue});continue;
	       			 }
	       		 }
		    }
		    if(resultflag){
		    	 uploadDataResultDto.getDatas().add(newInstance);
		    }
		    currRowIndex++;
	    }
	    
	    if(uploadDataResultDto.getResultMsg().size() != 0){
	    	uploadDataResultDto.setResultCode("500");
	    }else{
	    	uploadDataResultDto.setResultCode("200");
	    }
		return uploadDataResultDto;
	}
	 
	//====================================校验方法实现类===start===================================================

	 /**
	  * 自定义校验
	  * @param formatValue
	  * @return
	  */
	 private boolean handelCustomValidate(Object formatValue,String regex) {
		 if(formatValue == null){return true;}
	     return Pattern.compile(regex).matcher(formatValue.toString()).matches();
	}


	/**
	  * 邮箱校验
	  * @param formatValue
	  * @return
	  */
	 private boolean handelEmail(Object formatValue) {
		 if(formatValue == null){return true;}
	     return Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$").matcher(formatValue.toString()).matches();
	}


	/**
	  * 身份证校验
	  * @param formatValue
	  * @return
	  */
	 private boolean handelIdCard(Object formatValue) {
		 if(formatValue == null){return true;}
	     return Pattern.compile("^\\d{15}|^\\d{17}([0-9]|X|x)$").matcher(formatValue.toString()).matches();
	}


	/**
	  * 判断是否是中文
	  * @param formatValue
	  * @return
	  */
	 private boolean handelChinese(Object formatValue) {
		 if(formatValue == null){return true;}
	     return Pattern.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$").matcher(formatValue.toString()).matches();
	}


	/**
	  * 判断是是否是url
	  * @param formatValue
	  * @return
	  */
	 private boolean handelIsUrl(Object formatValue) {
		 if(formatValue == null){return true;}
	     return Pattern.compile("^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$").matcher(formatValue.toString()).matches();
	}


	private boolean  handelIsNum(Object formatValue){
		    if(formatValue == null || "".equals(String.valueOf(formatValue))){return true;}
	        return Pattern.compile("^([+-]?)\\d*\\.?\\d+$").matcher(formatValue.toString()).matches();
	 }
	 
	 /**
	  * 验证手机号
	  * @param formatValue
	  * @param resultflag
	  * @return
	  */
	 private boolean HandlePhone(Object formatValue, boolean resultflag) {
		 if(formatValue == null){return true;}
		 return Pattern
         .compile("^((13[0-9])|(15[^4,\\D])|(18[^1^4,\\D]))\\d{8}")
         .matcher(formatValue.toString()).matches();
	}

	
	 /**
	  * 校验字段是否是手机号
	  * @param formatValue
	  * @param resultflag
	  * @return
	  */
	private boolean HandleTelephone(Object formatValue, boolean resultflag) {
	  if(formatValue == null){return true;}
	  return Pattern.compile("^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$").matcher(formatValue.toString()).matches();
	}

	/**
     * 校验日期
	 * @param formatValue
     * @return
     */
	private boolean handelIsDate(Object formatValue) {
		if(formatValue == null){
			return true;
		}
		try {
			// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			sdf.setLenient(false);
			sdf.parse(formatValue.toString());
		   } catch (ParseException e) {

			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
			}
		return true;
	}
	
	

	 //====================================校验方法实现类==end====================================================
	
	/**
	 * 错误信息链接
	 * @param rowNum
	 * @param colNum
	 * @param mess
	 * @return
	 */
	private String addErrMsg(int rowNum,int colNum,String mess){
		return "第"+rowNum+"行第"+colNum+"列，"+mess;
	}
	
	
	private List<ExcelHeader> getImportExcelHeaders(Class clz) throws ExcelException {
		LOGGER.info("import Excel ====>>>> 导入的模板对象是：{}",clz);

		List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
		
		/*获得所有属性*/
		Field[] declaredFields = clz.getDeclaredFields();
		if(declaredFields == null || declaredFields.length ==0){
			throw new ExcelException("传入的导入对象没有使用<<ExcelImportField>>注解");
		}
		/*遍历所有属性*/
		for(Field field : declaredFields){
			//判断当前属性上面是否有ExcelField注解存在@ExcelImportField
			if(field.isAnnotationPresent(ExcelImportField.class)){
				ExcelImportField annotation = field.getAnnotation(ExcelImportField.class);
				headers.add(new ExcelHeader(annotation.order(),field.getName()));
			}
		}
		Collections.sort(headers);
		LOGGER.info("import Excel ====>>>> 标题对象排序以后结果是：{}", JSON.toJSONString(headers));
		currRowIndex = startLine;
		return headers;
	}
	
	
	/**
	 * 读取Excel数据
	 * @param cell
	 * @return
	 */
	private  Object getFormatValue(Cell cell) {
		 //暂存单元格内容 
		Object value = ""; 
		  //匹配单元格内容 
		  if(cell!=null){
			  switch (cell.getCellType()) { 
			   //数据格式类型 
			   case Cell.CELL_TYPE_NUMERIC:
				    //判断是否是日期类型 
				    if(DateUtil.isCellDateFormatted(cell)){
					     Date date = (Date) cell.getDateCellValue();
					     if(date!=null){ 
						      //格式化日期 
						      value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date); 
					     }else{ 
					    	 value = ""; 
					     } 
				    }else{ 
					     //格式化数据 
					     value =new  DecimalFormat("0").format(cell.getNumericCellValue()); 
				    } 
				    break; 
			   //字符串类型 
			  case Cell.CELL_TYPE_STRING:
				    value = cell.getStringCellValue(); 
				    break; 
			   //公式生成类型 
			   case Cell.CELL_TYPE_FORMULA:
				    //导入时如果为公式生成的数据则无值 
				   ////这样对于字符串cell.getStringCellValue()方法即可取得其值，如果公式生成的是数值，使用cell.getStringCellValue()方法会抛出IllegalStateException异常，在异常处理中使用cell.getNumericCellValue();即可
				    /*if(!cell.getStringCellValue().equals("")){ 
				    	value = cell.getStringCellValue(); 
				    }else{ 
				    	value = cell.getNumericCellValue(); 
				    } */
				   try {
					   value = cell.getStringCellValue();
				   } catch (IllegalStateException e) {
					   value = cell.getNumericCellValue();
				   }
				    break; 
			   //空白 
			   case Cell.CELL_TYPE_BLANK:
				    break; 
			   //布尔型 
			   case Cell.CELL_TYPE_BOOLEAN:
				    value = cell.getBooleanCellValue(); 
				    break; 
			   //错误格式 
			   case Cell.CELL_TYPE_ERROR:
				    break; 
			   //数字格式   
			   default: 
		   			value = cell.toString(); 
			  } 
		  }
		  return value;
	}
}
