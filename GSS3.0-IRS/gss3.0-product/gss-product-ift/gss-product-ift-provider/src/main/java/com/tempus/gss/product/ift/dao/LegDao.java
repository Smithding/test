package com.tempus.gss.product.ift.dao;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;

import com.tempus.gss.product.ift.api.entity.Leg;
import com.tempus.gss.product.ift.api.entity.vo.LegVo;

@Component
public interface LegDao extends BaseDao<Leg, LegVo> {

	/*
	* 通过订单编号获取航程集合
	* */
	public List<Leg> selectLegBySaleOrderNo(Long saleOrderNo);

	List<Leg> selectLegsBySaleChangeOrderNo(Long saleChangeNo);


	List<Leg> selectLegListByLegNoList(List<Long> legList);
}