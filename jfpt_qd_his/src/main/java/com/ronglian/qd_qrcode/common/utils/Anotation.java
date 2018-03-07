/**
 * 
 */
package com.ronglian.qd_qrcode.common.utils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import ano.CacheResult;

/** 
* @author zhangjian 
* @version JDK1.8
* 创建时间：2018年3月1日 下午8:31:22 
* 类说明 :
*/
@Service
public class Anotation implements ApplicationContextAware,ApplicationListener {

	private ApplicationContext applicationContext;
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2018年3月1日 下午8:41:26 
		* 方法说明 :
		*/
		Class<CacheResult> cacheResult = CacheResult.class;
		Map<String,Object> anotations = applicationContext.getBeansWithAnnotation(cacheResult);
		Set<Entry<String,Object>> entrySet = anotations.entrySet();
		for (Entry<String,Object> entry : entrySet) {
			String beanId = entry.getKey();
			Class clazz = entry.getValue().getClass();
			System.out.println(clazz.getName());
			
			CacheResult cr = AnnotationUtils.findAnnotation(clazz, cacheResult);
			System.out.println(cr.key());
			
			Method[] methods = entry.getValue().getClass().getMethods();
			for (Method m : methods) {
				CacheResult crm = AnnotationUtils.findAnnotation(m, cacheResult);
				System.out.println(crm.key());
			}
		}
		
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2018年3月1日 下午8:41:26 
		* 方法说明 :
		*/
		this.applicationContext = applicationContext;
	}

	
}
