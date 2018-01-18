package com.tempus.gss.product.ift.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.IftPlaneTicket;

import java.util.List;
import java.util.Map;

public interface IftPlaneTicketDao {
    /**
     * selectIftByMap:根据条件分页查询国际机票
     * @param page
     * @param map
     * @return
     */
    List<IftPlaneTicket> selectIftByMap(Map<String,Object> map);
}
