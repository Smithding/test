package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.ift.api.entity.Leg;
import com.tempus.gss.product.ift.api.entity.vo.LegVo;
import com.tempus.gss.product.ift.api.service.IftLegService;
import com.tempus.gss.product.ift.dao.LegDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class IftLegServiceImpl implements IftLegService {
    @Autowired
    private LegDao legDaoMapper;
    @Override
    public List<Leg> selectLegListByLegNoList(List<Long> legList) {
        return legDaoMapper.selectLegListByLegNoList(legList);
    }
}
