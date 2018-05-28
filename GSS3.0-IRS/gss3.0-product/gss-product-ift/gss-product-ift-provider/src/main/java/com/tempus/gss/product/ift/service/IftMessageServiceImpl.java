package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.mq.MqSender;
import com.tempus.gss.product.ift.api.entity.SaleChangeExt;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.product.ift.api.entity.TicketSender;
import com.tempus.gss.product.ift.api.entity.setting.IFTConfigs;
import com.tempus.gss.product.ift.api.entity.vo.TicketSenderVo;
import com.tempus.gss.product.ift.api.service.IOrderService;
import com.tempus.gss.product.ift.api.service.ITicketSenderService;
import com.tempus.gss.product.ift.api.service.IIftMessageService;
import com.tempus.gss.product.ift.api.service.setting.IConfigsService;
import com.tempus.gss.product.ift.dao.SaleChangeExtDao;
import com.tempus.gss.product.ift.dao.SaleOrderExtDao;
import com.tempus.gss.security.AgentUtil;
import com.tempus.gss.system.entity.User;
import com.tempus.gss.system.service.IUserService;
import com.tempus.gss.vo.Agent;
import com.tempus.gss.websocket.SocketDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 国际机票分单组件
 *
 * @author zhi.li
 * @create 2018-04-02 11:40
 **/
@Service
@org.springframework.stereotype.Service("iftMessageService")
@EnableAutoConfiguration
public class IftMessageServiceImpl implements IIftMessageService {
    protected final transient Logger log = LoggerFactory.getLogger(getClass());

    @Reference
    IConfigsService configsService;
    @Reference
    protected ITicketSenderService ticketSenderService;
    @Reference
    protected IOrderService orderService;
    @Reference
    protected IUserService userService;
    @Autowired
    SaleOrderExtDao saleOrderExtDao;
    @Autowired
    SaleChangeExtDao saleChangeExtDao;
    @Autowired
    MqSender mqSender;
    /*@Value("${gs.owner.code}")
    String ownerCode  private ;*/

    @Override
    public void sendMessage(String ownerCode, Long saleOrderNo,String type) {
        try {
            TicketSender ticketSender = getSender(ownerCode,type);
            if (ticketSender != null) {
               /* SocketDO sdo = new SocketDO();
                sdo.setSaleOrder(String.valueOf(saleOrderNo));
                sdo.setType(1000002);//2国际
                sdo.setOther("1");//放置订单状态  1：待核价
                sdo.setLoginName(ticketSender.getUserid());*/
                SocketDO sdo =buildDo(saleOrderNo,ticketSender.getUserid());
                sdo.setOther("1");
                mqSender.send("gss-websocket-exchange", "notice", sdo);
                log.info("放入MQ队列信息:" + sdo.toString());
                //Long saleOrderNum = Long.parseLong(saleOrderNo);
                SaleOrderExt saleOrderExt = saleOrderExtDao.selectByPrimaryKey(saleOrderNo);
                if(saleOrderExt!=null){
                    Agent agent = new Agent(Integer.valueOf(ownerCode));
                    User user = userService.findUserByLoginName(agent, sdo.getLoginName());
                    saleOrderExt.setLocker(user.getId());
                    Date date = new Date();
                    saleOrderExt.setLockTime(date);
                    saleOrderExt.setModifyTime(date);
                    log.info("分单更新销售订单分配信息:"+saleOrderExt.toString());
                    saleOrderExtDao.updateByPrimaryKey(saleOrderExt);
                    ticketSenderService.updateByLockerId(agent,user.getId(),"SALE_ORDER_NUM");
                }
                /*ticketSender.setSaleOrderNum(ticketSender.getSaleOrderNum() + 1);
                ticketSenderService.updateByPrimaryKey(ticketSender);*/
            }
        } catch (Exception e) {
            log.error("b2b国际机票添加消息提醒异常", e);
        }
    }

