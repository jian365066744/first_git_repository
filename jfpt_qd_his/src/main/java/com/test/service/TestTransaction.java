/**
 * 
 */
package com.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ronglian.qd_qrcode.test.service.InfoService;

/** 
* @author zhangjian 
* @version JDK1.8
* 创建时间：2018年4月24日 下午5:29:05 
* 类说明 :
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@Transactional(transactionManager = "transactionManager")
@Rollback(value=false)
public class TestTransaction {

	/** 
	* @author zhangjian
	* @version JDK1.8
	* 创建时间：2018年4月24日 下午5:29:05 
	* 方法说明 :
	*/
	
	@Autowired
	private InfoService infoService;
	@Test
	public void testTransaction() throws Exception {
		infoService.test1("1");
	}
}
