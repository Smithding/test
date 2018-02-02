package com.tempus.gss.product.hol.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.hol.api.entity.response.HotelShowOrder;
import com.tempus.gss.product.hol.api.entity.vo.HotelOrderVo;

public interface ITCHotelOrderExportService {
    /**
     * 根据条件查询酒店订单列表
     *
     * @param page
     * @param pageRequest
     * @return
     */
    Page<HotelShowOrder> queryOrderListWithPage(Page<HotelShowOrder> page, RequestWithActor<HotelOrderVo> pageRequest);



}
