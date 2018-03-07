package com.ronglian.qd_qrcode.common.utils.cache;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.modules.nosql.redis.JedisTemplate;
import org.modules.utils.spring.SpringContextHolder;

/**
 * 对SpyRedis Client的二次封装,提供常用的Get/GetBulk/Set/Delete/Incr/Decr函数的封装.
 * 
 * 未提供封装的函数可直接调用getClient()取出Spy的原版RedisClient来使用.
 */
public class redisCachedClient implements InitializingBean, DisposableBean, CacheService{

	private static Logger logger = LoggerFactory.getLogger(redisCachedClient.class);

	private JedisTemplate jedisTemplate;

	// 初始,关闭函数 //
	@Override
	public void afterPropertiesSet() throws Exception {
		jedisTemplate = SpringContextHolder.getBean("jedisTemplate");
	}

	@Override
	public void destroy() throws Exception {
		jedisTemplate.getJedisPool().destroy();
	}

	// Redis访问函数  //

	public JedisTemplate getJedisTemplate() {
		return jedisTemplate;
	}

	/**
	 * Get方法, 转换结果类型并屏蔽异常,仅返回Null.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		try {
			return (T) jedisTemplate.getAsObject(key);
		} catch (RuntimeException e) {
			logger.warn("Get from redis server fail,key is " + key, e);
			return null;
		}
	}

	/**
	 * Set方法.
	 */
	public boolean set(String key, int expiredTime, Object value) {
		boolean isSuccess = false;
		try {
			if( expiredTime>0 ){
				jedisTemplate.setex(key, value, expiredTime);
			} else {
				jedisTemplate.set(key, value);
			}
			
			isSuccess = true;
		} catch (Exception e) {
			logger.error("向缓存中set值错误" + key, e);
		}
		return isSuccess;
	}

	/**
	 * Delete方法.	 
	 */
	public boolean delete(String key) {
		boolean isSuccess = false;
		try {
			isSuccess = jedisTemplate.delete(key);
		} catch (Exception e) {
			logger.error("删除缓存错误: key:" + key, e);
		}
		return isSuccess;
	}
	
	/**
	 * Delete方法.	 
	 */
	public boolean flush() {
		boolean isSuccess = false;
		try {
			jedisTemplate.flushDB();
			isSuccess = true;
		} catch (Exception e) {
			logger.error("清空缓存错误", e);
		}
		return isSuccess;
	}

	@Override
	public <T> Map<String, T> getBulk(String... keys) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Map<String, T> getBulk(Collection<String> keys) {
		
		// TODO Auto-generated method stub
		return null;
	}
}