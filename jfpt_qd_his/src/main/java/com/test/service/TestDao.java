/**
 * 
 */
package com.test.service;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ronglian.qd_qrcode.test.entity.Info;
import com.test.entity.Test;

/** 
* @author zhangjian 
* @version JDK1.8
* 创建时间：2018年1月19日 下午2:40:22 
* 类说明 :
 * @param <TestDao>
*/
public interface TestDao<TestDao> extends JpaRepository<Test, Serializable>,JpaSpecificationExecutor<Info>{

	/** 
	* @author zhangjian
	* @version JDK1.8
	* 创建时间：2018年1月19日 下午2:40:22 
	* 方法说明 :
	*/
	
}
