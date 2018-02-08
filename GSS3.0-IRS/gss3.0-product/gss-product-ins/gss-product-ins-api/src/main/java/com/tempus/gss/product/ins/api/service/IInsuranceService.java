package com.tempus.gss.product.ins.api.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ins.api.entity.Insurance;
import com.tempus.gss.product.ins.api.entity.vo.InsuranceVo;

/**
 * 保险产品服务.
 */
public interface IInsuranceService {
	/**
	 * 查询保险产品列表. 不分页
	 * 
	 * @param requestWithActor
	 *            查询参数.
	 * @return
	 */
	List<Insurance> queryList(RequestWithActor<InsuranceVo> requestWithActor);

	/**
	 * 
	 * getDefaultList:根据险种类型(1,2,3)获取默认的产品列表
	 *
	 * @param  @param requestWithActor
	 * @param  @return    设定文件
	 * @return List<Insurance>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	List<Insurance> getDefaultList(RequestWithActor<List<String>> requestWithActor,Integer insuranceKindType);

	/**
	 * 创建保险产品接口
	 * @param requestWithActor
	 * @return 返回主键
	 */
	Long createInsurance(RequestWithActor<Insurance> requestWithActor);
	/**
	 *
	 * @param requestWithActor
	 */
	Long updateInsurance(RequestWithActor<Insurance> requestWithActor);
	/**
	 * 获取对应的产品
	 * @param requestWithActor
	 * @return
	 */
	Insurance getInsurance(RequestWithActor<Long> requestWithActor);



	/**
	 * 通过条件查询保险产品列表 分页
	 * @param pageRequest
	 * @return
	 */
	public Page<Insurance> queryInsuranceList(Page<Insurance> page,RequestWithActor<InsuranceVo> pageRequest);
	/**
	 * 通过条件查询保险产品列表 分页
	 * @param pageRequest
	 * @return
	 */
	public Page<Insurance> queryInsuranceAllList(Page<Insurance> page,RequestWithActor<InsuranceVo> pageRequest);
	/**
	 * 删除
	 * @param requestWithActor
	 * @return
	 */
	public Long delInsurance(RequestWithActor<Insurance> requestWithActor);
	/**
	 * 查询产品
	 */
	List<Insurance> selectInsurance(RequestWithActor<Insurance> requestWithActor);
	/**
	 * 根据保险类型返回默认的保险产品.
	 * 
	 * @param requestWithActor
	 * @return
	 */
	Insurance getDefault(RequestWithActor<String> requestWithActor, Integer insuranceKindType);
	/**
	 * 禁用和启用保险
	 * 
	 * @param requestWithActor
	 * @return
	 */
	Long forbitInsurance(RequestWithActor<Insurance> requestWithActor) throws GSSException;
	/**
	 * 查询保险产品代码是否已经存在
	 * 
	 * @param requestWithActor
	 * @return
	 */
	boolean  selectInsuranceCode(String code);
	/**
	 * 获取所有控润后的保险信息
	 */
	List<Insurance> getAllInsurance(RequestWithActor<InsuranceVo> requestWithActor);
}
