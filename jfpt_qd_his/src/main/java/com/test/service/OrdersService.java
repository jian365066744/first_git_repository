/**
 * 
 */
package com.test.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 
* @author zhangjian 
* @version JDK1.8
* 创建时间：2018年1月16日 上午10:41:02 
* 类说明 :
*/
@Service

public class OrdersService {

	@PersistenceContext  
    private EntityManager entityManager;
	@Transactional(value="transactionManager1")
	public List<Map<String, Object>> findHist(String sql) {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2018年1月15日 下午4:34:44 
		* 方法说明 :
		*/
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> result=query.getResultList();
		return result;
	}
}
