package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.TicketSender;
import com.tempus.gss.product.ift.api.entity.vo.TicketSenderVo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */
public interface ITicketSenderService {

    Page<TicketSender> pageList(Page<TicketSender> page, RequestWithActor<TicketSenderVo> requestWithActor);

    TicketSender queryById(Long id);

    //批量更新
    int update(TicketSender ticketSender);

    int insert(TicketSender ticketSender);

    List<TicketSender> queryByBean(TicketSenderVo ticketSenderVo);

    /**
     * 根据登录ID获取出票员信息
     * @param loginId
     * @return
     */
    TicketSender getTicketSenderByLoginId(String loginId);

    /**
     * 根据主键更新
     * @param ticketSender
     * @return
     */
    int updateByPrimaryKey(TicketSender ticketSender);

    /**
     * 获取在线出票员
     * @param onLine
     * @return
     */
    List<TicketSender> getOnlineTicketSender(Integer onLine);
}
