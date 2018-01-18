package com.tempus.gss.product.hol.api.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.tempus.gss.product.hol.api.entity.response.tc.CreditCardsTcType;

public class Tool {

	public static java.util.Date parseDate(String dateStr, String format) {
		java.util.Date date = null;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(format);
			String dt = dateStr.replaceAll("-", "/");
			if ((!dt.equals("")) && (dt.length() < format.length())) {
				dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
						"0");
			}
			date = (java.util.Date) df.parse(dt);
		} catch (Exception e) {
		}
		return date;
	}

	public static java.util.Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyy-MM-dd");
	}

	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	public static java.util.Date addDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	public static String md5(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	public static String encodeUri(String url) {

		try {
			return java.net.URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		return url;
	}

	public static String encryptDES(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return toHexString(cipher.doFinal(message.getBytes("UTF-8")));
	}

	public static String decryptDES(String message, String key) throws Exception {

		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}

	public static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}

		return digest;
	}

	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}

		return hexString.toString();
	}
	/**
	 * 将一个数字转化为2的n次方数组，数组里面装的是次方值
	 * @param num
	 * @return
	 */
	public static List<String> intToTwoPower(int num) {
		List<String> strs = new ArrayList<String>();
		String numStr=Integer.toBinaryString(num);
		StringBuffer bf=new StringBuffer();
		  for(int i=0;i<numStr.length();i++){
			  if(numStr.charAt(i)!='0'){
				  bf.append(numStr.length()-1-i);
			  }
		  }
		  int arr[]=new int[bf.length()];
		  for(int i=0;i<bf.length();i++){
		   arr[i]=bf.charAt(i)-'0';
		  }
		  for(int arrs : arr){
  			if(arrs == CreditCardsTcType.ZERO.getKey()){
  				strs.add(CreditCardsTcType.ZERO.getValue());
  			}
  			if(arrs == CreditCardsTcType.ONE.getKey()){
  				strs.add(CreditCardsTcType.ONE.getValue());
  			}
  			if(arrs == CreditCardsTcType.TWO.getKey()){
  				strs.add(CreditCardsTcType.TWO.getValue());
  			}
  			if(arrs == CreditCardsTcType.THREE.getKey()){
  				strs.add(CreditCardsTcType.THREE.getValue());
  			}
  			if(arrs == CreditCardsTcType.FOUR.getKey()){
  				strs.add(CreditCardsTcType.FOUR.getValue());
  			}
  			if(arrs == CreditCardsTcType.FIVE.getKey()){
  				strs.add(CreditCardsTcType.FIVE.getValue());
  			}
  		}
		  return strs;
	}
	
	public static <T> List<T> orderByKind(List<T> t, String one, String orderType, Class<T> clazz){
		
		Collections.sort(t, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				Object value1 = null;
				Object value2 = null;
				try {
					Field[] field= o1.getClass().getDeclaredFields();
					for(int i=0; i< field.length;i++){
						String namee= field[i].getName();
						String name = namee.substring(0,1).toUpperCase()+namee.substring(1);
						if(namee.equals(one)){
							String type = field[i].getGenericType().toString();
							System.out.println("type: "+ type);
					    	 if(type.equals("class java.lang.String")){   //如果type是类类型，则前面包含"class "，后面跟类名
				                    Method m = o1.getClass().getMethod("get"+name);
				                    value1 = (String) m.invoke(o1);    //调用getter方法获取属性值
					    	}else if(type.equals("class java.lang.Integer")){
					    		Method m = o1.getClass().getMethod("get"+name);
			                    value1 = (Integer) m.invoke(o1);    
					    	}else if(type.equals("class java.lang.Long")){
					    		Method m = o1.getClass().getMethod("get"+name);
			                    value1 = (Long) m.invoke(o1);   
					    	}
				                    break;
						}
					}
					Field[] field2= o2.getClass().getDeclaredFields();
					for(int i=0; i< field2.length;i++){
						String namee= field2[i].getName();
						String name = namee.substring(0,1).toUpperCase()+namee.substring(1);
						if(namee.equals(one)){
							String type = field[i].getGenericType().toString();
					    	 if(type.equals("class java.lang.String")){   //如果type是类类型，则前面包含"class "，后面跟类名
				                    Method m = o2.getClass().getMethod("get"+name);
				                    value2 = (String) m.invoke(o2);    //调用getter方法获取属性值
					    	 }else if(type.equals("class java.lang.Integer")){
						    		Method m = o2.getClass().getMethod("get"+name);
						    		value2 = (Integer) m.invoke(o2);    
						    	}else if(type.equals("class java.lang.Long")){
						    		Method m = o2.getClass().getMethod("get"+name);
						    		value2 = (Long) m.invoke(o2);   
						    	}
				                    break;
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(orderType.equals("desc")){
					if(Integer.valueOf(value1.toString()) > Integer.valueOf(value2.toString())){
						return 1;
					}else if(Integer.valueOf(value1.toString()) < Integer.valueOf(value2.toString())){
						return -1;
					}else{
						return 0;
					}
				}
				if(orderType.equals("asc")){
					if(Integer.valueOf(value1.toString()) < Integer.valueOf(value2.toString())){
						return 1;
					}else if(Integer.valueOf(value1.toString()) > Integer.valueOf(value2.toString())){
						return -1;
					}else{
						return 0;
					}
				}
				return 0;
			}
		});
		return t;
	}

}
