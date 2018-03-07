/**
 * 
 */
package com.ronglian.qd_qrcode.test.dao;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ronglian.qd_qrcode.test.entity.Info;
import com.ronglian.qd_qrcode.test.entity.UserInfo;

/** 
* @author zhangjian 
* @version JDK1.8
* 创建时间：2017年12月2日 下午4:59:22 
* 类说明 :
*/

public interface InfoDao extends JpaRepository<Info, Serializable>,JpaSpecificationExecutor<Info>{

	/** 
	* @author zhangjian
	* @version JDK1.8
	* 创建时间：2017年12月2日 下午4:59:22 
	* 方法说明 :
	*/
	public Info findById(int id);
	public Info findByAge(String age);
}
