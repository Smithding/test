package com.tempus.gss.product.hol.api.syn;

import org.springframework.data.domain.Pageable;

import com.tempus.gss.product.hol.api.contant.QueryProperty;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;

public interface IHolMongoQuery {
	public <T> TCResponse<T> findByProperty(QueryProperty queryProperty, Pageable pageable, Class<T> clazz);
}
