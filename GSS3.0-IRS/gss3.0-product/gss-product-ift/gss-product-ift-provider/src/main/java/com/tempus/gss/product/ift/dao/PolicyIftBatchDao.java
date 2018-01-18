package com.tempus.gss.product.ift.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.tempus.gss.product.ift.api.entity.PolicyIftBatch;

/**
 * <pre>
* <b>产品上传数据库控制层接口.</b>
* <b>Description:</b> 
 * </pre>
 */
public interface PolicyIftBatchDao extends AutoMapper<PolicyIftBatch> {

	/**
	 * 查找产品列表
	 * 
	 * @param owner 归集单位
	 * @param type 产品类型
	 * @param status
	 * @param policyType
	 * @param airLine
	 * @param arrive
	 * @param depart
	 * @param batchNum
	 * @param creator
	 * @param modifier
	 * @return List<Policy> 产品列表
	 */
	List<PolicyIftBatch> query(RowBounds page, @Param("owner") Integer owner, @Param("policyType") Integer policyType,
                            @Param("searchId") Long searchId, @Param("searchName") String searchName, @Param("searchCreateTimeStart") Date searchCreateTimeStart,
                            @Param("searchCreateTimeEnd") Date searchCreateTimeEnd, @Param("status") Integer status,
                            @Param("creator") String creator,@Param("modifier") String modifier);
	
	/**
	 * 查找指定ID的产品
	 * 
	 * @param owner 归集单位
	 * @param id 产品id
	 * @return Policy 产品信息
	 */
	PolicyIftBatch find(@Param("owner") int owner, @Param("id") long id);

	/**
	 * 添加产品
	 * 
	 * @param policy 产品信息
	 * @return int 添加数据行
	 */
	Long add(PolicyIftBatch PolicyIftBatch);
	
	/**
	 * 更新产品
	 * 
	 * @param policy 产品信息
	 * @return int 添加数据行
	 */
	void update(PolicyIftBatch PolicyIftBatch);

	/**
	 * 删除指定ID的产品
	 * 
	 * @param owner 归集单位
	 * @param modifier 修改人
	 * @param modifyTime 修改时间
	 * @param ids 待删除产品id
	 */
	void del(@Param("owner") int owner, @Param("modifier") String modifier, @Param("modifyTime") Date modifyTime,
			@Param("ids") Long... ids);
	
	
	/**
	 * 修改产品文件状态
	 * 
	 * @param account
	 * @param status
	 * @param ids
	 * @return
	 */
	void updateStatus(@Param("owner") int owner,
			@Param("auditTime") Date auditTime, @Param("modifier") String modifier, @Param("modifyTime") Date modifyTime,
			@Param("status") Integer status, @Param("ids") Long... ids);
	
	/**
	 * 查找指定产品文件ID和状态产品列表
	 * 
	 * @param owner 归集单位
	 * @param status 产品状态
	 * @param flag 查询方式 true: T.status= status false:T.status<>status
	 * @param ids
	 * @return List<Policy> 产品列表
	 */
	List<PolicyIftBatch> queryBatchByIDs(@Param("owner") Integer owner, @Param("status") Integer status,
                                      @Param("ids") Long... ids);

	/**
	 * 查找指定产品文件ID和排除状态之外的产品列表
	 * 
	 * @param owner 归集单位
	 * @param status 产品状态
	 * @param flag 查询方式 true: T.status= status false:T.status<>status
	 * @param ids
	 * @return List<Policy> 产品列表
	 */
	List<PolicyIftBatch> queryNotStatusByIDs(@Param("owner") Integer owner, @Param("status") Integer status,
                                          @Param("ids") Long... ids);

}
