package com.tempus.gss.product.ins.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ins.api.entity.Insurance;
import com.tempus.gss.product.ins.api.entity.vo.InsuranceVo;

@Component("insInsuranceDao")
public interface InsuranceDao extends InsBaseDao<Insurance,InsuranceVo>{

	/**
	 * 
	 * selectDefaultInsurance:根据保险类型返回默认的保险产品.
	 *
	 * @param  @param insureType
	 * @param  @return    设定文件
	 * @return List<Insurance>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	List<Insurance> selectDefaultInsurance(@Param("insureType")String insureType,@Param("insuranceKindType")int insuranceKindType);
	/**
	 * 查询产品
	 */
	List<Insurance> selectInsurance(Insurance insurance);
	/**
	 * 根据产品编号
	 */
	List<Insurance> selectInsuranceByNo(@Param("insuranceNo")Long insuranceNo);
	/**
	 * 查询国内国际都支持的保险
	 */
	List<Insurance> chooseDefaultInsurance(@Param("insureType")String insureType,@Param("insuranceKindType")int insuranceKindType);
	/**
	 * 查询产品代码相同的保险
	 */
	List<Insurance> selectInsuranceCode(@Param("productKey")String productKey);
}
