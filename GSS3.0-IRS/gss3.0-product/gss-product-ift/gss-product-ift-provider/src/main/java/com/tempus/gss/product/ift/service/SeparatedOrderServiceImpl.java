package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.product.ift.api.entity.SeparatedOrder;
import com.tempus.gss.product.ift.api.entity.TicketSender;
import com.tempus.gss.product.ift.api.entity.vo.SeparatedOrderVo;
import com.tempus.gss.product.ift.api.service.ISeparatedOrderService;
import com.tempus.gss.product.ift.dao.SaleOrderExtDao;
import com.tempus.gss.product.ift.dao.SeparatedOrderDao;
import com.tempus.gss.product.ift.dao.TicketSenderDao;
import com.tempus.gss.security.AgentUtil;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */
@Service
@Component
public class SeparatedOrderServiceImpl implements ISeparatedOrderService {
    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private SeparatedOrderDao separatedOrderDao;
    @Autowired
    private SaleOrderExtDao saleOrderExtDao;
    @Autowired
    private TicketSenderDao ticketSenderDao;

    @Override
    public Page<SeparatedOrder> pageList(Page<SeparatedOrder> page, RequestWithActor<SeparatedOrderVo> requestWithActor) {
        log.info("查询订单分配列表开始");
        if (requestWithActor == null ) {
            throw new GSSException("查询订单分配模块", "0301", "传入参数为空");
        }
        List<SeparatedOrder> SeparatedOrderList = separatedOrderDao.queryObjByKey(page,requestWithActor.getEntity());
        page.setRecords(SeparatedOrderList);
        log.info("查询订单分配列表结束");
        return page;
    }

    @Override
    @Transactional
    public int updateSeparatedOrder(SaleOrderExt saleOrderExt,String userid,String currentUserId){
        List<TicketSender> ticketSenders = ticketSenderDao.queryByLoginId(userid);
        if(ticketSenders!=null && ticketSenders.size()>0){
            TicketSender ticketSender = ticketSenders.get(0);
            Long orderNum = ticketSender.getOrdercount()+1;
            ticketSender.setOrdercount(orderNum);
            ticketSender.setUpdatetime(new Date());
            ticketSenderDao.updateByPrimaryKey(ticketSender);
        }

        ticketSenders  = ticketSenderDao.queryByLoginId(currentUserId);
        if(ticketSenders!=null && ticketSenders.size()>0){
            TicketSender ticketSender = ticketSenders.get(0);
            Long orderNum = ticketSender.getOrdercount()-1;
            ticketSender.setOrdercount(orderNum);
            ticketSender.setUpdatetime(new Date());
            ticketSenderDao.updateByPrimaryKey(ticketSender);
        }
        return saleOrderExtDao.updateByPrimaryKeySelective(saleOrderExt);
    }
}
