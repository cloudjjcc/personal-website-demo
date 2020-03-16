package site.nullpointer.utils;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
/**
 * redis 操作工具类
 * @author cloud-c
 *
 */
@Component
public class RedisUtils {
//	@Autowired
//	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 根据key获取对象
	 * @param <T>
	 * 
	 * @param key
	 * @return
	 */
	public <T> T getObject(String key,Class<T> clazz) {
		String jsonStr = stringRedisTemplate.opsForValue().get(key);
		return StringUtils.isNotEmpty(jsonStr)?JSON.parseObject(jsonStr, clazz):null;
	}

	/**
	 * 存放对象
	 * 
	 * @param key
	 * @param value
	 */
	public void setObject(String key, Object value) {
		stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value));
	}

	/**
	 * 存放对象（设置过期时间）
	 * 
	 * @param key
	 * @param value
	 * @param time
	 */
	public void setObject(String key, Object value, long time) {
		stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value), time, TimeUnit.SECONDS);
	}

	/**
	 * 存放字符串
	 * 
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 获取字符串
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	/**
	 * 存放字符串（设置过期时间）
	 * 
	 * @param key
	 * @param value
	 * @param time
	 */
	public void setString(String key, String value, long time) {
		stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
	}
	/**
	 * 删除对象
	 * @param key
	 */
	public void delete(String key) {
		stringRedisTemplate.delete(key);
	}
}
