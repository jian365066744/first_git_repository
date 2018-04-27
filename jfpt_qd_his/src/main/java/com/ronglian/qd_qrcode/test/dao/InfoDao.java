/**
 * 
 */
package com.ronglian.qd_qrcode.test.dao;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Query(value="select * from info where status<>1 order by id asc limit 0,1 for update",nativeQuery=true)
	public Info findByStatus();
	
	@Transactional
	@Modifying
	@Query(value="update info set status='1' where status='0' and id= ?1 ",nativeQuery=true)
	public void updateStatus(int id);
}
