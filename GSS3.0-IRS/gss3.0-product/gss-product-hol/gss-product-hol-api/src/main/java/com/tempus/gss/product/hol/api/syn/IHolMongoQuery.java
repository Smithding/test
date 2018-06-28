package com.tempus.gss.product.hol.api.syn;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;

import com.tempus.gss.product.hol.api.contant.QueryProperty;
import com.tempus.gss.product.hol.api.entity.HolMidBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;

public interface IHolMongoQuery {
	public <T> TCResponse<T> findByProperty(QueryProperty queryProperty, Pageable pageable, Class<T> clazz);
	
	public List<HolMidBaseInfo> queryAlikeHol(String lon, String lat, Set<String> phoneList);
}
