package com.tempus.gss.product.ift.dao;

import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.tempus.gss.product.ift.api.entity.vo.InRefundApprefRequest;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public interface InRefundApprefDao extends BaseDao<InRefundApprefRequest, InRefundApprefRequest> {
	/**
	 * 保存对象
	 * @param traveller Traveller
	 */
    int insertAndGetId(InRefundApprefRequest inRefundApprefVo);
    
    InRefundApprefRequest selectInRefundApprefVo(@Param("orderid")String orderid);
    
	/**
	 * 根据申请单号修改申请单
	 * @param paramsMap Map
	 */
	void updateStatus(Map<String, Object> paramsMap);

    InRefundApprefRequest selectInRefundApprefBySettlementId(@Param("settlementId")Long settlementId);
}
