package com.tempus.gss.product.ift.api.entity.setting;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 * <b>状态字典.</b>
 * <b>Description:</b> 状态,0:禁用; 1:启用
 *
 * <b>Author:</b> cz
 * <b>Date:</b> 2016年10月11日 下午3:52:51
 * <b>Copyright:</b> Copyright ©2016 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2016年10月11日 下午3:52:51    cz
 *         new file.
 * </pre>
 */
public enum Status {

	/** 0:禁用 */
	DISABLE(0, "禁用"),

	/** 1:启用 */
	ENABLE(1, "启用"),

    WAIT_AUDIT(2,"待审核"),

    AUDITED(3,"已审核");

	protected int key;

	protected String label;

	public static final Map<Integer, String> dicts = new LinkedHashMap<>();

	public static final Map<Integer, Status> items = new HashMap<>();

	static {
		for (Status item : Status.values()) {
			items.put(item.getKey(), item);
			dicts.put(item.getKey(), item.getLabel());
		}
	}

	Status(int key, String label) {
		this.key = key;
		this.label = label;
	}

	public int getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}

	public static String getLabel(Integer key) {
		return dicts.get(key);
	}

	public static Status valueOf(Integer key) {
		return items.get(key);
	}
}
