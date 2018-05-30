package com.tempus.gss.product.ift.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.SeparatedOrder;
import com.tempus.gss.product.ift.api.entity.vo.SeparatedOrderVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */
@Component
public interface SeparatedOrderDao extends BaseDao<SeparatedOrder,SeparatedOrderVo> {
    List<SeparatedOrder> queryNoHandleByKey(Page<SeparatedOrder> page, SeparatedOrderVo entity);
}
