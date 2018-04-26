/**
 * 
 */
package com.ronglian.qd_qrcode.test.service;

import java.util.List;
import java.util.Map;

import com.ronglian.qd_qrcode.test.entity.Info;

/** 
* @author zhangjian 
* @version JDK1.8
* 创建时间：2017年12月2日 下午5:01:54 
* 类说明 :
*/
public interface InfoService {

	public void save(Info info) ;
	public Info getById(int id) throws Exception;
	public Info getByAge(String age) throws Exception;
	public List<Map<String,Object>> getAll(String sql) throws Exception;
	
	public void test1(String age) throws Exception;
	public void test2(String name) ;
	
}
