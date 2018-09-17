package com.tempus.gss.product.ift.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.Profit;
import com.tempus.gss.product.ift.api.entity.vo.ProfitVO;

public interface ProfitDao extends BaseDao<Profit, Object> {

    int insert(Profit record);

    int insertSelective(Profit record);

    Profit selectByPrimaryKey(Long profitNo);

    int updateByPrimaryKeySelective(Profit record);

    int updateByPrimaryKey(Profit record);

	List<Profit> queryProfitList(Page<Profit> page, ProfitVO vo);

    List<Profit> queryProfit( ProfitVO vo);

	int deleteByPrimaryKeySelective(Long profitNo);
	
	/**
	 * 根据用户编号获取具体的控润
	 * 
	 * @param owner 归集单位
	 * @param customerNo 用户编号
	 * @return List<Profit> 国际控润
	 */
	List<Profit> getProfitByCustomerNo(@Param("owner") int owner, @Param("customerNo") long customerNo);
	
	/**
	 * 根据用户所属分组编号获取具体的控润
	 * 
	 * @param owner 归集单位
	 * @param customerTypeNo 用户所属分组编号
	 * @return List<Profit> 国际控润
	 */
	List<Profit> getProfitByCustomerTypeNo(@Param("owner") int owner, @Param("customerTypeNo") long customerTypeNo);
}