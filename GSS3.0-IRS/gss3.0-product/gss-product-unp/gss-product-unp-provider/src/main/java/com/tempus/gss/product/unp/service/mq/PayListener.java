package com.tempus.gss.product.unp.service.mq;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.vo.PayNoticeVO;
import com.tempus.gss.order.service.IBuyOrderService;
import com.tempus.gss.vo.Agent;
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
    
    private static final Logger logger = LoggerFactory.getLogger(PayListener.class);
    @Reference
    IBuyOrderService buyOrderService;
    
    @RabbitHandler
    public void processLogRecord(PayNoticeVO payNoticeVO) {
        try {
            logger.info("监听到支付消息队列" + JSON.toJSONString(payNoticeVO));
            // 商品类型 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送 9通用产品
            if (9 == payNoticeVO.getGoodsType()) {
                Agent agent = payNoticeVO.getAgent();
                if (payNoticeVO.getBusinessType() == 2 && payNoticeVO.getChangeType() == 0) {
                    //已支付
                    BuyOrder buyOrder = buyOrderService.updatePayStatus(agent, payNoticeVO.getBusinessNo(), 3);
                    logger.info("修改采购单操作是否成功,销售单号:" + payNoticeVO.getBusinessNo() + "->" + buyOrder.getPayStatus());
                }
            }
        } catch (Exception ex) {
            logger.error("消费队列异常 ：", ex);
        }
    }
    
}
