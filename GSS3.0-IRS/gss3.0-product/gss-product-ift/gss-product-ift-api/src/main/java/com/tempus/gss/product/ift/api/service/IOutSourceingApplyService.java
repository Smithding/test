package com.tempus.gss.product.ift.api.service;

import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.OutSourceingApply;
import com.tempus.gss.product.ift.api.entity.vo.OutSourceingApplyVo;

/**
 *
 * 国际机票临时外购付款接口.
 *
 */
public interface IOutSourceingApplyService {

	int create(OutSourceingApplyVo outSourceingApplyVo) throws Exception;

	Page<OutSourceingApply> queryInRefApprefByVo(Page<OutSourceingApply> page,
			RequestWithActor<OutSourceingApplyVo> requestWithActor);

	OutSourceingApply getInRefundApprefById(String orderId);

	int updateInRefundAppref(Map<String, Object> map);

	int updateByPrimaryKeySelective(OutSourceingApply outSourceingApplyVo);

	OutSourceingApply getValueById(String orderId);

	String getBankNameById(String bankId);
}