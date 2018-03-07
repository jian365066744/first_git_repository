package com.ronglian.qd_qrcode.common.utils.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisCluster;

/**
 * Created by admin on 2017/12/28.
 */
@Service
public class RedisClusterClient implements InitializingBean, DisposableBean, CacheService {
    private static Logger logger = LoggerFactory.getLogger(RedisClusterClient.class);

	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.common.utils.cache.CacheService#get(java.lang.String)
	 */
    
    @Autowired
    private JedisCluster jedisCluster;
	@Override
	public <T> T get(String key) {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2018年1月2日 上午10:55:34 
		* 方法说明 :
		*/
		try {
			return (T)jedisCluster.get(key);
		} catch (Exception e) {
			logger.error("拿出缓存出错！",e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.common.utils.cache.CacheService#getBulk(java.lang.String[])
	 */
	@Override
	public <T> Map<String, T> getBulk(String... keys) {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2018年1月2日 上午10:55:34 
		* 方法说明 :
		*/
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.common.utils.cache.CacheService#getBulk(java.util.Collection)
	 */
	@Override
	public <T> Map<String, T> getBulk(Collection<String> keys) {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2018年1月2日 上午10:55:34 
		* 方法说明 :
		*/
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.common.utils.cache.CacheService#set(java.lang.String, int, java.lang.Object)
	 */
	@Override
	public boolean set(String key, int expiredTime, Object value) {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2018年1月2日 上午10:55:34 
		* 方法说明 :
		*/
		boolean flag = false;
//		byte[] keyByte = this.toByteArray(key);
//		byte[] valueByte = this.toByteArray(value);
		try {
			if (expiredTime > 0 ) {
				jedisCluster.setex(SerializationUtils.serialize(key), expiredTime, SerializationUtils.serialize((Serializable)value));
			} else {
				//jedisCluster.set(SerializationUtils.serialize(key), SerializationUtils.serialize((Serializable)value));
				jedisCluster.setex(SerializationUtils.serialize(key),0, SerializationUtils.serialize((Serializable)value));
			}
			flag = true;
		} catch (Exception e) {
			logger.error("存入缓存出错",e);
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.common.utils.cache.CacheService#delete(java.lang.String)
	 */
	@Override
	public boolean delete(String key) {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2018年1月2日 上午10:55:34 
		* 方法说明 :
		*/
		boolean flag = false;
		try {
			jedisCluster.del(key);
			flag = true;
		} catch (Exception e) {
			logger.error("删除缓存出错",e);
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.common.utils.cache.CacheService#flush()
	 */
	@Override
	public boolean flush() {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2018年1月2日 上午10:55:34 
		* 方法说明 :
		*/
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2018年1月2日 上午10:55:34 
		* 方法说明 :
		*/
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2018年1月2日 上午10:55:34 
		* 方法说明 :
		*/
		
	}

    /*@Autowired
    public RedisTemplate<String,String> clusterRedisTemplate;

    private RedisTemplate redisTemplate;

    @Override
    public <T> T get(String key) {
        final String keyf = (String) key;
        Object object;
        object = clusterRedisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {

                byte[] key = keyf.getBytes();
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                return value;
            }
        });
        return (T)object;
    }

    @Override
    public <T> Map<String, T> getBulk(String... keys) {
        return null;
    }

    @Override
    public <T> Map<String, T> getBulk(Collection<String> keys) {
        return null;
    }

    @Override
    public boolean set(String key, int expiredTime, Object value) {
        if(null == value) {
            return false;
        }

        if(value instanceof String) {
            if(StringUtils.isEmpty(value.toString())) {
                return false;
            }
        }

        // TODO Auto-generated method stub
        final String keyf = key + "";
        final Object valuef = value;
        final long liveTime = 86400;

        clusterRedisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyb = keyf.getBytes();
                byte[] valueb = toByteArray(valuef);
                connection.set(keyb, valueb);
                if (liveTime > 0) {
                    connection.expire(keyb, liveTime);
                }
                return 1L;
            }
        });
        return true;
    }

    @Override
    public boolean delete(String key) {
        boolean isSuccess = false;
        try {
            redisTemplate.delete(key);
            isSuccess = true;
        } catch (Exception e) {
            logger.debug("删除缓存失败",e);
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean flush() {
        return false;
    }

    @Override
    public void destroy() throws Exception {
        redisTemplate.getConnectionFactory().getClusterConnection().shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        redisTemplate = SpringContextHolder.getBean("clusterRedisTemplate");
    }

    // 获取数据
    public Object get(Object key) {
        final String keyf = (String) key;
        Object object;
        object = clusterRedisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {

                byte[] key = keyf.getBytes();
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                return toObject(value);

            }
        });

        return object;
    }*/

    /**
     * 描述 : <byte[]转Object>. <br>
     * <p>
     * <使用方法说明>
     * </p>
     *
     * @param bytes
     * @return
     */
    private Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    private byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
}
