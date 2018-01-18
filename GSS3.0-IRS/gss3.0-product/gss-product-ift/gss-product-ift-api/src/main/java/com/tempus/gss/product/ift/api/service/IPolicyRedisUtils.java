package com.tempus.gss.product.ift.api.service;

/**
 * Created by 杨威 on 2017/12/11.
 */
public interface IPolicyRedisUtils {
    void set(final String key, final String value,final Long liveTime);
    String getStr(final String key);
    void delete(String key);
    String flushDb();
}
