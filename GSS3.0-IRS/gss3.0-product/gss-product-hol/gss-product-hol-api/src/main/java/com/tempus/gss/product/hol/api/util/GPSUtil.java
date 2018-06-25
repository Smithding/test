package com.tempus.gss.product.hol.api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GPSUtil {
	private static final double EARTH_RADIUS = 6378.137;
	private static double rad(double d)
	{
	    return d * Math.PI / 180.0;
	}
	public static double getDistance(double lat1, double lon1, double lat2, double lon2)
	{
	    double radLat1 = rad(lat1);
	    double radLat2 = rad(lat2);
	    double a = radLat1 - radLat2;
	    double b = rad(lon1) - rad(lon2);
	    double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	     Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	    s = s * EARTH_RADIUS;
	   // s = Math.round(s * 100000) / 100000;
	    BigDecimal bg = new BigDecimal(s).setScale(2, RoundingMode.UP);//UP四舍五入
	    return bg.doubleValue();
	    
	}
	public static void main(String[] args) {
		double s = getDistance(22.54642109902500000000,113.97567112334000000000,22.53212629322500000000,113.92600195984000000000);
		System.out.println(s);
	}

}
