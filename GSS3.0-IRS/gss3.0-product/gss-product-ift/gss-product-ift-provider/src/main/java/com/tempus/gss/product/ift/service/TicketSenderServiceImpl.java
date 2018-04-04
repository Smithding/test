package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.TicketSender;
import com.tempus.gss.product.ift.api.entity.vo.TicketSenderVo;
import com.tempus.gss.product.ift.api.service.ITicketSenderService;
import com.tempus.gss.product.ift.dao.TicketSenderDao;
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
        ticketSenderVo.setTypes("'both','ticketSender'");//只给出票员分单
        List<TicketSender> ticketSenderList = this.queryByBean(ticketSenderVo);
        return ticketSenderList;
    }

}
