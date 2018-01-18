package com.tempus.gss.product.ift.mq;

import com.google.common.collect.Lists;
import com.tempus.gss.mq.MqSender;
import com.tempus.gss.mq.event.Event;
import com.tempus.gss.mq.event.EventType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * <pre>
 * <b>DPS定制的.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> lh
 * <b>Date:</b> 2016年12月7日 上午10:32:20
 * <b>Copyright:</b> Copyright ©2016 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2016年12月7日 上午10:32:20    lh
 *         new file.
 * </pre>
 */
public class IFTMqSender extends MqSender {

	/** 队列名称 */
	protected String queue;

	/** 日志记录器. */
	protected static final Logger logger = LogManager.getLogger(IFTMqSender.class);

	/**
	 * 执行发送MQ数据
	 * 
	 * @param obj
	 */
	public void send(Object obj) {
		this.send(this.queue, obj);
	}

	/**
	 * 执行发送MQ数据
	 * 
	 * @param owner
	 * @param type
	 * @param ids
	 */
	public void send(int owner, EventType type, Long... ids) {
		List<Long> _ids = Lists.newArrayList();
		for (Long id : ids) {
			_ids.add(id);
		}
		this.send(owner, type, _ids);
	}

	/**
	 * 执行发送MQ数据
	 * 
	 * @param owner
	 * @param type
	 * @param ids
	 */
	public void send(int owner, EventType type, List<Long> ids) {
		String _type = type.name();
		Event<?> event = wrap(owner, _type, ids);

		try {
			this.send(this.queue, event);
		} catch (Throwable e) {
			logger.error("send mq data error, " + e.getMessage(), e);
		}
	}

	private Event<?> wrap(Integer owner, String type, Object value) {
		Event<?> event = new Event<>(type, owner, value);
		return event;
	}
}
