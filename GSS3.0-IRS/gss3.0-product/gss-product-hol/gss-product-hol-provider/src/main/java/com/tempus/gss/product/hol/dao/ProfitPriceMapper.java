package com.tempus.gss.product.hol.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.tempus.gss.product.hol.api.entity.ProfitPrice;
import com.tempus.gss.product.hol.api.entity.vo.QueryProfitPrice;

/**
 *
 * ProfitPrice 表数据库控制层接口
 *
 */
public interface ProfitPriceMapper extends AutoMapper<ProfitPrice> {

	List<ProfitPrice> selectByProfitNo(Long profitNo);
	
	int delectByPriceNo(Long priceNo);
	
	List<ProfitPrice> queryProfitPriceList(QueryProfitPrice query);
	
	int updateByProfitNoAndPriceNo(ProfitPrice vo);
}