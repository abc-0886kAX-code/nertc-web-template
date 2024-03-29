package com.ytxd.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component("RedisUtils")
public class RedisUtils {
	@Autowired
	private RedisTemplate<String, String> redisTemplate1;
	private static RedisTemplate<String, String> redisTemplate;

	@PostConstruct
	public void init() {
		redisTemplate = this.redisTemplate1;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	@Autowired(required = false)
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		RedisSerializer stringSerializer = new StringRedisSerializer();// 序列化为String
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);// 序列化为Json
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		this.redisTemplate = redisTemplate;
	}
	
	/**
	 * 人为设置redis是否启用
	 * @return
	 */
	public static boolean isRedis() {
		return true;
	}
	
	/**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
	public static boolean expire(String key, long time) {
		try {
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
	public static long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
	public static boolean hasKey(String key) {
		if(!isRedis()) {
			return false;
		}
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
	@SuppressWarnings("unchecked")
	public static void delete(String... key) {
		if(!isRedis()) {
			return;
		}
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}
    

	/**
	 * 读取
	 *
	 * @param key
	 * @return
	 */
	public static String get(final String key) {
		if (isRedis() && redisTemplate.hasKey(key)) {
			return redisTemplate.opsForValue().get(key);
		} else {
			return "";
		}
	}

	/**
	 * 写入缓存
	 */
	public static boolean set(final String key, String value) {
		if(!isRedis()) {
			return true;
		}
		try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}

	/**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
	public static boolean set(String key, String value, long time) {
		if(!isRedis()) {
			return true;
		}
		try {
			if (time > 0) {
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除缓存
	 */
	public static boolean delete(final String key) {
		if(!isRedis()) {
			return true;
		}
		try {
			redisTemplate.delete(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	
	public static void setMap(String name, Map<String, Object> map) {
		redisTemplate.opsForHash().putAll(name, map);
	}

    public static boolean setMap(String key, Map<String,Object> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if(time>0){
                expire(key, time);
            }            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	public static Map<Object, Object> getMap(String name) {
		if (redisTemplate.hasKey(name)) {
			return redisTemplate.opsForHash().entries(name);
		} else {
			return new HashMap<Object, Object>();
		}
	}

	public static Long setList(String name, List<String> list) {
		return redisTemplate.opsForList().rightPushAll(name, list);
	}

	public List<String> getList(String name) {
		if (redisTemplate.hasKey(name)) {
			return (List<String>) redisTemplate.opsForList().range(name, 0, -1);
		} else {
			return new ArrayList<String>(0);
		}
	}

	/*public static boolean isRedisOK() {
		RedisConnectionFactory exposeConnection = redisTemplate.getConnectionFactory();
		try {
			RedisConnection connection = exposeConnection.getConnection();
		} catch (Exception e) {
			return false;
		}
		return true;
	}*/
	public static long incr(String key, long delta) {
		if(!isRedis()) {
			return 0;
		}
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		if(!redisTemplate.hasKey(key)) {
			redisTemplate.opsForValue().set(key, delta + "");
			return delta;
		} else {
			return redisTemplate.opsForValue().increment(key, delta);
		}
	}
	public static long incr(String key) {
		return incr(key, 1);
	}
}
