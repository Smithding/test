package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.SaleOrderDetail;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.product.ift.api.entity.SeparatedOrder;
import com.tempus.gss.product.ift.api.entity.TicketSender;
import com.tempus.gss.product.ift.api.entity.vo.SeparatedOrderVo;
import com.tempus.gss.product.ift.api.service.ISeparatedOrderService;
import com.tempus.gss.product.ift.dao.SaleOrderExtDao;
import com.tempus.gss.product.ift.dao.SeparatedOrderDao;
import com.tempus.gss.product.ift.dao.TicketSenderDao;
import com.tempus.gss.security.AgentUtil;
import com.tempus.gss.system.entity.User;
import com.tempus.gss.system.service.IUserService;
import com.tempus.gss.vo.Agent;
import org.apache.commons.lang3.StringUtils;
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
    @Reference
    private IUserService userService;

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
    public int updateSeparatedOrder(Long saleOrderNo,String loginName,String currentUserId) {
        //查询订单，根据订单状态判定是那种情形下的重新分单
        String status = "1";//待核价
        SaleOrderExt saleOrderExt = saleOrderExtDao.selectByPrimaryKey(saleOrderNo);
        if (saleOrderExt != null) {
            List<SaleOrderDetail> details = saleOrderExt.getSaleOrderDetailList();
            if (details != null && details.size() > 0) {
                status = details.get(0).getStatus();
            }
        }
        //更新将被分单人员
        updateTicketSenderInfo(loginName,status,currentUserId,"add");
        Long lockerId = saleOrderExt.getLocker();
        //更新原被分配人员
        User user = userService.selectById(lockerId);
        if(user!=null) {
            updateTicketSenderInfo(user.getLoginName(), status, currentUserId,"sub");
        }
        //分给指定出票员后，订单被此人ID锁定
        Agent agent = new Agent(8755);
        User tempUser = userService.findUserByLoginName(agent,loginName);
        if(tempUser!=null) {
            saleOrderExt.setLocker(tempUser.getId());
        }
        return saleOrderExtDao.updateByPrimaryKeySelective(saleOrderExt);
    }

    private TicketSender setNumberByStatus(TicketSender ticketSender,String status,String method){
        if(StringUtils.equals("add",method)){
            if (StringUtils.equals(status, "1")) {
                ticketSender.setSaleOrderNum(ticketSender.getSaleOrderNum() + 1);
            } else {
                ticketSender.setOrdercount(ticketSender.getOrdercount() +1);
            }
        }else{
            if (StringUtils.equals(status, "1")) {
                ticketSender.setSaleOrderNum(ticketSender.getSaleOrderNum() - 1);
            } else {
                ticketSender.setOrdercount(ticketSender.getOrdercount() - 1);
            }
        }
        return ticketSender;
    }

    /**
     * 分单更新出票人信息
     * @param loginId
     * @param status
     * @param currentUserId
     * @param method
     */
    private void updateTicketSenderInfo(String loginId,String status,String currentUserId,String method){
        Date updateTime =  new Date();//更新时间
        List<TicketSender> ticketSenders = ticketSenderDao.queryByLoginId(loginId);
        if (ticketSenders != null && ticketSenders.size() > 0) {
            TicketSender ticketSender = ticketSenders.get(0);
            //1待审核订单 销售单重新分配 否则出票订单重新分配
            setNumberByStatus(ticketSender,status,method);
            ticketSender.setUpdatetime(updateTime);
            ticketSender.setModifier(currentUserId);
            log.info("重新分单，更新人信息：" + ticketSender.toString());
            ticketSenderDao.updateByPrimaryKey(ticketSender);
        }
    }
}
