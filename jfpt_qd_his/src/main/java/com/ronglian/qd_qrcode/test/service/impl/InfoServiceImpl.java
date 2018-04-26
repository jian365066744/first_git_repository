/**
 * 
 */
package com.ronglian.qd_qrcode.test.service.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.AopProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ronglian.qd_qrcode.common.utils.EbringJdbc;
import com.ronglian.qd_qrcode.test.dao.InfoDao;
import com.ronglian.qd_qrcode.test.entity.Info;
import com.ronglian.qd_qrcode.test.service.InfoService;

/** 
* @author zhangjian 
* @version JDK1.8
* 创建时间：2017年12月2日 下午5:04:48 
* 类说明 :
*/
@Service
public class InfoServiceImpl implements InfoService{

	@Autowired
	private InfoDao infoDao;
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private EbringJdbc jdbc;
	
//	private InfoService proxy = new InfoServiceImpl();
	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.test.service.InfoService#save(com.ronglian.qd_qrcode.test.entity.Info)
	 */
	@Override
	public void save(Info info)  {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2017年12月2日 下午5:05:14 
		* 方法说明 :
		*/
		infoDao.save(info);
	}
	

	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.test.service.InfoService#getById(int)
	 */
	@Override
	public Info getById(int id) throws Exception {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2017年12月2日 下午5:05:14 
		* 方法说明 :
		*/
		return infoDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.test.service.InfoService#getByAge(java.lang.String)
	 */
	@Override
	public Info getByAge(String age) throws Exception {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2017年12月2日 下午5:05:14 
		* 方法说明 :
		*/
		return infoDao.findByAge(age);
	}

	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.test.service.InfoService#getAll(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> getAll(String sql) throws Exception {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2018年3月26日 上午11:06:58 
		* 方法说明 :
		*/
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> result = query.getResultList();
		return result;
	}


	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.test.service.InfoService#test1(java.lang.String)
	 */
	@Transactional
	@Override
	public void test1(String age) throws Exception {
//		String sql = "insert into info(id,age,sex) values (1,'2','3')";
//		int i = jdbc.getJdbcTemplate().update(sql);
//		this.test2(age);
//		System.out.println("方法调用的对象为:"+this);
		Info info = new Info();
		info.setId(3);
		info.setSex("0");
		info.setAge(age);
		this.save(info);
		
//		InfoServiceImpl infoService = new InfoServiceImpl();
//		InfoService proxy = (InfoService)Proxy.newProxyInstance(this.getClass().getClassLoader(), this.getClass().getInterfaces(), new InvocationHandler() {
//			@Override
//			public Object invoke(Object arg0, Method method, Object[] arg2) throws Throwable {
//				if (method.getName().startsWith("test")) {
//					System.out.println("调用的test方法为："+method.getName());
//				}
//				return method.invoke(infoService, arg2);
//			}
//			
//		} );
		
		InfoService proxy = (InfoService) AopContext.currentProxy();
		System.out.println("方法调用的代理对象为:"+proxy.getClass().getName());
		System.out.println("方法调用的实体对象为:"+this.getClass().getName());
//		this.test2("987");
		proxy.test2("987");
	}


	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.test.service.InfoService#test2(java.lang.String)
	 */
	@Transactional
	@Override
	public void test2(String name) {
		try {
			Info info = new Info();
			info.setId(4);
			info.setSex("0");
			info.setAge(name);
			save(info);
			System.out.println("保存了test2");
//			int j = 1/0;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
