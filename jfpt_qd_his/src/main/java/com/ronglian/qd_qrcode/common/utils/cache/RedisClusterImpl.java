/**
 * 
 */
package com.ronglian.qd_qrcode.common.utils.cache;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.modules.nosql.redis.JedisTemplate;
import org.modules.nosql.redis.JedisTemplate.JedisAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ano.CacheResult;
import redis.clients.jedis.BinaryJedisCluster;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClusterCommand;
import redis.clients.jedis.JedisCommands;

/** 
* @author zhangjian 
* @version JDK1.8
* 创建时间：2018年1月6日 下午4:45:06 
* 类说明 :对redis集群使用的简单封装
*/
@CacheResult(key="c")
public class RedisClusterImpl extends BinaryJedisCluster {

	/**
	 * @param jedisClusterNode
	 * @param timeout
	 * @param maxAttempts
	 * @param poolConfig
	 */
	public RedisClusterImpl(Set<HostAndPort> jedisClusterNode, int timeout, int maxAttempts,
			GenericObjectPoolConfig poolConfig) {
		super(jedisClusterNode, timeout, maxAttempts, poolConfig);
		// TODO Auto-generated constructor stub
	}

	  private static Logger logger = LoggerFactory.getLogger(RedisClusterImpl.class);
	
	  /**
	   * 增加，更改
	   * @param key
	   * @param value
	   * @return
	   */
	  @CacheResult(key="m")
	  public String set(final String key,final Object value) {
		    return new JedisClusterCommand<String>(connectionHandler, maxAttempts) {
		      @Override
		      public String execute(Jedis jedis ) {
		        return jedis.set(SerializationUtils.serialize(key), SerializationUtils.serialize((Serializable)value));
		      }
		    }.runBinary(SerializationUtils.serialize(key));
		  }
	  
	  /**
	   * 查询
	   * @param key
	   * @return
	   */
	  public Object get(final String key) {
		    return SerializationUtils.deserialize(new JedisClusterCommand<byte[]>(connectionHandler, maxAttempts) {
		      @Override
		      public byte[] execute(Jedis connection) {
		        return connection.get(SerializationUtils.serialize(key));
		      }
		    }.runBinary(SerializationUtils.serialize(key)));
		  }
	  /**
	   * 删除操作
	   * @param key
	   * @return
	   */
	  public boolean del(final String key) {
		  return new JedisClusterCommand<Long>(connectionHandler, maxAttempts) {
		      @Override
		      public Long execute(Jedis connection) {
		        return connection.del((SerializationUtils.serialize(key)));
		      }
		    }.runBinary(SerializationUtils.serialize(key)) == 1 ? true : false;
		  }
}
