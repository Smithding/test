package com.tempus.gss.product.ift.api.service.search;

import java.util.List;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.Profit;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicyChange;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.vo.Agent;

/**
 * 
 * <pre>
 * <b>根据航班信息匹配政策接口.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年9月4日 下午2:11:52
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月4日 下午2:11:52    cz
 *         new file.
 * </pre>
 */

public interface IftFlightQueryService {
	/**
	 * 航班白屏列表查询；根据shoppring结果匹配政策
	 * @param queryIBEDetail 航班对象
	 * @param iftPolicyList 政策集合
	 * @param customerTypeNo 客户编号
	 * @param request 航班查询请求参数
	 * @param calcRound 销售价格是否取整
	 * @param calcRule 计算价格方式
	 * @param profit 控润
	 * @return
	 */
	 public QueryIBEDetail mappingPriceSpec(QueryIBEDetail queryIBEDetail,List<IftPolicy> iftPolicyList, String customerTypeNo, RequestWithActor<FlightQueryRequest> request,boolean calcRound,int calcRule,Profit profit); 
	 /**
	  * 订单预订异步实时获取政策数据
	  * @param queryIBEDetail
	  * @param request
	  * @return
	  */
	 public List<IftPolicyChange> orderPolicy(Agent agent, QueryIBEDetail queryIBEDetail, int adtNumber, int chdNumber, int infNumber);
	 
	 /**
	  * PNR预订异步实时获取政策数据
	  * @param queryIBEDetail
	  * @param request
	  * @return
	  */
	 public List<IftPolicyChange> orderPolicyByPnr(Agent agent, QueryIBEDetail queryIBEDetail, String pnr, String pnrContext);
	 
	 /**根据查询白屏航班条件获取政策信息
	  * 
	  * @param request
	  * @return
	  */
	 public List<IftPolicy> matcPolicy(RequestWithActor<FlightQueryRequest> request);
}
