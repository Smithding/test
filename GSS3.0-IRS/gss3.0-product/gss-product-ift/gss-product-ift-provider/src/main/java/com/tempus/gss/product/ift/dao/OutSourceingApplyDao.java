package com.tempus.gss.product.ift.dao;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.OutSourceingApply;
import com.tempus.gss.product.ift.api.entity.vo.OutSourceingApplyVo;

/**
 *
 * OutSourceingApply 表数据库控制层接口
 *
 */
public interface OutSourceingApplyDao extends BaseDao<OutSourceingApply, OutSourceingApplyVo> {
	
	int insertAndGetId(OutSourceingApplyVo outSourceingApplyVo);

	List<OutSourceingApply> queryObjByKey(Page<OutSourceingApplyVo> page, OutSourceingApplyVo entity);

	OutSourceingApply selectInRefundApprefVo(String orderId);

	void updateStatus(Map<String, Object> map);

	int updateByOrderId(OutSourceingApply outSourceingApply);

    List<OutSourceingApply> selectValueById(String orderId);

	String selectBankNameById(String bankId);
}