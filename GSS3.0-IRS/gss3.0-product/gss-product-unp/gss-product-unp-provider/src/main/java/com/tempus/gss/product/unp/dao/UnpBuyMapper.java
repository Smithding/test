package com.tempus.gss.product.unp.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpBuy;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderQueryVo;

import java.util.List;

public interface UnpBuyMapper {
    int deleteByPrimaryKey(Long buyOrderNo);

    int insert(UnpBuy record);

    int insertSelective(UnpBuy record);

    UnpBuy selectByPrimaryKey(Long buyOrderNo);

    int updateByPrimaryKeySelective(UnpBuy record);

    int updateByPrimaryKey(UnpBuy record);

    UnpBuy selectBySaleOrderNo(Long saleOrderNo);

    List<UnpBuy> queryBuyOrderList(Page<UnpBuy> page, UnpOrderQueryVo param);
}