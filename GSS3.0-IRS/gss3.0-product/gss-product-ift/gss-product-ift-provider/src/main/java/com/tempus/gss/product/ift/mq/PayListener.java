package com.tempus.gss.product.ift.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xujia.wu on 2016/12/16.
 */

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tempus.gss.order.entity.PayNoticeVO;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.service.IChangeService;
import com.tempus.gss.product.ift.api.service.IOrderService;
import com.tempus.gss.vo.Agent;

/**
 * 支付状态监听类
 */
@Component("iftPayListener")
@RabbitListener(bindings = @QueueBinding(
		value = @Queue(value = "ift.payNoticeQue", durable = "true")
		, exchange = @Exchange(value = "gss-pay-exchange", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true", durable = "true")
		, key = "pay"
)
)
public class PayListener {

	protected static final Logger logger = LoggerFactory.getLogger(PayListener.class);
	@Reference
	IOrderService iftOrderService;
	@Autowired
	IChangeService changeService;

	@RabbitHandler
	public void processLogRecord(PayNoticeVO payNoticeVO) {

		try {
			logger.info("监听到支付消息队列" + JSON.toJSONString(payNoticeVO));
			// 商品类型 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
			if (payNoticeVO.getGoodsType() == 2) {
				Agent agent = new Agent(payNoticeVO.getOwner(), "sys");
				if (payNoticeVO.getBusinessType() == 2 && payNoticeVO.getChangeType() == 0) {
					boolean flag = iftOrderService.updateBuyOrderStatus(new RequestWithActor<>(agent, payNoticeVO.getBusinessNo()));
					logger.info("修改采购单操作是否成功,销售单号:" + payNoticeVO.getBusinessNo() + "->" + flag);

				}
				if (payNoticeVO.getBusinessType() == 4 && payNoticeVO.getChangeType() == 3) {
					boolean flag = changeService
							.updateBuyChangeStatus(new RequestWithActor<>(agent, payNoticeVO.getBusinessNo()));
					logger.info("修改采购变更单操作是否成功,销售变更单号:" + payNoticeVO.getBusinessNo() + "->" + flag);

				}
			}

		} catch (Exception ex) {
			logger.error("消费队列异常 ：", ex);
		}
	}

}
