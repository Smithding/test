package com.tempus.gss.product.hol.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.HolStatement;
import com.tempus.gss.product.hol.api.entity.vo.QueryStatementVo;

import java.util.List;

/**
 * 酒店报表接口
 */
public interface IHolStatementService {

    /**
     * 列表
     * @return
     */
    Page<HolStatement> list(Page<HolStatement> page, QueryStatementVo queryStatementVo);

    /**
     * 保存
     * @param holStatement
     * @return
     */
    void save(List<HolStatement> holStatementList);
}