    @Override
    public void sendRefuseMessage(Long saleOrderNo,String ownerCode,String type) {
        try {
            TicketSender ticketSender = getSender(ownerCode,type);
            if (ticketSender != null) {
                SocketDO sdo = buildDo(0L,ticketSender.getUserid());
                sdo.setOther("5");//退票中
                List<SaleChangeExt> saleChangeExts = saleChangeExtDao.queryBySaleOrderNo(saleOrderNo);
                //将订单锁单
                for(SaleChangeExt sext:saleChangeExts){
                    Agent agent = new Agent(Integer.valueOf(ownerCode));
                    User user = userService.findUserByLoginName(agent, sdo.getLoginName());
                    sext.setLocker(user.getId());
                    Date date = new Date();
                    sext.setLockTime(date);
                    sext.setModifyTime(date);
                    saleChangeExtDao.updateLocker(sext);
                    ticketSenderService.updateByLockerId(agent,user.getId(),"SALE_REFUSE_NUM");
                }
                mqSender.send("gss-websocket-exchange", "notice", sdo);
                log.info("放入MQ队列信息:" + sdo.toString());
               /* ticketSender.setSaleRefuseNum(ticketSender.getSaleRefuseNum() + 1);
                ticketSenderService.updateByPrimaryKey(ticketSender);*/
            }
        } catch (Exception e) {
            log.error("b2b国际机票添加消息提醒异常", e);
        }
    }

    @Override
    public void sendChangeMessage(Long saleOrderNo, String ownerCode,String type) {
        {
            try {
                TicketSender ticketSender = getSender(ownerCode,type);
                if (ticketSender != null) {
                    SocketDO sdo = buildDo(saleOrderNo, ticketSender.getUserid());
                    sdo.setOther("6");//改签中
                    List<SaleChangeExt> saleChangeExts = saleChangeExtDao.queryBySaleOrderNo(saleOrderNo);
                    //将订单锁单
                    for (SaleChangeExt sext : saleChangeExts) {
                        Agent agent = new Agent(Integer.valueOf(ownerCode));
                        User user = userService.findUserByLoginName(agent, sdo.getLoginName());

                        sext.setLocker(user.getId());
                        Date date = new Date();
                        sext.setLockTime(date);
                        sext.setModifyTime(date);
                        saleChangeExtDao.updateLocker(sext);
                        ticketSenderService.updateByLockerId(agent,user.getId(),"SALE_CHANGE_NUM");
                    }
                    mqSender.send("gss-websocket-exchange", "notice", sdo);
                    log.info("放入MQ队列信息:" + sdo.toString());

                   /* //查询出被出票员锁定的改签类型的数量
                    Agent agent = new Agent(Integer.valueOf(ownerCode));
                    User user = userService.findUserByLoginName(agent, sdo.getLoginName());
                    int lockcount = saleChangeExtDao.queryCountByLockerAndType(user.getId(), 3);
                    //给出票员赋值他的SALE_CHANGE_NUM字段
                    ticketSender.setSaleChangeNum(lockcount);
                    ticketSenderService.updateByPrimaryKey(ticketSender);*/
                }
            } catch (Exception e) {
                log.error("b2b国际机票添加消息提醒异常", e);
            }
        }
    }

    public TicketSender getSender(String ownerCode,String type) {
        TicketSender ticketSender = null;
        TicketSenderVo senderVo = new TicketSenderVo();
        senderVo.setTypes("'"+type+"'");//只给销售员分单   只分在线即可
        senderVo.setStatus(3);//查询在线销售员 3-在线
        List<TicketSender> ticketSenders = ticketSenderService.queryByBean(senderVo);
        if (ticketSenders != null && ticketSenders.size() > 0) {
            Integer owner = Integer.valueOf(ownerCode);
            Agent agent = new Agent(owner);
            IFTConfigs configs = configsService.getConfigByChannelID(agent, 0L);
            Map config = configs.getConfig();
            String saleOrderNum = (String) config.get("saleOrderNum");//获取销售员最大可分数配置
            if (StringUtils.isEmpty(saleOrderNum)) {
                saleOrderNum = "0";
            }
            int saleNum = Integer.valueOf(saleOrderNum);
            for (TicketSender temp : ticketSenders) {
                if (temp.getSaleOrderNum() < saleNum) {
                    ticketSender = temp;
                    break;
                }
            }
            log.info("被分配人员信息:"+ticketSender.toString());
        }
        return ticketSender;
    }

    private SocketDO buildDo(Long saleOrderNo,String userId){
        SocketDO sdo = new SocketDO();
        sdo.setType(1000002);//2国际
        sdo.setLoginName(userId);
        sdo.setSaleOrder(String.valueOf(saleOrderNo));
        return sdo;
    }

}
