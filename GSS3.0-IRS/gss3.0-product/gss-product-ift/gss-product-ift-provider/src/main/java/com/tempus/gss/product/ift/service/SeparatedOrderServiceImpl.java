package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.*;
import com.tempus.gss.product.ift.api.entity.vo.SeparatedOrderVo;
import com.tempus.gss.product.ift.api.service.ISeparatedOrderService;
import com.tempus.gss.product.ift.api.service.ITicketSenderService;
import com.tempus.gss.product.ift.dao.SaleChangeExtDao;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
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
    @Autowired
    SaleChangeExtDao saleChangeExtDao;
    @Reference
    private IUserService userService;
    @Value("${dpsconfig.job.owner}")
    protected String ownerCode;
    @Reference
    private ITicketSenderService ticketSenderService;

    @Override
    public Page<SeparatedOrder> pageList(Page<SeparatedOrder> page, RequestWithActor<SeparatedOrderVo> requestWithActor) {
        log.info("查询订单分配列表开始");
        if (requestWithActor == null ) {
            throw new GSSException("查询订单分配模块", "0301", "传入参数为空");
        }

        List<SeparatedOrder> SeparatedOrderList = separatedOrderDao.queryNoHandleByKey(page,requestWithActor.getEntity());
        page.setRecords(SeparatedOrderList);
        log.info("查询订单分配列表结束");
        return page;
    }

    /**
     *
     * @param saleOrderNo
     * @param loginName
     * @param currentUserId  当前分单用户  用于记录是谁操作的分单
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int updateSeparatedOrder(Long saleOrderNo,String loginName,String currentUserId) {
        log.info("参数：saleOrderNo:{},loginName:{},currentUserId:{}",saleOrderNo,loginName,currentUserId);
        //查询订单，根据订单状态判定是那种情形下的重新分单
        String status = "1";//待核价
        SaleOrderExt saleOrderExt = saleOrderExtDao.selectByPrimaryKey(saleOrderNo);
        if (saleOrderExt != null) {
            List<SaleOrderDetail> details = saleOrderExt.getSaleOrderDetailList();
            if (details != null && details.size() > 0) {
                status = details.get(0).getStatus();
            }
        }
        //更新原被分配人员
        Long lockerId = saleOrderExt.getLocker();
        User user = userService.selectById(lockerId);
        //分给指定出票员后，订单被此人ID锁定
        Integer owner = Integer.valueOf(ownerCode);
        Agent agent = new Agent(owner);
        User tempUser = userService.findUserByLoginName(agent,loginName);
        Long tempUserId = tempUser.getId();
        if(tempUser!=null) {
            saleOrderExt.setLocker(tempUser.getId());
            saleOrderExt.setAloneLocker(tempUser.getId());
        }
        int num = saleOrderExtDao.updateByPrimaryKeySelective(saleOrderExt);
        //更新将被分单人员
        updateTicketSenderInfo(loginName, status, currentUserId, "add", 0, "",agent,tempUserId);
        if(user!=null) {
            updateTicketSenderInfo(user.getLoginName(), status, currentUserId, "sub", 0, "",agent,lockerId);
        }
        return num;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int updateSeparatedChangeOrder(Long saleChangeNo, String loginName, String currentUserId, String saleOrBuyType) {
        log.info("参数：saleOrderNo:{},loginName:{},currentUserId:{}",saleChangeNo,loginName,currentUserId);
        int changeType = 0;
        SaleChangeExt saleChangeExt = (SaleChangeExt)this.saleChangeExtDao.selectByPrimaryKey(saleChangeNo);
        if (saleChangeExt != null) {
            changeType = saleChangeExt.getChangeType().intValue();
        }

        if (changeType == 0) {
            return 0;
        } else {

            Long lockerId = saleChangeExt.getLocker();
            User user = (User)this.userService.selectById(lockerId);
            Integer owner = Integer.valueOf(this.ownerCode);
            Agent agent = new Agent(owner);
            User tempUser = this.userService.findUserByLoginName(agent, loginName);
            Long tempUserId = tempUser.getId();
            if (tempUser != null) {
                saleChangeExt.setLocker(tempUser.getId());
                saleChangeExt.setAloneLocker(tempUser.getId());
                saleChangeExt.setLockTime(new Date());
            }
            int num = this.saleChangeExtDao.updateByPrimaryKeySelective(saleChangeExt);
            this.updateTicketSenderInfo(loginName, "", currentUserId, "add", changeType, saleOrBuyType,agent,tempUserId);
            if (user != null) {
                this.updateTicketSenderInfo(user.getLoginName(), "", currentUserId, "sub", changeType, saleOrBuyType,agent,lockerId);
            }
            return num;
        }
    }

    private TicketSender setNumberByStatus(TicketSender ticketSender, String status, String method, Agent agent, Long lockerId){
        if (StringUtils.equals(status, "1")) {
            ticketSenderService.updateByLockerId(agent,lockerId,"SALE_ORDER_NUM");
            ticketSender.setSaleOrderNum(null);
        } else {
            ticketSenderService.updateByLockerId(agent,lockerId,"ORDERCOUNT");
            ticketSender.setOrdercount(null);
        }
        /*if(StringUtils.equals("add",method)){
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
        }*/
        return ticketSender;
    }

    /**
     * 分单更新出票人信息
     * @param loginId
     * @param status
     * @param currentUserId
     * @param method
     * @param type   0 正常单   1 2 3 是 废 退 改
     * @param saleOrBuyType   sale  是销售   buy   是采购
     */
    private void updateTicketSenderInfo(String loginId, String status, String currentUserId, String method, int type, String saleOrBuyType,Agent agent,Long lockerId) {
        Date updateTime = new Date();
        List<TicketSender> ticketSenders = this.ticketSenderDao.queryByLoginId(loginId);
        if (ticketSenders != null && ticketSenders.size() > 0) {
            TicketSender ticketSender = (TicketSender)ticketSenders.get(0);
            if (type == 0) {
                this.setNumberByStatus(ticketSender, status, method,agent,lockerId);
            } else if (type == 1 || type == 2 || type == 3) {
                this.setNumberByChangeType(ticketSender, type, method, saleOrBuyType,agent,lockerId);
            }

            ticketSender.setUpdatetime(updateTime);
            ticketSender.setModifier(currentUserId);
            this.log.info("重新分单，更新人信息：" + ticketSender.toString());
            this.ticketSenderDao.updateByPrimaryKey(ticketSender);
        }

    }

    private TicketSender setNumberByChangeType(TicketSender ticketSender, int changeType, String method, String saleOrBuyType, Agent agent, Long lockerId) {
        if ("buy".equals(saleOrBuyType)) {
            if (changeType != 2 && changeType != 1) {
                if (changeType == 3) {
                    ticketSenderService.updateByLockerId(agent,lockerId,"BUY_CHANGE_NUM");
                    ticketSender.setBuyChangeNum(null);
                }
            } else {
                ticketSenderService.updateByLockerId(agent,lockerId,"BUY_REFUSE_NUM");
                ticketSender.setBuyRefuseNum(null);
            }
        } else  {
            if (changeType != 2 && changeType != 1) {
                if (changeType == 3) {
                    ticketSenderService.updateByLockerId(agent,lockerId,"SALE_CHANGE_NUM");
                    ticketSender.setSaleChangeNum(null);
                }
            } else {
                ticketSenderService.updateByLockerId(agent,lockerId,"SALE_REFUSE_NUM");
                ticketSender.setSaleRefuseNum(null);
            }
        } /*else if (changeType != 2 && changeType != 1) {
            if (changeType == 3) {
                ticketSender.setSaleChangeNum(ticketSender.getSaleChangeNum().intValue() - 1);
            }
        } else {
            ticketSender.setSaleRefuseNum(ticketSender.getSaleRefuseNum().intValue() - 1);
        }*/

        return ticketSender;
    }
}
