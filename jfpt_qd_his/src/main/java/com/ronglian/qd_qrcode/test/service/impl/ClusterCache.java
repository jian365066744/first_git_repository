/**
 * 
 */
package com.ronglian.qd_qrcode.test.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/** 
* @author zhangjian 
* @version JDK1.8
* 创建时间：2018年1月2日 上午9:09:15 
* 类说明 :jedis缓存集群
*/
public class ClusterCache {

	private static Logger logger = LoggerFactory.getLogger(ClusterCache.class); 
	/** 
	* @author zhangjian
	* @version JDK1.8
	* 创建时间：2018年1月2日 上午9:09:15 
	* 方法说明 :
	*/
	public static void main (String[] args) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1000);
		config.setMaxIdle(50);
		config.setMinIdle(20);
		config.setMaxWaitMillis(6000);
		config.setTestOnBorrow(true);
		
		//设置Jedis集群节点
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("127.0.0.1",7001));
		nodes.add(new HostAndPort("127.0.0.1",7002));
		nodes.add(new HostAndPort("127.0.0.1",7003));
		nodes.add(new HostAndPort("127.0.0.1",7004));
		nodes.add(new HostAndPort("127.0.0.1",7005));
		nodes.add(new HostAndPort("127.0.0.1",7006));
		
		//根据节点创建链接对象
		JedisCluster jedisCluster = new JedisCluster(nodes,2000,100,config);
		String key = "test";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("testCluster", "ronglian");
		jedisCluster.setnx(SerializationUtils.serialize(key), SerializationUtils.serialize((Serializable)map));
		jedisCluster.setnx(SerializationUtils.serialize(key),  SerializationUtils.serialize((Serializable)"cscscs"));
//		Object value = jedisCluster.get(key);
//		System.out.println("从缓存中拿出的值为："+ value);
		//往缓存中存放100对象
//		int max = 100;
//		for (int i=0;i<max;i++) {
//			String key = "test"+i;
//			String value = "jian"+i;
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("testCluster", "ronglian");
//			jedisCluster.set(key, value);
//			logger.info("存入缓存的对象键值对为key："+key+",value:"+value);
//		}
		//从缓存中取存入的对象
//		int max = 100;
//		for (int i=0;i<max;i++) {
//			String key = "test"+i;
//			String value = jedisCluster.get(key);
//			logger.info("从缓存中取出对象的值为："+value);
//		}
	}
}
