package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.SeparatedOrder;
import com.tempus.gss.product.ift.api.entity.vo.SeparatedOrderVo;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/11/16.
 */
@Component
public interface SeparatedOrderDao extends BaseDao<SeparatedOrder,SeparatedOrderVo> {
}
