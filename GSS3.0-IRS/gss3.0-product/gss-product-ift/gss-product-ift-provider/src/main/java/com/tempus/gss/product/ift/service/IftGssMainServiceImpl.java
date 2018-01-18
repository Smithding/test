package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.ift.api.entity.GssMain;
import com.tempus.gss.product.ift.api.service.IftGssMainService;
import com.tempus.gss.product.ift.dao.GssMainDao;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class IftGssMainServiceImpl implements IftGssMainService {
    @Autowired
    private GssMainDao gssMainDao;
    @Override
    public GssMain selectByOrderNo(Long orderNo) {
        return gssMainDao.selectByOrderNo(orderNo);
    }
}
