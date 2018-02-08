package com.tempus.gss.product.ift.dao;

import java.util.List;

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
}