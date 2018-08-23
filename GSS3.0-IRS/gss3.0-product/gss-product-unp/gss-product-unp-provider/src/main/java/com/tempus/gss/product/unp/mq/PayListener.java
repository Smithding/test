package com.tempus.gss.product.unp.mq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tempus.gss.order.entity.enums.GoodsBigType;
import com.tempus.gss.order.entity.vo.PayNoticeVO;
import com.tempus.gss.order.entity.vo.PayReceiveVO;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;
import com.tempus.gss.product.unp.api.service.IUnpOrderService;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 支付状态监听类
 *
 * @author ZhangBro
 */
@Component("unpPayListener")
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "unp.payNoticeQue", durable = "true"),
                                         exchange = @Exchange(value = "gss-pay-exchange", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true", durable = "true"),
                                         key = "pay"))
public class PayListener {
    
    private Logger logger = LoggerFactory.getLogger(PayListener.class);
    @Reference(version = "2.2")
    IUnpOrderService unpOrderService;
    
    @RabbitHandler
    public void processLogRecord(PayNoticeVO payNoticeVO) {
        try {
            logger.info("监听到支付消息队列" + JSON.toJSONString(payNoticeVO));
            // 商品类型 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送 9通用产品
            if (GoodsBigType.GENERAL.getKey() == payNoticeVO.getGoodsType() && PayReceiveVO.PS_PAY_STATUS_SUCCESS == payNoticeVO.getPayStatus()) {
                Agent agent = payNoticeVO.getAgent();
                if (payNoticeVO.getBusinessType() == 2 && payNoticeVO.getChangeType() == 0) {
                    int payType = payNoticeVO.getPayWay() / 1000000;
                    UnpOrderVo unpOrderVo = new UnpOrderVo();
                    unpOrderVo.setOrderNo(payNoticeVO.getBusinessNo());
                    unpOrderVo.setSaleAccountType(payType);
                    //已支付
                    RequestWithActor<UnpOrderVo> request = new RequestWithActor<>();
                    request.setAgent(agent);
                    request.setEntity(unpOrderVo);
                    try {
                        
                        boolean update = unpOrderService.updateOrderPayStatus(request);
                        logger.info("修改通用产品支付状态--{}", update);
                    } catch (Exception e) {
                        logger.error("修改通用产品支付状态 Error", e);
                    }
                    
                }
            }
        } catch (Exception ex) {
            logger.error("消费队列异常 ：", ex);
        }
    }
    
}
