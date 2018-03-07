package com.ronglian.qd_qrcode.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ronglian.qd_qrcode.test.dao.UserInfoDao;
import com.ronglian.qd_qrcode.test.entity.UserInfo;
import com.ronglian.qd_qrcode.test.service.UserInfoService;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoDao userinfoDao;
	
	@Override
	public UserInfo findById(Integer id) throws Exception {
		return userinfoDao.findById(id);
	}

	@Override
	public UserInfo save(UserInfo entity) throws Exception{
		return userinfoDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.test.service.UserInfoService#findAll()
	 */
	@Override
	public List<UserInfo> findAll() throws Exception {
		String hql = "from UserInfo where 1=1 ";
		return userinfoDao.findAll();
	}

	/* (non-Javadoc)
	 * @see com.ronglian.qd_qrcode.test.service.UserInfoService#findBySql()
	 */
	@Override
	public List<UserInfo> findBySql(String name) throws Exception {
		return userinfoDao.findByName(name);
	}
}
