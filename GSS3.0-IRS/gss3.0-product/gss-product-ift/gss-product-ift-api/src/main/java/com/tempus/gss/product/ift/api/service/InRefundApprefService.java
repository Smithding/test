package com.tempus.gss.product.ift.api.service;

import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.vo.InRefundApprefRequest;

/**
 * 国际机票退款接口.
 */
public interface InRefundApprefService  {

	/**
	 * 更新国际退款申请单
	 *
	 * @param inRefundAppref
	 * @return
	 * @throws Exception
	 */
	int updateInRefundAppref(Map<String, Object> map) throws Exception;

	/**
	 * 查询国际退款申请单
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	InRefundApprefRequest getInRefundAppref(String orderid) throws Exception;

	/**
	 * 创建国际退款申请单.
	 *
	 * @param inRefundAppref 创建请求.
	 * @return
	 */
	int create(InRefundApprefRequest inRefundAppref)  throws Exception;

	int updateInRefundAppref(InRefundApprefRequest inRefundAppref);

	Page<InRefundApprefRequest> queryInRefApprefByVo(Page page,RequestWithActor<InRefundApprefRequest> requestWithActor);
}
