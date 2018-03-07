package com.ronglian.qd_qrcode.common.utils.cache;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modules.utils.spring.SpringContextHolder;

/*
 *  缓存工具类
*/
public class CacheUtils {

	private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);
	
	private static CacheService cacheService = null;
	
	static{
		if(null == cacheService){
			try{
				cacheService = (CacheService)SpringContextHolder.getBean("cacheServiceImpl");
			}catch(Exception e){
				logger.error("获取缓存存取服务错误");
			}
		}
	}
	
	/**
	 * 向缓存中存放值，默认为永久有效
	 * @param key     缓存标识
	 * @param value   缓存值
	 * @return        存放是否成功
	 */
	public static boolean set(String key, Object value){
		if(null == cacheService){
			return false;
		}
		return cacheService.set(key, 0, value);
	}
	
	/**
	 * 向缓存中存放值
	 * @param key     缓存标识
	 * @param expiredTime  失效时间 以秒为单位，设置为0时，标识永久有效
	 * @param value   缓存值
	 * @return        存放是否成功
	 */
	public static boolean set(String key, int expiredTime ,Object value){
		if(null == cacheService){
			return false;
		}
		return cacheService.set(key, expiredTime, value);
	}
	
	/**
	 * 根据key值在缓存中取值
	 * @param <T>
	 * @param key
	 * @return
	 */
	public static <T> T get(String key){
		if(null == cacheService){
			return null;
		}
		return (T)cacheService.get(key);
	}
	
	/**
	 * 根据多个keys值在缓存中取得多个值
	 * @param <T>
	 * @param keys
	 * @return
	 */
	public static <T> Map<String, T> getBulk(String... keys){
		if(null == cacheService){
			return null;
		}
		return cacheService.getBulk(keys);
	}
	
	/**
	 * 根据多个keys值在缓存中取得多个值
	 * @param <T>
	 * @param keys
	 * @return
	 */
	public static <T> Map<String, T> getBulk(Collection<String> keys){
		if(null == cacheService){
			return null;
		}
		return cacheService.getBulk(keys);
	}
	
	/**
	 * 从缓存中删除key值
	 * @param key
	 * @return
	 */
	public static boolean deleteKey(String key){
		if(null == cacheService){
			return false;
		}
		return cacheService.delete(key);
	}
	
	/**
	 * 清空缓存
	 * @return
	 */
	public static boolean flush(){
		if(null == cacheService){
			return false;
		}
		return cacheService.flush();
	}
	
}
