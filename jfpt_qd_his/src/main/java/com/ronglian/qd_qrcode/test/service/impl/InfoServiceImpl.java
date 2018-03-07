/**
 * 
 */
package com.ronglian.qd_qrcode.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.test.service.InfoService#save(com.ronglian.qd_qrcode.test.entity.Info)
	 */
	@Override
	public void save(Info info) throws Exception {
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

	/** 
	* @author zhangjian
	* @version JDK1.8
	* 创建时间：2017年12月2日 下午5:04:48 
	* 方法说明 :
	*/
}
