package com.ronglian.qd_qrcode.common.utils.cache;

import java.util.Collection;
import java.util.Map;
/*
 * Copyright (c) ebring All rights reserved.
 * @author xiaojun
 * @version 1.0
 * Date: 2011-9-29
 *  缓存接口 不同的缓存实现需要实现此接口
*/
public interface CacheService {

	/**
	 * 根据key值在缓存中取值
	 * @param <T>
	 * @param key
	 * @return
	 */
	public <T> T get(String key);
	
	/**
	 * 根据多个keys值在缓存中取得多个值
	 * @param <T>
	 * @param keys
	 * @return
	 */
	public <T> Map<String, T> getBulk(String... keys);
	
	/**
	 * 根据多个keys值在缓存中取得多个值
	 * @param <T>
	 * @param keys
	 * @return
	 */
	public <T> Map<String, T> getBulk(Collection<String> keys);
	
	/**
	 * 向缓存中存放值
	 * @param key     缓存标识
	 * @param expiredTime  失效时间 以秒为单位，设置为0时，标识永久有效
	 * @param value   缓存值
	 * @return        存放是否成功
	 */
	public boolean set(String key, int expiredTime, Object value);
	
	/**
	 * 从缓存中删除key值
	 * @param key
	 * @return
	 */
	public boolean delete(String key);
	
	/**
	 * 清空缓存
	 * @return
	 */
	public boolean flush();
}
