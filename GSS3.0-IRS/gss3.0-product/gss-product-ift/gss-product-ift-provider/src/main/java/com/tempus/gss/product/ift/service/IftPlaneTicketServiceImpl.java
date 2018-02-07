package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.IftPlaneTicket;
import com.tempus.gss.product.ift.api.service.IftPlaneTicketService;
import com.tempus.gss.product.ift.dao.IftPlaneTicketDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class IftPlaneTicketServiceImpl implements IftPlaneTicketService {
    @Autowired
    private IftPlaneTicketDao planeTicketMapper;

    @Override
    public List<IftPlaneTicket> selectIftByMap(Map<String, Object> map) {
        return planeTicketMapper.selectIftByMap(map);
    }
}
