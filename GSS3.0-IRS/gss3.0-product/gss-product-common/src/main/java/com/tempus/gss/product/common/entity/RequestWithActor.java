package com.tempus.gss.product.common.entity;

import com.tempus.gss.vo.Agent;

import java.io.Serializable;

/**
 * 带操作的请求.
 */
public class RequestWithActor<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 当前操作用户.
	 */
	private Agent agent;

	/**
	 * Vo对象.
	 */
	private T entity;

	/**
	 * 构造函数.
	 */
	public RequestWithActor() {
		// 默认构造函数
	}

	/**
	 * 构造函数.
	 * @param entity 实体
	 */
	public RequestWithActor(T entity) {

		this.entity = entity;
	}

	/**
	 * 构造函数.
	 * @param agent 当前操作者
	 * @param entity 实体
	 */
	public RequestWithActor(Agent agent, T entity) {

		this.agent = agent;
		this.entity = entity;
	}

	public T getEntity() {

		return entity;
	}

	public void setEntity(T entity) {

		this.entity = entity;
	}

	public Agent getAgent() {

		return agent;
	}

	public void setAgent(Agent agent) {

		this.agent = agent;
	}

	/**
	 * 从当前的request 创建新的类型的 request，Agent相同.
	 * @param entity 实体
	 * @param <X> 实体类型
	 * @return 创建的新request
	 */
	public <X> RequestWithActor<X> createFormThis(X entity){
		return new RequestWithActor(this.getAgent(),entity);
	}
}
