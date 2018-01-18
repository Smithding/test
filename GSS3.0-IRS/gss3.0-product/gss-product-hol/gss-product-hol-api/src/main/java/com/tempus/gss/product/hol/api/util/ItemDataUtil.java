package com.tempus.gss.product.hol.api.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

public class ItemDataUtil {
	public final static String USE_DEFAULT_VALUE="USE_DEFAULT_VALUE";
	private static Random random=new Random();
	
	public static Object setItemVal(Object obj) {
		return setItemVal(obj,null);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T setItemVal(Class<T> clazz) {
		try {
			return (T)setItemVal(clazz.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T setItemVal(Class<T> clazz,ISpecialVal specialVal) {
		try {
			return (T)setItemVal(clazz.newInstance(),specialVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T setItemFieldVal(Class<T> clazz,Object...params) {
		return setItemVal(clazz, (method,obj)->{
			for (int i = 0; i < params.length; i+=2) {
				if (method.getName().equals(params[i])) {
					return params[i+1];
				}
			}
			return USE_DEFAULT_VALUE;
		});
	}
	
	
	public static Object setItemVal(Object obj,ISpecialVal specialVal) {
		try {
			BeanInfo beanInfo=Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] pds=beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				Method method=pd.getWriteMethod();
				if (method!=null) {
					if (specialVal!=null) {
						Object result = specialVal.setVal(method, obj);
						if (!USE_DEFAULT_VALUE.equals(result)) {
							method.invoke(obj, result);
							continue;
						}
					}
					if(String.class==method.getParameterTypes()[0]){
						method.invoke(obj, "随机值"+random.nextInt(1000));
					}else if (Double.class==method.getParameterTypes()[0]) {
						method.invoke(obj, random.nextInt(1000)+0D);
					}else if (Long.class==method.getParameterTypes()[0]) {
						method.invoke(obj, random.nextInt(1000)+0L);
					}else if (Date.class==method.getParameterTypes()[0]) {
						Calendar calendar=Calendar.getInstance();
						calendar.setTime(new Date());
						calendar.add(Calendar.HOUR_OF_DAY, random.nextInt(24)*(random.nextInt(2)==1?1:-1));
						calendar.add(Calendar.MINUTE, random.nextInt(60)*(random.nextInt(2)==1?1:-1));
						calendar.add(Calendar.SECOND, random.nextInt(60)*(random.nextInt(2)==1?1:-1));
						method.invoke(obj, calendar.getTime());
					}else if (BigDecimal.class==method.getParameterTypes()[0])  {
						method.invoke(obj, new BigDecimal(random.nextInt(1000)));
					}else if (boolean.class==method.getParameterTypes()[0]) {
						method.invoke(obj, random.nextInt(2)==1);
					}else if (Integer.class==method.getParameterTypes()[0]) {
						method.invoke(obj, random.nextInt(1000));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	
	public static <T> List<T> getItemList(Class<T> clazz,int count) {
		return getItemList(clazz, count,null);
	}
	
	public static <T> List<T> getItemFieldList(Class<T> clazz,int count,Object...params) {
		return getItemList(clazz, count,(method,obj)->{
			for (int i = 0; i < params.length; i+=2) {
				if (method.getName().equals(params[i])) {
					return params[i+1];
				}
			}
			return USE_DEFAULT_VALUE;
		});
	}
	
	public static <T> List<T> getItemList(Class<T> clazz,int count,ISpecialVal specialVal) {
		try {
			List<T> list=new ArrayList<T>();
			for (int i = 0; i < count; i++) {
				T t=clazz.newInstance();
				setItemVal(t,specialVal);
				list.add(t);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T getFullItem(Class<T> clazz) {
		return getFullItem(clazz, null);
	}
	
	public static <T> T getFullItem(Class<T> clazz,ISpecialVal specialVal) {
		return setItemVal(clazz, (method,obj)->{
			try {
				if (specialVal!=null) {
					Object result = specialVal.setVal(method, obj);
					if (!USE_DEFAULT_VALUE.equals(result)) {
						return result;
					}
				}
				Class paramClazz=method.getParameterTypes()[0];
				if (paramClazz==List.class) {
					Type type=method.getGenericParameterTypes()[0];
					ParameterizedType pt = (ParameterizedType) type;
					Class listObjClazz = (Class) pt.getActualTypeArguments()[0];
					return getFullItemList(listObjClazz,5,specialVal);
				}else if (paramClazz.getName().startsWith("com.tempus")) {
					return getFullItem(paramClazz,specialVal);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ItemDataUtil.USE_DEFAULT_VALUE;
		});
	}
	
	public static <T> List<T> getFullItemList(Class<T> clazz,int count) {
		return getFullItemList(clazz, count, null);
	}
	
	public static <T> List<T> getFullItemList(Class<T> clazz,int count,ISpecialVal specialVal) {
		List<T> list=new ArrayList<T>();
		for (int i = 0; i < count; i++) {
			list.add(getFullItem(clazz,specialVal));
		}
		return list;
	}
	
	
	
	
	public static <T> T getFullItemField(Class<T> clazz,Object...params) {
		return getFullItem(clazz, (method,obj)->{
			for (int i = 0; i < params.length; i+=2) {
				if (method.getName().equals(params[i])) {
					return params[i+1];
				}
			}
			return USE_DEFAULT_VALUE;
		});
	}
	
	public static <T> List<T> getFullItemFieldList(Class<T> clazz,int count,Object...params) {
		return getFullItemList(clazz, count, (method,obj)->{
			for (int i = 0; i < params.length; i+=2) {
				if (method.getName().equals(params[i])) {
					return params[i+1];
				}
			}
			return USE_DEFAULT_VALUE;
		});
	}
}

