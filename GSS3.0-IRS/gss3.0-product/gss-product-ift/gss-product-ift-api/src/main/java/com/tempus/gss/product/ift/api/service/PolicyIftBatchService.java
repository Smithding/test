package com.tempus.gss.product.ift.api.service;

import java.util.Date;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.PolicyIftBatch;
import com.tempus.gss.product.ift.api.entity.exception.ProductException;
import com.tempus.gss.vo.Agent;

/**
 * <pre>
* <b>产品上传 操作服务接口.</b>
* <b>Description:</b> 
 */
public interface PolicyIftBatchService {

	/**
	 * 根据id 获取产品上传详细.
	 * 
	 * @param agent 终端信息
	 * @param id 查询ID
	 * @return boolean 执行结果
	 * @throws ProductException
	 */
	PolicyIftBatch get(Agent agent, Long id) throws ProductException;

	/**
	 * 查询产品列表
	 * 
	 * @param agent
	 * @param page
	 * @param no id
	 * @param voyageType
	 * @param batchNum
	 * @param depart
	 * @param arrive
	 * @param airLine
	 * @param policyType
	 * @param fixProfit 指定控润标识
	 * @param statuses 状态
	 * @param creator 创建人
	 * @param modifier 更新人
	 * @return Page<Policy>
	 * @throws ProductException
	 */
	Page<PolicyIftBatch> search(Agent agent, Page<PolicyIftBatch> page, Integer policyType, Long searchId, String searchName,
                             Date searchCreateTimeStart, Date searchCreateTimeEnd, Integer status,String creator,String modifier) throws ProductException;
	/**
	 * 新增产品上传信息
	 * 
	 * @param agent 终端信息
	 * @param Policy 未具有id的实体
	 * @return boolean 插入结果
	 * @throws ProductException
	 */
	long create(Agent agent, PolicyIftBatch PolicyIftBatch) throws ProductException;

	/**
	 * 更新产品信息.上传信息
	 * 
	 * @param agent 终端信息
	 * @param PolicyIftBatch 修改后的实体
	 * @throws ProductException
	 */
	void modify(Agent agent, PolicyIftBatch PolicyIftBatch) throws ProductException;

	/**
	 * 删除产品信息上传信息
	 * 
	 * @param agent 终端信息
	 * @param id 数据记录id
	 * @throws ProductException
	 */
	void remove(Agent agent, Long... id) throws ProductException;
}
