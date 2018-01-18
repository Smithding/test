package com.tempus.gss.product.tra.util;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
	static Logger logger = LoggerFactory.getLogger(Util.class);
	private static DecimalFormat decimalFormator = null;

	/**
	 * 获取一个随机数当作页面上的验证数字。防止同session在多页面操作时引起的混乱
	 * 
	 * @return
	 */
	public static String getValidateNum() {
		long lResult = Math.round(Math.random() * 1000000000000000000.0);
		return String.valueOf(lResult);
	}

	public static String getDateRandomId() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(Util.getDateFormatUsStr(new Date(), "yyyyMMddHHmmss"));
		double a = 0.0;
		while (true) {
			a = Math.random();
			if (a != 0)
				break;
		}
		buffer.append((int) (100000 * a));
		return buffer.toString();
	}

	/**
	 * 获取费用的数字，只保留小数点1位。55.45 ->55.5 55.635->55.6
	 * 
	 * @param fee
	 * @return
	 */
	public static double formatFee(double fee) {
		return Math.round(fee * 10) * 1.0 / 10;
	}

	/**
	 * 获取价格的数字，不保留小数点。55.51->56 55.490->55
	 * 
	 * @param fee
	 * @return
	 */
	public static double formatPrice(double price) {
		return Math.round(price);
	}

	/**
	 * 
	 * @Title: isBankFormat
	 * @Description: 判断一个格式是否是银行货币格式
	 * @param
	 * @param str
	 * @param
	 * @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isBankFormat(String str) {

		Pattern pattern = Pattern
				.compile("^[+]?(0\\.\\d+)|([1-9][0-9]*(\\.\\d{0,2})?)$");
		Matcher matcher = pattern.matcher(str);
		boolean flag = matcher.matches();
		return flag;

	}

	private static DecimalFormat getDecimalFormator() {
		if (decimalFormator == null)
			decimalFormator = new DecimalFormat("#,###,###.00");
		return decimalFormator;
	}

	/**
	 * Description: 以“#,###,###.00”的格式输出数字
	 * 
	 * @param num
	 * @return 以“#,###,###.00”的格式输出数字
	 */
	public static String decimalFormat(double num) {
		DecimalFormat df = getDecimalFormator();
		return df.format(num);
	}

	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param str
	 * @param token
	 *            (分隔符)
	 * @return
	 */
	public static String[] getStrArray(String str, String token) {
		if (isNull(str))
			return null;
		StringTokenizer st = new StringTokenizer(str, token);
		ArrayList<String> strlist = new ArrayList<String>();
		for (; st.hasMoreElements(); strlist.add(st.nextToken()))
			;
		String strarr[] = new String[strlist.size()];
		for (int i = 0; i < strlist.size(); i++)
			strarr[i] = strlist.get(i).toString().trim();

		return strarr;
	}

	/**
	 * 字符串转换为list
	 * 
	 * @param str
	 * @param token
	 *            (分隔符)
	 * @return
	 */
	public static List<String> getStrList(String str, String token) {
		if (isNull(str))
			return null;
		StringTokenizer st = new StringTokenizer(str, token);
		ArrayList<String> strlist = new ArrayList<String>();
		for (; st.hasMoreElements(); strlist.add(st.nextToken()))
			;
		return strlist;
	}

	/** 判断一个字符是否是数字 */
	public static boolean isDigital(char c) {
		return (c >= '0' && c <= '9') || c == '.' || c == '-';
	}

	public static int getLength(String value) {
		if (isNull(value))
			return 0;
		return value.trim().length();
	}

	/** 判断一个字符串是否全部是数字组成 */
	public static boolean isDigitalString(String value) {
		if (isNull(value))
			return false;
		for (int i = 0; i < value.length(); i++) {
			if (!isDigital(value.charAt(i)))
				return false;
		}
		return true;
	}

	/** 判断一个字符是否是数字0-9 */
	public static boolean isInt(char c) {
		return (c >= '0' && c <= '9');
	}

	/** 判断一个字符串是否全部是数字0-9组成 */
	public static boolean isIntString(String value) {
		if (isNull(value))
			return false;
		for (int i = 0; i < value.length(); i++) {
			if (!isInt(value.charAt(i)))
				return false;
		}
		return true;
	}

	/** 判断一个字符串是否全部是由以下字符组成（"0-9"，".","/","-","()"） */
	public static boolean isFixedString(String value) {
		if (isNull(value))
			return false;
		if (getCharCount(value, '(') != getCharCount(value, ')'))
			return false;
		for (int i = 0; i < value.length(); i++) {
			if (!isFixedStr(value.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * 判断一个字符是否是以下字符（"0-9"，".","/","()"）
	 */
	private static boolean isFixedStr(char c) {
		return (c >= '0' && c <= '9') || c == '.' || c == '-' || c == '/'
				|| c == '(' || c == ')';
	}

	/** 判断一个字符是否是字母 */
	public static boolean isAlpha(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	/** 判断一个字符是否是数字或者字符 */
	public static boolean isAlphaOrDigital(char c) {
		return isDigital(c) || isAlpha(c);
	}

	/** 判断一个字符串是否全部是数字或者字符组成 */
	public static boolean isAlphaOrDigital(String value) {
		if (isNull(value))
			return false;
		for (int i = 0; i < value.length(); i++) {
			if (!isAlphaOrDigital(value.charAt(i)))
				return false;
		}
		return true;
	}

	/** 判断一个字符串是否全部是字符组成 */
	public static boolean isAlphaString(String value) {
		if (isNull(value))
			return false;
		for (int i = 0; i < value.length(); i++) {
			if (!isAlpha(value.charAt(i)))
				return false;
		}
		return true;
	}

	/** 从一行line获得字符串的数组，没有数据返回null */
	public static String[] splitString(String line, char deli) {
		if (Util.isNull(line))
			return new String[] {};
		String[] array_tokens = line.split("[" + deli + "]");
		int count = Util.getCharCount(line, deli);
		String[] tokens = new String[count + 1];
		for (int i = 0; i < count + 1; i++) {
			if (array_tokens != null && i < array_tokens.length) {
				if (!isNull(array_tokens[i]))
					tokens[i] = array_tokens[i].trim().toUpperCase();
				else
					tokens[i] = "";
			} else
				tokens[i] = "";
		}
		return tokens;
	}

	/**
	 * 判断某一个字符在字符串中出现的次数
	 */
	public static int getCharCount(String value, char c) {
		if (value == null)
			return 0;
		int count = 0;
		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == c)
				count++;
		}
		return count;
	}

	public static int getTravelskyPnrLength() {
		return 5;
	}

	public static double getDouble(String value) {
		Double i = null;
		try {
			i = new Double(value);
		} catch (Exception e) {

		}
		if (i != null)
			return i.doubleValue();
		return -1.0;
	}

	public static int getInt(String value) {
		Integer i = null;
		try {
			i = new Integer(value);
		} catch (Exception e) {

		}
		if (i != null)
			return i.intValue();
		return -1;
	}

	public static String trim(String value) {
		return isNull(value) ? "" : value.trim();
	}

	public static String replace(String value, char c) {
		if (isNull(value))
			return "";
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) != c)
				buff.append(value.charAt(i));
		}
		return buff.toString();
	}

	public static boolean validatePnrFormat(String pnrno) {
		if (isNull(pnrno))
			return false;
		pnrno = pnrno.trim();
		if (pnrno.length() != getTravelskyPnrLength())
			return false;
		if (!isAlphaOrDigital(pnrno))
			return false;
		return true;
	}

	/**
	 * 把字符串org中去掉字符串filter中出现的所有字符
	 */
	public static String replace(String org, String filter) {
		if (isNull(org))
			return "";
		StringBuffer buff = new StringBuffer();
		for (int i = 0; filter != null && i < filter.length(); i++) {
			org = replace(org, filter.charAt(i));
		}
		buff.append(org);
		return buff.toString();
	}

	/** 返回标准化的浮点数 */
	public static double getNormalDouble(double value, int num) {
		return Math.round(value * Math.pow(10, num)) / (Math.pow(10, num));
	}

	/** 判断两个字符串是否相等 */
	public static boolean equals(String value1, String value2) {
		if (value1 != null && value1.equals(value2))
			return true;
		return false;
	}

	/** 判断两个双浮点数是否相等 */
	public static boolean equals(double value1, double value2) {
		if (Math.abs(value1 - value2) < 0.0001)
			return true;
		return false;
	}

	/** 判断两个字符串是否相等 */
	public static boolean equalsIgnoreCase(String value1, String value2) {
		if (value1 != null && value1.equalsIgnoreCase(value2))
			return true;
		return false;
	}

	/**
	 * 判断（字符串、对象、集合）是否为空？true空。 作 者：Administrator 日 期：2012-12-6-上午10:57:22
	 * 
	 * @param obj
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String) {
			/** 判断字符串是否为空：true空，false非空。 */
			String temp = (String) obj;
			return temp.trim().length() < 1;
		} else if (obj instanceof Object[]) {
			Object[] objects = (Object[]) obj;
			return objects.length < 1;
		} else if (obj instanceof Collection<?>) {
			/** 集合对象为空 或者 里面无对象都 当空处理 */
			if ((Collection) obj == null || ((Collection) obj).size() == 0)
				return true;
			for (Iterator<?> it = ((Collection) obj).iterator(); it.hasNext();) {
				if (it.next() != null)
					return false;
			}
			return true;
		} else if (obj instanceof Map<?, ?>) {
			if ((Map) obj == null)
				return true;
			if (((Map) obj).isEmpty())
				return true;
		} else if (obj instanceof Date) {
			/** 判断日期变量是否为空 */
			return (Date) obj == null;
		} else if (obj instanceof Integer) {
			/** 判断整型变量是否为空或者小于0 */
			return (Integer) obj == null || ((Integer) obj).intValue() < 0;
		}
		return false;
	}

	/** 从用户输入得到字符串,空则返回"" */
	public static String getString(String value) {
		return (value != null && !value.equals("")) ? value.trim() : "";
	}

	/** 获得字符串变量(upper)，如果字符串为空返回"" */
	public static String getUpperString(String value) {
		return getUpperString(value, "");
	}

	/** 获得字符串变量(upper)，如果字符串为空返回null_str */
	public static String getUpperString(String value, String null_str) {
		return value != null ? value.trim().toUpperCase() : null_str;
	}

	/** 获得字符串变量(lower)，如果字符串为空返回null_str */
	public static String getLowerUpperString(String value, String null_str) {
		return value != null ? value.trim().toLowerCase() : null_str;
	}

	/** 获得字符串变量(lower)，如果字符串为空返回null_str */
	public static String getLowerUpperString(String value) {
		return getLowerUpperString(value, "");
	}

	/** 获得字符串变量，如果字符串为空返回null_str */
	public static String getString(String value, String null_str) {
		if (value == null || equalsIgnoreCase(value, "NULL"))
			return null_str;
		return value.trim();
	}

	/** 获得字符串变量，如果字符串为空返回null_str或者"" */
	public static String getStringnullandspace(String value, String null_str) {
		value = value.trim();
		if (value == null || value.equals("")
				|| equalsIgnoreCase(value, "NULL"))
			return null_str;
		return value.trim();
	}

	/** 获得字符串变量，如果字符串为空返回null_str */
	public static String getDateString(String value, String null_str) {
		return value != null ? value.trim() : "0000-00-00";
	}

	/** 显示浮点数 */
	public static String getSimpleNumberFormat(double value, int int_num,
			int fract_num) {
		String result = getNumberFormat(value, int_num, fract_num);
		return replace(result, ",");
	}

	/** 显示浮点数 */
	public static String getNumberFormat(double value, int int_num,
			int fract_num) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumIntegerDigits(int_num);
		nf.setMaximumFractionDigits(fract_num);
		nf.setMinimumFractionDigits(fract_num);
		return nf.format(value);
	}

	/** 显示浮点数 */
	public static String getCnCurrency(double value, int int_num, int fract_num) {
		NumberFormat nf = NumberFormat
				.getCurrencyInstance(Locale.SIMPLIFIED_CHINESE);
		nf.setMaximumIntegerDigits(int_num);
		nf.setMaximumFractionDigits(fract_num);
		nf.setMinimumFractionDigits(fract_num);
		return nf.format(value);
	}

	/** 获得整型的double数，并返回字符串类型 */
	public static String getIntString(double value) {
		int int_val = (int) (value);
		return Integer.toString(int_val);
	}

	/**
	 * 中文数字
	 */
	private static String[] cnNumber = { "零", "一", "二", "三", "四", "五", "六",
			"七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八",
			"十九", "二十" };

	public static String getChineseNumber(int i) {
		if (i >= 0 && i < cnNumber.length)
			return cnNumber[i];
		else
			return "";
	}

	/** 字符串编码 */
	public static String encode(String password) {
		return password;
	}

	/** 字符串解码 */
	public static String decode(String password) {
		return password;
	}

	/**
	 * 判断大客户代码是否符合以下规则：（九位）六位字母+三位数字，例如PEKXMN001；
	 */
	public static boolean isGroupNameValid(String groupName) {
		if (groupName == null || groupName.length() != 9)
			return false;
		String prefix = groupName.substring(0, 6);
		for (int i = 0; i < prefix.length(); i++) {
			if (!isAlpha(prefix.charAt(i)))
				return false;
		}
		String suffix = groupName.substring(6);
		for (int i = 0; i < suffix.length(); i++) {
			if (!isDigital(suffix.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * 对大客户代码进行转化，遇数字即转化为字母，规则：0->A,1->B,2->C,3->D,4->E,5->F,6->G,7->H,8->I,9->J
	 * 例如PEKXMN001转化为PEKXMNAAB
	 */
	public static String convertGroupName(String orginalGroupName) {
		StringBuffer newGroupName = new StringBuffer(
				orginalGroupName.substring(0, 6));
		char chNew;
		for (int j = 6; j < orginalGroupName.length(); j++) {
			chNew = (char) (orginalGroupName.charAt(j) + 17);
			newGroupName.append(chNew);
		}
		return newGroupName.toString();
	}

	/**
	 * 将整数值转换为二位字符的字符串，0->"00", 1->"01",...,99->"99"
	 */
	public static String getStandartNumber(int num) {
		if (num > 9)
			return new Integer(num).toString();
		if (num >= 0 && num <= 9)
			return "0" + new Integer(num).toString();
		return "";
	}

	// 判断一个数是否为double类型。判断是否为浮点数，包括double和float
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pattern.matcher(str).matches();
	}

	public static boolean containsInt(int[] valueArray, int value) {
		if (valueArray == null || valueArray.length == 0)
			return false;
		for (int i = 0; i < valueArray.length; i++) {
			if (value == valueArray[i])
				return true;
		}
		return false;
	}

	public static String getTime(String date, String date2) {
		if (Util.isNull(date) || Util.isNull(date2)) {
			return "";
		} else {
			if (date2.length() > 10) {
				return date2;
			} else {
				Date startDate = Util.getCnDateNew(date);
				Integer startHours = 0;
				Integer endHours = 0;
				if (!Util.isNull(date)) {
					startHours = Integer.parseInt(date.substring(
							date.indexOf(" ") + 1, date.indexOf(":")));
				}
				if (!Util.isNull(date2)) {
					endHours = Integer.parseInt(date2.substring(
							date2.indexOf(":") - 2, date2.indexOf(":")));
				}
				if (!Util.isNull(startHours) && !Util.isNull(endHours)) {
					if (startHours > endHours) {
						return Util.getDateFormatCnStr(
								Util.getPreTime(startDate, +(24 * 60)),
								"yyyy-MM-dd")
								+ " "
								+ date2.replaceAll("null ", "");
					} else {
						return Util.getDateFormatCnStr(startDate, "yyyy-MM-dd")
								+ " " + date2.replaceAll("null ", "");
					}
				} else {
					return "";
				}
			}
		}

	}

	/**
	 * 方法描述: 时间前推或后推分钟,其中minute表示分钟. 正表示 之后 ，负数表之前 作 者： yiming.zhang 日 期：
	 * 2012-11-30-下午04:14:03
	 * 
	 * @param oriengTime
	 * @param minute
	 * @return 返回类型： Date
	 */
	public static Date getPreTime(Date originalTime, int minute) {
		// SimpleDateFormat format = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long Time = (originalTime.getTime() / 1000) + minute * 60;
			originalTime.setTime(Time * 1000);
		} catch (Exception e) {
		}
		return originalTime;
	}

	/**
	 * 获得时间对象，输入时间为“2007-12-12”
	 */
	public static Date getCnDate(String date_str, String patt) {
		if (isNull(date_str))
			return null;
		String pattern = "yyyy-MM-dd";
		if (!isNull(patt))
			pattern = patt;
		Date result = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,
					Locale.SIMPLIFIED_CHINESE);
			result = sdf.parse(date_str);
		} catch (Exception e) {
			logger.error("时间转换出错：Date:" + date_str + ",pattern:" + patt);
			e.printStackTrace();
		}
		return result;
	}

	/** 获得时间对象，支持两种格式的输入时间 */
	public static Date getCnDateNew(String date_str) {
		String pattern = "yyyy-MM-dd";
		if (!Util.isNull(date_str) && date_str.indexOf('-') == -1)
			pattern = "yyyyMMdd";
		return getCnDate(date_str, pattern);
	}

	/** 获得中文格式的时间字符串 */
	public static String getDateFormatCnStr(Date date, String pattern) {
		if (date == null)
			return "";
		String result = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,
					Locale.SIMPLIFIED_CHINESE);
			result = sdf.format(date);
		} catch (Exception e) {
			logger.error("时间转换出错：Date" + date.toString() + ",pattern" + pattern);
			e.printStackTrace();
		}
		if (!isNull(result))
			return result;
		return "";
	}

	/** 获得英文格式的时间字符串 */
	public static String getDateFormatUsStr(Date date, String pattern) {
		if (date == null)
			return "";
		String result = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
			result = sdf.format(date);
			if (!isNull(result))
				result = result.toUpperCase();
		} catch (Exception e) {
			logger.error("时间转换出错：Date" + date.toString() + ",pattern" + pattern);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将字符串每割fragLength长度增加一个<br />
	 * 
	 */
	public static String getFragString(String originalString, int fragLength) {
		StringBuffer buf = new StringBuffer();
		int index = 0;
		while (index + fragLength < originalString.length()) {
			buf.append(originalString.substring(index, index + fragLength))
					.append("<br />");
			index += fragLength;
		}
		buf.append(originalString.substring(index));
		return buf.toString();
	}

	public static boolean judgeStrIsGb2312(String str, int[] errCharIndex) {
		if (str == null) {
			errCharIndex[0] = -1;
			return true;
		}
		for (int i = 0; i < str.length(); i++) {
			String chara = str.substring(i, i + 1);
			byte[] bts = chara.getBytes();
			if (bts.length == 2) {
				String s1 = (Integer.toHexString(bts[0]).length() > 2 ? Integer
						.toHexString(bts[0]).substring(6) : Integer
						.toHexString(bts[0]));
				String s2 = (Integer.toHexString(bts[1]).length() > 2 ? Integer
						.toHexString(bts[1]).substring(6) : Integer
						.toHexString(bts[1]));
				int i1 = Integer.valueOf(s1, 16).intValue();
				if (i1 < 0xb0 || i1 > 0xf7) {
					errCharIndex[0] = i;
					return false;
				}
				int i2 = Integer.valueOf(s2, 16).intValue();
				if (i2 <= 0xa0 || i2 >= 0xff) {
					errCharIndex[0] = i;
					return false;
				}
			}
		}
		errCharIndex[0] = -1;
		return true;
	}

	/**
	 * 如果字符穿中有汉字,判断此汉字是否在gb2312字符集内，西文字符做判断
	 * 
	 * @param str
	 * @return
	 */
	public static boolean judgeStrIsGb2312(String str) {
		int[] errCharIndex = new int[1];
		return judgeStrIsGb2312(str, errCharIndex);
	}

	/** 将segidxs数字字符串里面的元素按照从低到高进行排序“053241”-》“012345” */
	public static String sortStringAsc(String segidxs) {
		if (isNull(segidxs))
			return "";
		char[] items = segidxs.toCharArray();
		for (int i = 0; i < items.length; i++) {
			for (int j = i + 1; j < items.length; j++) {
				if (items[j] < items[i]) {
					char b = items[i];
					items[i] = items[j];
					items[j] = b;
				}
			}
		}
		return String.valueOf(items);
	}

	/** 替换字符串中的javascript特殊字符 xbxia */
	public static String javascriptEscape(String input) {
		if (input == null) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		int length = input.length();
		for (int i = 0; i < length; i++) {
			char c = input.charAt(i);
			if (c == '\"') {
				result.append("\\\"");
			} else if (c == '\'') {
				result.append("\\'");
			} else if (c == '\\') {
				result.append("\\\\");
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}

	/** 替换字符串中的html特殊字符 xbxia */
	public static String htmlEscape(String input) {
		if (input == null) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		int length = input.length();
		for (int i = 0; i < length; i++) {
			char c = input.charAt(i);
			if (c == '&') {
				result.append("&amp;");
			} else if (c == '\"') {
				result.append("&quot;");
			} else if (c == '<') {
				result.append("&lt;");
			} else if (c == '>') {
				result.append("&gt;");
			} else if (c == '\'') {
				result.append("&#39;");
			} else if (c == '\\') {
				result.append("&#092;");
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}

	/**
	 * 将姓名转化为“名/姓”
	 * 
	 * @param str
	 * @return
	 */
	public static String getEnName(String str) {
		int index = str.indexOf("/");
		String enName = "";
		enName = str.substring(index + 1, str.length()) + "/"
				+ str.substring(0, index);
		return enName;
	}

	// 读取properties配置文件
	public static Map<String, String> getPropertiesVal(String fileUrl) {

		Properties properties = new Properties();
		Map<String, String> keyValue = new HashMap<String, String>();
		try {
			InputStream inStream = Util.class.getResourceAsStream(fileUrl);
			properties.load(inStream);
			Set<Entry<Object, Object>> set = properties.entrySet();
			Iterator<Entry<Object, Object>> it = set.iterator();
			while (it.hasNext()) {
				Entry<Object, Object> entry = (Entry<Object, Object>) it.next();
				keyValue.put(entry.getKey().toString(), entry.getValue()
						.toString());
			}
			inStream.close();
			return keyValue;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("找不到对应文件：" + fileUrl);
			return null;
		}
	}

}
