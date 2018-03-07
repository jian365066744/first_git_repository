
package com.ronglian.qd_qrcode.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试处理类
 * @author   
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Component
public class TestDispatcher{
	
	private static final Logger logger = LoggerFactory.getLogger(TestDispatcher.class);
	
	public void onMessage(Object object) {
		try {
			logger.info("接收到消息！");
			Integer o = (int)object;
			logger.info("打印消息："+object);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("消息处理错误！", e);
		}
	}
	
	public void onMessageObject(Object o) {
			logger.info("跨项目受到对象！");
			logger.info("对象是："+o);
		
	}
}

