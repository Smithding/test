/**
 * IftOrderMqSender.java
 * com.tempus.gss.product.ift.mq
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年11月10日 		shuo.cheng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ift.mq;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tempus.gss.mq.MqSender;


/**
 * ClassName:IftTicketMqSender
 * Function: 国际机票出票消息发送器
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年11月10日		下午2:56:55
 *
 * @see 	 
 *  
 */
@Service(value = "ift.order.iftTicketMqSender")
public class IftTicketMqSender extends MqSender {

	/** 交换器名称 */
	@Value("${mq.exchange.ift.ticket}")
	protected String exchanage;

	/** 已出票路由 */
	public static final String TICKETED_KEY = "iftTicketed";

	/** 已退票路由 */
	public static final String IFT_REFUNDED_KEY = "iftrefunded";

	/** 日志记录器. */
	protected static final Logger logger = LogManager.getLogger(IftTicketMqSender.class);

	/** 构造方法 */
	public IftTicketMqSender() {
		super();
	}

	@PostConstruct
	public void initialize() {

		logger.info(this.getClass().getName() + "(exchanage:" + this.exchanage + ") init ...");
	}

	/**
	 * @param routeKey 指定关键KEY（路由）
	 * @param obj 消息数据
	 */
	@Override
	public void send(String routeKey, Object obj) {

		super.send(this.exchanage, routeKey, obj);
	}

}

