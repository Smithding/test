package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.IftPlaneTicket;

import java.util.List;
import java.util.Map;

public interface IftPlaneTicketService {
    /**
     * selectIftByMap:根据条件查询国际机票列表
     * @param map
     * @return
     */
    List<IftPlaneTicket> selectIftByMap(Map<String,Object> map);
}
