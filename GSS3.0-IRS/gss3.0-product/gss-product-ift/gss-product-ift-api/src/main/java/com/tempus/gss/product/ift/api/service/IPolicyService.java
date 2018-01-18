package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.controller.Result;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.Policy;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.product.ift.api.entity.exception.ProductException;
import com.tempus.gss.product.ift.api.entity.vo.PolicyVo;
import com.tempus.gss.vo.Agent;
import java.util.List;

/**
 * 国际机票产品服务接口.
 */
public interface IPolicyService  {

	/**
	 * 创建政策.
	 *
	 * @param policy
	 * @return
	 * @throws Exception
	 */
	int createPolicy(RequestWithActor<Policy> policy) throws Exception;

	/**
	 * 修改政策.
	 *
	 * @param policy
	 * @return
	 * @throws Exception
	 */
	int updatePolicy(RequestWithActor<Policy> policy) throws Exception;
	
	
	/**
	 * 修改政策.
	 *
	 * @param policy
	 * @return
	 * @throws Exception
	 */
	int updatePolicyById(RequestWithActor<Policy> policy) throws Exception;
	

	/**
	 * 政策查询
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Page<Policy> selectPolicy(Page<Policy> page,RequestWithActor<PolicyVo> query) throws Exception;

	/**
	 * 删除政策.
	 *
	 * @param policyId
	 * @return
	 * @throws Exception
	 */
	int deletePolicy(RequestWithActor<Long> policyId) throws Exception;

	/**
	 * 挂起、解挂政策.
	 *
	 * @param policyId
	 * @param hangUp   ：true（挂起，停用），false（解挂，启用）.
	 * @return
	 * @throws Exception
	 */
	int hangUpPolicy(RequestWithActor<Long> policyId, boolean hangUp) throws Exception;
	/**
	 * 根据出发地点和到达地点，航司查询政策.
	 *
	 * @param record
	 * @return
	 * @throws Exception
	 */
	List<Policy> queryObjByOD (Policy record) throws Exception;
	/**
	 * 
	* getPolicyById(根据id查找policy)
	* @param id
	* @return
	* Policy
	* @exception
	* @since 1.0.0
	 */
	Policy getPolicyById(Long id);
	
	/**
	 * 
	* getPolicyByPolicyId(根据policyId查找policy)
	* @param policyId
	* @return
	* Policy
	* @exception
	* @since 1.0.0
	 */
	Policy getPolicyByPolicyId(Long policyId);

	/**
	 * 上传产品信息.
	 * @param type 产品类型
	 * @param 文件路径
	 * @throws ProductException
	 */
	List<String> importFile(Agent agent, Integer type, String filePath, String name, String num, Integer status, Integer fileType) throws ProductException;

	/**
	 * 将文本数据转换成对象
	 * @param filePath
	 * @return
	 */
	public List<Policy> importFile(String filePath);
	/**
	 * 根据拼接的id组成的字符串查找List<Policy>
	 * @param policyIds
	 * @return
	 */
	List<Policy> getPolicyByIds(String policyIds);

	/**
	 * 根据SaleOrderExt数据创建PNR
	 * @param createPnr
	 * @return
	 */
	 Result createPnr(SaleOrderExt saleOrderExt);

}
