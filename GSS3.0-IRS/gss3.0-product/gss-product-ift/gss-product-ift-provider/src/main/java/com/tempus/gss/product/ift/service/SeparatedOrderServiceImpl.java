package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.*;
import com.tempus.gss.product.ift.api.entity.vo.SeparatedOrderVo;
import com.tempus.gss.product.ift.api.service.IBuyOrderExtService;
import com.tempus.gss.product.ift.api.service.ISeparatedOrderService;
import com.tempus.gss.product.ift.api.service.ITicketSenderService;
import com.tempus.gss.product.ift.api.service.IftBuyChangeExtService;
import com.tempus.gss.product.ift.api.service.setting.IConfigsService;
import com.tempus.gss.product.ift.dao.SaleChangeExtDao;
import com.tempus.gss.product.ift.dao.SaleOrderExtDao;
import com.tempus.gss.product.ift.dao.SeparatedOrderDao;
import com.tempus.gss.product.ift.dao.TicketSenderDao;
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
    @Reference
    private IBuyOrderExtService buyOrderExtService;
    @Reference
    private IftBuyChangeExtService  buyChangeExtService;
    @Reference
    IConfigsService configsService;
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
     * @param saleOrBuyType
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int updateSeparatedOrder(Long saleOrderNo, String loginName, String currentUserId, String saleOrBuyType) {
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
        //分给指定出票员后，订单被此人ID锁定
        Integer owner = Integer.valueOf(ownerCode);
        Agent agent = new Agent(owner);
        User tempUser = userService.findUserByLoginName(agent,loginName);
        Long tempUserId = tempUser.getId();
        int num = 0 ;
        //更新原被分配人员
        Long lockerId = null;
        if(tempUser!=null) {
            boolean isDistributeTicket = configsService.getIsDistributeTicket(agent);
            if(!isDistributeTicket){
                //如果不是系统自动分单，那么把saleLocker和buyLocker和AloneLocker全部改成一个人
                BuyOrderExt buyOrderExt=null;
                try {
                    buyOrderExt = buyOrderExtService.selectBySaleOrderNo(agent, saleOrderExt.getSaleOrderNo());
                    lockerId = "buy".equals(saleOrBuyType)?buyOrderExt.getBuyLocker():null;
                    buyOrderExt.setBuyLocker(tempUser.getId());
                    buyOrderExtService.update(agent,buyOrderExt);
                } catch (Exception e) {
                    log.error("查询采购扩展单异常:",e);
                }
                lockerId = "buy".equals(saleOrBuyType)?lockerId:saleOrderExt.getLocker();
                saleOrderExt.setLocker(tempUser.getId());
                saleOrderExt.setAloneLocker(tempUser.getId());
                num = saleOrderExtDao.updateByPrimaryKeySelective(saleOrderExt);
            } else{
                //如果是系统自动分单，就分采购和销售调度
                if("buy".equals(saleOrBuyType)){
                    try {
                        BuyOrderExt buyOrderExt = buyOrderExtService.selectBySaleOrderNo(agent, saleOrderExt.getSaleOrderNo());
                        lockerId = buyOrderExt.getBuyLocker();
                        buyOrderExt.setBuyLocker(tempUser.getId());
                        num = buyOrderExtService.update(agent,buyOrderExt);
                    } catch (Exception e) {
                        log.error("查询采购扩展单异常:",e);
                    }
                } else{
                    lockerId = saleOrderExt.getLocker();
                    saleOrderExt.setLocker(tempUser.getId());
                    saleOrderExt.setAloneLocker(tempUser.getId());
                    num = saleOrderExtDao.updateByPrimaryKeySelective(saleOrderExt);
                }
            }
        }
        User user = userService.selectById(lockerId);
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

            Integer owner = Integer.valueOf(this.ownerCode);
            Agent agent = new Agent(owner);
            User tempUser = this.userService.findUserByLoginName(agent, loginName);
            Long tempUserId = tempUser.getId();
            Long lockerId = null;
            int num = 0;
            if (tempUser != null) {
                boolean isDistributeTicket = configsService.getIsDistributeTicket(agent);
                if(!isDistributeTicket){
                    //如果不是系统自动分单，那么把saleLocker和buyLocker和AloneLocker全部改成一个人
                     BuyChangeExt buyChangeExt = buyChangeExtService.queryBuyChgeExtByNo(saleChangeNo);
                    try {
                        lockerId = "buy".equals(saleOrBuyType)?buyChangeExt.getBuyLocker():null;
                         buyChangeExt.setBuyLocker(tempUser.getId());
                         buyChangeExtService.updateBuyChangeExt(buyChangeExt);
                    } catch (Exception e) {
                        log.error("查询采购变更扩展单异常:",e);
                    }
                    lockerId = "buy".equals(saleOrBuyType)?lockerId:saleChangeExt.getLocker();
                    saleChangeExt.setLocker(tempUser.getId());
                    saleChangeExt.setAloneLocker(tempUser.getId());
                    saleChangeExt.setLockTime(new Date());
                    num = this.saleChangeExtDao.updateByPrimaryKeySelective(saleChangeExt);
                } else{
                    //如果是系统自动分单，就分采购和销售调度
                    if("buy".equals(saleOrBuyType)){
                        try {
                            BuyChangeExt buyChangeExt = buyChangeExtService.queryBuyChgeExtByNo(saleChangeNo);
                            lockerId = buyChangeExt.getBuyLocker();
                            buyChangeExt.setBuyLocker(tempUser.getId());
                            num = buyChangeExtService.updateBuyChangeExt(buyChangeExt);
                        } catch (Exception e) {
                            log.error("查询采购变更扩展单异常:",e);
                        }
                    } else{
                        lockerId = saleChangeExt.getLocker();
                        saleChangeExt.setLocker(tempUser.getId());
                        saleChangeExt.setAloneLocker(tempUser.getId());
                        saleChangeExt.setLockTime(new Date());
                        num = this.saleChangeExtDao.updateByPrimaryKeySelective(saleChangeExt);
                    }
                }
            }
            User user = (User)this.userService.selectById(lockerId);
            this.updateTicketSenderInfo(loginName, "", currentUserId, "add", changeType, saleOrBuyType,agent,tempUserId);
            if (user != null) {
                this.updateTicketSenderInfo(user.getLoginName(), "", currentUserId, "sub", changeType, saleOrBuyType,agent,lockerId);
            }
            return num;
        }
    }

    private TicketSender setNumberByStatus(TicketSender ticketSender, String status, String method, Agent agent, Long lockerId){
       /* if (StringUtils.equals(status, "1")) {*/
            ticketSenderService.updateByLockerId(agent,lockerId,"SALE_ORDER_NUM");
            ticketSenderService.updateByLockerId(agent,lockerId,"ORDERCOUNT");
            ticketSender.setSaleOrderNum(null);
            ticketSender.setOrdercount(null);
        /*} else {*/
          //  ticketSender.setOrdercount(null);
        /*}*/
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
       /* if ("buy".equals(saleOrBuyType)) {*/
        if (changeType != 2 && changeType != 1) {
            if (changeType == 3) {
                ticketSenderService.updateByLockerId(agent,lockerId,"BUY_CHANGE_NUM");
                ticketSenderService.updateByLockerId(agent,lockerId,"SALE_CHANGE_NUM");
                ticketSender.setBuyChangeNum(null);
                ticketSender.setSaleChangeNum(null);
            }
        } else {
            ticketSenderService.updateByLockerId(agent,lockerId,"BUY_REFUSE_NUM");
            ticketSenderService.updateByLockerId(agent,lockerId,"SALE_REFUSE_NUM");
            ticketSender.setBuyRefuseNum(null);
            ticketSender.setSaleRefuseNum(null);
        }
       /* } else  {
            if (changeType != 2 && changeType != 1) {
                if (changeType == 3) {
                    ticketSender.setSaleChangeNum(null);
                }
            } else {
                ticketSender.setSaleRefuseNum(null);
            }
        }*/ /*else if (changeType != 2 && changeType != 1) {
            if (changeType == 3) {
                ticketSender.setSaleChangeNum(ticketSender.getSaleChangeNum().intValue() - 1);
            }
        } else {
            ticketSender.setSaleRefuseNum(ticketSender.getSaleRefuseNum().intValue() - 1);
        }*/

        return ticketSender;
    }
}
