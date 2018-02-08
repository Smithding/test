package com.tempus.gss.product.tra.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TrainUtil {

	public static String getSeatClass(String seatName) {
		if(seatName.equals("硬座")){return "hardseat";}
		if(seatName.equals("软座")){return "softseat";}
		if(seatName.equals("一等座")){return "firstseat";}
		if(seatName.equals("二等座")){return "secondseat";}
		if(seatName.equals("硬卧上铺")){return "hardsleeperup";}
		if(seatName.equals("硬卧中铺")){return "hardsleepermid";}
		if(seatName.equals("硬卧")){return "hardsleepermid";}
		if(seatName.equals("硬卧下铺")){return "hardsleeperdown";}
		if(seatName.equals("软卧上铺")){return "softsleeperup";}
		if(seatName.equals("软卧下铺")){return "softsleeperdown";}
		if(seatName.equals("软卧")){return "softsleeperdown";}
		if(seatName.equals("无座")){return "noseat";}
		if(seatName.equals("商务座")){return "businessseat";}
		if(seatName.equals("特等座")){return "specialseat";}
		if(seatName.equals("高级软卧")){return "advancedsoftsleeper";}
		if(seatName.equals("其他")){return "otherseat";}
		return "";
	}



	
	public static String getType(String trainClass) {
		if(trainClass.equals("GD")){
			return "高速动车";
		}
		if(trainClass.equals("C")){
			return "城际高速";
		}
		if(trainClass.equals("D")){
			return "动车组";
		}
		if(trainClass.equals("KT")){
			return "空调特快";
		}
		if(trainClass.equals("KKS")){
			return "空调快速";
		}
		if(trainClass.equals("KPK")){
			return "空调普快";
		}
		if(trainClass.equals("KPM")){
			return "空调普慢";
		}
		if(trainClass.equals("KS")){
			return "快速";
		}
		if(trainClass.equals("PK")){
			return "普快";
		}
		if(trainClass.equals("PM")){
			return "普慢";
		}
		if(trainClass.equals("XGZ")){
			return "香港直通车";
		}
		if(trainClass.equals("Z")){
			return "直达特快";
		}
		return trainClass;
	}
	
	public static String getDays(String startTime,String runTimeSpan)throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(format.parse("2016-01-01 "+startTime));
		int i = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.MINUTE,Integer.valueOf(runTimeSpan));
		int o = calendar.get(Calendar.DAY_OF_MONTH);
		return (o-i+1)+"";
	}
	public static String getDay(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	public static String getChinese(String str){
		if(ChineseFormat.isMessyCode(str)){
			try {
				return new String(str.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(getDays("", "1034"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
