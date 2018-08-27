package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.ift.api.entity.Leg;
import com.tempus.gss.product.ift.api.service.ILegService;
import com.tempus.gss.product.ift.dao.LegDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class LegServiceImpl implements ILegService{
    @Autowired
    private LegDao legDao;

    @Override
    public List<Leg> selectLegBySaleOrderNo(Long saleOrderNo) {
        return legDao.selectLegBySaleOrderNo(saleOrderNo);
    }

    @Override
    public List<Leg> selectLegsBySaleChangeOrderNo(Long saleChangeNo) {
        return legDao.selectLegsBySaleChangeOrderNo(saleChangeNo);
    }
}
