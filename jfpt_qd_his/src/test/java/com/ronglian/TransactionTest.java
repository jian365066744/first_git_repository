/**
 * 
 */
package com.ronglian;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ronglian.qd_qrcode.test.service.InfoService;

/** 
* @author zhangjian 
* @version JDK1.8
* 创建时间：2018年4月23日 上午9:18:07 
* 类说明 :
*/
public class TransactionTest {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext factory=new ClassPathXmlApplicationContext("applicationContext.xml");
		factory.start();
		InfoService infoService = (InfoService)factory.getBean("infoServiceImpl");
		System.out.println("开始调用的infoServiceImpl为:"+infoService.getClass().getName());
		infoService.test1("1");
		
	}
}
