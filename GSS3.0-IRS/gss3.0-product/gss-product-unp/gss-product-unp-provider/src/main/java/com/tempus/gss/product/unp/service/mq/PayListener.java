package com.tempus.gss.product.unp.service.mq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tempus.gss.order.entity.enums.GoodsBigType;
import com.tempus.gss.order.entity.vo.PayNoticeVO;
import com.tempus.gss.order.entity.vo.PayReceiveVO;
import com.tempus.gss.product.unp.api.service.UnpOrderService;
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
    @Reference
    UnpOrderService unpOrderService;
    
    @RabbitHandler
    public void processLogRecord(PayNoticeVO payNoticeVO) {
        try {
            logger.info("监听到支付消息队列" + JSON.toJSONString(payNoticeVO));
            // 商品类型 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送 9通用产品
            if (GoodsBigType.GENERAL.getKey() == payNoticeVO.getGoodsType() && PayReceiveVO.PS_PAY_STATUS_SUCCESS == payNoticeVO.getPayStatus()) {
                Agent agent = payNoticeVO.getAgent();
            }
        } catch (Exception ex) {
            logger.error("消费队列异常 ：", ex);
        }
    }
    
}
