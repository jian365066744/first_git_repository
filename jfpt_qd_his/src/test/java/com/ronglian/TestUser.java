/**
 * 
 */
package com.ronglian;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ronglian.qd_qrcode.common.utils.AppUtils;
import com.ronglian.qd_qrcode.common.utils.cache.CacheUtils;
import com.ronglian.qd_qrcode.common.utils.cache.RedisClusterClient;
import com.ronglian.qd_qrcode.common.utils.cache.RedisClusterImpl;
import com.ronglian.qd_qrcode.test.entity.Info;
import com.ronglian.qd_qrcode.test.entity.UserInfo;
import com.ronglian.qd_qrcode.test.service.InfoService;
import com.ronglian.qd_qrcode.test.service.UserInfoService;
import com.test.service.OrdersService;

import ano.CacheResult;
import io.shardingjdbc.core.keygen.DefaultKeyGenerator;

/** 
* @author 作者 zhangjian: 
* @version 创建时间：2017年11月16日 下午1:50:33 
* 类说明 :
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestUser {

	private Logger logger = LoggerFactory.getLogger(TestUser.class);
	/** 
	* @author 作者 zhangjian: 
	* @version 创建时间：2017年11月16日 下午1:50:33 
	* 方法说明 :
	*/
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private RedisClusterClient redisClusterClient;
	@Autowired
	private RedisClusterImpl redisClusterImpl;
	
	@Autowired
	private OrdersService ordersService;
	
	@Test
	public void testInsertUser () throws Exception {
		Info info = new Info();
		info.setAge("20");
		UserInfo user = new UserInfo();
		user.setId(1234567890123L);
		user.setName("o121");
		//user.setInfo(info);
		try {
			userInfoService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMSUser () {
		List<UserInfo> users;
		List<Info> infos;
		String name = "123";
		try {
			//users = userInfoService.findAll();
			users = userInfoService.findBySql(name);
			System.out.println("取出对象的个数为："+users.size());
			if (null != users && users.size() > 0) {
				for (UserInfo user:users) {
					System.out.println("=================");
					System.out.println(user.getId());
					System.out.println(user.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetCache() {
		String testStr1 = "test";
		int testStr2 = 123;
		CacheUtils.set("test1", 1000,testStr1);
		CacheUtils.set("test2", 1000,testStr2);
	}
	
	@Test
	public void testGetCache1() {
		Integer testStr2 = CacheUtils.get("test2");
		//testStr1 = null;
		/*try {
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		String testStr1 = CacheUtils.get("test1");
		//Integer strtest = Integer.valueOf(testStr1);
		System.out.println("取出缓存的值为："+testStr1);
//		testStr1 = null;
//		System.out.println(testStr1.length());
		//jmsTemplate.convertAndSend(testStr1);
		System.out.println("取出缓存的值为："+testStr1);
	}
	
	@Test
	public void sendObject() {
		//测试jms跨项目传输对象
				final UserInfo user = new UserInfo();
				user.setId(Long.valueOf(1234567890));
				user.setName("测试对象");
				//jmsTemplate.convertAndSend("qd_queue_update_cache", "测试String");
				jmsTemplate.send("qd_queue_check_caches",new MessageCreator() {
					public Message createMessage(Session session) throws JMSException {
						return session.createObjectMessage(user);
					}
				});
	}
	
	@Test
	public void testqune() {
		jmsTemplate.convertAndSend("qd_queue_update_cache", "qd_front_counter_"+"c0120160303112637323");
	}
	
	@Test
	public void getKey() {
		DefaultKeyGenerator dkg = new DefaultKeyGenerator();
		for(int i = 0 ;i<100;i++) {
			Number generateKey = dkg.generateKey();
			System.out.println("得到的生成器key值为：" + generateKey);
		}
	}
	
	@Test
	public void testGetCache() {
		String value = CacheUtils.get("test0");
		System.out.println("测试拿出的值为："+value);
	}
	
	@Test
	public void testSetCache1() {
		String key = "testCluster";
		String value = "ronglian";
		try {
			CacheUtils.set(key, 0,value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("缓存存入完毕！");
	}
	
	@Test
	public void testGetRedisImpl() {
		String key = "test";
		Object value = redisClusterImpl.get(key);
		System.out.println("缓存中取出的value值为："+value);
	}
	
	@Test
	public void testSetRedisImpl() {
		String key = "test";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("map", "mapmap");
		try {
			String value = AppUtils.Map2json(map);
//			String value = "123";
			redisClusterImpl.set(key, value);
			System.out.println("缓存中存入的value值为："+value);
			logger.info("存入缓存成功！");
		} catch (Exception e) {
			logger.info("存入缓存出错");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testfk() {
		List<Map<String,Object>> lists = null;
		String sql = "SELECT o.orderId as orderId,o.addtime as addtime from Orders_his as o LEFT JOIN PayStream_his as p on o.status=p.orders_his and o.addtime<'2017-01-01 00:00:00';";
		try {
			lists = ordersService.findHist(sql);
			logger.info("查询数量为:"+lists.size());
		} catch (Exception e) {
			logger.error("联合查询出错",e);
		}
	}
	
	@Test
	public void genKey() {
		DefaultKeyGenerator dkg = new DefaultKeyGenerator();
		Long idKey = (Long) dkg.generateKey();
		logger.info("生成的id为:"+idKey);
		System.out.println("生成的id为:"+idKey);
	}
	
	@Test
	public void testAno() {
		Class<RedisClusterImpl> clazzBo = RedisClusterImpl.class;
		Class<CacheResult> cr = CacheResult.class;
		if (clazzBo.isAnnotationPresent(cr)) {
			CacheResult cacheResult = clazzBo.getAnnotation(cr);
			System.out.println(cacheResult.key());
		}
		
		Method[] methods = clazzBo.getMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(cr)) {
				CacheResult cacheResult = method.getAnnotation(cr);
				System.out.println(cacheResult.key());
			}
		}
	}
	
	public void testGitCommit() {
		System.out.println("first git commit");
	}
}
