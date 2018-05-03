package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.SaleChangeExt;
import com.tempus.gss.product.ift.api.entity.TicketSender;
import com.tempus.gss.product.ift.api.entity.vo.TicketSenderVo;
import com.tempus.gss.product.ift.api.service.IChangeService;
import com.tempus.gss.product.ift.api.service.ITicketSenderService;
import com.tempus.gss.product.ift.dao.SaleChangeExtDao;
import com.tempus.gss.product.ift.dao.SaleOrderExtDao;
import com.tempus.gss.product.ift.dao.TicketSenderDao;
import com.tempus.gss.system.entity.User;
import com.tempus.gss.system.service.IUserService;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */
@Service
@EnableAutoConfiguration
public class TicketSenderServiceImpl implements ITicketSenderService {

    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    TicketSenderDao ticketSenderDao;
    @Reference
    IUserService userService;
    @Autowired
    SaleChangeExtDao saleChangeExtDao;
    @Autowired
    SaleOrderExtDao saleOrderExtDao;
    @Override
    public void decreaseBySaleChangeExt(Agent agent, SaleChangeExt salechangeExt, int type) {

        Long lockerId = salechangeExt.getLocker();
        log.info("减少lockerID："+lockerId+" 出票员 第"+type+"类的num值开始");
        if(lockerId == null || lockerId.equals(0l)){
            log.info("无此lockerID："+lockerId+" 出票员mun减少结束");
            return ;
        }
        User user = userService.selectById(agent,lockerId);
        if(user != null){
            TicketSender sender = getTicketSenderByLoginId(user.getLoginName());
            if(sender != null){
                if(type == 1){
                    sender.setBuyChangeNum(sender.getBuyChangeNum() - 1);
                } else if(type == 2){
                    sender.setSaleChangeNum(sender.getSaleChangeNum() - 1);
                } else if(type == 3){
                    sender.setBuyRefuseNum(sender.getBuyRefuseNum() - 1);
                } else if(type == 4){
                    sender.setSaleRefuseNum(sender.getSaleRefuseNum() - 1);
                }
                sender.setIds(sender.getId() + "");
                update(sender);
            }
            //设置单的locker字段为0
            salechangeExt.setLocker(0L);
            saleChangeExtDao.updateByPrimaryKeySelective(salechangeExt);
        }
        log.info("减少lockerID："+lockerId+" 出票员 第"+type+"的num值结束");
    }

    @Override
    public void updateByLockerId(Agent agent, Long lockerId, String type) {

        log.info("更新lockerID："+lockerId+" 出票员"+type+"值更新开始");
        if(lockerId == null || lockerId.equals(0l)){
            log.info("无此lockerID："+lockerId+" 出票员"+type+"值更新结束");
            return ;
        }
        User user = userService.selectById(agent,lockerId);
        if(user != null){
            TicketSender sender = getTicketSenderByLoginId(user.getLoginName());
            if(sender != null){
                if("BUY_CHANGE_NUM".equals(type)){
                    //采购改签数量
                    int count = saleChangeExtDao.queryChangeCountBylocker(null, lockerId);
                    sender.setBuyChangeNum(count);
                } else if("SALE_CHANGE_NUM".equals(type)){
                    //销售改签数量
                    int count = saleChangeExtDao.querySaleChangeCountBylocker(null, lockerId);
                    sender.setSaleChangeNum(count);
                } else if("BUY_REFUSE_NUM".equals(type) || "BUY_DELETE_NUM".equals(type)){
                    //采购废票和退票数量
                    int count = saleChangeExtDao.queryBuyRefundAndDelCountBylocker(null, lockerId);
                    sender.setBuyRefuseNum(count);
                } else if("SALE_REFUSE_NUM".equals(type) || "SALE_DELTE_NUM".equals(type)){
                    //销售退票和销售废票数量
                    int count = saleChangeExtDao.querySaleRefundAndDelCountBylocker(null, lockerId);
                    sender.setSaleRefuseNum(count);
                } else if("SALE_ORDER_NUM".equals(type)){
                    //核价单
                    int count = saleOrderExtDao.querySaleCountByLocker(lockerId);
                    sender.setSaleOrderNum(count);
                } else if("ORDERCOUNT".equals(type)){
                    //出票单
                    int count = saleOrderExtDao.queryBuyCountByLocker(lockerId);
                    sender.setOrdercount(count + 0l);
                }
                sender.setIds(sender.getId() + "");
                update(sender);
            }
        }
        log.info("更新lockerID："+lockerId+" 出票员"+type+"值更新结束");
    }

