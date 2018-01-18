package com.tempus.gss.product.hol.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.Profit;
import com.tempus.gss.product.hol.api.entity.vo.ProfitVo;

/**
 *
 * Profit 表数据库控制层接口
 *
 */
public interface ProfitMapper extends AutoMapper<Profit> {

	/**
	 * 分页查询
	 * @param page
	 * @param vo
	 * @return
	 */
	List<Profit> queryProfitList(Page<Profit> page,ProfitVo vo);
	
	Profit selectByProfitNo(Long profitNo);
	
	Profit queryProfitList(ProfitVo vo);
	
	int updateByProfitNo(ProfitVo vo);
	
	List<Profit> selectProfitList(ProfitVo vo);
	
}