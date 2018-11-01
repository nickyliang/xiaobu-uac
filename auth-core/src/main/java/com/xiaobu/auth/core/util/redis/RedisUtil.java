package com.xiaobu.auth.core.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作工具类
 *
 * @author qichao
 * @create 2018-10-31
 **/
@Component
public class RedisUtil {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 批量删除对应的value
	 *
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 *
	 * @param pattern
	 */
	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 *
	 * @param key
	 */
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 *
	 * @param key
	 *
	 * @return
	 */
	public boolean exists(final String key) {
//		logger.info(redisTemplate.getValueSerializer().getClass().getName().toString());
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取
	 *
	 * @param key
	 *
	 * @return
	 */
	public String get(final String key) {
		Object result = null;
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		if (result == null) {
			return null;
		}
		return result.toString();
	}

	/**
	 * 写入
	 *
	 * @param key
	 * @param value
	 *
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 *
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 向一张hash表中放值
	 * @param key 键
	 * @param value 对应多个键值
	 * @return
	 */
	public boolean hmset(String key, Map<String, String> value) {
		boolean result = false;
		try {
			redisTemplate.opsForHash().putAll(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取hashKey对应的所有键值
	 * @param key 键
	 * @return 对应的多个键值
	 */
	public Map<String, String> hmget(String key) {
		Map<String, String> result = null;
		try {
			result = redisTemplate.opsForHash().entries(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