    /**
     * @param type   1代表BUY_CHANGE_NUM    2代表SALE_CHANGE_NUM    3代表BUY_REFUSE_NUM
     *               4代表SALE_REFUSE_NUM    5代表SALE_ORDER_NUM    6代表ORDERCOUNT
     */
    private String getTypeString(int type) {
        if(type == 1){
            return "BUY_CHANGE_NUM";
        } else if(type == 2){
            return "SALE_CHANGE_NUM";
        } else if(type == 3){
            return "BUY_REFUSE_NUM";
        } else if(type == 4){
            return "SALE_REFUSE_NUM";
        } else if(type == 5){
            return "SALE_ORDER_NUM";
        } else if(type == 6){
            return "ORDERCOUNT";
        } else{
            return "";
        }
    }

    @Override
    public Page<TicketSender> pageList(Page<TicketSender> page, RequestWithActor<TicketSenderVo> requestWithActor) {
        log.info("查询出票人信息开始");
        try {
            if (requestWithActor == null ) {
                throw new GSSException("查询出票人信息模块", "0301", "传入参数为空");
            }
            if ( requestWithActor.getAgent().getOwner() == 0) {
                throw new GSSException("查询出票人信息模块", "0301", "传入参数为空");
            }
            List<TicketSender> filePriceList = ticketSenderDao.queryObjByKey(page,requestWithActor.getEntity());
            page.setRecords(filePriceList);
        } catch (Exception e) {
            log.error("出票人信息表中未查询出相应的结果集", e);
            throw new GSSException("查询出票人信息模块", "0302", "查询出票人信息失败");
        }
        log.info("查询出票人信息结束");
        return page;
    }

    @Override
    public TicketSender queryById(Long id) {
        List<TicketSender> filePriceList = ticketSenderDao.queryById(id);
        return filePriceList.get(0);
    }

    @Override
    public int update(TicketSender ticketSender) {
        log.info("update更新国际机票业务员信息:"+ticketSenderDao.toString());
        return ticketSenderDao.updateByIds(ticketSender);
    }

    @Override
    public int insert(TicketSender ticketSender) {
        return ticketSenderDao.insert(ticketSender);
    }

    @Override
    public List<TicketSender> queryByBean(TicketSenderVo queryObjByKey) {
        return ticketSenderDao.queryObjByKey(queryObjByKey);
    }

    public TicketSender getTicketSenderByLoginId(String loginId){
        List<TicketSender> ticketSenders = ticketSenderDao.queryByLoginId(loginId);
        TicketSender ticketSender = null;
        if(ticketSenders!=null && ticketSenders.size()>0){
            ticketSender = ticketSenders.get(0);
        }
        return  ticketSender;
    }

    @Override
    public int updateByPrimaryKey(TicketSender ticketSender) {
        ticketSender.setUpdatetime(new Date());
        log.info("updateByPrimaryKey更新国际机票业务员信息:"+ticketSenderDao.toString());
        return ticketSenderDao.updateByPrimaryKey(ticketSender);
    }

    @Override
    public List<TicketSender> getOnlineTicketSender(Integer onLine) {
        TicketSenderVo ticketSenderVo = new TicketSenderVo();
        ticketSenderVo.setStatus(onLine);//只给在线用户分单
        //ticketSenderVo.setTypes("'both','ticketSender'");//只给出票员分单
        List<TicketSender> ticketSenderList = this.queryByBean(ticketSenderVo);
        return ticketSenderList;
    }

}
