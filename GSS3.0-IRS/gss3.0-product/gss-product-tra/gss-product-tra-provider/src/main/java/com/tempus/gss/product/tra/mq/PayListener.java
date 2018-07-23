package com.tempus.gss.product.tra.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tempus.gss.order.entity.vo.PayNoticeVO;
import com.tempus.gss.product.tra.api.service.ITrainService;
import com.tempus.gss.vo.Agent;

/**
 * Created by xujia.wu on 2016/12/16.
 */

/**
 * 支付状态监听类
 */
@Component("traPayListener")
@RabbitListener(bindings = @QueueBinding(
		value = @Queue(value = "tra.payNoticeQue", durable = "true")
		, exchange = @Exchange(value = "ubp-pay-exchange", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true", durable = "true")
		, key = "pay"
)
)
public class PayListener {

	protected static final Logger logger = LoggerFactory.getLogger(PayListener.class);
	@Reference
	ITrainService trainService;


	@RabbitHandler
	public void processLogRecord(PayNoticeVO payNoticeVO) {

		try {
			logger.info("监听到支付消息队列" + JSON.toJSONString(payNoticeVO));
			// 商品类型 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送 10.火车票
			if (payNoticeVO.getGoodsType() == 10) {
				Agent agent = new Agent(payNoticeVO.getOwner(), "sys");
				if (payNoticeVO.getBusinessType() == 2 && payNoticeVO.getChangeType() == 0) {
					//boolean flag = trainService.buyTrain(new RequestWithActor<>(agent, payNoticeVO.getBusinessNo()));
					//logger.info("火车票申请出票，修改订单状态,销售单号:" + payNoticeVO.getBusinessNo() + "->" + flag);

				}
			}

		} catch (Exception ex) {
			logger.error("消费队列异常 ：", ex);
		}
	}

}
