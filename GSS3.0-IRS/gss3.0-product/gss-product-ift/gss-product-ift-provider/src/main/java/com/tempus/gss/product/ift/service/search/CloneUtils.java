package com.tempus.gss.product.ift.service.search;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 克隆对象以及对象中的属性和bean
 * @author juan.yin
 *
 */
public class CloneUtils implements Cloneable,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /**
     * 克隆对象
     * @param object
     * @return
     */
	public static Object deepCopy(Object object) {
		ObjectOutputStream os = null;
		ObjectInputStream ois = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			os.writeObject(object);
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			Object target = ois.readObject();
			return target;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				os.close();
			} catch (IOException e) {
				os = null;
				ois = null;
			}
		}
		return null;
	}
}
