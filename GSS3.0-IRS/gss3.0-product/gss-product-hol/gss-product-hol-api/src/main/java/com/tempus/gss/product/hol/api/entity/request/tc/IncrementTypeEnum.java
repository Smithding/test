package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;
/**
 * 同步同程增量数据枚举类
 * @author kai.yang
 *
 */
public enum IncrementTypeEnum implements Serializable{
	/**
	 * 信息变更 1
	 */
	INFOCHANGE(1),  
	/**
	 * 价格变动 2
	 */
	PRICECHANGE(2),
	/**
	 * 无效增量 3
	 */
	INVALIDINCR(3),
	/**
	 * 有效增量 4
	 */
	VALIDINCR(4),
	/**
	 * 库存变动 7
	 */
	STORECHANGE(7);
	
	private int key;
	
	IncrementTypeEnum(int key){
		this.key=key;
	}

	public int getKey() {
		return key;
	}

	public static IncrementTypeEnum stateOf(int index)
    {
        for (IncrementTypeEnum key : values())
        {
            if (key.getKey()==index)
            {
                return key;
            }
        }
        return null;
    }
}
