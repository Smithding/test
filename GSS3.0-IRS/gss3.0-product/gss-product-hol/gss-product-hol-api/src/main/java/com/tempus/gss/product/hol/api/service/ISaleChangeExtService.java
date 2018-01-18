package com.tempus.gss.product.hol.api.service;


import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.hol.api.entity.SaleChangeExt;
import com.tempus.gss.product.hol.api.entity.response.HotelOrder;
import com.tempus.gss.product.hol.api.entity.vo.HotelOrderVo;
import com.tempus.gss.product.hol.api.entity.vo.SaleChangeExtVo;
import com.tempus.gss.vo.Agent;

/**
 * 酒店变更单操作
 * SaleChangeExt 表数据服务层接口
 *
 */
public interface ISaleChangeExtService extends ISuperService<SaleChangeExt> {

    /**
     * 根据条件查询退房订单列表
     *
     * @param page
     * @param pageRequest
     * @return
     */
    Page<SaleChangeExt> querySaleChangeExtWithPage(Page<SaleChangeExt> page, RequestWithActor<SaleChangeExtVo> pageRequest);

    /**
     * 确认退房
     * @param agent
     * @param saleChangeNo
     * @return
     */
    Boolean confirmCancelRoom(Agent agent,Long saleChangeNo);

}