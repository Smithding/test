package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.product.ift.api.entity.SeparatedOrder;
import com.tempus.gss.product.ift.api.entity.vo.SeparatedOrderVo;

/**
 * Created by Administrator on 2017/11/16.
 */
public interface ISeparatedOrderService {
    /***
     * 获取分单列表
     * @param page
     * @param requestWithActor
     * @return
     */
    Page<SeparatedOrder> pageList(Page<SeparatedOrder> page, RequestWithActor<SeparatedOrderVo> requestWithActor);

    /**
     * 修改出票人
     * @param saleOrderNo
     */
    int updateSeparatedOrder(Long saleOrderNo,String loginName,String currentUserId);

    /**
     * 废退改单修改出票员
     * @param saleChangeNo
     * @param loginName
     * @param currentUserId
     * @param saleOrBuyType buy 为采购类的分单，sale 为销售类的分单
     * @return
     */
    int updateSeparatedChangeOrder(Long saleChangeNo,String loginName,String currentUserId,String saleOrBuyType);
}
