package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.TicketSender;
import com.tempus.gss.product.ift.api.entity.vo.TicketSenderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */
@Component
public interface TicketSenderDao extends BaseDao<TicketSender,TicketSenderVo> {

    int updateByIds(TicketSender ticketSender);

    List<TicketSender> queryById(Long id);

    List<TicketSender> queryObjByKey(TicketSenderVo ticketSenderVo);

    List<TicketSender> queryByLoginId(@Param("loginId") String loginId);
}
