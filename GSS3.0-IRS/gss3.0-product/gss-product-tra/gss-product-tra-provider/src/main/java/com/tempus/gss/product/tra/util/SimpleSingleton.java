package com.tempus.gss.product.tra.util;

import java.lang.reflect.Constructor;
public class SimpleSingleton implements SingletonStrategy {
	
	private String singletonClassName = null;

    private Object singletonInstance = null;

    public SimpleSingleton() {}

	@Override
	public Object instance() {
		return singletonInstance;
	}

	@Override
	public void reset() {

        if (singletonClassName != null) {
            Class<?> clazz = null;
            try {
                clazz = Thread.currentThread().getContextClassLoader().loadClass(
                        singletonClassName);
                
                Constructor<?> constructor = clazz.getEnclosingConstructor();
                constructor.setAccessible(true);
                singletonInstance = constructor.newInstance();
            } catch (Exception ignore) {
                try {
                    clazz = Class.forName(singletonClassName);
                    Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[]{});
                    constructor.setAccessible(true);
                    singletonInstance = constructor.newInstance();
                } catch (Exception ignore2) {
                	ignore2.printStackTrace();
                }
            }

        }
	}

	@Override
	public void setSingletonClassName(String singletonClassName) {
		 this.singletonClassName = singletonClassName;
	     reset();
	}
}
