package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.SaleChangeExt;
import com.tempus.gss.product.ift.api.entity.TicketSender;
import com.tempus.gss.product.ift.api.entity.iftVo.IftUserVo;
import com.tempus.gss.product.ift.api.entity.vo.TicketSenderVo;
import com.tempus.gss.system.entity.User;
import com.tempus.gss.vo.Agent;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/21 0021.
 */
public interface ITicketSenderService {

    /**
     * 减少出票员锁定单数量
     * @param agent
     * @param salechangeExt
     * @param type   1代表BUY_CHANGE_NUM    2代表SALE_CHANGE_NUM    3代表BUY_REFUSE_NUM   4代表SALE_REFUSE_NUM
     */
    void decreaseBySaleChangeExt(Agent agent, SaleChangeExt salechangeExt, int type) ;

    /**
     * 更新出票员的锁定单数量
     * @param agent
     * @param type 1.BUY_CHANGE_NUM 2.SALE_CHANGE_NUM  3.BUY_REFUSE_NUM 4.SALE_REFUSE_NUM 5.SALE_ORDER_NUM 6.ORDERCOUNT
     */
    void updateByLockerId(Agent agent,Long lockerId,String type);

    public void asynUpdateByLockerId(Agent agent, Long lockerId, String type);

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

    /**
     * 查询出票员名称和对应的LockId
     * @return
     */
    List<Map<String,Object>> queryTicketNameAndLockerId();

    /**
     * 获取指定类型的在线业务人员
     * @param type
     * @return
     */
    List<TicketSender> getSpecTypeOnLineTicketSender(String type);

    TicketSender queryByUserId(Long userId);

    List<IftUserVo> queryTkMembersInUsers();
}
