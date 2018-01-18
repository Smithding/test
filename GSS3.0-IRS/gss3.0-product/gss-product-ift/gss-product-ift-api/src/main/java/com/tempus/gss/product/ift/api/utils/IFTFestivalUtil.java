package com.tempus.gss.product.ift.api.utils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tempus.gss.system.service.IParamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * <pre>
 * <b>获取节假日工具服务，节假日数据来源参数【holiday】.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2017年10月28日 下午4:43:16
 * <b>Copyright:</b> Copyright ©2017 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2017年10月28日 下午4:43:16    cz
 *         new file.
 * </pre>
 */
@Service
public class IFTFestivalUtil {
	
	@Reference
	private IParamService iParamService;
	
	private List<Date> festival = new ArrayList<Date>();// 节假日


	/**
	 * 获取当年所有节假日
	 * 
	 * @return List<Date> 当年所有节假日
	 */
	public List<Date> getFestival() {
		String holidayVale = iParamService.getValueByKey("holiday");
		if(StringUtils.isNotBlank(holidayVale)){
			String[] holidays = holidayVale.split(",");
			for(String holiday : holidays){
				Date holidayDate = getDate(holiday);
				festival.add(holidayDate);
			}
		}
		return this.festival;
	}

	/**
	 * 判断一个日期是否日节假日 法定节假日只判断月份和天，不判断年
	 * 
	 * @param date
	 * @return
	 */
	public boolean isFestival(Date date) {
		boolean festival = false;
		Calendar fcal = Calendar.getInstance();
		Calendar dcal = Calendar.getInstance();
		dcal.setTime(date);
		List<Date> list = this.getFestival();
		for (Date dt : list) {
			fcal.setTime(dt);
			// 法定节假日判断
			if (fcal.get(Calendar.YEAR) == dcal.get(Calendar.YEAR) && fcal.get(Calendar.MONTH) == dcal.get(Calendar.MONTH) && fcal.get(Calendar.DATE) == dcal.get(Calendar.DATE)) {
				festival = true;
			}
		}
		return festival;
	}

	/**
	 * 周六周日判断
	 * 
	 * @param date
	 * @return
	 */
	public boolean isWeekend(Date date) {
		boolean weekend = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			weekend = true;
		}
		return weekend;
	}


	/**
	 * 字符串转时间,格式：yyyy-MM-dd
	 * 
	 * @param str
	 * @return
	 */
	public Date getDate(String str) {
		Date dt = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dt = df.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dt;

	}

	public String getDate(Date date) {
		String dt = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dt = df.format(date);
		return dt;
	}


}
