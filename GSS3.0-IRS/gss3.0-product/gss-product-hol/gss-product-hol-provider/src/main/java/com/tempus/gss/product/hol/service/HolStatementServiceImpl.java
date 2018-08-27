package com.tempus.gss.product.hol.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.HolStatement;
import com.tempus.gss.product.hol.api.entity.vo.QueryStatementVo;
import com.tempus.gss.product.hol.api.service.IHolStatementService;
import com.tempus.gss.product.hol.dao.HolStatementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/8/24.
 */
@Service
public class HolStatementServiceImpl implements IHolStatementService {

    @Autowired
    private HolStatementMapper holStatementMapper;
    @Override
    public Page<HolStatement> list(Page<HolStatement> page, QueryStatementVo queryStatementVo) {
        List<HolStatement> holStatementList = holStatementMapper.queryList(page, queryStatementVo);
        page.setRecords(holStatementList);
        return page;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void save(List<HolStatement> holStatementList) {
        holStatementMapper.insertBatch(holStatementList);
    }
}
