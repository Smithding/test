package com.tempus.gss.product.ins.dao;

import java.util.List;

import org.omg.CORBA.Object;
import org.springframework.stereotype.Component;

import com.tempus.gss.product.ins.api.entity.ProfitControl;

@Component("insProfitControlDao")
public interface ProfitControlDao extends InsBaseDao<ProfitControl,Object>{

	/**
	 * 
	 * selectByInsuranceNo:根据产品编号查询销售价
	 *
	 * @param @param
	 *            insuranceNo
	 * @param @return
	 *            设定文件
	 * @return List<ProfitControl> DOM对象
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	List<ProfitControl> selectByInsuranceNo(Long insuranceNo);
	/**
	 * 根据产品编号删除控润规则
	 * @param insuranceNo
	 * @return
	 */
	public int deleteByInsuranceNo(Long insuranceNo);
}
