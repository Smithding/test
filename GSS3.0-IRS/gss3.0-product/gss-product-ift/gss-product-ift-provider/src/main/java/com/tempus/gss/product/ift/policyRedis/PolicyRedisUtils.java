package com.tempus.gss.product.ift.policyRedis;
import com.tempus.gss.product.ift.api.service.IPolicyRedisUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by 杨威 on 2017/12/5.
 */
@Component
public class PolicyRedisUtils implements IPolicyRedisUtils {
    private static Logger logger = Logger.getLogger(PolicyRedisUtils.class);
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public void set(final String key, final String value,final Long liveTime) {
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.set(redisTemplate.getStringSerializer().serialize(key), redisTemplate.getStringSerializer().serialize(value));
                if (liveTime > 0) {
                    connection.expire(redisTemplate.getStringSerializer().serialize(key), liveTime);
                }
                logger.debug("save key:" + key + ",value:" + value);
                connection.close();
                return null;
            }
        });
    }

    /**
     *
     * 方法描述:通过键得到字符串形式的缓存<br/>
     * 作 者： Administrator<br/>
     * 日 期： 2015-6-4-下午02:42:56<br/>
     *
     * @param key
     * @return <br/>
     *         返回类型： String<br/>
     */
    public String getStr(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] byteKey = redisTemplate.getStringSerializer().serialize(key );
                if (connection.exists(byteKey)) {
                    byte[] byteValue = connection.get(byteKey);
                    String value = redisTemplate.getStringSerializer().deserialize(byteValue);
                    logger.debug("get key:" + key + ",value:" + value);
                    return value;
                }
                logger.error("valus does not exist!,key:" + key);
                connection.close();
                return null;
            }
        });
    }

    /***
     * 根据KEY删除缓存
     * @param key
     */
    public void delete(String key){
        redisTemplate.delete(key);
    }

    public String flushDb() {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.flushDb();
                logger.debug("清除缓存");
                return "清除缓存";
            }
        });
    }

}
